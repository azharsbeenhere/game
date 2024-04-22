package com.rivertech.game.exception;

public class InsufficientCreditsException extends RuntimeException {

    public InsufficientCreditsException(final int currentCredits) {
        super("Insufficient credits " + currentCredits);
    }

}
