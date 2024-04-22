package com.rivertech.game.service;

import com.rivertech.game.dto.BetDto;

import java.util.List;

public interface BetService {
    BetDto addBet(BetDto betDto);
    List<BetDto> getAllBetsByPlayerId(String playerId);
}
