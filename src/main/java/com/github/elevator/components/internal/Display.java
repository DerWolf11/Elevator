package main.java.com.github.elevator.components.internal;

import main.java.com.github.elevator.enums.ElevatorDirection;
import main.java.com.github.elevator.enums.FloorNumber;

public class Display {
    private ElevatorDirection direction;
    private FloorNumber currentFloor;

    public ElevatorDirection getDirection() {
        return direction;
    }

    public void setDirection(ElevatorDirection direction) {
        this.direction = direction;
    }

    public FloorNumber getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(FloorNumber floorNumber) {
        this.currentFloor = floorNumber;
    }
}
