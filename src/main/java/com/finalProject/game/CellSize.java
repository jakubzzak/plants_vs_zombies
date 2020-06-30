package com.finalProject.game;

import com.finalProject.exceptions.WrongWindowSizeException;


/**
 * Represents the properties of different sizes of the screen.
 */
public enum CellSize {
    SMALL,
    MID,
    LARGE;

    /**
     * Gets max character height according to the provided cell size.
     * @param size
     * Cell size.
     * @return
     * Number representing height of the image.
     * @throws WrongWindowSizeException
     * If the provided cell size is not presented.
     */
    public static int getCharacterHeight(CellSize size) throws WrongWindowSizeException {
        switch (size) {
            case SMALL:
                return 43;
            case MID:
                return 75;
            case LARGE:
                return 100;
            default:
                throw new WrongWindowSizeException("No such window size with id " + size);
        }
    }

    /**
     * Gets max character width according to the provided cell size.
     * @param size
     * Cell size.
     * @return
     * Number representing width of the image.
     * @throws WrongWindowSizeException
     * If the provided cell size is not presented.
     */
    public static int getCharacterWidth(CellSize size) throws WrongWindowSizeException {
        switch (size) {
            case SMALL:
                return 60;
            case MID:
                return 80;
            case LARGE:
                return 105;
            default:
                throw new WrongWindowSizeException("No such window size with id " + size);
        }
    }
}
