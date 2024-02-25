package org.example.interfaces;

import org.example.impl.Direction;

import java.util.List;

public interface Elevator {
    boolean isFloorButtonsPressed(int floorID);
    void pushFloorButton(int floorID);
    boolean canAddPassenger();
    void addPassenger(Person person);
    int getCurrentFloor();
    int getID();
    int getCapacity();
    List<Person> getPassengers();
    void update();
    int getPassengersCount();
    int getDesiredFloor();
    void setDesiredFloor(int floorID);
    void setCurrentFloor(int floorID);
    int getETA(int fromFloor, Direction direction);
    void call(int fromFloor, Direction direction);
    void addFloor();
    void delFloor();
    void setAllFloors(Floor[] floors);
    void setCapacity(int capacity);
    Direction getDirection();
}
