package com.finalProject.exceptions;


public class WrongZombieTypeException extends Exception {

    /**
     * Exception thrown by requiring of the none existent zombie type.
     * @param message
     * Exact message to be shown.
     */
    public WrongZombieTypeException(String message) {
        super(message);
    }
}
