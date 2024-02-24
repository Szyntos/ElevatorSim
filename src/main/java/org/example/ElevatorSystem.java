package org.example;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem {
    public Floor[] floors;
    public int floorCount;
    public Elevator[] elevators;
    public int elevatorCount;
    public int elevatorCapacity;
    Random random;
    public ElevatorSystem(int floorCount, int elevatorCount, int elevatorCapacity, long seed){
        random = new Random(seed);

        this.floorCount = floorCount;
        this.floors = new Floor[floorCount];
        initializeFloors();

        this.elevatorCount = elevatorCount;
        this.elevators = new Elevator[elevatorCount];
        this.elevatorCapacity = elevatorCapacity;
        initializeElevators();
    }

    public ElevatorSystem(int floorCount, int elevatorCount, int elevatorCapacity){
        random = new Random();

        this.floorCount = floorCount;
        this.floors = new Floor[floorCount];
        initializeFloors();

        this.elevatorCount = elevatorCount;
        this.elevators = new Elevator[elevatorCount];
        this.elevatorCapacity = elevatorCapacity;
        initializeElevators();
    }

    public void initializeFloors(){
        for (int i = 0; i < floorCount; i++) {
            this.floors[i] = new Floor(i, this);
        }
    }

    public void initializeElevators(){
        for (int i = 0; i < elevatorCount; i++) {
            this.elevators[i] = new Elevator(i, this.elevatorCapacity, floors);
            this.elevators[i].update();
        }
    }

    public void addPersonToFloor(int floorID, int desiredFloorID){
        Person Gregory = new Person(0, this.floors[floorID], this.floors[desiredFloorID]);
        this.floors[floorID].addPerson(Gregory);
    }

    public void addPersonToFloor(int floorID, int desiredFloorID, int personID){
        Person Gregory = new Person(personID, this.floors[floorID], this.floors[desiredFloorID]);
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
        List<Elevator> suitableElevators = new ArrayList<>();

        // Find the minimal ETA among all elevators
        for (Elevator elevator : elevators) {
            int currentETA = elevator.getETA(fromFloor, direction);
            if (currentETA < minimalETA) {
                minimalETA = currentETA;
                suitableElevators.clear(); // Reset the list for new minimal ETA
                suitableElevators.add(elevator);
            } else if (currentETA == minimalETA) {
                suitableElevators.add(elevator);
            }
        }
        
        // Randomly choose one of the suitable elevators and call it
        if (!suitableElevators.isEmpty()) {
            Elevator chosenElevator = suitableElevators.get(random.nextInt(suitableElevators.size()));
            chosenElevator.call(fromFloor, direction);
        }

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
