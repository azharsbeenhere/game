package com.rivertech.game.util;

import com.rivertech.game.exception.NullNotAllowedException;

public class ValidationUtil {
    public static <T> void validateNotNull(T value, String propertyName) {
        if (value == null) {
            throw new NullNotAllowedException(propertyName);
        }
    }
}
