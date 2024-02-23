package org.example;

import processing.core.PApplet;

public class Person {
    private final PApplet parent;
    public int ID;
    public Floor floor;
    public Floor desiredFloor;
    private final int width = 10;
    private final int height = 10;
    private final Direction direction;
    private boolean isWaiting = true;
    private boolean isElevatorSet = false;
    private Elevator elevator;
    Color color = new Color(255, 255, 0);
    public Person(int ID, Floor floor, Floor desiredFloor, PApplet parent){
        this.ID = ID;
        this.floor = floor;
        this.desiredFloor = desiredFloor;
        this.parent = parent;
        this.direction = floor.ID > desiredFloor.ID ? Direction.DOWN :
                ( floor.ID == desiredFloor.ID ? Direction.ZERO : Direction.UP);
        this.color = desiredFloor.color;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public Direction getDirection() {
        return direction;
    }

    public void update(){
        if (isWaiting){
            if (direction == Direction.UP){
                if (!floor.upPressed){
                    floor.pressUp();
                }
            } else {
                if (!floor.downPressed){
                    floor.pressDown();
                }
            }
        } else {
            if (!this.elevator.isFloorButtonsPressed(this.desiredFloor.ID)){
                this.elevator.pushFloorButton(this.desiredFloor.ID);
            }

        }

    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public boolean isElevatorSet() {
        return isElevatorSet;
    }

    public void setElevator(Elevator elevator){
        this.elevator = elevator;
        this.isWaiting = false;
        this.isElevatorSet = true;
    }

    public void draw(int x, int y){
        parent.fill(color.r, color.g, color.b);
        parent.ellipse(x, y, width, height);
    }
}
