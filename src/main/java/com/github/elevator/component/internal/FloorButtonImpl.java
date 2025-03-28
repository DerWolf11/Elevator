package main.java.com.github.elevator.component.internal;

import main.java.com.github.elevator.component.interfaces.Button;

public class FloorButtonImpl implements Button {
    private final int floorNumber;
    private boolean pressed;
    
    public FloorButtonImpl(int floorNumber, boolean pressed) {
        this.floorNumber = floorNumber;
        this.pressed = pressed;
    }

    public int getFloorNumber() {
        return floorNumber;
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
