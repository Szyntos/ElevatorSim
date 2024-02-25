package org.example.interfaces;

import java.util.List;

public interface Floor {
    int getID();
    boolean isUpPressed();
    boolean isDownPressed();
    void pressUp();
    void pressDown();
    void addElevatorSystem(ElevatorSystem elevatorSystem);
    void addPerson(Person person);
    List<Person> getPeople();
    void addElevator();
    void delElevator();
    void openDoors(Elevator elevator);
    void closeDoors(Elevator elevator);
    void update();
}
