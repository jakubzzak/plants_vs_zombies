package com.finalProject.game.bullets;

import com.finalProject.game.PowerType;

public interface Hit extends Runnable {

    int getDamage();
    PowerType getPowerType();
    void setStartXY(int x, int y);
    void setEndXY(int x, int y);
}
