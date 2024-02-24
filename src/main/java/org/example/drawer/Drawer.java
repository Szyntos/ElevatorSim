package org.example.drawer;

import org.example.ElevatorSystem;
import org.example.FloorSystem;
import processing.core.PApplet;

public class Drawer implements Drawable{
    PApplet parent;
    ElevatorSystem system;
    FloorSystem floorSystem;
    FloorDrawer[] floorDrawers;
    ElevatorDrawer[] elevatorDrawers;
    public Drawer(FloorSystem floorSystem, ElevatorSystem system, PApplet parent){
        this.floorSystem = floorSystem;
        this.system = system;
        this.parent = parent;
        initializeFloorDrawers();
        initializeElevatorDrawers();
    }

    public void initializeFloorDrawers(){
        this.floorDrawers = new FloorDrawer[floorSystem.getFloorCount()];
        for (int i = 0; i < floorSystem.getFloorCount(); i++) {
            this.floorDrawers[i] = new FloorDrawer(floorSystem.getFloors()[i], floorSystem.getFloorCount(), parent);
        }
        this.elevatorDrawers = new ElevatorDrawer[system.elevatorCount];
    }
    public void initializeElevatorDrawers(){
        int elevatorHeight = parent.height/(floorSystem.getFloorCount() * 2);
        int elevatorWidth = parent.width/20;
        for (int i = 0; i < system.elevatorCount; i++) {
            this.elevatorDrawers[i] = new ElevatorDrawer(system.elevators[i], system.elevatorCount, floorSystem.getFloorCount(), parent);
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
