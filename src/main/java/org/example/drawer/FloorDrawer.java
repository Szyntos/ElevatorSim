package org.example.drawer;

import org.example.Floor;
import org.example.Person;
import processing.core.PApplet;

public class FloorDrawer implements Drawable {
    Floor floor;
    PApplet parent;
    int x;
    int y;
    int width;
    int height;
    int floorCount;
    Color floorColor = new Color(0, 0, 0);
    Color personColor = new Color(0, 0, 0);
    PersonDrawer personDrawer;
    public FloorDrawer(Floor floor, int floorCount, PApplet parent){
        this.floorCount = floorCount;
        this.floor = floor;
        this.parent = parent;
        int floorHeight = parent.height/(floorCount * 2);
        int floorWidth = parent.width;
        setPosition(0, parent.height - parent.height / floorCount * (floor.ID) - floorHeight);
        setDimensions(floorWidth, floorHeight/2);
        this.floorColor.setToFloor(floor.ID, floorCount);
        this.personDrawer = new PersonDrawer(parent);
    }

    public void update(){

    }

    private void drawFloor(){
        parent.fill(floorColor.r, floorColor.g, floorColor.b);
        parent.rect(this.x, this.y, this.width, this.height);
        int rows = 3;
        int j = 0;
        int k = 0;
        for (Person person : floor.getPeople()) {
            if (j > rows - 1) {
                j = 0;
                k++;
            }
            personColor.setToFloor(person.desiredFloor.ID, this.floorCount);
            personDrawer.setColor(personColor);
            personDrawer.setID(person.ID);
            personDrawer.setPosition(this.x - k * personDrawer.getWidth() * 2 + parent.width/3, this.y - j * personDrawer.getHeight());
            personDrawer.draw();
            j++;
        }
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;

    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        drawFloor();
    }
}
