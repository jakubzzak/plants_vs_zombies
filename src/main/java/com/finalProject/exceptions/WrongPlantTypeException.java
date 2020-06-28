package com.finalProject.exceptions;


public class WrongPlantTypeException extends Exception {

    /**
     * Exception thrown by requiring of the none existent plant type.
     * @param message
     * Exact message to be shown.
     */
    public WrongPlantTypeException(String message) {
        super(message);
    }
}
