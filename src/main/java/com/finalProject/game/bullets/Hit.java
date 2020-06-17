package com.finalProject.game.bullets;

import com.finalProject.game.Plant;
import com.finalProject.game.PowerType;
import com.finalProject.level.PlantType;

public interface Hit extends Runnable {
    int getDamage();
    PowerType getPowerType(PlantType type);
    String getImageSrc();
    Plant getParent();
    double getSpeed();
    int getMaxPicSize();
    int getOffsetY();
    int getOffsetX();
}
