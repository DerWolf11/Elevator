package main.java.com.github.elevator.component.environment;

import main.java.com.github.elevator.component.external.ExternalPanel;
import main.java.com.github.elevator.enums.DoorState;

public class Floor {
    private final Doors doors;
    private final ExternalPanel externalPanel;
    private final int floorNumber;

    public Floor(int floorNumber) {
        doors = new Doors(DoorState.CLOSE);
        // Assume ground floor (1st floor) is the main egress and will therefore have a firefighter key access on the external panel
        boolean groundFloor = floorNumber == 1;
        externalPanel = new ExternalPanel(groundFloor);
        this.floorNumber = floorNumber;
    }

    public Doors getDoors() {
        return doors;
    }
    
    public ExternalPanel getExternalPanel() {
        return externalPanel;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
