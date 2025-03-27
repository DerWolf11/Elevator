package main.java.com.github.elevator.component.internal;

import java.util.ArrayList;
import java.util.List;

import main.java.com.github.elevator.enums.DoorState;
import main.java.com.github.elevator.enums.FloorNumber;

public class InternalPanel {
    private AlarmButtonImpl alarmButton;
    private CallButtonImpl callButton;
    private DoorButtonImpl closeDoorButton;
    private EmergencyStopButtonImpl emergencyStopButton;
    private List<FloorButtonImpl> floorButtonList;
    private DoorButtonImpl holdDoorButton;
    private DoorButtonImpl openDoorButton;

    public InternalPanel(int floorCount) {
        alarmButton = new AlarmButtonImpl();
        callButton = new CallButtonImpl();
        closeDoorButton = new DoorButtonImpl(DoorState.CLOSE, false);
        emergencyStopButton = new EmergencyStopButtonImpl();
        floorButtonList = new ArrayList<>(); {
            // Generate a floor button for each floor and add to the internal panel
            for (int i = 1; i <= floorCount; i++) {
                floorButtonList.add(new FloorButtonImpl(FloorNumber.values()[i], false));
            }
        }
        holdDoorButton = new DoorButtonImpl(DoorState.HOLD, false);
        openDoorButton = new DoorButtonImpl(DoorState.OPEN, false);
    }

    public AlarmButtonImpl getAlarmButton() {
        return alarmButton;
    }

    public CallButtonImpl getCallButton() {
        return callButton;
    }

    public DoorButtonImpl getCloseDoorButton() {
        return closeDoorButton;
    }

    public EmergencyStopButtonImpl getEmergencyStopButton() {
        return emergencyStopButton;
    }

    public List<FloorButtonImpl> getFloorButtonList() {
        return floorButtonList;
    }

    public DoorButtonImpl getHoldDoorButton() {
        return holdDoorButton;
    }

    public DoorButtonImpl getOpenDoorButton() {
        return openDoorButton;
    }
}
