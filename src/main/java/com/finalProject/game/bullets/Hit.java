package com.finalProject.game.bullets;

import com.finalProject.game.Plant;
import com.finalProject.game.PowerType;
import com.finalProject.level.PlantType;
import javafx.scene.image.ImageView;


public interface Hit {
    int getDamage();
    PowerType getPowerType(PlantType type);
    String getImageSrc();
    Plant getParent();
    double getSpeed();
    int getMaxPicSize();
    int getOffsetY();
    int getOffsetX();
    boolean isDead();
    void setImg(ImageView img);
    ImageView getImg();
    void setDead();
    void moveForward();
    void setCurrentX(double x);
    void setCurrentY(double y);
    void run();
}
