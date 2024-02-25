package org.example;


public interface ElevatorSystemInterface {
    void step();

    void pickup(int fromFloor, Direction direction);

    int[] status(int elevatorID);

}


