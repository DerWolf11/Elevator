package main.java.com.github.elevator.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import main.java.com.github.elevator.component.constants.Constants;
import main.java.com.github.elevator.component.environment.Elevator;
import main.java.com.github.elevator.component.environment.Floor;
import main.java.com.github.elevator.enums.FloorNumber;

public class ElevatorManager {
    private static final Logger logger = Logger.getLogger(ElevatorManager.class.getName());
    private static volatile ElevatorManager INSTANCE;
    private static final Object MUTEX = new Object();

    private volatile List<Elevator> elevatorList;
    private volatile List<Floor> floorList;

    public ElevatorManager(int elevatorCount, int floorCount) throws IllegalArgumentException {
        if (elevatorCount > Constants.MAX_ELEVATOR_COUNT) {
            String errorMsg = "Invalid number of elevators specified: " + elevatorCount + ". Maximum supported: " + Constants.MAX_ELEVATOR_COUNT;
            logger.severe(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        if (elevatorCount > Constants.MAX_FLOOR_COUNT) {
            String errorMsg = "Invalid number of floors specified: " + floorCount + ". Maximum supported: " + Constants.MAX_FLOOR_COUNT;
            logger.severe(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        elevatorList = new ArrayList<>(); {
            // Generate the list of elevator objects
            for (int i = 1; i <= elevatorCount; i++) {
                elevatorList.add(new Elevator(floorCount));
            }
        }
        floorList = new ArrayList<>(); {
            // Generate the list of floor objects
            for (int i = 1; i <= floorCount; i++) {
                FloorNumber floorNumber;
                String floorStr = "FLOOR_" + i;
                try {
                    floorNumber = FloorNumber.valueOf(floorStr);
                }
                catch (IllegalArgumentException ex) {
                    String errorMsg = "Unable to parse input to valid floor number: " + floorStr;
                    logger.severe(errorMsg);
                    throw new IllegalArgumentException(errorMsg);
                }
                floorList.add(new Floor(floorNumber));
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

    public List<Floor> getFloorList() {
        return floorList;
    }
    
    public void setFloorList(List<Floor> floorList) {
        this.floorList = floorList;
    }
    
    public List<Elevator> getElevatorList() {
        return elevatorList;
    }
    
    public void setElevatorList(List<Elevator> elevatorList) {
        this.elevatorList = elevatorList;
    }
}
