package com.finalProject.game.bullets;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.game.Plant;
import com.finalProject.game.PowerType;
import com.finalProject.level.PlantType;

import java.io.Serializable;

public class Regular implements Hit, Serializable {
    private final PowerType POWER = PowerType.REGULAR_BULLET;

    private final Plant parent;
    private final int current_x, current_y;
    private final int end_x, end_y;
    private final int DAMAGE;
    private String imgSrc = "/pics/regular_shot.png";

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
        return 0.5;
    }

    @Override
    public int getMaxPicSize() {
        return 15;
    }

    @Override
    public int getOffsetY() {
        return 5;
    }

    @Override
    public int getOffsetX() {
        return 5;
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
