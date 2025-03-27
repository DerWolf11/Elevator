package main.java.com.github.elevator.component.external;

import main.java.com.github.elevator.enums.ElevatorDirection;

public class ExternalPanel {
    private FireExternalKeyAccessImpl fireKeyAccess;
    private DirectionButtonImpl downButton;
    private DirectionButtonImpl upButton;

    public ExternalPanel(boolean groundFloor) {
        upButton = new DirectionButtonImpl(ElevatorDirection.UP, false);
        // Ground floor is assumed to be the main egress and fire panel access point
        // This also assumes there is no service to a basement or parking level, just numbered floors from the ground up.
        if (groundFloor) {
            fireKeyAccess = new FireExternalKeyAccessImpl();
        }
        else {
            downButton = new DirectionButtonImpl(ElevatorDirection.DOWN, false);
        }
    }

    public DirectionButtonImpl getDownButton() {
        return downButton;
    }

    public FireExternalKeyAccessImpl getFireKeyAccess() {
        return fireKeyAccess;
    }

    public DirectionButtonImpl getUpButton() {
        return upButton;
    }
}
