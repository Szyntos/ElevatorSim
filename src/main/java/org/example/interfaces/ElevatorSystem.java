package org.example.interfaces;


import org.example.impl.Direction;

public interface ElevatorSystem {
    void step();

    void pickup(int fromFloor, Direction direction);

    int[] status(int elevatorID);

    int getElevatorCount();
    Elevator[] getElevators();
    void addFloor();
    void delFloor();
    void incrementElevatorCount();
    void decrementElevatorCount();
    void incrementElevatorCapacity();
    void decrementElevatorCapacity();
    int getElevatorCapacity();

}


