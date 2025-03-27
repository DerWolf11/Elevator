package main.java.com.github.elevator.components.internal;

import main.java.com.github.elevator.enums.DoorState;

public class FireAccessPanel {
    private FireDoorButtonImpl closeDoorButton;
    private FireInternalKeyAccessImpl fireKeyAccess;
    private FireDoorButtonImpl openDoorButton;
    private FireRunStopButtonImpl runStopButton;

    public FireAccessPanel() {
        closeDoorButton = new FireDoorButtonImpl(DoorState.CLOSED, false);
        fireKeyAccess = new FireInternalKeyAccessImpl();
        openDoorButton = new FireDoorButtonImpl(DoorState.OPENED, false);
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
