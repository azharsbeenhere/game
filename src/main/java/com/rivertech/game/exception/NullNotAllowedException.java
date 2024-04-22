package com.rivertech.game.exception;

public class NullNotAllowedException extends RuntimeException {

    public NullNotAllowedException(final String propertyName) {
        super(propertyName + " cannot be null.");
    }

}
