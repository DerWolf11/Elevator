package main.java.com.github.elevator.component.internal;

import main.java.com.github.elevator.component.interfaces.Button;

public class AlarmButtonImpl implements Button {
    private boolean isPressed;
    
    @Override
    public boolean isPressed() {
        return isPressed;
    }

    @Override
    public void setPressed() {
        isPressed = !isPressed;
    }
}
