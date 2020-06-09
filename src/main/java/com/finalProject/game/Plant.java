package com.finalProject.game;

import com.finalProject.level.PlantType;

public class Plant {
    private final PlantType type;
    private final int cost;

    public Plant(PlantType type, int cost) {
        this.type = type;
        this.cost = cost;
    }

    public int getCost() { return cost; }
    public PlantType getType() { return type; }
}
