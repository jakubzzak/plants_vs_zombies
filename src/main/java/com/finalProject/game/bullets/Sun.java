package com.finalProject.game.bullets;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.game.Plant;
import com.finalProject.game.PowerType;
import com.finalProject.level.PlantType;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Sun implements Hit, Serializable {
    private final PowerType POWER = PowerType.SUN_PRODUCING;

    private Plant parent;
    private double current_x, current_y;
    private final int end_x, end_y;
    private final int DAMAGE;
    private String imgSrc = "/pics/regular_sun.png";
    private boolean isDead = false;
    private ImageView img;

    public Sun(Plant parent, int startCellX, int startCellY, int endCellX, int endCellY) throws WrongPlantTypeException {
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
        return 0.2;
    }

    @Override
    public int getMaxPicSize() {
        return 50;
    }

    @Override
    public int getOffsetY() {
        return 0;
    }

    @Override
    public int getOffsetX() {
        return 0;
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
        current_x += 0.5;
        img.setX(current_x);
        img.setTranslateX(current_x);
        if (current_x >= 220 || current_y >= 120) {
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
//        new Timeline(new KeyFrame(Duration.millis(10), e -> {
//            ImageView bullet = new ImageView(new Image(imgSrc, 50, 50, true, true));
//            parent.getController().releaseHit(bullet);
//        })).setCycleCount(Animation.INDEFINITE);
//        // TODO: create bullet pic
//        while (current_x != end_x) {
//            // TODO: move bullet to the right
//        }
    }
}
