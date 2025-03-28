package main.java.com.github.elevator.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import main.java.com.github.elevator.component.constants.Constants;
import main.java.com.github.elevator.component.environment.Elevator;
import main.java.com.github.elevator.component.environment.Floor;
import main.java.com.github.elevator.enums.ElevatorDirection;

public class ElevatorManager {
    private static final Logger logger = Logger.getLogger(ElevatorManager.class.getName());
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
            // Create elevator objects and add them to the idle bucket
            Elevator elevator = new Elevator(floorCount, i);
            elevatorPositionMap.get(1).add(elevator);
        }

        floorList = new ArrayList<>(); {
            // Generate the list of floor objects
            for (int i = 1; i <= floorCount; i++) {
                floorList.add(new Floor(i));
            }
        }
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

    public Elevator callElevator(ElevatorDirection direction, int floorNumber) {
        int topFloor = floorList.size();
        if (floorNumber <= 0 || floorNumber > topFloor) {
            String errorMsg = "Invalid floor number specified: " + floorNumber + ". Value must be between 1 and " + topFloor;
            logger.severe(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        /* Attempt to get the nearest elevator by bucket.
         * If none is found, walk adjacent buckets.
         */
        int bucket = (floorNumber % 5 == 0) ? floorNumber / 5 : (floorNumber / 5) + 1;
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

        int floorCount = floorList.size();
        int totalBuckets = (floorCount % 5 == 0) ? floorCount / 5 : (floorCount / 5) + 1;
        int offset = 0;
        while (elevator == null) {
            // Expand search outward by 1
            offset++;
            
            int bucketAbove = bucket + offset;
            if (bucketAbove <= totalBuckets) {
                matchingElevatorList = elevatorPositionMap.get(bucket);
                for (Elevator potentialElevator: matchingElevatorList) {
                    int travelDistance = Math.abs(potentialElevator.getCurrentFloor() - floorNumber);
                    if (travelDistance < shortestTravelDistance) {
                        elevator = potentialElevator;
                    }
                }
            }

            int bucketBelow = floorNumber - offset;
            if (bucketBelow >= 0) {
                matchingElevatorList = elevatorPositionMap.get(bucket);
                for (Elevator potentialElevator: matchingElevatorList) {
                    int travelDistance = Math.abs(potentialElevator.getCurrentFloor() - floorNumber);
                    if (travelDistance < shortestTravelDistance) {
                        elevator = potentialElevator;
                    }
                }
            }
        }

        return elevator;
    }

    public List<Floor> getFloorList() {
        return floorList;
    }

    public Map<Integer, List<Elevator>> getElevatorPositionMap() {
        return elevatorPositionMap;
    }
}
