package main.java.com.github.elevator.component.internal;

import main.java.com.github.elevator.component.interfaces.Button;

public class FireRunStopButtonImpl implements Button {
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
