package com.rivertech.game.util;

import java.util.Random;

public class GameUtil {
    public static int getGeneratedNumber() {
        return new Random().nextInt(10) + 1;
    }
}
