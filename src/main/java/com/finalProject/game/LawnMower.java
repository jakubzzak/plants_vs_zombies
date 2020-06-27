package com.finalProject.game;

import com.finalProject.ui.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.List;

public class LawnMower extends Thread {
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

    public void moveForward() {
        x += SPEED;
        img.setX(x);
        img.setTranslateX(x);
    }

    @Override
    public void run() {
        System.out.println("running lawnMover");
//        running = true;
//        while(x < 210) {
//            x += SPEED;
//            img.setX(x);
//            img.setTranslateX(x);
//            try { sleep(100); } catch (Exception e) { System.out.println("lawnMover handling thread interrupted"); }
//        }
//        parent.remove(this);
//        controller.removeObjectFromZombieLayer(img);
//        Timeline t = new Timeline(new KeyFrame(Duration.millis(50), e -> {
//            x += SPEED;
//            img.setX(x);
//            img.setTranslateX(x);
//        }));
//        t.setCycleCount(50);
//        t.setOnFinished(event -> {
//            if (x < 210) {
//                t.play();
//            } else {
//                parent.remove(this);
//                controller.removeObjectFromZombieLayer(img);
//            }
//        });
//        t.play();
    }
}
