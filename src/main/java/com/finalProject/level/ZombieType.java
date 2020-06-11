package com.finalProject.level;

import com.finalProject.exceptions.WrongPlantTypeException;
import com.finalProject.exceptions.WrongZombieTypeException;
import com.finalProject.game.Zombie;

public enum ZombieType {
    REGULAR,
    PIRATE,
    ARMY,
    COWBOY,
    HIPPIE,
    EASTER,
    PHARAON,
    SINGER,
    SWIMMER,

    STRONGER_SNOWY,
    STRONGER_CONE,
    STRONGER_PIRATE,
    STRONGER_PHARAON,

    STRONGEST_PHARAON,
    STRONGEST_REGULAR,
    STRONGEST_SHIRT,
    STRONGEST_DOORS,
    STRONGEST_BRICK,

    BALLOON,

    GIANT,
    ;

    public static int getHP(ZombieType type) throws WrongZombieTypeException {
        switch (type) {
            case REGULAR:
            case PIRATE:
            case ARMY:
            case COWBOY:
            case HIPPIE:
            case EASTER:
            case PHARAON:
            case SINGER:
            case SWIMMER:
                return 1000;
            case STRONGER_SNOWY:
            case STRONGER_CONE:
            case STRONGER_PIRATE:
            case STRONGER_PHARAON:
                return 2000;
            case STRONGEST_PHARAON:
            case STRONGEST_REGULAR:
            case STRONGEST_DOORS:
            case STRONGEST_BRICK:
            case STRONGEST_SHIRT:
                return 3000;
            case BALLOON:
                return 300;
            case GIANT:
                return 5000;
            default:
                throw new WrongZombieTypeException("No such zombie with id " + type);
        }
    }

    public static int getMovingSpeed(ZombieType type) throws WrongZombieTypeException {
        switch (type) {
            case HIPPIE:
            case SINGER:
            case SWIMMER:
            case PIRATE:
            case COWBOY:
            case BALLOON:
            case STRONGER_PIRATE:
                return 15;
            case REGULAR:
            case ARMY:
            case EASTER:
            case PHARAON:
            case STRONGER_SNOWY:
            case STRONGER_CONE:
            case STRONGER_PHARAON:
            case STRONGEST_PHARAON:
            case STRONGEST_REGULAR:
            case STRONGEST_DOORS:
            case STRONGEST_BRICK:
            case STRONGEST_SHIRT:
                return 10;
            case GIANT:
                return 2;
            default:
                throw new WrongZombieTypeException("No such zombie with id " + type);
        }
    }

    public static int getDamageAtOneHit(ZombieType type) throws WrongZombieTypeException {
        switch (type) {
            case REGULAR:
            case PIRATE:
            case ARMY:
            case COWBOY:
            case HIPPIE:
            case EASTER:
            case PHARAON:
            case SINGER:
            case SWIMMER:
            case STRONGER_SNOWY:
            case STRONGER_CONE:
            case STRONGER_PHARAON:
            case STRONGER_PIRATE:
            case STRONGEST_PHARAON:
            case STRONGEST_REGULAR:
            case STRONGEST_DOORS:
            case STRONGEST_BRICK:
            case STRONGEST_SHIRT:
            case BALLOON:
                return 10;
            case GIANT:
                return 1000;
            default:
                throw new WrongZombieTypeException("No such zombie with id " + type);
        }
    }

    public static ZombieType getTypeImport(String type) throws WrongZombieTypeException {
        switch (type) {
            case "REGULAR":
                return REGULAR;
            case "PIRATE":
                return PIRATE;
            case "ARMY":
                return ARMY;
            case "COWBOY":
                return COWBOY;
            case "HIPPIE":
                return HIPPIE;
            case "EASTER":
                return EASTER;
            case "PHARAON":
                return PHARAON;
            case "SINGER":
                return SINGER;
            case "SWIMMER":
                return SWIMMER;
            case "STRONGER_SNOWY":
                return STRONGER_SNOWY;
            case "STRONGER_CONE":
                return STRONGER_CONE;
            case "STRONGER_PIRATE":
                return STRONGER_PIRATE;
            case "STRONGER_PHARAON":
                return STRONGER_PHARAON;
            case "STRONGEST_PHARAON":
                return STRONGEST_PHARAON;
            case "STRONGEST_REGULAR":
                return STRONGEST_REGULAR;
            case "STRONGEST_DOORS":
                return STRONGEST_DOORS;
            case "STRONGEST_SHIRT":
                return STRONGEST_SHIRT;
            case "STRONGEST_BRICK":
                return STRONGEST_BRICK;
            case "BALLOON":
                return BALLOON;
            case "GIANT":
                return GIANT;
            default:
                throw new WrongZombieTypeException("No such zombie with id " + type);
        }
    }
}
