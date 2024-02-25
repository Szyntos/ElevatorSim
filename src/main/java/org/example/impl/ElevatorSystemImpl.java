package org.example.impl;

import org.example.interfaces.Elevator;
import org.example.interfaces.ElevatorSystem;
import org.example.interfaces.Floor;
import org.example.interfaces.FloorSystem;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class ElevatorSystemImpl implements ElevatorSystem {
    public FloorSystem floorSystem;

    public Elevator[] elevators;
    public int elevatorCount;
    public int elevatorCapacity;
    Random random;
    public ElevatorSystemImpl(FloorSystem floorSystem, int elevatorCount, int elevatorCapacity, long seed){
        random = new Random(seed);

        this.floorSystem = floorSystem;

        this.elevatorCount = elevatorCount;
        this.elevators = new ElevatorImpl[elevatorCount];
        this.elevatorCapacity = elevatorCapacity;
        initializeElevators();
    }

    public ElevatorSystemImpl(FloorSystem floorSystem, int elevatorCount, int elevatorCapacity){
        random = new Random();

        this.floorSystem = floorSystem;
        this.elevatorCount = elevatorCount;
        this.elevators = new ElevatorImpl[elevatorCount];
        this.elevatorCapacity = elevatorCapacity;
        initializeElevators();
    }


    public void initializeElevators(){
        for (int i = 0; i < elevatorCount; i++) {
            this.elevators[i] = new ElevatorImpl(i, this.elevatorCapacity, this.floorSystem.getFloors());
            this.elevators[i].update();
        }
    }

    public int getElevatorCount(){
        return elevatorCount;
    }
    public Elevator[] getElevators(){
        return elevators;
    }

    public int getElevatorCapacity() {
        return elevatorCapacity;
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

    public void addFloor(){
        for (Elevator elevator :
                elevators) {
            elevator.addFloor();
            elevator.setAllFloors(floorSystem.getFloors());
        }

    }
    public void delFloor(){
        for (Elevator elevator :
                elevators) {
            elevator.delFloor();
            elevator.setAllFloors(floorSystem.getFloors());
        }
    }

    public Elevator[] addElevatorToEnd(Elevator[] array) {
        // Create a new array with size one element greater than the original array
        Elevator[] newArray = new Elevator[array.length + 1];

        // Copy elements from the original array to the new array
        System.arraycopy(array, 0, newArray, 0, array.length);

        // Assign 'false' to the last element of the new array
        newArray[newArray.length - 1] = new ElevatorImpl(newArray.length - 1, elevatorCapacity, floorSystem.getFloors());
//        newArray[newArray.length - 1].addElevatorSystem(this.elevatorSystem);

        // Return the modified array
        return newArray;
    }

    public static Elevator[] removeLastElement(Elevator[] array) {
        // Check if the array is empty or has only one element
        if (array == null || array.length <= 1) {
            return new Elevator[0]; // return an empty array or the original array if it's empty or has only one element
        }

        // Create a new array with size one element smaller than the original array
        Elevator[] newArray = new Elevator[array.length - 1];

        // Copy elements from the original array to the new array, excluding the last element
        System.arraycopy(array, 0, newArray, 0, newArray.length);

        // Return the modified array
        return newArray;
    }

    public void incrementElevatorCount(){
        elevatorCount++;
        elevators = addElevatorToEnd(elevators);
        floorSystem.addElevator();
    }

    public void decrementElevatorCount(){
        if (elevatorCount == 1){
            return;
        }
        elevatorCount--;
        elevators = removeLastElement(elevators);
        floorSystem.delElevator();
    }
    public void incrementElevatorCapacity(){
        elevatorCapacity++;
        for (Elevator elevator :
                elevators) {
            elevator.setCapacity(elevator.getCapacity() + 1);
        }
    }
    public void decrementElevatorCapacity(){
        if (elevators[0].getCapacity() == 1){
            return;
        }
        elevatorCapacity--;
        for (Elevator elevator :
                elevators) {
            elevator.setCapacity(elevator.getCapacity() - 1);
        }
    }
}
