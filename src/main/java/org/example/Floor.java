package org.example;

import java.util.ArrayList;
import java.util.List;



public class Floor {
    private final ElevatorSystem elevatorSystem;
    public int ID;
    boolean upPressed = false;
    boolean downPressed = false;

    List<Elevator> upElevators = new ArrayList<>();
    List<Elevator> downElevators = new ArrayList<>();

    List<Person> people = new ArrayList<>();

    public Floor(int ID, ElevatorSystem elevatorSystem){
        this.ID = ID;
        this.elevatorSystem = elevatorSystem;
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


        if (elevator.getDirection() == Direction.ZERO){
            Person[] upBoardingPeople = people.stream()
                    .filter(person -> person.isWaiting() && person.getDirection() == Direction.UP)
                    .toArray(Person[]::new);

            Person[] downBoardingPeople = people.stream()
                    .filter(person -> person.isWaiting() && person.getDirection() == Direction.DOWN)
                    .toArray(Person[]::new);
            boardingPeople = upBoardingPeople.length > downBoardingPeople.length ? upBoardingPeople : downBoardingPeople;

        }else{
            boardingPeople = people.stream()
                    .filter(person -> person.isWaiting() && person.getDirection() == elevator.getDirection())
                    .toArray(Person[]::new);
        }

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

        for (Person person :
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
            upElevators.remove(elevatorToDelete);
        }
    }

    public void setElevatorForPerson(Person person){
        switch (person.getDirection()){
            case UP -> transferPerson(person, upElevators);
            case DOWN -> transferPerson(person, downElevators);
        }
    }

    public void transferPerson(Person person, List<Elevator> elevatorList){
        for (Elevator elevator : elevatorList) {
            if (elevator.canAddPassenger()) {
                person.setElevator(elevator);
                elevator.addPassenger(person);
                people.remove(person);
                return;
            }
        }
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
