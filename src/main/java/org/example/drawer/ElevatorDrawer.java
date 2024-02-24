package org.example.drawer;

import org.example.Elevator;
import org.example.Person;
import org.example.drawer.Color;
import org.example.drawer.Drawable;
import org.example.drawer.PersonDrawer;
import processing.core.PApplet;

public class ElevatorDrawer implements Drawable {

    Elevator elevator;
    PApplet parent;
    int x;
    int y;
    int width;
    int height;
    int elevatorCount;
    Color floorColor = new Color(0, 0, 0);
    Color personColor = new Color(0, 0, 0);
    PersonDrawer personDrawer;
    int floorCount;


    public ElevatorDrawer(Elevator elevator, int elevatorCount, int floorCount, PApplet parent){
        this.elevatorCount = elevatorCount;
        this.floorCount = floorCount;
        this.elevator = elevator;
        this.parent = parent;
        this.floorColor.setToFloor(elevator.getID(), elevatorCount);
        this.personDrawer = new PersonDrawer(parent);
    }

    public void updatePosition(){
        setPosition(parent.width/elevatorCount/2 * elevator.getID() + parent.width/5 * 2,
                parent.height - parent.height / floorCount * (elevator.getCurrentFloor())
                        - parent.height/(floorCount * 2) - height);
    }



    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void drawElevator(){
        parent.fill(95, 108, 133);
        parent.rect(this.x, this.y, this.width, this.height);
        int j = 0;
        int k = 0;
        int rows = 3;
        for (Person person : elevator.getPassengers()) {
            if (j > rows - 1) {
                j = 0;
                k++;
            }
            personColor.setToFloor(person.desiredFloor.ID, floorCount);
            personDrawer.setColor(personColor);
            personDrawer.setID(person.ID);
            personDrawer.setPosition(this.x - k * personDrawer.getWidth() * 2, this.y - j * personDrawer.getHeight());
            personDrawer.draw();
            j++;
        }
        parent.textAlign(parent.CENTER);
        parent.textSize(20);
        parent.fill(0);
        parent.text(elevator.getDirection().toString() + "\n" + elevator.getPassengersCount(), x + width, y + height);
    }

    @Override
    public void draw() {
        drawElevator();
    }
}
