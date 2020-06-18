package com.finalProject.game;

import com.finalProject.level.ZombieType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Zombie extends Thread {
    private int row, col;
    private ZombieType type;
    private double x, y;
    private boolean eating = false;

    public Zombie(ZombieType type, int row) {
        this.type = type;
        this.row = row;
    }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setEating(boolean eating) { this.eating = eating; }

    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isEating() { return eating; }

    @Override
    public String toString() {
        return "Zombie{" +
                "row=" + row +
                ", col=" + col +
                ", type=" + type +
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
