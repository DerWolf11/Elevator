package main.java.com.github.elevator.component.environment;

import java.util.logging.Logger;

import main.java.com.github.elevator.component.internal.AudioSystem;
import main.java.com.github.elevator.component.internal.Display;
import main.java.com.github.elevator.component.internal.FireAccessPanel;
import main.java.com.github.elevator.component.internal.FloorButtonImpl;
import main.java.com.github.elevator.component.internal.InternalPanel;
import main.java.com.github.elevator.component.internal.KeycardReader;
import main.java.com.github.elevator.component.internal.Scale;
import main.java.com.github.elevator.config.LoggerConfig;
import main.java.com.github.elevator.enums.DoorState;
import main.java.com.github.elevator.enums.ElevatorDirection;
import main.java.com.github.elevator.enums.ElevatorMode;
import main.java.com.github.elevator.manager.AuditManager;
import main.java.com.github.elevator.manager.AuthenticationManager;
import main.java.com.github.elevator.manager.ElevatorManager;

public class Elevator {
    private static final Logger logger = LoggerConfig.getLogger(Elevator.class.getName());
    
    private final AudioSystem audioSystem;
    private ElevatorDirection currentDirection;
    private int currentFloor;
    private int destinationFloor;
    private final Display display;
    private final Doors doors;
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
        doors = new Doors(DoorState.CLOSE);
        fireAccessPanel = new FireAccessPanel();
        this.id = id;
        internalPanel = new InternalPanel(floorCount);
        keycardReader = new KeycardReader();
        operationalMode = ElevatorMode.DEFAULT;
        scale = new Scale();
    }

    public void pressFloorButton(int floorNumber) throws Exception {
        int topFloor = internalPanel.getFloorButtonList().size();
        if (floorNumber <= 0 || floorNumber > topFloor) {
            String errorMsg = "Invalid floor number specified: " + floorNumber + ". Value must be between 1 and " + topFloor;
            logger.severe(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        FloorButtonImpl floorButton = internalPanel.getFloorButtonList().get(floorNumber);
        floorButton.setPressed();
        
        // Authentication check
        if (operationalMode == ElevatorMode.SECURE) {
            String requestor = keycardReader.getRequestor();
            if (!AuthenticationManager.getInstance().isAuthorized(requestor)) {
                String errorMsg = "Unauthorized access attempt by " + requestor;
                logger.severe(errorMsg);
                AuditManager.getInstance().auditEvent(errorMsg);
                throw new Exception(errorMsg);
            }
        }

        String eventMsg = "Service requested from floor " + currentFloor + " to " + floorNumber;
        logger.info(eventMsg);
        AuditManager.getInstance().auditEvent(eventMsg);

        this.destinationFloor = floorNumber;
        this.doors.setDoorState(DoorState.CLOSE);
        ElevatorManager.getInstance().getFloorList().get(floorNumber).getDoors().setDoorState(DoorState.CLOSE);
        this.currentDirection = (destinationFloor > currentFloor) ? ElevatorDirection.UP : ElevatorDirection.DOWN;
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

    public Doors getDoors() {
        return doors;
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
