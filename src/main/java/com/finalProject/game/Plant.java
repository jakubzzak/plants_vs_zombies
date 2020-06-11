package com.finalProject.game;

import com.finalProject.level.PlantType;

import java.io.Serializable;

public class Plant implements Serializable {
    private final PlantType type;
    private final int cost;

    public Plant(PlantType type, int cost) {
        this.type = type;
        this.cost = cost;
    }

    public int getCost() { return cost; }
    public PlantType getType() { return type; }
}
