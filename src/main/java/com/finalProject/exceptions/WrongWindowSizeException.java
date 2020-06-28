package com.finalProject.exceptions;


public class WrongWindowSizeException extends Exception {

    /**
     * Exception thrown by requiring of the none existent window size.
     * @param message
     * Exact message to be shown.
     */
    public WrongWindowSizeException(String message) {
        super(message);
    }
}
