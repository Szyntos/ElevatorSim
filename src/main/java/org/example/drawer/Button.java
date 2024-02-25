package org.example.drawer;

import org.example.impl.Direction;
import processing.core.PApplet;

import java.util.Random;

public class Button implements Drawable{
    PApplet parent;
    Shape shape;
    Shape icon = Shape.TRIANGLE;
    Behaviour behaviour = Behaviour.NONE;
    private NoArgumentFunction onClickNoArgumentFunction;
    private IntIntFunction onClickIntIntFunction;
    private IntDirFunction onClickIntDirFunction;
    boolean isHovered = false;
    boolean mousePressedInside = false;
    int x;
    int y;
    int width;
    int height;
    int floorID = 0;
    int floorCount = 0;
    int randomPeopleCount = 50;
    boolean isBackground = true;
    Direction direction = Direction.UP;
    Color primaryColor = new Color(0, 0, 0);
    Color secondaryColor = new Color(255, 255, 255);
    Color iconColor = new Color(0, 255, 0);
    Color altIconColor = new Color(140, 140, 140);
    Random random = new Random();
    public Button(PApplet parent){
        this.parent = parent;
        this.shape = Shape.SQUARE;
    }
    public Button(Shape shape, PApplet parent){
        this.parent = parent;
        this.shape = shape;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }
    public void setIcon(Shape shape){
        this.icon = shape;
    }
    public void setIconColor(Color iconColor){
        this.iconColor = iconColor;
    }

    public void setPrimaryColor(Color color){
        this.primaryColor = color;
    }
    public void setSecondaryColor(Color color){
        this.secondaryColor = color;
    }
    public void swapIconColors(){
        Color tmp = iconColor;
        iconColor = altIconColor;
        altIconColor = tmp;
    }
    public void setAltIconColor(Color altIconColor){
        this.altIconColor = altIconColor;
    }

    public void swapIcon(){
        switch (icon){
            case TRIANGLE -> {
                swapIconColors();
                this.icon = Shape.STOP;
            }
            case STOP -> {
                swapIconColors();
                this.icon = Shape.TRIANGLE;
            }
        }
    }

    public void click() {
        switch (behaviour){
            case STEP, INCREMENT, DECREMENT -> {
                onClickNoArgumentFunction.call();
            }
            case PLAY -> {
                onClickNoArgumentFunction.call();
                swapIcon();
            }
            case SPAWN -> {
                for (int i = 0; i < randomPeopleCount; i++) {
                    onClickNoArgumentFunction.call();
                }
            }
            case ADDPERSON -> {
                int randomFloorID = random.nextInt(floorCount);
                while (randomFloorID == floorID){
                    randomFloorID = random.nextInt(floorCount);
                }
                onClickIntIntFunction.call(floorID, randomFloorID);
            }
            case PRESSBUTTON -> {
                onClickIntDirFunction.call(floorID, direction);
            }
        }


    }

    public void setRandomPeopleCount(int randomPeopleCount){
        this.randomPeopleCount = randomPeopleCount;
    }

    public void setFloorID(int floorID){
        this.floorID = floorID;
    }

    public void setFloorCount(int floorCount){
        this.floorCount = floorCount;
    }

    public void setPickupDirection(Direction direction){
        this.direction = direction;
    }

    public void setOnClickBehaviour(NoArgumentFunction onClickBehaviour, Behaviour behaviour) {
        this.onClickNoArgumentFunction= onClickBehaviour;
        this.behaviour = behaviour;
    }

    public void setOnClickBehaviour(IntIntFunction onClickBehaviour, Behaviour behaviour) {
        this.onClickIntIntFunction = onClickBehaviour;
        this.behaviour = behaviour;
    }
    public void setOnClickBehaviour(IntDirFunction onClickBehaviour, Behaviour behaviour) {
        this.onClickIntDirFunction = onClickBehaviour;
        this.behaviour = behaviour;
    }

    private void drawIcon(){
        if (isHovered && !isBackground){
            parent.fill(altIconColor.r, altIconColor.g, altIconColor.b);
        }else{
            parent.fill(iconColor.r, iconColor.g, iconColor.b);
        }
        switch (icon){
            case TRIANGLE -> {
                parent.triangle(x + width * (0.5f - 1/6f), y + height * (0.5f - 1/4f),
                                x + width * (0.5f - 1/6f), y + height * (0.5f + 1/4f),
                                x + width * (0.5f + 1/3f), y + height * (0.5f));
            }
            case FASTFORWARD -> {
                parent.triangle(x + width * (0.5f - 1/4f - 2/14f), y + height * (0.5f - 1/4f),
                        x + width * (0.5f - 1/4f - 2/14f), y + height * (0.5f + 1/4f),
                        x + width * (0.5f + 1/4f - 2/14f), y + height * (0.5f));
                parent.triangle(x + width * (0.5f - 1/4f + 2/12f), y + height * (0.5f - 1/4f),
                        x + width * (0.5f - 1/4f + 2/12f), y + height * (0.5f + 1/4f),
                        x + width * (0.5f + 1/4f + 2/12f), y + height * (0.5f));
            }
            case PLUS -> {
                parent.rect(x + width/2f - width/16f, y + height/2f - height/16f*5, width / 8f, height * 5/8f);
                parent.rect(x + width/2f - width/16f*5, y + height/2f - height/16f, width / 8f * 5, height / 8f);
            }
            case MINUS -> parent.rect(x + width/2f - width/16f*5, y + height/2f - height/16f, width / 8f * 5, height / 8f);
            case STOP -> {
                parent.rect(x + width/4f, y + height/6f, width / 6f, height * 4/6f);
                parent.rect(x + width - width/4f - width/6f, y + height/6f, width / 6f, height * 4/6f);
            }
            case UPARROW -> {
                parent.rect(x + width/2f - width/10f, y + height/2f - height/10f*2, width / 6f, height * 3/5f);
                parent.triangle(x + width/2f, y + 1/8f*height,
                        x + width/2f - 1/3f*width, y + height/2f - 1/16f * height,
                        x + width/2f + 1/3f*width, y + height/2f - 1/16f * height);
            }
            case DOWNARROW -> {
                parent.rect(x + width/2f - width/10f, y + height/2f - height/10f*4, width / 6f, height * 3/5f);
                parent.triangle(x + width/2f, y + height - 1/8f*height,
                        x + width/2f - 1/3f*width, y + height/2f + 1/16f * height,
                        x + width/2f + 1/3f*width, y + height/2f + 1/16f * height);
            }
            case CIRCLE -> {
                this.parent.ellipse(x+width/2f, y+height/2f, width/2f, height/2f);
            }
        }
    }

    public void cycleBackground(){
        isBackground = !isBackground;
    }

    public void updateHover(){
        if (this.shape == Shape.CIRCLE) {
            this.isHovered = Math.pow(((x + width / 2f) - parent.mouseX), 2) +
                    Math.pow(((y + width / 2f) - parent.mouseY), 2) <= Math.pow(width / 2f, 2);
        } else {
            this.isHovered = parent.mouseX >= x && parent.mouseX <= x + width &&
                    parent.mouseY >= y && parent.mouseY <= y + height;
        }
    }
    public void updateClick(){
        if (isHovered){
            if (parent.mousePressed){
                mousePressedInside = true;
            }
            if (mousePressedInside && !parent.mousePressed){
                mousePressedInside = false;
                click();
            }
        } else {
            mousePressedInside = false;
        }
    }

    public void update(){
        updateHover();
        updateClick();
    }


    @Override
    public void draw() {
        if (isHovered){
            parent.fill(secondaryColor.r, secondaryColor.g, secondaryColor.b);
        } else{
            parent.fill(primaryColor.r, primaryColor.g, primaryColor.b);
        }

        if (isBackground){
            if (this.shape == Shape.CIRCLE) {
                this.parent.ellipse(x+width/2f, y+height/2f, width, height);
            } else {
                this.parent.rect(x, y, width, height);
            }
        }

        drawIcon();
    }
}
