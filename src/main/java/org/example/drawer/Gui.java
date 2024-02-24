package org.example.drawer;

import org.example.ElevatorSystem;
import processing.core.PApplet;

public class Gui implements Drawable{
    PApplet parent;
    ElevatorSystem system;
    Color jacaranda = new Color(33, 2, 25);
    Color melanzane = new Color(52, 8, 41);
    Color stiletto = new Color(149, 44, 77);
    Color newYorkPink = new Color(219, 117, 120);
    Color harvestGold = new Color(220, 171, 106);
    Button playButton;
    Button stepButton;




    public Gui(ElevatorSystem system, PApplet parent){
        this.parent = parent;
        this.system = system;
        this.playButton = new Button(Shape.CIRCLE, parent);
        this.playButton.setIcon(Shape.TRIANGLE);
        this.stepButton = new Button(Shape.SQUARE, parent);
        this.stepButton.setIcon(Shape.FASTFORWARD);
    }

    private void drawMainBody(){
        parent.fill(jacaranda.r, jacaranda.g, jacaranda.b);
        parent.rect(0, 0, parent.width, parent.height*0.05f);
        parent.fill(melanzane.r, melanzane.g, melanzane.b);
        parent.rect(0, parent.height*0.05f, parent.width*0.3f, parent.height*0.95f);
        parent.fill(255);
        parent.textAlign(parent.LEFT);
        parent.textSize(parent.height*0.05f*0.75f);
        parent.text("Elevator System", 0, parent.height*0.05f*0.75f);
    }

    private void drawControlButtons(){
        this.playButton.setPosition((int) (parent.width*0.5f-parent.height*0.05f), 0);
        this.playButton.setDimensions((int) (parent.height*(0.05f)), (int) (parent.height*(0.05f)));
        this.stepButton.setPosition((int) (parent.width*(0.5f)), 0);
        this.stepButton.setDimensions((int) (parent.height*(0.05f)), (int) (parent.height*(0.05f)));
        this.playButton.update();
        this.playButton.draw();
        this.stepButton.update();
        this.stepButton.draw();
    }

    @Override
    public void draw() {
        drawMainBody();
        drawControlButtons();
    }
}
