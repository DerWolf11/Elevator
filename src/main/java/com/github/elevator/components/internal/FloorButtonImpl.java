package main.java.com.github.elevator.components.internal;

import main.java.com.github.elevator.components.interfaces.Button;
import main.java.com.github.elevator.enums.FloorNumber;

public class FloorButtonImpl implements Button {
    private FloorNumber floorNumber;
    private boolean pressed;
    
    public FloorButtonImpl(FloorNumber floorNumber, boolean pressed) {
        this.floorNumber = floorNumber;
        this.pressed = pressed;
    }

    public FloorNumber getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(FloorNumber floorNumber) {
        this.floorNumber = floorNumber;
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
