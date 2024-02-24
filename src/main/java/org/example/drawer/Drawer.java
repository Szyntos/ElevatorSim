package org.example.drawer;

import org.example.ElevatorSystem;
import org.example.FloorSystem;
import processing.core.PApplet;

public class Drawer implements Drawable{
    PApplet parent;
    ElevatorSystem elevatorSystem;
    FloorSystem floorSystem;
    FloorDrawer[] floorDrawers;
    ElevatorDrawer[] elevatorDrawers;
    public Drawer(FloorSystem floorSystem, ElevatorSystem elevatorSystem, PApplet parent){
        this.floorSystem = floorSystem;
        this.elevatorSystem = elevatorSystem;
        this.parent = parent;
        initializeFloorDrawers();
        initializeElevatorDrawers();
    }

    public void initializeFloorDrawers(){
        this.floorDrawers = new FloorDrawer[floorSystem.getFloorCount()];
        for (int i = 0; i < floorSystem.getFloorCount(); i++) {
            this.floorDrawers[i] = new FloorDrawer(floorSystem.getFloors()[i], floorSystem.getFloorCount(), parent);
        }
        this.elevatorDrawers = new ElevatorDrawer[elevatorSystem.elevatorCount];
    }
    public void initializeElevatorDrawers(){
        int elevatorHeight = parent.height/(floorSystem.getFloorCount() * 2);
        int elevatorWidth = parent.width/20;
        for (int i = 0; i < elevatorSystem.elevatorCount; i++) {
            this.elevatorDrawers[i] = new ElevatorDrawer(elevatorSystem.elevators[i], elevatorSystem.elevatorCount, floorSystem.getFloorCount(), parent);
            this.elevatorDrawers[i].setDimensions(elevatorWidth, elevatorHeight);
        }
    }

    public void draw(){
        drawFloors();
        drawElevators();
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
