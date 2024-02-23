package org.example;

import java.util.Random;

public class ElevatorSystem {
    private final Main parent;
    public Floor[] floors;
    public int floorCount;
    public Elevator[] elevators;
    public int elevatorCount;
    public int elevatorCapacity;
    Random random;
    public ElevatorSystem(int floorCount, int elevatorCount, int elevatorCapacity, Main parent, long seed){
        random = new Random(seed);
        this.parent = parent;

        this.floorCount = floorCount;
        this.floors = new Floor[floorCount];
        initializeFloors();

        this.elevatorCount = elevatorCount;
        this.elevators = new Elevator[elevatorCount];
        this.elevatorCapacity = elevatorCapacity;
        initializeElevators();
    }

    public ElevatorSystem(int floorCount, int elevatorCount, int elevatorCapacity, Main parent){
        random = new Random();
        this.parent = parent;

        this.floorCount = floorCount;
        this.floors = new Floor[floorCount];
        initializeFloors();

        this.elevatorCount = elevatorCount;
        this.elevators = new Elevator[elevatorCount];
        this.elevatorCapacity = elevatorCapacity;
        initializeElevators();
    }

    public void initializeFloors(){
//        int floorHeight = parent.height/(floorCount * 2);
//        int floorWidth = parent.width;
        for (int i = 0; i < floorCount; i++) {
            this.floors[i] = new Floor(i, this, parent);
//            this.floors[i].setPosition(0, parent.height - parent.height / floorCount * (i) - floorHeight,
//                    floorWidth, floorHeight/2);
        }
    }

    public void initializeElevators(){
//        int elevatorHeight = parent.height/(floorCount * 2);
//        int elevatorWidth = parent.width/20;
        for (int i = 0; i < elevatorCount; i++) {
            this.elevators[i] = new Elevator(i, this.elevatorCapacity, floors);
//            this.elevators[i].setDimensions(elevatorWidth, elevatorHeight);
            this.elevators[i].update();
        }
    }

    public void addPersonToFloor(int floorID, int desiredFloorID){
        Person Gregory = new Person(0, this.floors[floorID], this.floors[desiredFloorID], parent);
        this.floors[floorID].addPerson(Gregory);
    }

    public void addPersonToFloor(int floorID, int desiredFloorID, int personID){
        Person Gregory = new Person(personID, this.floors[floorID], this.floors[desiredFloorID], parent);
        this.floors[floorID].addPerson(Gregory);
    }

    public void spawnRandomPerson(int personID){

        int floorID = random.nextInt(floorCount);

        int desiredFloorID = random.nextInt(floorCount);
        while (desiredFloorID == floorID){
            desiredFloorID = random.nextInt(floorCount);
        }

        addPersonToFloor(floorID, desiredFloorID, personID);
    }

    public void pickup(int fromFloor, Direction direction){

        int minimalETA = Integer.MAX_VALUE;

        for (Elevator elevator :
                elevators) {
            if (minimalETA > elevator.getETA(fromFloor, direction)) {
                minimalETA = elevator.getETA(fromFloor, direction);
            }
        }

        int count = 0;
        for (Elevator elevator :
                elevators) {
            if (minimalETA == elevator.getETA(fromFloor, direction)) {
                count++;
            }
        }

        Elevator[] suitableElevators = new Elevator[count];
        int j = 0;
        for (int i = 0; i < elevatorCount; i++) {
            if (minimalETA == elevators[i].getETA(fromFloor, direction)){
                suitableElevators[j] = elevators[i];
                j++;
            }
        }

        suitableElevators[random.nextInt(suitableElevators.length)].call(fromFloor, direction);
    }

    public void update(int elevatorID, int newCurrentFloor, int newDesiredFloor){
        this.elevators[elevatorID].setCurrentFloor(newCurrentFloor);
        this.elevators[elevatorID].setDesiredFloor(newDesiredFloor);
        this.elevators[elevatorID].update();
    }

    public void step(){
        for (Elevator elevator :
                elevators) {
            elevator.update();
        }
        for (Floor floor :
                floors) {
            floor.update();
        }
    }

    public String status() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        if (elevatorCount > 0) {
            for (int i = 0; i < elevatorCount - 1; i++) {
                s.append("(");
                s.append(elevators[i].getID());
                s.append(", ");
                s.append(elevators[i].getCurrentFloor());
                s.append("->");
                s.append(elevators[i].getDesiredFloor());
                s.append("), ");
            }
            s.append("(");
            s.append(elevators[elevatorCount - 1].getID());
            s.append(", ");
            s.append(elevators[elevatorCount - 1].getCurrentFloor());
            s.append("->");
            s.append(elevators[elevatorCount - 1].getDesiredFloor());
            s.append(")");
        }
        s.append("]");
        return s.toString();
    }
}
