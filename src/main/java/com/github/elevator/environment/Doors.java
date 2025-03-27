package main.java.com.github.elevator.environment;

import main.java.com.github.elevator.enums.DoorState;

public class Doors {
    private DoorState doorState;

    public Doors(DoorState doorState) {
        this.doorState = doorState;
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public void setDoorState(DoorState doorState) {
        this.doorState = doorState;
    }
}
