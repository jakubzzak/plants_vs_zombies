package com.finalProject.exceptions;


public class WrongPowerTypeException extends Exception {

    /**
     * Exception thrown by requiring of the none existent power type of a plant.
     * @param message
     * Exact message to be shown.
     */
    public WrongPowerTypeException(String message) {
        super(message);
    }
}
