package org.example.interfaces;

import org.example.impl.Direction;

public interface FloorSystem {
    void addPersonToFloor(int floorID, int desiredFloorID);

    Floor[] getFloors();
    void addElevator();
    void delElevator();
    int getFloorCount();
    void pressButton(int floorID, Direction direction);
    void incrementFloorCount();
    void decrementFloorCount();
    int[] status(int floorID);
    void spawnRandomPerson();
}

