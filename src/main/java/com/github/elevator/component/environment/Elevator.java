package main.java.com.github.elevator.component.environment;

import main.java.com.github.elevator.component.internal.AudioSystem;
import main.java.com.github.elevator.component.internal.Display;
import main.java.com.github.elevator.component.internal.FireAccessPanel;
import main.java.com.github.elevator.component.internal.InternalPanel;
import main.java.com.github.elevator.component.internal.KeycardReader;
import main.java.com.github.elevator.component.internal.Scale;
import main.java.com.github.elevator.enums.ElevatorDirection;
import main.java.com.github.elevator.enums.ElevatorMode;

public class Elevator {
    private final AudioSystem audioSystem;
    private ElevatorDirection currentDirection;
    private int currentFloor;
    private int destinationFloor;
    private final Display display;
    private final FireAccessPanel fireAccessPanel;
    private final int id;
    private final InternalPanel internalPanel;
    private final KeycardReader keycardReader;
    private ElevatorMode operationalMode;
    private final Scale scale;

    public Elevator(int floorCount, int id) {
        audioSystem = new AudioSystem();
        currentDirection = ElevatorDirection.STOPPED;
        currentFloor = 1;
        destinationFloor = 1;
        display = new Display();
        fireAccessPanel = new FireAccessPanel();
        this.id = id;
        internalPanel = new InternalPanel(floorCount);
        keycardReader = new KeycardReader();
        operationalMode = ElevatorMode.DEFAULT;
        scale = new Scale();
    }

    public AudioSystem getAudioSystem() {
        return audioSystem;
    }
    
    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorDirection getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(ElevatorDirection currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public Display getDisplay() {
        return display;
    }
    
    public FireAccessPanel getFireAccessPanel() {
        return fireAccessPanel;
    }
    
    public int getId() {
        return id;
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
