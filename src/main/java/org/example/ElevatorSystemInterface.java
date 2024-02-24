package org.example;


public interface ElevatorSystemInterface {
    void addPersonToFloor(int floorID, int desiredFloorID);

    void step();

    void pickup(int fromFloor, Direction direction);

    int[] status(int elevatorID);

}

@FunctionalInterface
interface AddPersonToFloor {
    void addPersonToFloor(int floorID, int desiredFloorID);
}

@FunctionalInterface
interface Step {
    void step();
}

@FunctionalInterface
interface Pickup {
    void pickup(int fromFloor, Direction direction);
}
