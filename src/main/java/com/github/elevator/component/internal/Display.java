package main.java.com.github.elevator.component.internal;

import main.java.com.github.elevator.enums.ElevatorDirection;

public class Display {
    private ElevatorDirection direction;
    private int currentFloor;

    public ElevatorDirection getDirection() {
        return direction;
    }

    public void setDirection(ElevatorDirection direction) {
        this.direction = direction;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int floorNumber) {
        this.currentFloor = floorNumber;
    }
}
