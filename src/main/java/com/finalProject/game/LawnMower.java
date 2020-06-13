package com.finalProject.game;

public class LawnMower extends Thread {
    int row, col;
    String picSrc = "/pics/lawn_mower.png";
    // TODO: constant speed

    public LawnMower(int row, int col, String picSrc) {
        this.row = row;
        this.col = col;
        this.picSrc = picSrc;
    }

    public LawnMower(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void run() {
        // TODO: - run if a zombie reaches it,
        //       - simulate movement
    }
}
