package com.finalProject.level;

import com.finalProject.exceptions.WrongPlantTypeException;

public enum PlantType {
    CANNON,
    DOUBLE_CANNON,

    FROZEN_CANNON,

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
            case CORN:
                return 100;
            case DOUBLE_CANNON:
                return 200;
            case BARRIER:
            case BARRIER_APRON:
            case BARRIER_NURSE:
            case FLOWER:
                return 50;
            case DOUBLE_FLOWER:
                return 125;
            case LARGE_BARRIER:
            case EATER:
            case CACTUS:
            case FROZEN_CANNON:
                return 175;
            case MINE:
                return 25;
            default:
                throw new WrongPlantTypeException("No such plant with id " + type);
        }
    }

    public static int getHP(PlantType type) throws WrongPlantTypeException {
        switch (type) {
            case CANNON:
            case CORN:
            case DOUBLE_CANNON:
            case FROZEN_CANNON:
            case EATER:
            case CACTUS:
                return 100;
            case BARRIER:
            case BARRIER_APRON:
            case BARRIER_NURSE:
                return 500;
            case LARGE_BARRIER:
                return 1000;
            case MINE:
            case FLOWER:
            case DOUBLE_FLOWER:
                return 50;
            default:
                throw new WrongPlantTypeException("No such plant with id " + type);
        }
    }

    public static int getReloadTimeInSec(PlantType type) throws WrongPlantTypeException {
        switch (type) {
            case CANNON:
            case CORN:
            case DOUBLE_CANNON:
            case FROZEN_CANNON:
                return 2;
            case BARRIER:
            case BARRIER_APRON:
            case BARRIER_NURSE:
            case LARGE_BARRIER:
            case MINE:
                return -1;
            case FLOWER:
            case DOUBLE_FLOWER:
                return 7;
            case EATER:
                return 12;
            case CACTUS:
                return 1;
            default:
                throw new WrongPlantTypeException("No such plant with id " + type);
        }
    }

    public static int getDamagePerHit(PlantType type) throws WrongPlantTypeException {
        switch (type) {
            case CANNON:
            case CORN:
            case DOUBLE_CANNON:
            case FROZEN_CANNON:
                return 2;
            case BARRIER:
            case BARRIER_APRON:
            case BARRIER_NURSE:
            case LARGE_BARRIER:
            case MINE:
                return -1;
            case FLOWER:
            case DOUBLE_FLOWER:
                return 7;
            case EATER:
                return 12;
            case CACTUS:
                return 1;
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
