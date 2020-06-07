package com.finalProject.level;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.exceptions.WrongZombieTypeException;

public enum ZombieType {
    REGULAR,
    REGULAR_STRONGER,
    RUNNER;

    public static ZombieType getTypeImport(String type) throws WrongZombieTypeException {
        switch (type) {
            case "REGULAR":
                return REGULAR;
            case "REGULAR_STRONGER":
                return REGULAR_STRONGER;
            case "RUNNER":
                return RUNNER;
            default:
                throw new WrongZombieTypeException("No such zombie with id " + type);
        }
    }
}
