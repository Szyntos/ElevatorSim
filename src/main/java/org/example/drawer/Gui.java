package org.example.drawer;

import org.example.*;
import org.example.impl.Direction;
import org.example.interfaces.ElevatorSystem;
import org.example.interfaces.FloorSystem;
import processing.core.PApplet;

public class Gui implements Drawable{
    PApplet parent;
    Main simulation;
    ElevatorSystem elevatorSystem;
    FloorSystem floorSystem;
    int selectedElevatorID = 0;
    int selectedFloorID = 0;
    Color headerColor = new Color(57, 2, 49);
    Color panelColor = new Color(99, 27, 50);
    Color elevatorInfoColor = new Color(163, 64, 61);
    Color floorInfoColor = new Color(179, 79, 94);
    Color setupColor = new Color(149, 37, 53);
    Color playColor = new Color(37, 150, 24);
    Color stopColor = new Color(130, 130, 130);
    Color buttonPrimaryColor = new Color(102, 40, 93);
    Color buttonSecondaryColor = new Color(130, 50, 120);
    Color white = new Color(255, 255, 255);
    Color arrowColor = new Color(222, 57, 110);
    Color arrowButtonPrimaryColor = new Color(149, 44, 77);
    Color arrowButtonSecondaryColor = new Color(163, 80, 106);

    Button playButton;
    Button stepButton;
    Button spawnButton;
    Button elevatorPlusButton;
    Button elevatorMinusButton;
    Button floorPlusButton;
    Button floorMinusButton;
    Button elevatorCountPlusButton;
    Button elevatorCountMinusButton;
    Button floorCountPlusButton;
    Button floorCountMinusButton;
    Button elevatorCapacityMinusButton;
    Button elevatorCapacityPlusButton;
    Button[] upButtons;
    Button[] downButtons;
    Button[] spawnButtons;
    int[] elevatorInformation;
    int[] floorInformation;





    public Gui(FloorSystem floorSystem, ElevatorSystem elevatorSystem, PApplet parent, Main main){
        this.simulation = main;
        this.parent = parent;
        this.floorSystem = floorSystem;
        this.elevatorSystem = elevatorSystem;

        this.playButton = new Button(Shape.CIRCLE, parent);
        this.playButton.setOnClickBehaviour(main::playPauseSimulation, Behaviour.PLAY);
        this.playButton.setIcon(Shape.STOP);
        this.playButton.setIconColor(stopColor);
        this.playButton.setAltIconColor(playColor);
        this.playButton.setPrimaryColor(buttonPrimaryColor);
        this.playButton.setSecondaryColor(buttonSecondaryColor);

        this.stepButton = new Button(Shape.CIRCLE, parent);
        this.stepButton.setOnClickBehaviour(elevatorSystem::step, Behaviour.STEP);
        this.stepButton.setIcon(Shape.FASTFORWARD);
        this.stepButton.setIconColor(playColor);
        this.stepButton.setPrimaryColor(buttonPrimaryColor);
        this.stepButton.setSecondaryColor(buttonSecondaryColor);

        this.spawnButton = new Button(Shape.SQUARE, parent);
        this.spawnButton.setOnClickBehaviour(floorSystem::spawnRandomPerson, Behaviour.SPAWN);
        this.spawnButton.setIcon(Shape.PLUS);
        this.spawnButton.setIconColor(setupColor);
        this.spawnButton.setPrimaryColor(buttonPrimaryColor);
        this.spawnButton.setSecondaryColor(buttonSecondaryColor);

        this.elevatorPlusButton = new Button(Shape.SQUARE, parent);
        this.elevatorPlusButton.cycleBackground();
        this.elevatorPlusButton.setIcon(Shape.PLUS);
        this.elevatorPlusButton.setIconColor(white);
        this.elevatorPlusButton.setOnClickBehaviour(this::incrementElevatorID, Behaviour.INCREMENT);

        this.elevatorMinusButton = new Button(Shape.SQUARE, parent);
        this.elevatorMinusButton.cycleBackground();
        this.elevatorMinusButton.setIcon(Shape.MINUS);
        this.elevatorMinusButton.setIconColor(white);
        this.elevatorMinusButton.setOnClickBehaviour(this::decrementElevatorID, Behaviour.DECREMENT);

        this.floorPlusButton = new Button(Shape.SQUARE, parent);
        this.floorPlusButton.cycleBackground();
        this.floorPlusButton.setIcon(Shape.PLUS);
        this.floorPlusButton.setIconColor(white);
        this.floorPlusButton.setOnClickBehaviour(this::incrementFloorID, Behaviour.INCREMENT);

        this.floorMinusButton = new Button(Shape.SQUARE, parent);
        this.floorMinusButton.cycleBackground();
        this.floorMinusButton.setIcon(Shape.MINUS);
        this.floorMinusButton.setIconColor(white);
        this.floorMinusButton.setOnClickBehaviour(this::decrementFloorID, Behaviour.DECREMENT);

        this.elevatorCountPlusButton = new Button(Shape.SQUARE, parent);
        this.elevatorCountPlusButton.cycleBackground();
        this.elevatorCountPlusButton.setIcon(Shape.PLUS);
        this.elevatorCountPlusButton.setIconColor(white);
        this.elevatorCountPlusButton.setOnClickBehaviour(this::incrementElevatorCount, Behaviour.INCREMENT);

        this.elevatorCountMinusButton = new Button(Shape.SQUARE, parent);
        this.elevatorCountMinusButton.cycleBackground();
        this.elevatorCountMinusButton.setIcon(Shape.MINUS);
        this.elevatorCountMinusButton.setIconColor(white);
        this.elevatorCountMinusButton.setOnClickBehaviour(this::decrementElevatorCount, Behaviour.DECREMENT);

        this.floorCountPlusButton = new Button(Shape.SQUARE, parent);
        this.floorCountPlusButton.cycleBackground();
        this.floorCountPlusButton.setIcon(Shape.PLUS);
        this.floorCountPlusButton.setIconColor(white);
        this.floorCountPlusButton.setOnClickBehaviour(this::incrementFloorCount, Behaviour.INCREMENT);

        this.floorCountMinusButton = new Button(Shape.SQUARE, parent);
        this.floorCountMinusButton.cycleBackground();
        this.floorCountMinusButton.setIcon(Shape.MINUS);
        this.floorCountMinusButton.setIconColor(white);
        this.floorCountMinusButton.setOnClickBehaviour(this::decrementFloorCount, Behaviour.DECREMENT);

        this.elevatorCapacityPlusButton = new Button(Shape.SQUARE, parent);
        this.elevatorCapacityPlusButton.cycleBackground();
        this.elevatorCapacityPlusButton.setIcon(Shape.PLUS);
        this.elevatorCapacityPlusButton.setIconColor(white);
        this.elevatorCapacityPlusButton.setOnClickBehaviour(this::incrementElevatorCapacity, Behaviour.INCREMENT);

        this.elevatorCapacityMinusButton = new Button(Shape.SQUARE, parent);
        this.elevatorCapacityMinusButton.cycleBackground();
        this.elevatorCapacityMinusButton.setIcon(Shape.MINUS);
        this.elevatorCapacityMinusButton.setIconColor(white);
        this.elevatorCapacityMinusButton.setOnClickBehaviour(this::decrementElevatorCapacity, Behaviour.DECREMENT);
        initializeButtonArrays();
    }

    private void initializeButtonArrays(){
        upButtons = new Button[floorSystem.getFloorCount() - 1];
        downButtons = new Button[floorSystem.getFloorCount() - 1];
        spawnButtons = new Button[floorSystem.getFloorCount()];
        for (int i = 0; i < floorSystem.getFloorCount()-1; i++) {
            upButtons[i] = new Button(Shape.SQUARE, parent);
            upButtons[i].setIcon(Shape.UPARROW);
            upButtons[i].setIconColor(arrowColor);
            upButtons[i].setPrimaryColor(arrowButtonPrimaryColor);
            upButtons[i].setSecondaryColor(arrowButtonSecondaryColor);
            upButtons[i].setOnClickBehaviour(floorSystem::pressButton, Behaviour.PRESSBUTTON);
            upButtons[i].setFloorID(i);
            upButtons[i].setPickupDirection(Direction.UP);
            downButtons[i] = new Button(Shape.SQUARE, parent);
            downButtons[i].setIcon(Shape.DOWNARROW);
            downButtons[i].setIconColor(arrowColor);
            downButtons[i].setPrimaryColor(arrowButtonPrimaryColor);
            downButtons[i].setSecondaryColor(arrowButtonSecondaryColor);
            downButtons[i].setOnClickBehaviour(floorSystem::pressButton, Behaviour.PRESSBUTTON);
            downButtons[i].setFloorID(i+1);
            downButtons[i].setPickupDirection(Direction.DOWN);
        }
        for (int i = 0; i < floorSystem.getFloorCount(); i++) {
            spawnButtons[i] = new Button(Shape.CIRCLE, parent);
            spawnButtons[i].setIcon(Shape.CIRCLE);
            spawnButtons[i].setIconColor(white);
            spawnButtons[i].setPrimaryColor(new Color(99, 75, 83));
            spawnButtons[i].setSecondaryColor(new Color(135, 98, 110));
            spawnButtons[i].setOnClickBehaviour(floorSystem::addPersonToFloor, Behaviour.ADDPERSON);
            spawnButtons[i].setFloorID(i);
            spawnButtons[i].setFloorCount(floorSystem.getFloorCount());
        }
        updateButtonArrayPositions();
    }

    private void updateButtonArrayPositions(){
        float floorHeight = ((parent.height * 0.95f)/(floorSystem.getFloorCount() * 2f));
        int buttonWidth = (int) Math.min(0.045f*parent.width, floorHeight*1.75f);
        for (int i = 0; i < floorSystem.getFloorCount()-1; i++) {
            upButtons[i].setDimensions(buttonWidth, buttonWidth);
            upButtons[i].setPosition((int) (0.15*parent.width + 0.05*(1.5)*parent.width - 0.5 * buttonWidth),
                    (int) (parent.height - buttonWidth * 1.1f - i * floorHeight*2));
            downButtons[i].setDimensions(buttonWidth, buttonWidth);
            downButtons[i].setPosition((int) (0.15*parent.width + 0.05*(2.5)*parent.width - 0.5 * buttonWidth),
                    (int) (parent.height - buttonWidth * 1.1f - (i+1) * floorHeight*2));
        }
        for (int i = 0; i < floorSystem.getFloorCount(); i++) {
            spawnButtons[i].setDimensions(buttonWidth, buttonWidth);
            spawnButtons[i].setPosition((int) (0.15*parent.width + 0.05*(0.5)*parent.width - 0.5 * buttonWidth),
                    (int) (parent.height - buttonWidth * 1.1f - (i) * floorHeight*2));
        }
    }

    private void drawHeaderBody(){
        parent.fill(headerColor.r, headerColor.g, headerColor.b);
        parent.rect(0, 0, parent.width, parent.height*0.05f);
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
        this.spawnButton.setPosition((int) (parent.width*(0.5f)+parent.height*0.05f), 0);
        this.spawnButton.setDimensions((int) (parent.height*(0.05f)), (int) (parent.height*(0.05f)));
        this.playButton.update();
        this.playButton.draw();
        this.stepButton.update();
        this.stepButton.draw();
        this.spawnButton.update();
        this.spawnButton.draw();
    }

    public void drawElevatorControlPanel(){
        updateButtonArrayPositions();
        parent.fill(panelColor.r, panelColor.g, panelColor.b);
        parent.rect(0, parent.height*0.05f, parent.width*0.3f, parent.height*0.95f);
        for (Button upButton :
                upButtons) {
            upButton.update();
            upButton.draw();
        }
        for (Button downButton :
                downButtons) {
            downButton.update();
            downButton.draw();
        }
        for (Button spawnButton :
                spawnButtons) {
            spawnButton.update();
            spawnButton.draw();
        }

    }

    public void incrementElevatorID(){
        selectedElevatorID++;
        selectedElevatorID = Math.max(0, Math.min(selectedElevatorID, elevatorSystem.getElevatorCount()-1));
    }
    public void decrementElevatorID(){
        selectedElevatorID--;
        selectedElevatorID = Math.max(0, Math.min(selectedElevatorID, elevatorSystem.getElevatorCount()-1));
    }
    public void incrementFloorID(){
        selectedFloorID++;
        selectedFloorID = Math.max(0, Math.min(selectedFloorID, floorSystem.getFloorCount()-1));
    }
    public void decrementFloorID(){
        selectedFloorID--;
        selectedFloorID = Math.max(0, Math.min(selectedFloorID, floorSystem.getFloorCount()-1));
    }

    public void incrementFloorCount(){
        floorSystem.incrementFloorCount();
        initializeButtonArrays();
    }

    public void decrementFloorCount(){

        floorSystem.decrementFloorCount();
        if (selectedFloorID > floorSystem.getFloorCount()-1){
            selectedFloorID--;
        }
        initializeButtonArrays();
    }

    public void incrementElevatorCount(){
        elevatorSystem.incrementElevatorCount();
    }

    public void decrementElevatorCount(){

        elevatorSystem.decrementElevatorCount();
        if (selectedElevatorID > elevatorSystem.getElevatorCount()-1){
            selectedElevatorID--;
        }
    }

    public void incrementElevatorCapacity(){
        elevatorSystem.incrementElevatorCapacity();
    }

    public void decrementElevatorCapacity(){
        elevatorSystem.decrementElevatorCapacity();
    }


    public void drawElevatorInformation(){
        int buttonWidth = Math.min((int) (0.05 * parent.width), (int) (0.05 * parent.height));
        parent.fill(elevatorInfoColor.r, elevatorInfoColor.g, elevatorInfoColor.b);
        this.parent.rect(0, parent.height * 0.05f + parent.height * 0.95f * 1/3,
                parent.width * 0.15f, parent.height * 0.95f * 1/3);
        parent.fill(255);
        parent.textAlign(parent.CENTER);
        parent.textSize(parent.height*0.05f*0.5f);
        parent.text("Elevator\nInformation", parent.width*0.3f/4, parent.height * 0.05f + parent.height * 0.95f * 1/3 + parent.height*0.05f*0.75f);
        parent.textSize(parent.height*0.05f);
        parent.text(selectedElevatorID, parent.width*0.3f/4, parent.height * 0.05f + parent.height * 0.95f * 1/3 + parent.height*0.05f*2.5f);
        this.elevatorPlusButton.setPosition((int) (parent.width*0.3f/4 + parent.width * 0.05f/2f), (int) (parent.height * 0.05f + parent.height * 0.95f * 1/3 + parent.height*0.05f*1.6f));
        this.elevatorPlusButton.setDimensions(buttonWidth, buttonWidth);
        this.elevatorPlusButton.update();
        this.elevatorPlusButton.draw();
        this.elevatorMinusButton.setPosition((int) (parent.width*0.3f/4 - parent.width * 0.05f/2f - buttonWidth), (int) (parent.height * 0.05f + parent.height * 0.95f * 1/3 + parent.height*0.05f*1.6f));
        this.elevatorMinusButton.setDimensions(buttonWidth, buttonWidth);
        this.elevatorMinusButton.update();
        this.elevatorMinusButton.draw();
        this.elevatorInformation = elevatorSystem.status(selectedElevatorID);
        parent.fill(255);
        parent.textSize(parent.height*0.02f);
        parent.textAlign(parent.LEFT);
        parent.text("Passengers: ", parent.width*0.3f/40, parent.height * 0.05f + parent.height * 0.95f * 1/3 + parent.height*0.05f*3.8f);
        parent.text("CurrentFloor: ", parent.width*0.3f/40, parent.height * 0.05f + parent.height * 0.95f * 1/3 + parent.height*0.05f*4.8f);
        parent.text("DesiredFloor: ", parent.width*0.3f/40, parent.height * 0.05f + parent.height * 0.95f * 1/3 + parent.height*0.05f*5.8f);
        parent.textSize(parent.height*0.05f);
        parent.textAlign(parent.CENTER);
        parent.text(elevatorInformation[0], parent.width*0.3f/7*3, parent.height * 0.05f + parent.height * 0.95f * 1/3 + parent.height*0.05f*4f);
        parent.text(elevatorInformation[1], parent.width*0.3f/7*3, parent.height * 0.05f + parent.height * 0.95f * 1/3 + parent.height*0.05f*5f);
        parent.text(elevatorInformation[2], parent.width*0.3f/7*3, parent.height * 0.05f + parent.height * 0.95f * 1/3 + parent.height*0.05f*6f);
    }
    public void drawFloorInformation(){
        int buttonWidth = Math.min((int) (0.05 * parent.width), (int) (0.05 * parent.height));
        parent.fill(floorInfoColor.r, floorInfoColor.g, floorInfoColor.b);
        this.parent.rect(0, parent.height * 0.05f + parent.height * 0.95f * 2/3,
                parent.width * 0.15f, parent.height * 0.95f * 1/3);
        parent.fill(255);
        parent.textAlign(parent.CENTER);
        parent.textSize(parent.height*0.05f*0.5f);
        parent.text("Floor\nInformation", parent.width*0.3f/4, parent.height * 0.05f + parent.height * 0.95f * 2/3 + parent.height*0.05f*0.75f);
        parent.textSize(parent.height*0.05f);
        parent.text(selectedFloorID, parent.width*0.3f/4, parent.height * 0.05f + parent.height * 0.95f * 2/3 + parent.height*0.05f*2.5f);
        this.floorPlusButton.setPosition((int) (parent.width*0.3f/4 + parent.width * 0.05f/2f), (int) (parent.height * 0.05f + parent.height * 0.95f * 2/3 + parent.height*0.05f*1.6f));
        this.floorPlusButton.setDimensions(buttonWidth, buttonWidth);
        this.floorPlusButton.update();
        this.floorPlusButton.draw();
        this.floorMinusButton.setPosition((int) (parent.width*0.3f/4 - parent.width * 0.05f/2f - buttonWidth), (int) (parent.height * 0.05f + parent.height * 0.95f * 2/3 + parent.height*0.05f*1.6f));
        this.floorMinusButton.setDimensions(buttonWidth, buttonWidth);
        this.floorMinusButton.update();
        this.floorMinusButton.draw();
        this.floorInformation = floorSystem.status(selectedFloorID);
        parent.fill(255);
        parent.textSize(parent.height*0.02f);
        parent.textAlign(parent.LEFT);
        parent.text("Waiting: ", parent.width*0.3f/40, parent.height * 0.05f + parent.height * 0.95f * 2/3 + parent.height*0.05f*3.8f);
        parent.text("upPressed: ", parent.width*0.3f/40, parent.height * 0.05f + parent.height * 0.95f * 2/3 + parent.height*0.05f*4.8f);
        parent.text("downPressed: ", parent.width*0.3f/40, parent.height * 0.05f + parent.height * 0.95f * 2/3 + parent.height*0.05f*5.8f);
        parent.textSize(parent.height*0.05f);
        parent.textAlign(parent.CENTER);
        parent.text(floorInformation[0], parent.width*0.3f/7*3, parent.height * 0.05f + parent.height * 0.95f * 2/3 + parent.height*0.05f*4f);
        parent.text(floorInformation[1], parent.width*0.3f/7*3, parent.height * 0.05f + parent.height * 0.95f * 2/3 + parent.height*0.05f*5f);
        parent.text(floorInformation[2], parent.width*0.3f/7*3, parent.height * 0.05f + parent.height * 0.95f * 2/3 + parent.height*0.05f*6f);
    }

    public void drawSetup(){
        int buttonWidth = Math.min((int) (0.05 * parent.width), (int) (0.05 * parent.height));
        parent.fill(setupColor.r, setupColor.g, setupColor.b);
        this.parent.rect(0, parent.height * 0.05f,
                parent.width * 0.15f, parent.height * 0.95f * 1/3);
        parent.fill(255);
        parent.textAlign(parent.CENTER);
        parent.textSize(parent.height*0.05f*0.5f);
        parent.text("Setup", parent.width*0.3f/4, parent.height * 0.05f + parent.height*0.05f*0.65f);
        parent.rect(parent.width*0.3f/2f/10f*2f, parent.height * 0.05f + parent.height*0.04f, parent.width*0.3f/10f/2f*6f, parent.height*0.002f);
        parent.text("FloorCount", parent.width*0.3f/4, parent.height * 0.05f + parent.height*0.05f*1.5f);
        parent.textSize(parent.height*0.05f);
        parent.text(floorSystem.getFloorCount(), parent.width*0.3f/4, parent.height * 0.05f + parent.height*0.05f*2.5f);
        this.floorCountPlusButton.setPosition((int) (parent.width*0.3f/4 + parent.width * 0.05f/2f), (int) (parent.height * 0.05f  + parent.height*0.05f*1.6f));
        this.floorCountPlusButton.setDimensions(buttonWidth, buttonWidth);
        this.floorCountPlusButton.update();
        this.floorCountPlusButton.draw();
        this.floorCountMinusButton.setPosition((int) (parent.width*0.3f/4 - parent.width * 0.05f/2f - buttonWidth), (int) (parent.height * 0.05f + parent.height*0.05f*1.6f));
        this.floorCountMinusButton.setDimensions(buttonWidth, buttonWidth);
        this.floorCountMinusButton.update();
        this.floorCountMinusButton.draw();
        parent.fill(255);
        parent.textAlign(parent.CENTER);
        parent.textSize(parent.height*0.05f*0.5f);
        parent.text("ElevatorCount", parent.width*0.3f/4, parent.height * 0.05f + parent.height*0.05f*(2.5f + 0.65f));
        parent.textSize(parent.height*0.05f);
        parent.text(elevatorSystem.getElevatorCount(), parent.width*0.3f/4, parent.height * 0.05f + parent.height*0.05f*(3.5f + 0.65f));
        this.elevatorCountPlusButton.setPosition((int) (parent.width*0.3f/4 + parent.width * 0.05f/2f), (int) (parent.height * 0.05f  + parent.height*0.05f*(2.6f + 0.65f)));
        this.elevatorCountPlusButton.setDimensions(buttonWidth, buttonWidth);
        this.elevatorCountPlusButton.update();
        this.elevatorCountPlusButton.draw();
        this.elevatorCountMinusButton.setPosition((int) (parent.width*0.3f/4 - parent.width * 0.05f/2f - buttonWidth), (int) (parent.height * 0.05f + parent.height*0.05f*(2.6f + 0.65f)));
        this.elevatorCountMinusButton.setDimensions(buttonWidth, buttonWidth);
        this.elevatorCountMinusButton.update();
        this.elevatorCountMinusButton.draw();
        parent.fill(255);
        parent.textAlign(parent.CENTER);
        parent.textSize(parent.height*0.05f*0.5f);
        parent.text("ElevatorCapacity", parent.width*0.3f/4, parent.height * 0.05f + parent.height*0.05f*(3.5f + 0.65f + 0.65f));
        parent.textSize(parent.height*0.05f);
        parent.text(elevatorSystem.getElevatorCapacity(), parent.width*0.3f/4, parent.height * 0.05f + parent.height*0.05f*(4.5f + 0.65f + 0.65f));
        this.elevatorCapacityPlusButton.setPosition((int) (parent.width*0.3f/4 + parent.width * 0.05f/2f), (int) (parent.height * 0.05f  + parent.height*0.05f*(3.6f + 0.65f + 0.65f)));
        this.elevatorCapacityPlusButton.setDimensions(buttonWidth, buttonWidth);
        this.elevatorCapacityPlusButton.update();
        this.elevatorCapacityPlusButton.draw();
        this.elevatorCapacityMinusButton.setPosition((int) (parent.width*0.3f/4 - parent.width * 0.05f/2f - buttonWidth), (int) (parent.height * 0.05f + parent.height*0.05f*(3.6f + 0.65f + 0.65f)));
        this.elevatorCapacityMinusButton.setDimensions(buttonWidth, buttonWidth);
        this.elevatorCapacityMinusButton.update();
        this.elevatorCapacityMinusButton.draw();
    }

    @Override
    public void draw() {
        drawHeaderBody();
        drawControlButtons();
        drawElevatorControlPanel();
        drawElevatorInformation();
        drawFloorInformation();
        drawSetup();
    }
}
