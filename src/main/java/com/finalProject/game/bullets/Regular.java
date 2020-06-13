package com.finalProject.game.bullets;

import com.finalProject.game.PowerType;

public class Regular implements Hit {
    int start_x, start_y;
    int end_x, end_y;

    @Override
    public int getDamage() {
        return 10;
    }

    @Override
    public PowerType getPowerType() {
        return null;
    }

    @Override
    public void setStartXY(int x, int y) {
        this.start_x = x;
        this.start_y = y;
    }

    @Override
    public void setEndXY(int x, int y) {
        this.start_x = x;
        this.start_y = y;
    }

    @Override
    public void run() {

    }
}
