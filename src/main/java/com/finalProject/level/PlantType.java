package com.finalProject.level;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.exceptions.WrongZombieTypeException;
import com.finalProject.game.Plant;
import com.finalProject.game.bullets.Hit;
import com.finalProject.game.bullets.Regular;
import com.finalProject.game.bullets.Sun;

import java.util.Random;


/**
 * Represents all properties of certain types of plants.
 */
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

    /**
     * Gets the amount of suns required to plant the certain plant.
     * @param type
     * The type of the plant.
     * @return
     * Number of suns required to plant the certain plant.
     * @throws WrongPlantTypeException
     * If plant type not presented.
     */
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

    /**
     * Gets the HP of the certain plant.
     * @param type
     * The type of the plant.
     * @return
     * Number of suns required to plant the certain plant.
     * @throws WrongPlantTypeException
     * If plant type not presented.
     */
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

    /**
     * Gets the reloading time of the certain plant.
     * @param type
     * The type of the plant.
     * @return
     * Reloading time of the certain plant in seconds.
     * @throws WrongPlantTypeException
     * If plant type not presented.
     */
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
                return 10;
            case DOUBLE_FLOWER:
                return 12;
            case EATER:
                return 12;
            case CACTUS:
                return 1;
            default:
                throw new WrongPlantTypeException("No such plant with id " + type);
        }
    }

    /**
     * Gets the damage of the certain plant.
     * @param type
     * The type of the plant.
     * @return
     * Damage of the certain plant per hit.
     * @throws WrongPlantTypeException
     * If plant type not presented.
     */
    public static int getDamagePerHit(PlantType type) throws WrongPlantTypeException {
        switch (type) {
            case CANNON:
            case CORN:
            case DOUBLE_CANNON:
            case FROZEN_CANNON:
                return 200;
            case BARRIER:
            case BARRIER_APRON:
            case BARRIER_NURSE:
            case LARGE_BARRIER:
            case MINE:
                return -1;
            case FLOWER:
                return 50;
            case DOUBLE_FLOWER:
                return 100;
            case EATER:
                return 10000;
            case CACTUS:
                return 100;
            default:
                throw new WrongPlantTypeException("No such plant with id " + type);
        }
    }

    /**
     * Gets the instance of a hit of the certain plant.
     * @param plant
     * An instance of a plant.
     * @return
     * An instance of the hit of the certain plant.
     * @throws WrongPlantTypeException
     * If type of the plant does not exists.
     */
    public static Hit getHit(Plant plant) throws WrongPlantTypeException {
        Random rnd = new Random();
        switch (plant.getType()) {
            case CANNON:
                return new Regular(plant, plant.getCol() + 3, plant.getRow());
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
                return new Sun(plant, rnd.nextInt(5),  rnd.nextInt(9));
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

    /**
     * Converts plant from string to PlantType.
     * @param type
     * Type of the plant.
     * @return
     * Plant type according to the input string.
     * @throws WrongPlantTypeException
     * If plant type not presented.
     */
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

    /**
     * Gets if the provided plant is a flower.
     * @param plant
     * An instance of the plant.
     * @return
     * If the provided plant is of type flower.
     */
    public static boolean isFlower(Plant plant) {
        return plant.getType() == PlantType.FLOWER || plant.getType() == PlantType.DOUBLE_FLOWER;
    }

    /**
     * Gets if the provided plant is a barrier.
     * @param plant
     * An instance of the plant.
     * @return
     * If the provided plant is of type barrier.
     */
    public static boolean isBarrier(Plant plant) {
        return plant.getType() == PlantType.BARRIER || plant.getType() == PlantType.BARRIER_APRON || plant.getType() == PlantType.BARRIER_NURSE || plant.getType() == PlantType.LARGE_BARRIER;
    }
}
