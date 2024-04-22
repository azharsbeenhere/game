package com.rivertech.game.exception;

public class DuplicationException extends RuntimeException {

    public DuplicationException(final String propertyName) {
        super(propertyName + " already exists.");
    }

}
