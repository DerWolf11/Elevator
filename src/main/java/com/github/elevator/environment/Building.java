package main.java.com.github.elevator.environment;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private List<Elevator> elevatorList;
    private List<Floor> floorList;

    public Building(int elevatorCount, int floorCount) {
        elevatorList = new ArrayList<>(); {
            // Generate the list of elevator objects
            for (int i = 1; i <= elevatorCount; i++) {
                elevatorList.add(new Elevator(floorCount));
            }
        }
        floorList = new ArrayList<>(); {
            // Generate the list of floor objects
            for (int i = 1; i <= floorCount; i++) {
                floorList.add(new Floor(i));
            }
        }
    }

    public List<Floor> getFloorList() {
        return floorList;
    }
    
    public void setFloorList(List<Floor> floorList) {
        this.floorList = floorList;
    }
    
    public List<Elevator> getElevatorList() {
        return elevatorList;
    }
    
    public void setElevatorList(List<Elevator> elevatorList) {
        this.elevatorList = elevatorList;
    }
}
