package main.java.com.github.elevator.components.internal;

import main.java.com.github.elevator.components.interfaces.Button;
import main.java.com.github.elevator.enums.DoorState;

public class FireDoorButtonImpl implements Button {
    private boolean pressed;
    private DoorState doorState;
    
    public FireDoorButtonImpl(DoorState doorState, boolean pressed) {
        this.doorState = doorState;
        this.pressed = pressed;
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public void setDoorState(DoorState doorState) {
        this.doorState = doorState;
    }

    @Override
    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void setPressed() {
        pressed = !pressed;
    }
}
