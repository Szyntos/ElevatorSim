package org.example.drawer;

import org.example.Main;
import org.example.SpawnRandomPerson;
import processing.core.PApplet;

public class Button implements Drawable{
    PApplet parent;
    Shape shape;
    Shape icon = Shape.TRIANGLE;
    Behaviour behaviour = Behaviour.NONE;
    private SpawnRandomPerson onClickSpawn;
    boolean isHovered = false;
    boolean mousePressedInside = false;
    boolean mouseReleasedInside = false;
    int x;
    int y;
    int width;
    int height;
    Color primaryColor = new Color(0, 0, 0);
    Color secondaryColor = new Color(255, 255, 255);
    Color iconColor = new Color(0, 255, 0);
    public Button(PApplet parent){
        this.parent = parent;
        this.shape = Shape.SQUARE;
    }
    public Button(Shape shape, PApplet parent){
        this.parent = parent;
        this.shape = shape;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }
    public void setIcon(Shape shape){
        this.icon = shape;
    }

    public void click() {
        switch (behaviour){
            case SPAWN -> {
                onClickSpawn.spawnRandomPerson();
            }
        }


    }

    public void setOnClickSpawn(SpawnRandomPerson onClickBehaviour) {
        this.onClickSpawn = onClickBehaviour;
        this.behaviour = Behaviour.SPAWN;
    }

    private void drawIcon(){
        parent.fill(iconColor.r, iconColor.g, iconColor.b);
        switch (icon){
            case TRIANGLE -> {
                parent.triangle(x + width * (0.5f - 1/4f), y + height * (0.5f - 1/4f),
                                x + width * (0.5f - 1/4f), y + height * (0.5f + 1/4f),
                                x + width * (0.5f + 1/4f), y + height * (0.5f));
            }
            case FASTFORWARD -> {
                parent.triangle(x + width * (0.5f - 1/4f - 2/14f), y + height * (0.5f - 1/4f),
                        x + width * (0.5f - 1/4f - 2/14f), y + height * (0.5f + 1/4f),
                        x + width * (0.5f + 1/4f - 2/14f), y + height * (0.5f));
                parent.triangle(x + width * (0.5f - 1/4f + 2/12f), y + height * (0.5f - 1/4f),
                        x + width * (0.5f - 1/4f + 2/12f), y + height * (0.5f + 1/4f),
                        x + width * (0.5f + 1/4f + 2/12f), y + height * (0.5f));
            }
        }
    }

    public void updateHover(){
        if (this.shape == Shape.CIRCLE) {
            this.isHovered = Math.pow(((x + width / 2f) - parent.mouseX), 2) +
                    Math.pow(((y + width / 2f) - parent.mouseY), 2) <= Math.pow(width / 2f, 2);
        } else {
            this.isHovered = parent.mouseX >= x && parent.mouseX <= x + width &&
                    parent.mouseY >= y && parent.mouseY <= y + height;
        }
    }
    public void updateClick(){
        if (isHovered){
            if (parent.mousePressed){
                mousePressedInside = true;
            }
            if (mousePressedInside && !parent.mousePressed){
                mousePressedInside = false;
                click();
            }
        } else {
            mousePressedInside = false;
        }
    }

    public void update(){
        updateHover();
        updateClick();
    }


    @Override
    public void draw() {
        if (isHovered){
            parent.fill(secondaryColor.r, secondaryColor.g, secondaryColor.b);
        } else{
            parent.fill(primaryColor.r, primaryColor.g, primaryColor.b);
        }

        if (this.shape == Shape.CIRCLE) {
            this.parent.ellipse(x+width/2f, y+height/2f, width, height);
        } else {
            this.parent.rect(x, y, width, height);
        }
        drawIcon();
    }
}
