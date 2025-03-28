package main.java.com.github.elevator.component.internal;

import main.java.com.github.elevator.enums.DoorState;

public class FireAccessPanel {
    private final FireDoorButtonImpl closeDoorButton;
    private final FireInternalKeyAccessImpl fireKeyAccess;
    private final FireDoorButtonImpl openDoorButton;
    private final FireRunStopButtonImpl runStopButton;

    public FireAccessPanel() {
        closeDoorButton = new FireDoorButtonImpl(DoorState.CLOSE, false);
        fireKeyAccess = new FireInternalKeyAccessImpl();
        openDoorButton = new FireDoorButtonImpl(DoorState.OPEN, false);
        runStopButton = new FireRunStopButtonImpl();
    }

    public FireDoorButtonImpl getCloseDoorButton() {
        return closeDoorButton;
    }
    
    public FireInternalKeyAccessImpl getFireKeyAccess() {
        return fireKeyAccess;
    }
    
    public FireDoorButtonImpl getOpenDoorButton() {
        return openDoorButton;
    }
    
    public FireRunStopButtonImpl getRunStopButton() {
        return runStopButton;
    }
}
