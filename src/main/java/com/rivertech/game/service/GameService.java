package com.rivertech.game.service;

import com.rivertech.game.dto.BetDto;

public interface GameService {
    BetDto placeBet(String playerId, Integer betAmount);
}
