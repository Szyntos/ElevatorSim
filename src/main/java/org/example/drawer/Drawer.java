package org.example.drawer;

import org.example.ElevatorDrawer;
import org.example.ElevatorSystem;
import processing.core.PApplet;

public class Drawer {
    PApplet parent;
    ElevatorSystem system;
    FloorDrawer[] floorDrawers;
    ElevatorDrawer[] elevatorDrawers;
    public Drawer(ElevatorSystem system, PApplet parent){
        this.system = system;
        this.parent = parent;
        this.floorDrawers = new FloorDrawer[system.floorCount];
        for (int i = 0; i < system.floorCount; i++) {
            this.floorDrawers[i] = new FloorDrawer(system.floors[i], system.floorCount, parent);
        }
        this.elevatorDrawers = new ElevatorDrawer[system.elevatorCount];
        int elevatorHeight = parent.height/(system.floorCount * 2);
        int elevatorWidth = parent.width/20;
        for (int i = 0; i < system.elevatorCount; i++) {
            this.elevatorDrawers[i] = new ElevatorDrawer(system.elevators[i], system.elevatorCount, system.floorCount, parent);
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
