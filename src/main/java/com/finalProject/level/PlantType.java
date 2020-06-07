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
