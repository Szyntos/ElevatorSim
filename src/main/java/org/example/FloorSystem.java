package org.example;

import java.util.Random;

public class FloorSystem implements FloorSystemInterface{
    Random random;
    public Floor[] floors;
    public ElevatorSystem elevatorSystem;
    int allTimePeopleCount = 0;

    public int floorCount;
    public FloorSystem(int floorCount, long seed){
        this.random = new Random(seed);
        this.floorCount = floorCount;
        initializeFloors();
    }
    public FloorSystem(int floorCount){
        this.random = new Random();
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

    public void addPersonToFloor(int floorID, int desiredFloorID){
        Person Gregory = new Person(allTimePeopleCount, this.floors[floorID], this.floors[desiredFloorID]);
        allTimePeopleCount++;
        this.floors[floorID].addPerson(Gregory);
    }

    public void spawnRandomPerson(){

        int floorID = random.nextInt(this.floorCount);

        int desiredFloorID = random.nextInt(this.floorCount);
        while (desiredFloorID == floorID){
            desiredFloorID = random.nextInt(this.floorCount);
        }

        addPersonToFloor(floorID, desiredFloorID);
    }


    public Floor[] getFloors() {
        return floors;
    }

    public int getFloorCount() {
        return floorCount;
    }
}
