package main.java.com.github.elevator.component.external;

import main.java.com.github.elevator.enums.ElevatorDirection;

public class ExternalPanel {
    private final FireExternalKeyAccessImpl fireKeyAccess;
    private final DirectionButtonImpl downButton;
    private final DirectionButtonImpl upButton;

    public ExternalPanel(boolean groundFloor) {
        upButton = new DirectionButtonImpl(ElevatorDirection.UP, false);
        // Ground floor is assumed to be the main egress and fire panel access point
        // This also assumes there is no service to a basement or parking level, just numbered floors from the ground up.
        if (groundFloor) {
            fireKeyAccess = new FireExternalKeyAccessImpl();
            downButton = null;
        }
        else {
            fireKeyAccess = null;
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
