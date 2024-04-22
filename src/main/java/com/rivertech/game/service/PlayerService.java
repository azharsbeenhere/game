package com.rivertech.game.service;

import com.rivertech.game.dto.PlayerDto;

public interface PlayerService {
    PlayerDto registerPlayer(PlayerDto playerDto);
    Integer getCredits(String playerId);
    Integer updateCredits(String playerId, Integer credits);
}
