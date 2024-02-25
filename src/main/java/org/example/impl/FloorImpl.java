package org.example.impl;

import org.example.interfaces.Elevator;
import org.example.interfaces.ElevatorSystem;
import org.example.interfaces.Floor;
import org.example.interfaces.Person;

import java.util.ArrayList;
import java.util.List;



public class FloorImpl implements Floor {
    private ElevatorSystem elevatorSystem;
    public int ID;
    boolean upPressed = false;
    boolean downPressed = false;

    List<Elevator> upElevators = new ArrayList<>();
    List<Elevator> downElevators = new ArrayList<>();

    List<Person> people = new ArrayList<>();

    public FloorImpl(int ID){
        this.ID = ID;
    }

    public void addElevatorSystem(ElevatorSystem elevatorSystem){
        this.elevatorSystem = elevatorSystem;
    }



    public void addPerson(Person person){
        people.add(person);
    }

    public void pressUp(){
        if (elevatorSystem != null){
            upPressed = true;
            elevatorSystem.pickup(ID, Direction.UP);
        }
    }

    public void pressDown(){
        if (elevatorSystem != null){
            downPressed = true;
            elevatorSystem.pickup(ID, Direction.DOWN);
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }


    public int getID() {
        return ID;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void openDoors(Elevator elevator){
        PersonImpl[] boardingPeople;


        if (elevator.getDirection() == Direction.ZERO){
            // Select the more numerous group to board
            PersonImpl[] upBoardingPeople = people.stream()
                    .filter(person -> person.isWaiting() && person.getDirection() == Direction.UP)
                    .toArray(PersonImpl[]::new);

            PersonImpl[] downBoardingPeople = people.stream()
                    .filter(person -> person.isWaiting() && person.getDirection() == Direction.DOWN)
                    .toArray(PersonImpl[]::new);
            boardingPeople = upBoardingPeople.length > downBoardingPeople.length ? upBoardingPeople : downBoardingPeople;

        }else{
            boardingPeople = people.stream()
                    .filter(person -> person.isWaiting() && person.getDirection() == elevator.getDirection())
                    .toArray(PersonImpl[]::new);
        }

        // Add the elevator to the relevant group
        switch (elevator.getDirection()){
            case UP -> {
                upPressed = false;
                if (!doesElevatorAlreadyExistInList(elevator, upElevators)){
                    upElevators.add(elevator);
                }
            }
            case DOWN -> {
                downPressed = false;
                if (!doesElevatorAlreadyExistInList(elevator, downElevators)){
                    downElevators.add(elevator);
                }
            }
            case ZERO -> {
                upPressed = false;
                if (!doesElevatorAlreadyExistInList(elevator, upElevators)){
                    upElevators.add(elevator);
                }

                downPressed = false;
                if (!doesElevatorAlreadyExistInList(elevator, downElevators)){
                    downElevators.add(elevator);
                }
            }
        }

        for (PersonImpl person :
                boardingPeople) {
            setElevatorForPerson(person);
        }

    }

    public boolean doesElevatorAlreadyExistInList(Elevator elevator, List<Elevator> elevatorList){
        for (Elevator e :
                elevatorList) {
            if (e == elevator) {
                return true;
            }
        }
        return false;
    }


    public void closeDoors(Elevator elevator){
        removeElevatorFromList(elevator, upElevators);
        removeElevatorFromList(elevator, downElevators);
    }

    public void removeElevatorFromList(Elevator elevatorToDelete, List<Elevator> elevatorList){
        boolean elevatorExists = false;

        for (Elevator elevator :
                elevatorList) {
            if (elevator == elevatorToDelete) {
                elevatorExists = true;
                break;
            }
        }

        while (elevatorExists){
            elevatorExists = false;
            for (Elevator elevator :
                    elevatorList) {
                if (elevator == elevatorToDelete) {
                    elevatorExists = true;
                    break;
                }
            }
            elevatorList.remove(elevatorToDelete);
        }
    }

    public void setElevatorForPerson(PersonImpl person){
        switch (person.getDirection()){
            case UP -> transferPerson(person, upElevators);
            case DOWN -> transferPerson(person, downElevators);
        }
    }

    public void transferPerson(PersonImpl person, List<Elevator> elevatorList){
        for (Elevator elevator : elevatorList) {
            if (elevator.canAddPassenger()) {
                person.setElevator(elevator);
                elevator.addPassenger(person);
                people.remove(person);
                return;
            }
        }
    }

    public void addElevator(){

    }

    public void delElevator(){
        upElevators.clear();
        downElevators.clear();
        pressUp();
        pressDown();
    }

    public List<Person> getPeople(){
        return people;
    }

    public void update(){
        for (Person person : people) {
            person.update();
        }
    }
}
