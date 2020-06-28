package com.finalProject.game;

import javafx.scene.image.ImageView;

import java.util.List;


/**
 * Represents a lawn mover as an object.
 */
public class LawnMower {
    private int row, col;
    private final double SPEED = 1;
    private GameController controller;
    private double x = 25;
    private String picSrc = "/pics/lawn_mower4.png";
    private boolean running = false;
    private ImageView img;
    private List<LawnMower> parent;

    public LawnMower(int row, int col, GameController controller, String picSrc) {
        this.row = row;
        this.col = col;
        this.controller = controller;
        this.picSrc = picSrc;
    }

    public LawnMower(int row, int col, GameController controller) {
        this.row = row;
        this.col = col;
        this.controller = controller;
    }

    public int getRow() { return row; }
    public boolean isRunning() { return running; }
    public String getImageSrc() { return picSrc; }
    public int getMaxPicSize() { return 65; }
    public double getX() { return x; }
    public ImageView getImg() { return img; }

    public void setImg(ImageView img) { this.img = img; }
    public void setParent(List<LawnMower> parent) { this.parent = parent; }
    public void setRunning(boolean run) {  running = run; }

    /**
     * Moves the lawn mover forward.
     */
    public void moveForward() {
        x += SPEED;
        img.setX(x);
        img.setTranslateX(x);
    }
}
