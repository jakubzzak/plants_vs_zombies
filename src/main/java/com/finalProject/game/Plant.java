package com.finalProject.game;

import com.finalProject.game.bullets.Hit;
import com.finalProject.level.PlantType;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Objects;


public class Plant extends Thread implements Serializable {
    private final PlantType type;
    private final int cost;
    private int row, col;
    private int HP;
    private int RELOADING;
    private GameController controller;
    private ImageView img;
    private Double x;

    private Hit hit;
    private boolean isFiring = false;

    public Plant(PlantType type, int cost) {
        this.type = type;
        this.cost = cost;
        initialize();
    }

    public int getCost() { return cost; }
    public PlantType getType() { return type; }
    public String getImageSrc() { return "/pics/plants/" + type + ".png"; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public double getX() {
        if (x == null) {
            try{
                x = 65. + col * 18;
            } catch (Exception e) {
                x = 0.;
                System.out.println("getting X of a plant failed");
            }
        }
        return x;
    }
    public int getHP() { return HP; }
    public String getCell() { return "cell#" + (row * 9 + col); }
    public int getCellId() { return row * 9 + col; }
    public GameController getController() { return controller; }
    public boolean isDead() { return HP <= 0; }
    public ImageView getImg() { return img; }
    public boolean isFiring() { return isFiring; }

    private void initialize() {
        try {
            this.hit = PlantType.getHit(this);
            this.HP = PlantType.getHP(type);
            this.RELOADING = PlantType.getReloadTimeInSec(type);
        } catch (Exception e) {
            System.out.println("plant initialization failed -> " + e.getMessage());
        }
    }

    public void setController(GameController controller) { this.controller = controller; }
    public void setFiring(boolean state) { this.isFiring = state; }
    public void setCellPos(int row, int col) { this.row = row; this.col = col; }
    public void setImg(ImageView img) { this.img = img; }

    synchronized public void isBeingEaten(Zombie zombie) {
        if (HP <= 0) {
            zombie.setEating(false);
            zombie.setEatingPlant(null);
        } else {
            HP -= zombie.getDamage();
        }
    }

    @Override
    public void run() {
        try { sleep(3000); } catch (Exception e) { System. out.println("sleeping interrupted -> " + e.getMessage()); }
        if (type == PlantType.FLOWER || type == PlantType.DOUBLE_FLOWER) {
            isFiring = true;
        }
        while (isFiring) {
            try {
                Objects.requireNonNull(PlantType.getHit(this)).releaseHit();
                sleep(RELOADING * 1000);
            } catch (Exception e) {
//                System.out.println("exception occurred when firing");
                e.getStackTrace();
            }
        }
    }
}
