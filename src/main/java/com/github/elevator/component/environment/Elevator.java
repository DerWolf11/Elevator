package main.java.com.github.elevator.component.environment;

import main.java.com.github.elevator.component.internal.AudioSystem;
import main.java.com.github.elevator.component.internal.Display;
import main.java.com.github.elevator.component.internal.FireAccessPanel;
import main.java.com.github.elevator.component.internal.InternalPanel;
import main.java.com.github.elevator.component.internal.KeycardReader;
import main.java.com.github.elevator.component.internal.Scale;
import main.java.com.github.elevator.enums.ElevatorDirection;
import main.java.com.github.elevator.enums.ElevatorMode;
import main.java.com.github.elevator.enums.FloorNumber;

public class Elevator {
    private AudioSystem audioSystem;
    private ElevatorDirection currentDirection;
    private FloorNumber currentFloor;
    private Display display;
    private FireAccessPanel fireAccessPanel;
    private InternalPanel internalPanel;
    private KeycardReader keycardReader;
    private ElevatorMode operationalMode;
    private Scale scale;

    public Elevator(int floorCount) {
        audioSystem = new AudioSystem();
        currentDirection = ElevatorDirection.STOPPED;
        currentFloor = FloorNumber.FLOOR_1;
        display = new Display();
        fireAccessPanel = new FireAccessPanel();
        internalPanel = new InternalPanel(floorCount);
        keycardReader = new KeycardReader();
        operationalMode = ElevatorMode.DEFAULT;
        scale = new Scale();
    }

    public AudioSystem getAudioSystem() {
        return audioSystem;
    }
    
    public FloorNumber getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorDirection getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(ElevatorDirection currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setCurrentFloor(FloorNumber currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Display getDisplay() {
        return display;
    }
    
    public FireAccessPanel getFireAccessPanel() {
        return fireAccessPanel;
    }
    
    public InternalPanel getInternalPanel() {
        return internalPanel;
    }
    
    public KeycardReader getKeycardReader() {
        return keycardReader;
    }
    
    public ElevatorMode getOperationalMode() {
        return operationalMode;
    }

    public void setOperationalMode(ElevatorMode operationalMode) {
        this.operationalMode = operationalMode;
    }

    public Scale getScale() {
        return scale;
    }
}
