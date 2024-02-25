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

    public void pressButton(int floorID, Direction direction){
        switch (direction){
            case UP -> {
                floors[floorID].pressUp();
            }
            case DOWN -> {
                floors[floorID].pressDown();
            }
        }
    }

    public int[] status(int FloorID){
        return new int[]{floors[FloorID].getPeople().size(), floors[FloorID].upPressed ? 1 : 0,
                floors[FloorID].downPressed ? 1 : 0};
    }

    public Floor[] addFloorToEnd(Floor[] array) {
        // Create a new array with size one element greater than the original array
        Floor[] newArray = new Floor[array.length + 1];

        // Copy elements from the original array to the new array
        System.arraycopy(array, 0, newArray, 0, array.length);

        // Assign 'false' to the last element of the new array
        newArray[newArray.length - 1] = new Floor(newArray.length - 1);
        newArray[newArray.length - 1].addElevatorSystem(this.elevatorSystem);

        // Return the modified array
        return newArray;
    }

    public static Floor[] removeLastElement(Floor[] array) {
        // Check if the array is empty or has only one element
        if (array == null || array.length <= 1) {
            return new Floor[0]; // return an empty array or the original array if it's empty or has only one element
        }

        // Create a new array with size one element smaller than the original array
        Floor[] newArray = new Floor[array.length - 1];

        // Copy elements from the original array to the new array, excluding the last element
        System.arraycopy(array, 0, newArray, 0, newArray.length);

        // Return the modified array
        return newArray;
    }

    public void incrementFloorCount(){
        floorCount++;
        floors = addFloorToEnd(floors);
        elevatorSystem.addFloor();
    }

    public void decrementFloorCount(){
        if (floorCount == 2){
            return;
        }
        floorCount--;
        floors = removeLastElement(floors);
        elevatorSystem.delFloor();
        for (Floor floor :
                floors) {
            for (Person person :
                    floor.getPeople()) {
                if (person.desiredFloor.ID >= floorCount) {
                    person.desiredFloor = floors[floorCount - 1];
                }
            }
        }
    }

    public void addElevator(){
        for (Floor floor :
                floors) {
            floor.addElevator();
        }
    }
    public void delElevator(){
        for (Floor floor :
                floors) {
            floor.delElevator();
        }
    }

    public Floor[] getFloors() {
        return floors;
    }

    public int getFloorCount() {
        return floorCount;
    }

}
