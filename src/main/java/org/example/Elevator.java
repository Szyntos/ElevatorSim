package org.example;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private int currentFloor = 0;
    private int desiredFloor = 0;
    private Floor[] allFloors;
    private boolean[] floorButtonsPressed;
    private boolean[] pickupUpDirections;
    private boolean[] pickupDownDirections;
    private boolean[] floorsToVisit;
    private int capacity;
    private final int ID;
    private boolean isMoving = false;
    private final List<Person> passengers = new ArrayList<>();
    private Direction direction = Direction.ZERO;
    public Elevator(int ID, int capacity, Floor[] allFloors){
        this.ID = ID;
        this.capacity = capacity;
        this.allFloors = allFloors;
        this.floorButtonsPressed = new boolean[allFloors.length];


        this.pickupUpDirections = new boolean[allFloors.length];
        this.pickupDownDirections = new boolean[allFloors.length];

        this.floorsToVisit = new boolean[allFloors.length];

        for (int i = 0; i < allFloors.length; i++) {
            floorButtonsPressed[i] = false;
            pickupUpDirections[i] = false;
            pickupDownDirections[i] = false;
            floorsToVisit[i] = false;
        }
    }

    public void addPassenger(Person passenger){
        if (!canAddPassenger()){
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
            }

            move();

        } else {
            if (!isAnyFloorPressed()){
                isMoving = false;
                direction = Direction.ZERO;
            }

            dropOffPassengers();
            allFloors[currentFloor].openDoors(this);
            allFloors[currentFloor].closeDoors(this);
        }

        for (Person person : passengers) {
            person.update();
        }

        setNewDesiredFloor();
    }

    public void move(){
        if (direction == Direction.UP){
            setCurrentFloor(currentFloor + 1);
        } else if (direction == Direction.DOWN){
            setCurrentFloor(currentFloor - 1);
        } else {
            setCurrentFloor(currentFloor);
        }
    }

    public boolean isAnyFloorPressed(){
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

    public void updateFloorsToVisit(){
        Direction directionToPickUp;
        Direction directionToNextButtonPress = checkDirectionToNextStop(floorButtonsPressed);

        // Set the chosen floors as to-be-visited
        for (int i = 0; i < floorsToVisit.length; i++) {
            if (floorButtonsPressed[i]){
                floorsToVisit[i] = true;
            }
        }

        // Set suitable pickups as to-be-visited
        // Elevator can pick up passengers, that want to go in the same direction as itself, from floors above itself

        switch (this.direction){
            case UP -> {

                for (int i = currentFloor; i < allFloors.length; i++) {
                    if (pickupUpDirections[i]){
                        if (i == currentFloor && !canAddPassenger()){
                            continue;
                        }
                        floorsToVisit[i] = true;
                        pickupUpDirections[i] = false;
                    }
                }

            }
            case DOWN -> {

                for (int i = currentFloor; i >= 0; i--) {
                    if (pickupDownDirections[i]){
                        if (i == currentFloor && !canAddPassenger()){
                            continue;
                        }
                        floorsToVisit[i] = true;
                        pickupDownDirections[i] = false;
                    }
                }

            }
            case ZERO -> {
                // If Elevator is stationary it can pick up people on its way to the next floor
                for (int i = 0; i < allFloors.length; i++) {

                    directionToPickUp = currentFloor == i ? Direction.ZERO :
                            (currentFloor > i ? Direction.DOWN : Direction.UP);

                    switch (directionToPickUp){
                        case ZERO -> {

                            pickupUpDirections[i] = false;
                            pickupDownDirections[i] = false;

                        }
                        case UP -> {

                            if (pickupUpDirections[i] && directionToPickUp == directionToNextButtonPress){
                                floorsToVisit[i] = true;
                                pickupUpDirections[i] = false;
                            }

                        }
                        case DOWN -> {

                            if (pickupDownDirections[i] && directionToPickUp == directionToNextButtonPress){
                                floorsToVisit[i] = true;
                                pickupDownDirections[i] = false;
                            }

                        }
                    }

                }

                // If the elevator has no to-be-visited-floors it sets one of the pickups as to-be-visited
                if (!isAnyFloorToBeVisited()){

                    for (int i = 0; i < allFloors.length; i++) {
                        if (pickupUpDirections[i]){
                            floorsToVisit[i] = true;
                            break;
                        } else if (pickupDownDirections[i]){
                            floorsToVisit[i] = true;
                            break;
                        }
                    }

                }
            }
        }
    }

    public Direction checkDirectionToNextStop(boolean[] floors){
        Direction direction = Direction.ZERO;
        int distanceUp = 0;
        int distanceDown = 0;
        for (int i = currentFloor; i < floors.length; i++) {
            if (floors[i]) {
                direction = Direction.UP;
                distanceUp++;
                break;
            }
        }
        for (int i = currentFloor; i >= 0; i--) {
            if (floors[i]) {
                direction = Direction.DOWN;
                distanceDown++;
                break;
            }
        }
        return direction == Direction.ZERO ? Direction.ZERO :
                (distanceUp > distanceDown ? Direction.UP : Direction.DOWN);
    }

    public void setNewDesiredFloor(){
        updateFloorsToVisit();
        Direction directionToNextFloor = checkDirectionToNextStop(floorsToVisit);

        // New desired floor should be the first floor in the direction of travel
        switch (this.direction){
            case UP -> {

                for (int i = currentFloor; i < allFloors.length; i++) {
                    if (isFloorToBeVisited(i)){
                        setDesiredFloor(i);
                        return;
                    }
                }

            }
            case DOWN -> {

                for (int i = currentFloor; i >= 0; i--) {
                    if (isFloorToBeVisited(i)){
                        setDesiredFloor(i);
                        return;
                    }
                }

            }
            case ZERO -> {

                for (int i = 0; i < allFloors.length; i++) {
                    if (isFloorToBeVisited(i)){
                        setDesiredFloor(i);
                        return;
                    }
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

    public boolean isMoving() {
        return isMoving;
    }


    public void call(int toFloor, Direction direction){
        if (direction == Direction.UP){
            pickupUpDirections[toFloor] = true;
        } else if (direction == Direction.DOWN) {
            pickupDownDirections[toFloor] = true;
        }
    }

    public boolean delPassenger(Person passenger){
        return passengers.remove(passenger);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity){

        this.capacity = capacity;
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

    public int getDistanceToLastStop(){
        int maxDistance = 0;
        for (int i = 0; i < floorsToVisit.length; i++) {
            if (floorsToVisit[i]){
                maxDistance = Math.max(maxDistance, Math.abs(currentFloor - i));
            }
        }
        return maxDistance;
    }



    public int getETA(int toFloor, Direction direction){
        int multiplier = 10;

        // Penalty is computed as twice the sum of the amount of floors already in the queue and distance to the last stop
        // It is added when it is not suitable to pick this elevator for this particular pickup
        int penalty = (getToVisitCount() + getDistanceToLastStop()) + multiplier * 2 + 1;

        int distance = Math.abs(currentFloor - toFloor)*multiplier + 1;

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
        switch (direction){
            case UP -> {

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

            }
            case DOWN -> {

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
            default -> {
                return distance;
            }
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public int getPassengersCount() {
        return passengers.size();
    }

    public List<Person> getPassengers(){
        return passengers;
    }

    public int getDesiredFloor() {
        return desiredFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void updateDirection(){

        // This prevents the elevator from changing its direction to ZERO when, in fact, it is moving
        boolean doNotChangeDirection = false;

        switch (this.direction){
            case UP -> {
                for (int i = currentFloor; i < floorsToVisit.length; i++) {
                    if (floorsToVisit[i]) {
                        doNotChangeDirection = true;
                        break;
                    }
                }
            }
            case DOWN -> {
                for (int i = currentFloor; i >= 0; i--) {
                    if (floorsToVisit[i]) {
                        doNotChangeDirection = true;
                        break;
                    }
                }
            }
            default -> {

            }
        }
        if (!doNotChangeDirection){
            this.direction = currentFloor == desiredFloor ? Direction.ZERO :
                    (currentFloor > desiredFloor ? Direction.DOWN : Direction.UP);
        }
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
        updateDirection();
    }

    public void setDesiredFloor(int desiredFloor) {
        this.desiredFloor = desiredFloor;
        updateDirection();
    }

    public static boolean[] addFalseToEnd(boolean[] array) {
        // Create a new array with size one element greater than the original array
        boolean[] newArray = new boolean[array.length + 1];

        // Copy elements from the original array to the new array
        System.arraycopy(array, 0, newArray, 0, array.length);

        // Assign 'false' to the last element of the new array
        newArray[newArray.length - 1] = false;

        // Return the modified array
        return newArray;
    }

    public static boolean[] removeLastElement(boolean[] array) {
        // Check if the array is empty or has only one element
        if (array == null || array.length <= 1) {
            return new boolean[0]; // return an empty array or the original array if it's empty or has only one element
        }

        // Create a new array with size one element smaller than the original array
        boolean[] newArray = new boolean[array.length - 1];

        // Copy elements from the original array to the new array, excluding the last element
        System.arraycopy(array, 0, newArray, 0, newArray.length);

        // Return the modified array
        return newArray;
    }

    public void addFloor() {
        this.passengers.clear();
        floorButtonsPressed = addFalseToEnd(floorButtonsPressed);
        pickupUpDirections = addFalseToEnd(pickupUpDirections);
        pickupDownDirections = addFalseToEnd(pickupDownDirections);
        floorsToVisit = addFalseToEnd(floorsToVisit);
    }
    public void delFloor() {
        if (this.getCurrentFloor() == floorButtonsPressed.length-1){
            this.currentFloor = floorButtonsPressed.length-2;
        }
        if (this.getDesiredFloor() == floorButtonsPressed.length-1){
            this.desiredFloor = floorButtonsPressed.length-2;
        }
        this.passengers.clear();
        floorButtonsPressed = removeLastElement(floorButtonsPressed);
        pickupUpDirections = removeLastElement(pickupUpDirections);
        pickupDownDirections = removeLastElement(pickupDownDirections);
        floorsToVisit = removeLastElement(floorsToVisit);
    }
    public void setAllFloors(Floor[] floors){
        allFloors = floors;
    }
}
