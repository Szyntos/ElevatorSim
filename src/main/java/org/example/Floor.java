package org.example;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;



public class Floor {
    private final PApplet parent;
    private final ElevatorSystem elevatorSystem;
    public int ID;
    int x;
    int y;
    int width;
    int height;
    boolean upPressed = false;
    boolean downPressed = false;
    List<Elevator> upElevators = new ArrayList<>();
    List<Elevator> downElevators = new ArrayList<>();
    List<Person> people = new ArrayList<>();
    Color color = new Color(0, 0, 0);
    public Floor(int ID, ElevatorSystem elevatorSystem, PApplet parent){
        this.ID = ID;
        this.elevatorSystem = elevatorSystem;
        this.parent = parent;
        this.color.setHSL(1/(double)(elevatorSystem.floorCount+1) * ID,1, 0.5);
    }



    public void addPerson(Person person){
        people.add(person);
    }

    public void pressUp(){
        upPressed = true;
        elevatorSystem.pickup(ID, Direction.UP);
    }

    public void pressDown(){
        downPressed = true;
        elevatorSystem.pickup(ID, Direction.DOWN);
    }


    public void openDoors(Elevator elevator){
        Person[] boardingPeople;
        Person[] upBoardingPeople;
        Person[] downBoardingPeople;
//        if (!elevator.isMoving()){
//            boardingPeople = people.stream()
//                    .filter(person -> person.isWaiting() && !person.isElevatorSet())
//                    .toArray(Person[]::new);
//            upElevators.add(elevator);
//            downElevators.add(elevator);
//            downPressed = false;
//            upPressed = false;
//        } else {
//
//        }
        if (elevator.getDirection() == Direction.ZERO){
            upBoardingPeople = people.stream()
                    .filter(person -> person.isWaiting() && !person.isElevatorSet() &&
                            person.getDirection() == Direction.UP)
                    .toArray(Person[]::new);
            downBoardingPeople = people.stream()
                    .filter(person -> person.isWaiting() && !person.isElevatorSet() &&
                            person.getDirection() == Direction.DOWN)
                    .toArray(Person[]::new);
            if (upBoardingPeople.length > downBoardingPeople.length){
                boardingPeople = upBoardingPeople;
            } else {
                boardingPeople = downBoardingPeople;
            }
        }else{
            boardingPeople = people.stream()
                    .filter(person -> person.isWaiting() && !person.isElevatorSet() &&
                            person.getDirection() == elevator.getDirection())
                    .toArray(Person[]::new);
        }


        if (elevator.getDirection() == Direction.UP){
            upPressed = false;
            upElevators.add(elevator);

        } else if (elevator.getDirection() == Direction.DOWN){
            downPressed = false;
            downElevators.add(elevator);
        } else {
            upPressed = false;
            upElevators.add(elevator);
            downPressed = false;
            downElevators.add(elevator);
        }


        for (Person person :
                boardingPeople) {
            setElevatorForPerson(person);
        }

    }

    public void addPassengersToElevator(){

    }

    public void closeDoors(Elevator elevator, Direction direction){
        upElevators.remove(elevator);
        downElevators.remove(elevator);
    }

    public boolean setElevatorForPerson(Person person){
        Elevator elevator;
        if (person.getDirection() == Direction.UP){
            for (Elevator upElevator : upElevators) {
                elevator = upElevator;
                if (elevator.canAddPassenger()) {
                    person.setElevator(elevator);
                    elevator.addPassenger(person);
                    people.remove(person);
                    return true;
                }
            }
        } else {
            for (Elevator downElevator : downElevators) {
                elevator = downElevator;
                if (elevator.canAddPassenger()) {
                    person.setElevator(elevator);
                    elevator.addPassenger(person);
                    people.remove(person);
                    return true;
                }

            }
        }
        return false;
    }

    public void update(){
        for (Person person : people) {
            person.update();
        }
    }



    public void draw(){
        parent.fill(color.r, color.g, color.b);
        parent.rect(this.x, this.y, this.width, this.height);
        int rows = 3;
        int j = 0;
        int k = 0;
        for (Person person : people) {
            if (j > rows - 1) {
                j = 0;
                k++;
            }
            person.draw(this.x - k * person.getWidth() * 2, this.y - j * person.getHeight());
            j++;
        }
    }

    public void setPosition(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getYPos(){
        return this.y;
    }

    public int getXPos(){
        return this.x;
    }
}
