package org.example;

public class FloorSystem {
    public Floor[] floors;
    public ElevatorSystem elevatorSystem;

    public int floorCount;
    public FloorSystem(int floorCount){
        this.floorCount = floorCount;
        initializeFloors();
    }

    public void initializeFloors(){
        this.floors = new Floor[floorCount];
        for (int i = 0; i < floorCount; i++) {
            this.floors[i] = new Floor(i);
        }
    }

    public void addElevatorSystem(ElevatorSystem elevatorSystem){
        this.elevatorSystem = elevatorSystem;
        for (int i = 0; i < floorCount; i++) {
            this.floors[i].addElevatorSystem(elevatorSystem);
        }
    }


    public Floor[] getFloors() {
        return floors;
    }

    public int getFloorCount() {
        return floorCount;
    }
}
