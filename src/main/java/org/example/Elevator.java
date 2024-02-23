package org.example;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private final PApplet parent;
    int x;
    int y;
    int width;
    int height;
    private int currentFloor = 0;
    private int desiredFloor = 0;
    private final Floor[] allFloors;
    private boolean[] floorButtonsPressed;
    private boolean[] pickups;
    private boolean[] pickupUpDirections;
    private boolean[] pickupDownDirections;
    private boolean[] floorsToVisit;
    private final int capacity;
    private final int ID;
    private boolean isMoving = false;
    List<Person> passengers = new ArrayList<>();
    private Direction direction = Direction.ZERO;
    private Direction nextDirection = Direction.ZERO;
    private final int elevatorCount;
    public Elevator(int ID, int capacity, Floor[] allFloors, int elevatorCount, PApplet parent){
        this.ID = ID;
        this.capacity = capacity;
        this.parent = parent;
        this.allFloors = allFloors;
        this.floorButtonsPressed = new boolean[allFloors.length];

        this.pickups = new boolean[allFloors.length];
        this.pickupUpDirections = new boolean[allFloors.length];
        this.pickupDownDirections = new boolean[allFloors.length];
        this.floorsToVisit = new boolean[allFloors.length];
        for (int i = 0; i < allFloors.length; i++) {
            floorButtonsPressed[i] = false;
            pickups[i] = false;
            pickupUpDirections[i] = false;
            pickupDownDirections[i] = false;
            floorsToVisit[i] = false;
        }
        this.elevatorCount = elevatorCount;
    }

    public void addPassenger(Person passenger){
        if (passengers.size() + 1 > capacity){
            return;
        }
        passengers.add(passenger);
    }

    public boolean canAddPassenger(){
        return passengers.size() + 1 <= capacity;
    }

    public int getID() {
        return ID;
    }

    public void update(){
        if (currentFloor != desiredFloor){
            if (!isMoving){
                isMoving = true;
                allFloors[currentFloor].closeDoors(this, direction);
            }

            if (direction == Direction.UP){
                setCurrentFloor(currentFloor + 1);
            } else if (direction == Direction.DOWN){
                setCurrentFloor(currentFloor - 1);
            } else {
                setCurrentFloor(currentFloor);
            }
//            currentFloor += (desiredFloor - currentFloor) / Math.abs(desiredFloor - currentFloor);
        } else {
            if (!isAnyFloorIsPressed()){
                isMoving = false;
                direction = Direction.ZERO;
            }
            dropOffPassengers();
            allFloors[currentFloor].openDoors(this);



        }
        updatePosition();

        for (Person person : passengers) {
            person.update();
        }
        setNewDesiredFloor();

    }

    public boolean isAnyFloorIsPressed(){
        for (boolean button :
                floorButtonsPressed) {
            if (button) {
                return true;
            }
        }
        return false;
    }

    public boolean isAnyFloorToBeVisited(){
        for (boolean floor :
                floorsToVisit) {
            if (floor) {
                return true;
            }
        }
        return false;
    }
    public void printButtons(){
        if (this.ID == 1){
            for (boolean button :
                    floorButtonsPressed) {
                System.out.print(button + ",");
            }
            System.out.println();
        }
    }

    public void updateFloorsToVisit(){
//        updateDirection();
        Direction directionToPickUp;




        for (int i = 0; i < floorsToVisit.length; i++) {
            if (floorButtonsPressed[i]){
                floorsToVisit[i] = true;
            }
        }
        if (this.direction == Direction.UP){
            for (int i = currentFloor; i < allFloors.length; i++) {
                if (pickupUpDirections[i]){
                    if (i == currentFloor && !canAddPassenger()){
                        continue;
                    }
                    floorsToVisit[i] = true;
//                    pickups[i] = false;
                    pickupUpDirections[i] = false;
                }
            }
        }else if (this.direction == Direction.DOWN){
            for (int i = currentFloor; i >= 0; i--) {
                if (pickupDownDirections[i]){
                    if (i == currentFloor && !canAddPassenger()){
                        continue;
                    }
                    floorsToVisit[i] = true;
//                    pickups[i] = false;
                    pickupDownDirections[i] = false;
                }
            }
        } else {
//            for (int i = 0; i < allFloors.length; i++) {
//                directionToPickUp = currentFloor == i ? Direction.ZERO :
//                        (currentFloor > i ? Direction.DOWN : Direction.UP);
//                if (directionToPickUp == Direction.ZERO){
//                    pickupUpDirections[i] = false;
//                    pickupDownDirections[i] = false;
//                }else
//                if (directionToPickUp == Direction.UP && pickupUpDirections[i] &&
//                        directionToPickUp == directionToNextButtonPress){
//                    floorsToVisit[i] = true;
//                    pickupUpDirections[i] = false;
//                } else if (directionToPickUp == Direction.DOWN && pickupDownDirections[i] &&
//                        directionToPickUp == directionToNextButtonPress){
//                    floorsToVisit[i] = true;
//                    pickupDownDirections[i] = false;
//                }
//            }
            if (!isAnyFloorToBeVisited()){
                for (int i = 0; i < allFloors.length; i++) {
                    if (pickupUpDirections[i]){
                        floorsToVisit[i] = true;
                        pickupUpDirections[i] = false;
                        break;
                    } else if (pickupDownDirections[i]){
                        floorsToVisit[i] = true;
                        pickupDownDirections[i] = false;
                        break;
                    }
                }
            }
        }

    }

    public void setNewDesiredFloor(){
        updateFloorsToVisit();


//        if (this.ID == 0){
//            System.out.println("Floors to visit:");
//            for (int i = 0; i < floorButtonsPressed.length; i++) {
//                if (isFloorToBeVisited(i)){
//                    System.out.println("Floor " + i);
//                }
//
//            }
//            System.out.println("Pickups:");
//            for (int i = 0; i < pickups.length; i++) {
//                if (pickupUpDirections[i]){
//                    System.out.println("Pickup UP " + i);
//                }
//                if (pickupDownDirections[i]){
//                    System.out.println("Pickup Down " + i);
//                }
//
//            }
//        }

        Direction directionToNextFloor = Direction.ZERO;
        for (int i = currentFloor; i < floorsToVisit.length; i++) {
            if (floorsToVisit[i]) {
                directionToNextFloor = Direction.UP;
                break;
            }
        }
        for (int i = currentFloor; i >= 0; i--) {
            if (floorsToVisit[i]) {
                directionToNextFloor = Direction.DOWN;
                break;
            }
        }

        if (this.direction == Direction.UP){
            for (int i = currentFloor; i < allFloors.length; i++) {
                if (isFloorToBeVisited(i)){
                    setDesiredFloor(i);
                    return;
                }
            }
        }else if (this.direction == Direction.DOWN){

            for (int i = currentFloor; i >= 0; i--) {
                if (isFloorToBeVisited(i)){
                    setDesiredFloor(i);
                    return;
                }
            }
        } else {
            for (int i = 0; i < allFloors.length; i++) {
                if (isFloorToBeVisited(i)){
                    setDesiredFloor(i);
                    return;
                }
            }
        }
        this.direction = directionToNextFloor;

    }

    public void dropOffPassengers(){
        passengers.removeIf(passenger -> passenger.desiredFloor.ID == currentFloor);
        this.floorsToVisit[currentFloor] = false;
        this.floorButtonsPressed[currentFloor] = false;
    }

    public void pushFloorButton(int floor){
        this.floorButtonsPressed[floor] = true;

    }

    public boolean[] getFloorButtonsPressed() {
        return floorButtonsPressed;
    }

    public boolean isFloorButtonsPressed(int floor) {
        return floorButtonsPressed[floor];
    }

    public boolean isFloorToBeVisited(int floor){
        return floorsToVisit[floor];
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void updatePosition(){
        setPosition(parent.width/elevatorCount/4 * ID + parent.width/5 * 2,
                allFloors[currentFloor].getYPos() - height);
    }

    public void call(int toFloor, Direction direction){
        if (direction == Direction.UP){
            pickupUpDirections[toFloor] = true;
        } else if (direction == Direction.DOWN) {
            pickupDownDirections[toFloor] = true;
        }
    }

    public void draw(){
        parent.fill(95, 108, 133);
        parent.rect(this.x, this.y, this.width, this.height);
        int j = 0;
        int k = 0;
        int rows = 3;
        for (Person person : passengers) {
            if (j > rows - 1) {
                j = 0;
                k++;
            }
            person.draw(this.x - k * person.getWidth() * 2, this.y - j * person.getHeight());
            j++;
        }
        parent.textAlign(parent.CENTER);
        parent.textSize(20);
        parent.fill(0);
        parent.text(direction.toString() + "\n" + passengers.size(), x + width, y + height);
    }

    public boolean delPassenger(Person passenger){
        return passengers.remove(passenger);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getToVisitCount(){
        int count = 0;
        for (boolean floor :
                floorsToVisit) {
            if (floor) {
                count++;
            }
        }
        return count;
    }


    public int getETA(int toFloor, Direction direction){
        int penalty = getToVisitCount()*10 * 2;
        int distance = Math.abs(currentFloor - toFloor)*10+1;
        if (!isMoving || this.direction == Direction.ZERO){
            return distance;
        }
        if (currentFloor == toFloor){
            if (this.direction == direction){
                return 0;
            } else {
                return distance + penalty;
            }
        }
        if (direction == Direction.UP){
            if (currentFloor > toFloor){
                if (direction == this.direction){
                    return distance + penalty;
                }else{
                    return distance;
                }
            } else {
                if (direction == this.direction){
                    return distance;
                }else{
                    return distance + penalty;
                }
            }
        } else {
            if (currentFloor > toFloor){
                if (direction == this.direction){
                    return distance;
                }else{
                    return distance + penalty;
                }
            } else {
                if (direction == this.direction){
                    return distance + penalty;
                }else{
                    return distance;
                }
            }
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public int getPassengersCount() {
        return passengers.size();
    }

    public int getDesiredFloor() {
        return desiredFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void updateDirection(){
        boolean doNotChangeDirection = false;
        if (this.direction == Direction.UP){
            for (int i = currentFloor; i < floorsToVisit.length; i++) {
                if (floorsToVisit[i]) {
                    doNotChangeDirection = true;
                    break;
                }
            }
        } else if (this.direction == Direction.DOWN) {
            for (int i = currentFloor; i >= 0; i--) {
                if (floorsToVisit[i]) {
                    doNotChangeDirection = true;
                    break;
                }
            }
        }
        if (!doNotChangeDirection){
            this.direction = currentFloor == desiredFloor ? Direction.ZERO :
                    (currentFloor > desiredFloor ? Direction.DOWN : Direction.UP);
        }


    }

    public void setCurrentFloor(int currentFloor) {
//        printButtons();
        this.currentFloor = currentFloor;
        updateDirection();
//        if (!isAnyFloorIsPressed()){
//            this.direction = currentFloor == desiredFloor ? Direction.ZERO :
//                    (currentFloor > desiredFloor ? Direction.DOWN : Direction.UP);
//        }else {
//            this.direction = currentFloor > desiredFloor ? Direction.DOWN : Direction.UP;
//        }

    }

    public void setDesiredFloor(int desiredFloor) {
        this.desiredFloor = desiredFloor;

        updateDirection();
//        if (!isAnyFloorIsPressed()){
//            this.direction = currentFloor == desiredFloor ? Direction.ZERO :
//                    (currentFloor > desiredFloor ? Direction.DOWN : Direction.UP);
//        }else {
//            this.direction = currentFloor > desiredFloor ? Direction.DOWN : Direction.UP;
//        }
    }
}
