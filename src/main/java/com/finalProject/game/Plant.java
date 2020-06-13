package com.finalProject.game;

import com.finalProject.level.PlantType;
import javafx.scene.image.Image;
import java.io.Serializable;


public class Plant extends Thread implements Serializable {
    private final PlantType type;
    private final int cost;
    private int row, col;

    public Plant(PlantType type, int cost) {
        this.type = type;
        this.cost = cost;
    }

    public int getCost() { return cost; }
    public PlantType getType() { return type; }
    public String getImageSrc() { return "/pics/plants/" + type + ".png"; }
    public int getRow() { return row; }
    public int getCol() { return col; }

    public void setCellPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void run() {
        // TODO: fire if a zombie isPresent in the line,
        //  vytvor timeline, bude bezat kazde 1/2 sec a bude spustat/vypinat fire pri kazder rastline
    }
}
