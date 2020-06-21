package com.finalProject.game;

import com.finalProject.level.ZombieType;

import javafx.scene.image.ImageView;


public class Zombie extends Thread {
    private int row, col;
    private ZombieType type;
    private double x, y;
    private boolean eating = false;
    private int HP;
    private int RELOADING = 1;
    private int DAMAGE;
    private GameController controller;
    private ImageView img;

    public Zombie(ZombieType type, int row, double x) {
        this.type = type;
        this.row = row;
        this.x = x;
        initialize();
    }

    private void initialize() {
        try {
            this.HP = ZombieType.getHP(type);
            this.DAMAGE = ZombieType.getDamageAtOneHit(type);
        } catch (Exception e) {
            System.out.println("zombie initialization failed -> " + e.getMessage());
        }
    }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setAxes(double x, double y) { this.x = x; this.y = y; }
    public void setEating(boolean eating) { this.eating = eating; }
    public void setImg(ImageView img) { this.img = img; }

    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isEating() { return eating; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getHP() { return HP; }
    public boolean isDead() { return HP <= 0; }
    public boolean reachedHouse() { return x <= 40; }
    public String getImageSrc() { return "/pics/zombies/" + type + ".png"; }
    public int getMaxPicSize() { return 60; }
    public ImageView getImg () { return img; }

    public void moveForward() {
//        if (isDead() || reachedHouse()) {
//            return;
//        }
        this.x -= 0.2;
        img.setX(x);
        img.setTranslateX(x);
    }

    @Override
    public String toString() {
        return "Zombie{" +
                "row=" + row +
                ", type=" + type +
                ", x=" + x +
                ", eating=" + eating +
                ", HP=" + HP +
                ", RELOADING=" + RELOADING +
                ", DAMAGE=" + DAMAGE +
                '}';
    }

    @Override
    public void run() {
//        Timeline t = new Timeline(new KeyFrame(Duration.millis(1), e -> {
//            bullet.setX(bullet.getX() + hit.getSpeed());
////            bullet.setY(bullet.getY() + 0.2);
//            bullet.setTranslateX(bullet.getX());
//            bullet.setTranslateY(bullet.getY());
//        }));
//        t.setDelay(Duration.seconds(3));
//        t.setCycleCount(Animation.INDEFINITE);
    }
}
