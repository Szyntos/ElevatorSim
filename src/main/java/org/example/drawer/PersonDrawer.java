package org.example.drawer;

import org.example.Person;
import processing.core.PApplet;

public final class PersonDrawer implements Drawable{
    PApplet parent;
    int x;
    int y;
    int width = 10;
    int height = 10;
    Color color = new Color(0, 0, 0);
    int ID;
    public PersonDrawer(PApplet parent){
        this.parent = parent;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setColor(Color color){
        this.color = color;
    }
    public void setID(int personID){
        this.ID = personID;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void draw() {
        parent.fill(color.r, color.g, color.b);
        parent.ellipse(x, y, width, height);
    }
}
