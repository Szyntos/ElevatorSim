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
    Color personColor = new Color(0, 0, 0);
    PersonDrawer personDrawer;
    int floorCount;


    public ElevatorDrawer(Elevator elevator, int elevatorCount, int floorCount, PApplet parent){
        this.elevatorCount = elevatorCount;
        this.floorCount = floorCount;
        this.elevator = elevator;
        this.parent = parent;
        this.personDrawer = new PersonDrawer(parent);
    }

    public void updatePosition(){
        float floorHeight = (parent.height * 0.95f)/(floorCount * 2);
        int floorWidth = (int) (parent.width * 0.7f);
        setPosition((int) ( (parent.width * 0.3f) + floorWidth / (elevatorCount) * (elevator.getID() + 0.5) - width/2),
                (int) (parent.height - floorHeight/2f - elevator.getCurrentFloor() * floorHeight*2f - height));
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
        int rows = elevator.getCapacity()/3+1;
        for (Person person : elevator.getPassengers()) {
            if (j > rows - 1) {
                j = 0;
                k++;
            }
            personColor.setToFloor(person.desiredFloor.ID, floorCount);
            personDrawer.setColor(personColor);
            personDrawer.setID(person.ID);
            personDrawer.setPosition((int) (this.x - (k+0.5f) * personDrawer.getWidth() * 2 + this.width),
                    (int) (this.y - (j+0.75f) * personDrawer.getHeight() + this.height));
            personDrawer.draw();
            j++;
        }
//        parent.textAlign(parent.CENTER);
//        parent.textSize(20);
//        parent.fill(0);
//        parent.text(elevator.getDirection().toString() + "\n" + elevator.getPassengersCount(), x + width, y + height);
    }

    @Override
    public void draw() {
        drawElevator();
    }
}
