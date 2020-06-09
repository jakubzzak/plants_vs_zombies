package com.finalProject.level;

import com.finalProject.exceptions.WrongPlantTypeException;

public enum PlantType {
    CANNON,
    DOUBLE_CANNON,

    FROZEN_CANNON,
    DOUBLE_FROZEN_CANNON,

    BARRIER,
    BARRIER_APRON,
    BARRIER_NURSE,
    LARGE_BARRIER,

    FLOWER,
    DOUBLE_FLOWER,

    CORN,
    EATER,
    CACTUS,
    MINE,
    ;

    public static int getCost(PlantType type) throws WrongPlantTypeException {
        switch (type) {
            case CANNON:
                return 50;
            case DOUBLE_CANNON:
                return 100;
            case FROZEN_CANNON:
                return 125;
            case DOUBLE_FROZEN_CANNON:
                return 175;
            case BARRIER:
            case BARRIER_APRON:
            case BARRIER_NURSE:
                return 50;
            case LARGE_BARRIER:
                return 100;
            case FLOWER:
                return 25;
            case DOUBLE_FLOWER:
                return 75;
            case CORN:
                return 100;
            case EATER:
                return 150;
            case CACTUS:
                return 100;
            case MINE:
                return 25;
            default:
                throw new WrongPlantTypeException("No such plant with id " + type);
        }
    }

    public static PlantType getTypeImport(String type) throws WrongPlantTypeException {
        switch (type) {
            case "CANNON":
                return CANNON;
            case "DOUBLE_CANNON":
                return DOUBLE_CANNON;
            case "FROZEN_CANNON":
                return FROZEN_CANNON;
            case "DOUBLE_FROZEN_CANNON":
                return DOUBLE_FROZEN_CANNON;
            case "BARRIER":
                return BARRIER;
            case "BARRIER_APRON":
                return BARRIER_APRON;
            case "BARRIER_NURSE":
                return BARRIER_NURSE;
            case "LARGE_BARRIER":
                return LARGE_BARRIER;
            case "FLOWER":
                return FLOWER;
            case "DOUBLE_FLOWER":
                return DOUBLE_FLOWER;
            case "CORN":
                return CORN;
            case "EATER":
                return EATER;
            case "CACTUS":
                return CACTUS;
            case "MINE":
                return MINE;
            default:
                throw new WrongPlantTypeException("No such plant with id " + type);
        }
    }
}
