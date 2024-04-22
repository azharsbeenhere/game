package com.rivertech.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BetRequestDto {
    private String playerId;
    private Integer betAmount;
}
