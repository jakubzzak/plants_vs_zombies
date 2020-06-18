package com.finalProject.game;

import com.finalProject.exceptions.WrongPowerTypeException;
import com.finalProject.game.bullets.Hit;
import com.finalProject.game.bullets.Regular;
import com.finalProject.level.PlantType;
import javafx.scene.image.Image;
import java.io.Serializable;


public class Plant extends Thread implements Serializable {
    private final PlantType type;
    private final int cost;
    private int row, col;
    private int HP;
    private int RELOADING;
    private GameController controller;

    private Hit hit;
    private boolean isFiring = false;

    public Plant(PlantType type, int cost) {
        this.type = type;
        this.cost = cost;
        initialize();
    }

    public Plant(PlantType type, int cost, int row, int col) {
        this.type = type;
        this.cost = cost;
        this.row = row;
        this.col = col;
        initialize();
    }

    public int getCost() { return cost; }
    public PlantType getType() { return type; }
    public java.lang.String getImageSrc() { return "/pics/plants/" + type + ".png"; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getHP() { return HP; }
    public java.lang.String getCell() { return "cell#" + (row * 9 + col); }
    public GameController getController() { return controller; }

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
    public void setCellPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void run() {
        if (type == PlantType.FLOWER || type == PlantType.DOUBLE_FLOWER || type == PlantType.CANNON) {
            isFiring = true;
        }
        while (isFiring) {
            new Thread(hit).start();
            try { sleep(RELOADING * 1000); } catch (Exception e) { System.out.println("sleeping interrupted when firing"); }
        }

        // TODO: fire if a zombie isPresent in the line,
        //  vytvor timeline, bude bezat kazde 1/2 sec a bude spustat/vypinat fire pri kazder rastline
    }
}
