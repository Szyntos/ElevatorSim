package org.example.drawer;

import org.example.ElevatorSystem;
import org.example.FloorSystem;
import processing.core.PApplet;

public class Drawer implements Drawable{
    PApplet parent;
    ElevatorSystem elevatorSystem;
    FloorSystem floorSystem;
    int floorCount;
    int elevatorCount;
    FloorDrawer[] floorDrawers;
    ElevatorDrawer[] elevatorDrawers;
    public Drawer(FloorSystem floorSystem, ElevatorSystem elevatorSystem, PApplet parent){
        this.floorSystem = floorSystem;
        this.elevatorSystem = elevatorSystem;
        this.floorCount = floorSystem.getFloorCount();
        this.elevatorCount = elevatorSystem.elevatorCount;
        this.parent = parent;
        initializeFloorDrawers();
        initializeElevatorDrawers();
    }

    public void initializeFloorDrawers(){
        this.floorDrawers = new FloorDrawer[floorSystem.getFloorCount()];
        for (int i = 0; i < floorSystem.getFloorCount(); i++) {
            this.floorDrawers[i] = new FloorDrawer(floorSystem.getFloors()[i], floorSystem.getFloorCount(), parent);
        }

    }
    public void initializeElevatorDrawers(){
        this.elevatorDrawers = new ElevatorDrawer[elevatorSystem.elevatorCount];
        int elevatorHeight = (int) (parent.height * 0.95f/(floorSystem.getFloorCount() * 2f));
        int elevatorWidth = (int) (parent.width * 0.7f / (float)(elevatorSystem.elevatorCount) / 2f);
        for (int i = 0; i < elevatorSystem.elevatorCount; i++) {
            this.elevatorDrawers[i] = new ElevatorDrawer(elevatorSystem.elevators[i], elevatorSystem.elevatorCount, floorSystem.getFloorCount(), parent);
            this.elevatorDrawers[i].setDimensions(elevatorWidth, elevatorHeight);
        }
    }

    public void draw(){
        if (elevatorCount != elevatorSystem.elevatorCount || floorCount != floorSystem.getFloorCount()){
            this.elevatorCount = elevatorSystem.elevatorCount;
            this.floorCount = floorSystem.getFloorCount();
            initializeElevatorDrawers();
            initializeFloorDrawers();
        }
        drawBackground();
        drawFloors();
        drawElevators();
    }

    public void drawBackground(){
        parent.fill(224, 201, 196);
        parent.rect(parent.width * 0.3f, parent.height * 0.05f, parent.width * 0.7f, parent.height * 0.95f);
    }
    public void drawFloors(){
        for (FloorDrawer floorDrawer :
                floorDrawers) {
            floorDrawer.draw();
        }
    }
    public void drawElevators(){
        for (ElevatorDrawer elevatorDrawer :
                elevatorDrawers) {
            elevatorDrawer.updatePosition();
            elevatorDrawer.draw();
        }
    }
}
