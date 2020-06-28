package com.finalProject.game;

import com.finalProject.game.bullets.Hit;
import com.finalProject.level.ZombieType;

import javafx.scene.image.ImageView;


/**
 * Represents a zombie as an object.
 */
public class Zombie {
    private int row, col;
    private ZombieType type;
    private double x, y;
    private boolean eating = false;
    private Plant eatingPlant;
    private int HP;
    private final int RELOADING = 1;
    private int DAMAGE;
    private double SPEED;
    private GameController controller;
    private ImageView img;

    public Zombie(ZombieType type, int row, double x) {
        this.type = type;
        this.row = row;
        this.x = x;
        try {
            this.HP = ZombieType.getHP(type);
            this.DAMAGE = ZombieType.getDamageAtOneHit(type);
            this.SPEED = ZombieType.getMovingSpeed(type);
        } catch (Exception e) {
            System.out.println("zombie initialization failed -> " + e.getMessage());
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isEating() { return eating; }
    public int getDamage() { return DAMAGE; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getHP() { return HP; }
    public boolean isDead() { return HP <= 0; }
    public boolean reachedHouse() { return x <= 40; }
    public String getImageSrc() { return "/pics/zombies/" + type + ".png"; }
    public int getMaxPicSize() { return 60; }
    public ImageView getImg () { return img; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setAxes(double x, double y) { this.x = x; this.y = y; }
    public void setEating(boolean eating, Plant plant) {
        this.eating = eating;
        this.eatingPlant = plant;
    }
    public void setEating(boolean eating) { this.eating = eating; }
    public void setEatingPlant(Plant plant) { this.eatingPlant = plant; }
    public void setImg(ImageView img) { this.img = img; }
    public void setDead() { HP = 0; }

    /**
     * Moves the zombie forward if no plant if present in its way.
     */
    public void moveForward() {
        if (!eating) {
            x -= SPEED;
            img.setX(x);
            img.setTranslateX(x);
        } else if (eatingPlant == null) {
            eating = false;
        } else {
            eatingPlant.isBeingEaten(this);
        }
    }

    /**
     * Decrease health point of the zombie by amount given by the presented hit.
     * @param hit
     * Represents the exact bullet that hit the zombie.
     */
    public void receiveHit(Hit hit) {
        HP -= hit.getDamage();
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
}
