package org.example;
import org.example.drawer.Drawer;
import org.example.drawer.Gui;
import processing.core.PApplet;

public class Main extends PApplet{
    static ElevatorSystem system;
    static FloorSystem floorSystem;
    public int windowWidth = 800;
    public int windowHeight = 600;
    int elevatorCapacity = 10;
    int elevatorCount = 5;
    int floorCount = 10;
    int initialPeopleCount = 500;
    int frameStep = 10;
    Drawer drawer;
    Gui gui;
    public static void main(String[] args) {
        PApplet.main("org.example.Main");
    }



    public void settings() {
        size(windowWidth, windowHeight);
        floorSystem = new FloorSystem(floorCount);
        system = new ElevatorSystem(floorSystem, elevatorCount, elevatorCapacity, 123);
        floorSystem.addElevatorSystem(system);
        drawer = new Drawer(floorSystem, system, this);
        gui = new Gui(system, this);
    }

    public void setup(){
        frameRate(60);
        surface.setTitle("Elevator System");
        surface.setResizable(true);


        for (int j = 0; j < initialPeopleCount; j++) {
            system.spawnRandomPerson();
        }
    }

    public void draw() {
        background(255);


        if (frameCount % frameStep == 0 && frameCount != 0){

            system.step();

//            System.out.println("============================================================\n\n\n");
//            System.out.println((system.status()));

        }
//        if (frameCount % frameStep > 0 && frameCount % frameStep < frameStep/2){
//            ellipse(0, 0, 50, 50);
//        }

        drawer.draw();
//        gui.draw();

    }
}