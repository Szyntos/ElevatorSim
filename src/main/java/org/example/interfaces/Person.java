package org.example.interfaces;

import org.example.impl.Direction;

public interface Person {
    void update();
    boolean isWaiting();
    Direction getDirection();
    Floor getDesiredFloor();
    void setDesiredFloor(Floor floor);
    int getID();
}
