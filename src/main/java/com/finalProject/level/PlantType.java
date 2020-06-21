package com.finalProject.level;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.game.Plant;
import com.finalProject.game.bullets.Hit;
import com.finalProject.game.bullets.Regular;
import com.finalProject.game.bullets.Sun;

import java.util.Random;

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
                return 4;
            case BARRIER:
            case BARRIER_APRON:
            case BARRIER_NURSE:
            case LARGE_BARRIER:
            case MINE:
                return -1;
            case FLOWER:
            case DOUBLE_FLOWER:
                return 10;
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
                return 25;
            case DOUBLE_FLOWER:
                return 50;
            case EATER:
                return 12;
            case CACTUS:
                return 1;
            default:
                throw new WrongPlantTypeException("No such plant with id " + type);
        }
    }

    public static Hit getHit(Plant plant) throws WrongPlantTypeException {
        Random rnd = new Random();
        switch (plant.getType()) {
            case CANNON:
                return new Regular(plant, plant.getRow(), plant.getCol(), plant.getCol() + 3, plant.getRow());
            case CORN:
                return null;
            case CACTUS:
            case DOUBLE_CANNON:
                return null;
            case BARRIER:
            case BARRIER_APRON:
            case BARRIER_NURSE:
            case LARGE_BARRIER:
                return null;
            case FLOWER:
            case DOUBLE_FLOWER:
                return new Sun(plant, plant.getRow(), plant.getCol(), rnd.nextInt(5),  rnd.nextInt(9));
            case EATER:
                return null;
            case FROZEN_CANNON:
                return null;
            case MINE:
                return null;
            default:
                throw new WrongPlantTypeException("No such plant type -> " + plant.getType());
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

    public static boolean ifFlower(Plant plant) {
        return plant.getType() == PlantType.FLOWER || plant.getType() == PlantType.DOUBLE_FLOWER;
    }
}
