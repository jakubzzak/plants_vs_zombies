package com.finalProject.game.bullets;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.game.Plant;
import com.finalProject.game.PowerType;
import com.finalProject.level.PlantType;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Regular implements Hit, Serializable {
    private final PowerType POWER = PowerType.REGULAR_BULLET;

    private final Plant parent;
    private double current_x, current_y;
    private final int end_x, end_y;
    private final int DAMAGE;
    private String imgSrc = "/pics/regular_shot.png";
    private boolean isDead = false;
    private ImageView img;

    public Regular(Plant parent, int startCellX, int startCellY, int endCellX, int endCellY) throws WrongPlantTypeException {
        this.parent = parent;
        this.current_x = startCellX;
        this.current_y = startCellY;
        this.end_x = endCellX;
        this.end_y = endCellY;
        this.DAMAGE = PlantType.getDamagePerHit(parent.getType());
    }

    @Override
    public int getDamage() {
        return DAMAGE;
    }

    @Override
    public PowerType getPowerType(PlantType type) {
        return POWER;
    }

    @Override
    public String getImageSrc() {
        return imgSrc;
    }

    @Override
    public Plant getParent() {
        return parent;
    }

    @Override
    public double getSpeed() {
        return 1;
    }

    @Override
    public int getMaxPicSize() {
        return 10;
    }

    @Override
    public int getOffsetY() {
        return 7;
    }

    @Override
    public int getOffsetX() {
        return 10;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void setImg(ImageView img) {
        this.img = img;
    }

    @Override
    public ImageView getImg() {
        return img;
    }

    @Override
    public void setDead() {
        isDead = true;
    }

    @Override
    public void moveForward() {
        current_x += getSpeed();
        img.setX(current_x);
        img.setTranslateX(current_x);
        if (current_x >= 220) {
            setDead();
        }
    }

    @Override
    public void setCurrentX(double x) {
        current_x = x;
    }

    @Override
    public void setCurrentY(double y) {
        current_y = y;
    }

    @Override
    public void run() {
        parent.getController().releaseHit(this);
        // TODO: create bullet pic
//        while (current_x != end_x) {
//            // TODO: move bullet to the right
//        }
    }
}
