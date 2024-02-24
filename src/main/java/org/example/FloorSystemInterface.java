package org.example;

public interface FloorSystemInterface {
    void addPersonToFloor(int floorID, int desiredFloorID);

    void spawnRandomPerson();
}

@FunctionalInterface
interface AddPersonToFloor {
    void addPersonToFloor(int floorID, int desiredFloorID);
}

