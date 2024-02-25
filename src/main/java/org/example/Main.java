package org.example;
import org.example.drawer.Drawer;
import org.example.drawer.Gui;
import processing.core.PApplet;

public class Main extends PApplet{
    static ElevatorSystem elevatorSystem;
    static FloorSystem floorSystem;
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
            floorSystem = new FloorSystem(floorCount, seed);
            elevatorSystem = new ElevatorSystem(floorSystem, elevatorCount, elevatorCapacity, seed);
        } else {
            floorSystem = new FloorSystem(floorCount);
            elevatorSystem = new ElevatorSystem(floorSystem, elevatorCount, elevatorCapacity);
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