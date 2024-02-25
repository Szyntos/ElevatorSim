package org.example.drawer;

import org.example.interfaces.ElevatorSystem;
import org.example.interfaces.FloorSystem;
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
        this.elevatorCount = elevatorSystem.getElevatorCount();
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
        this.elevatorDrawers = new ElevatorDrawer[elevatorSystem.getElevatorCount()];
        int elevatorHeight = (int) (parent.height * 0.95f/(floorSystem.getFloorCount() * 2f));
        int elevatorWidth = (int) (parent.width * 0.7f / (float)(elevatorSystem.getElevatorCount()) / 2f);
        for (int i = 0; i < elevatorSystem.getElevatorCount(); i++) {
            this.elevatorDrawers[i] = new ElevatorDrawer(elevatorSystem.getElevators()[i], elevatorSystem.getElevatorCount(), floorSystem.getFloorCount(), parent);
            this.elevatorDrawers[i].setDimensions(elevatorWidth, elevatorHeight);
        }
    }

    public void draw(){
        if (elevatorCount != elevatorSystem.getElevatorCount() || floorCount != floorSystem.getFloorCount()){
            this.elevatorCount = elevatorSystem.getElevatorCount();
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
