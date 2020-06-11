package com.finalProject.game;

import com.finalProject.exceptions.WrongWindowSizeException;

public enum WindowSize {
    SMALL,
    MID,
    LARGE;

    public static int getCharacterHeight(WindowSize size) throws WrongWindowSizeException {
        switch (size) {
            case SMALL:
                return 35;
            case MID:
                return 75;
            case LARGE:
                return 100;
            default:
                throw new WrongWindowSizeException("No such window size with id " + size);
        }
    }
}
