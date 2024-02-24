package org.example;

public class Person {
    public int ID;
    public Floor floor;
    public Floor desiredFloor;
    private final Direction direction;
    private boolean isWaiting = true;
    private Elevator elevator;
    public Person(int ID, Floor floor, Floor desiredFloor){
        this.ID = ID;
        this.floor = floor;
        this.desiredFloor = desiredFloor;
        this.direction = floor.ID > desiredFloor.ID ? Direction.DOWN :
                ( floor.ID == desiredFloor.ID ? Direction.ZERO : Direction.UP);
    }

    public Direction getDirection() {
        return direction;
    }

    public void update(){
        if (isWaiting){
            pushButton();
        } else {
            if (!this.elevator.isFloorButtonsPressed(this.desiredFloor.ID)){
                this.elevator.pushFloorButton(this.desiredFloor.ID);
            }
        }
    }

    public void pushButton(){
        switch (direction){
            case UP -> {
                if (!floor.upPressed){
                    floor.pressUp();
                }
            }
            case DOWN -> {
                if (!floor.downPressed){
                    floor.pressDown();
                }
            }
        }
    }

    public boolean isWaiting() {
        return isWaiting;
    }


    public void setElevator(Elevator elevator){
        this.elevator = elevator;
        this.isWaiting = false;
    }
}
