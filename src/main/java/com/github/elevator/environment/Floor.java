package main.java.com.github.elevator.environment;

import main.java.com.github.elevator.components.external.ExternalPanel;
import main.java.com.github.elevator.enums.DoorState;

public class Floor {
    private Doors doors;
    private ExternalPanel externalPanel;
    private int floorNumber;

    public Floor(int floorNumber) {
        doors = new Doors(DoorState.CLOSED);
        // Assume ground floor (1st floor) is the main egress and will therefore have a firefighter key access on the external panel
        boolean groundFloor = floorNumber == 1;
        externalPanel = new ExternalPanel(groundFloor);
        this.floorNumber = floorNumber;
    }

    public Doors getDoors() {
        return doors;
    }
    
    public void setDoors(Doors doors) {
        this.doors = doors;
    }
    
    public ExternalPanel getExternalPanel() {
        return externalPanel;
    }
    
    public void setExternalPanel(ExternalPanel externalPanel) {
        this.externalPanel = externalPanel;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }
}
