package com.finalProject.game.bullets;

import com.finalProject.game.PowerType;

public class Butter implements Hit {

    @Override
    public int getDamage() {
        return 10;
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.BUTTER_BULLET;
    }

    @Override
    public void setStartXY(int x, int y) {

    }

    @Override
    public void setEndXY(int x, int y) {

    }

    @Override
    public void run() {

    }
}
