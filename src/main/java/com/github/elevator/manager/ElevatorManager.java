package main.java.com.github.elevator.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import main.java.com.github.elevator.component.constants.Constants;
import main.java.com.github.elevator.component.environment.Elevator;
import main.java.com.github.elevator.component.environment.Floor;
import main.java.com.github.elevator.component.external.DirectionButtonImpl;
import main.java.com.github.elevator.config.LoggerConfig;
import main.java.com.github.elevator.enums.DoorState;
import main.java.com.github.elevator.enums.ElevatorDirection;

public class ElevatorManager {
    private static final Logger logger = LoggerConfig.getLogger(ElevatorManager.class.getName());
    private static volatile ElevatorManager INSTANCE;
    private static final Object MUTEX = new Object();

    private final Map<Integer, List<Elevator>> elevatorPositionMap;
    private final List<Floor> floorList;

    public ElevatorManager(int elevatorCount, int floorCount) throws IllegalArgumentException {
        if (elevatorCount <= 0 || elevatorCount > Constants.MAX_ELEVATOR_COUNT) {
            String errorMsg = "Invalid number of elevators specified: " + elevatorCount + ". Value must be between 1 and " + Constants.MAX_ELEVATOR_COUNT;
            logger.severe(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        if (floorCount <= 0 || floorCount > Constants.MAX_FLOOR_COUNT) {
            String errorMsg = "Invalid number of floors specified: " + floorCount + ". Value must be between 1 and " + Constants.MAX_FLOOR_COUNT;
            logger.severe(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        // The key is the "bucket" of floors, the value is a map of directions with a list of elevators currently in the area
        // This will help with dispatching determinations. Every 5 floors is a "bucket"
        elevatorPositionMap = new HashMap<>();
        int buckets = (floorCount % 5 == 0) ? floorCount / 5 : (floorCount / 5) + 1;
        for (int i = 0; i < buckets; i++) {
            elevatorPositionMap.put(i, new ArrayList<>());
        }

        // Generate the list of elevator objects
        for (int i = 1; i <= elevatorCount; i++) {
            // Create elevator objects and add them to the bottom floor bucket by default
            Elevator elevator = new Elevator(floorCount, i);
            elevatorPositionMap.get(0).add(elevator);
        }

        floorList = new ArrayList<>(); {
            // Generate the list of floor objects
            for (int i = 1; i <= floorCount; i++) {
                floorList.add(new Floor(i));
            }
        }
    }

    public static ElevatorManager getInstance() {
        // Quick method to get already instantiated ElevatorManager
        return getInstance(1, 1);
    }

    public static ElevatorManager getInstance(int elevatorCount, int floorCount) {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        // Double-Check Locking, make new instance
        synchronized (MUTEX) {
            if (INSTANCE == null) {
                INSTANCE = new ElevatorManager(elevatorCount, floorCount);
            }
            return INSTANCE;
        }
    }

    public Elevator callElevator(ElevatorDirection direction, int floorNumber) throws Exception {
        // Checks incoming call request and retrieves an elevator from dispatch
        String eventMsg = "Service request from floor " + floorNumber + " going " + direction.name().toLowerCase();
        logger.info(eventMsg);
        AuditManager.getInstance().auditEvent(eventMsg);

        int topFloor = floorList.size();
        if (floorNumber <= 0 || floorNumber > topFloor) {
            String errorMsg = "Invalid floor number specified: " + floorNumber + ". Value must be between 1 and " + topFloor;
            logger.severe(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        
        Floor requestOriginFloor = floorList.get(floorNumber);
        DirectionButtonImpl requestButton;
        if (direction == ElevatorDirection.DOWN) {
            requestButton = requestOriginFloor.getExternalPanel().getDownButton();
        }
        else {
            requestButton = requestOriginFloor.getExternalPanel().getUpButton();
        }
        
        requestButton.setPressed();
        Elevator elevator = dispatchElevator(floorNumber);
        // Current floor and direction updates
        elevator.setCurrentFloor(floorNumber);
        elevator.setCurrentDirection(ElevatorDirection.STOPPED);
        elevator.getDisplay().setCurrentFloor(floorNumber);
        elevator.getDisplay().setDirection(direction);

        // Open doors upon arrival and turn off external floor call button press light
        requestOriginFloor.getDoors().setDoorState(DoorState.OPEN);
        elevator.getDoors().setDoorState(DoorState.OPEN);
        requestButton.setPressed();

        logger.info("Elevator " + elevator.getId() + " dispatched to request on floor " + floorNumber);

        return elevator;
    }

    private Elevator dispatchElevator(int floorNumber) throws Exception {
        int topFloor = floorList.size();
        if (floorNumber <= 0 || floorNumber > topFloor) {
            String errorMsg = "Invalid floor number specified: " + floorNumber + ". Value must be between 1 and " + topFloor;
            logger.severe(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        /* Attempt to get the nearest elevator by bucket.
         * If none is found, walk adjacent buckets.
         */
        int bucket = floorNumber / 5;
        Elevator elevator = null;
        // Check the immediate bucket first
        List<Elevator> matchingElevatorList = elevatorPositionMap.get(bucket);
        int shortestTravelDistance = Constants.MAX_FLOOR_COUNT;
        for (Elevator potentialElevator: matchingElevatorList) {
            int travelDistance = Math.abs(potentialElevator.getCurrentFloor() - floorNumber);
            if (travelDistance < shortestTravelDistance) {
                elevator = potentialElevator;
            }
        }

        int offset = 0;
        int bucketsChecked = 0;
        while (elevator == null && bucketsChecked < elevatorPositionMap.size()) {
            // Expand search outward by 1
            offset++;
            
            int bucketAbove = bucket + offset;
            if (elevatorPositionMap.containsKey(bucketAbove)) {
                List<Elevator> adjacentElevatorList = elevatorPositionMap.get(bucketAbove);
                for (Elevator potentialElevator: adjacentElevatorList) {
                    int travelDistance = Math.abs(potentialElevator.getCurrentFloor() - floorNumber);
                    if (travelDistance < shortestTravelDistance) {
                        elevator = potentialElevator;
                    }
                }

                // Adjacent bucket check found a valid elevator, return
                if (elevator != null) {
                    adjacentElevatorList.remove(elevator);
                    elevatorPositionMap.get(bucket).add(elevator);
                    return elevator;
                }
            }

            int bucketBelow = bucket - offset;
            if (elevatorPositionMap.containsKey(bucketBelow)) {
                List<Elevator> adjacentElevatorList = elevatorPositionMap.get(bucketBelow);
                for (Elevator potentialElevator: adjacentElevatorList) {
                    int travelDistance = Math.abs(potentialElevator.getCurrentFloor() - floorNumber);
                    if (travelDistance < shortestTravelDistance) {
                        elevator = potentialElevator;
                    }
                }

                // Adjacent bucket check found a valid elevator, return
                if (elevator != null) {
                    adjacentElevatorList.remove(elevator);
                    elevatorPositionMap.get(bucket).add(elevator);
                    return elevator;
                }
            }
            // Failsafe exit. Shouldn't get tripped, error path.
            bucketsChecked++;
        }

        if (elevator == null) {
            String errorMsg = "Unable to dispatch elevator.";
            logger.severe(errorMsg);
            throw new Exception(errorMsg);
        }

        return elevator;
    }

    public void travel(Elevator elevator) {
        // Completes the travel portion of the request, after user has pressed a floor button
        String eventMsg = "Elevator " + elevator.getId() + " in transit from floor " + elevator.getCurrentFloor() + " to " + elevator.getDestinationFloor();
        logger.info(eventMsg);
        AuditManager.getInstance().auditEvent(eventMsg);

        // Update current floor info and display
        int departureFloor = elevator.getCurrentFloor();
        int destinationFloor = elevator.getDestinationFloor();
        elevator.setCurrentFloor(destinationFloor);
        elevator.getDisplay().setCurrentFloor(destinationFloor);

        // Update doors, status, and button light
        elevator.setCurrentDirection(ElevatorDirection.STOPPED);
        elevator.getInternalPanel().getFloorButtonList().get(destinationFloor).setPressed();
        elevator.getDoors().setDoorState(DoorState.OPEN);
        floorList.get(destinationFloor).getDoors().setDoorState(DoorState.OPEN);
        
        // Update the bucket mapping with elevator's new position
        int departureBucket = departureFloor / 5;
        elevatorPositionMap.get(departureBucket).remove(elevator);
        int destinationBucket = destinationFloor / 5;
        elevatorPositionMap.get(destinationBucket).add(elevator);

        eventMsg = "Elevator " + elevator.getId() + " has completed its trip and is now idle.";
        logger.info(eventMsg);
    }

    public List<Floor> getFloorList() {
        return floorList;
    }

    public Map<Integer, List<Elevator>> getElevatorPositionMap() {
        return elevatorPositionMap;
    }
}
