package main.java.com.github.elevator.components.internal;

import main.java.com.github.elevator.components.interfaces.Button;

public class EmergencyStopButtonImpl implements Button {
    private boolean pressed;

    @Override
    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void setPressed() {
        pressed = !pressed;
    }
}
