package main.java.com.github.elevator.component.external;

import main.java.com.github.elevator.component.interfaces.Button;
import main.java.com.github.elevator.enums.ElevatorDirection;

public class DirectionButtonImpl implements Button {
    private ElevatorDirection direction;
    private boolean pressed;
    
    public DirectionButtonImpl(ElevatorDirection direction, boolean pressed) {
        this.direction = direction;
        this.pressed = pressed;
    }

    public ElevatorDirection getDirection() {
        return direction;
    }

    public void setDirection(ElevatorDirection direction) {
        this.direction = direction;
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
