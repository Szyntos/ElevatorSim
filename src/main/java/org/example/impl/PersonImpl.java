package org.example.impl;

import org.example.interfaces.Elevator;
import org.example.interfaces.Floor;
import org.example.interfaces.Person;

public class PersonImpl implements Person {
    public int ID;
    public Floor floor;
    public Floor desiredFloor;
    private final Direction direction;
    private boolean isWaiting = true;
    private Elevator elevator;
    public PersonImpl(int ID, Floor floor, Floor desiredFloor){
        this.ID = ID;
        this.floor = floor;
        this.desiredFloor = desiredFloor;
        this.direction = floor.getID() > desiredFloor.getID() ? Direction.DOWN :
                ( floor.getID() == desiredFloor.getID() ? Direction.ZERO : Direction.UP);
    }

    public Direction getDirection() {
        return direction;
    }

    public Floor getDesiredFloor() {
        return desiredFloor;
    }

    @Override
    public void setDesiredFloor(Floor floor) {
        desiredFloor = floor;
    }

    public void update(){
        if (isWaiting){
            pushButton();
        } else {
            if (!this.elevator.isFloorButtonsPressed(this.desiredFloor.getID())){
                this.elevator.pushFloorButton(this.desiredFloor.getID());
            }
        }
    }

    public void pushButton(){
        switch (direction){
            case UP -> {
                if (!floor.isUpPressed()){
                    floor.pressUp();
                }
            }
            case DOWN -> {
                if (!floor.isDownPressed()){
                    floor.pressDown();
                }
            }
        }
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public int getID() {
        return ID;
    }

    public void setElevator(Elevator elevator){
        this.elevator = elevator;
        this.isWaiting = false;
    }
}
