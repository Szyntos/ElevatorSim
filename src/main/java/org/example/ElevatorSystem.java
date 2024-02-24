package org.example;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem implements ElevatorSystemInterface{
    public FloorSystem floorSystem;

    public Elevator[] elevators;
    public int elevatorCount;
    public int elevatorCapacity;
    Random random;
    public ElevatorSystem(FloorSystem floorSystem, int elevatorCount, int elevatorCapacity, long seed){
        random = new Random(seed);

        this.floorSystem = floorSystem;

        this.elevatorCount = elevatorCount;
        this.elevators = new Elevator[elevatorCount];
        this.elevatorCapacity = elevatorCapacity;
        initializeElevators();
    }

    public ElevatorSystem(FloorSystem floorSystem, int elevatorCount, int elevatorCapacity){
        random = new Random();

        this.floorSystem = floorSystem;
        this.elevatorCount = elevatorCount;
        this.elevators = new Elevator[elevatorCount];
        this.elevatorCapacity = elevatorCapacity;
        initializeElevators();
    }

    public void initializeFloors(){
        for (int i = 0; i < this.floorSystem.getFloorCount(); i++) {
            this.floorSystem.getFloors()[i] = new Floor(i);
        }
    }

    public void initializeElevators(){
        for (int i = 0; i < elevatorCount; i++) {
            this.elevators[i] = new Elevator(i, this.elevatorCapacity, this.floorSystem.getFloors());
            this.elevators[i].update();
        }
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
                this.floorSystem.getFloors()) {
            floor.update();
        }
    }

    public int[] status(int elevatorID) {
        return new int[]{elevators[elevatorID].getPassengersCount(), elevators[elevatorID].getCurrentFloor(),
                elevators[elevatorID].getDesiredFloor()};
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
