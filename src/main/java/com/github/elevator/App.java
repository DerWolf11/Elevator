package main.java.com.github.elevator;

import java.util.logging.Logger;

import main.java.com.github.elevator.component.environment.Elevator;
import main.java.com.github.elevator.config.LoggerConfig;
import main.java.com.github.elevator.enums.ElevatorDirection;
import main.java.com.github.elevator.manager.ElevatorManager;

public class App {
    private static final Logger logger = LoggerConfig.getLogger(App.class.getName());

    public static void main(String[] args) {
        int elevatorCount = 4;
        int floorCount = 14;
        logger.info("Creating a new Elevator System with " + elevatorCount + " elevators and " + floorCount + " floors.");
        ElevatorManager elevatorManager = ElevatorManager.getInstance(elevatorCount, floorCount);
        try {
            // Up from 1 to 6
            Elevator elevator = elevatorManager.callElevator(ElevatorDirection.UP, 1);
            elevator.pressFloorButton(6);
            elevatorManager.travel(elevator);
            logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            // Down from 2 to 1
            elevator = elevatorManager.callElevator(ElevatorDirection.DOWN, 2);
            elevator.pressFloorButton(1);
            elevatorManager.travel(elevator);
            logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            // Down from 8 to 1
            elevator = elevatorManager.callElevator(ElevatorDirection.DOWN, 8);
            elevator.pressFloorButton(1);
            elevatorManager.travel(elevator);
            logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            elevator = elevatorManager.callElevator(ElevatorDirection.UP, 111);
            elevator.pressFloorButton(10);
            elevatorManager.travel(elevator);
        }
        catch (Exception ex) {
            logger.severe("Unable to service request due to " + ex.getMessage());
        }
    }
}
