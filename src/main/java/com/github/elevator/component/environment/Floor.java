package main.java.com.github.elevator.component.environment;

import main.java.com.github.elevator.component.external.ExternalPanel;
import main.java.com.github.elevator.enums.DoorState;
import main.java.com.github.elevator.enums.FloorNumber;

public class Floor {
    private Doors doors;
    private ExternalPanel externalPanel;
    private FloorNumber floorNumber;

    public Floor(FloorNumber floorNumber) {
        doors = new Doors(DoorState.CLOSE);
        // Assume ground floor (1st floor) is the main egress and will therefore have a firefighter key access on the external panel
        boolean groundFloor = floorNumber == FloorNumber.FLOOR_1;
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

    public FloorNumber getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(FloorNumber floorNumber) {
        this.floorNumber = floorNumber;
    }
}
