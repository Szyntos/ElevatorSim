package org.example;
import org.example.drawer.Drawer;
import org.example.drawer.Gui;
import org.example.impl.ElevatorSystemImpl;
import org.example.impl.FloorSystemImpl;
import processing.core.PApplet;

public class Main extends PApplet{
    static ElevatorSystemImpl elevatorSystem;
    static FloorSystemImpl floorSystem;
    public int windowWidth = 800;
    public int windowHeight = 600;
    int elevatorCapacity = 10;
    int elevatorCount = 5;
    int floorCount = 8;
    int initialPeopleCount = 0;
    int frameStep = 10;
    int seed = 123;
    boolean setSeed = true;
    boolean startSimulation = true;
    Drawer drawer;
    Gui gui;
    public static void main(String[] args) {
        PApplet.main("org.example.Main");
    }



    public void settings() {
        size(windowWidth, windowHeight);
        if (setSeed){
            floorSystem = new FloorSystemImpl(floorCount, seed);
            elevatorSystem = new ElevatorSystemImpl(floorSystem, elevatorCount, elevatorCapacity, seed);
        } else {
            floorSystem = new FloorSystemImpl(floorCount);
            elevatorSystem = new ElevatorSystemImpl(floorSystem, elevatorCount, elevatorCapacity);
        }

        floorSystem.addElevatorSystem(elevatorSystem);
        drawer = new Drawer(floorSystem, elevatorSystem, this);
        gui = new Gui(floorSystem, elevatorSystem, this, this);
    }

    public void playPauseSimulation(){
        startSimulation = !startSimulation;
    }

    public void setup(){
        frameRate(60);
        surface.setTitle("Elevator System");
        surface.setResizable(true);
        noStroke();


        for (int j = 0; j < initialPeopleCount; j++) {
            floorSystem.spawnRandomPerson();
        }
    }

    public void draw() {
        background(255);


        if (frameCount % frameStep == 0 && frameCount != 0 && startSimulation){

            elevatorSystem.step();

        }

        drawer.draw();
        gui.draw();

    }
}