package com.rivertech.game.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BetDto {
    private String id;
    private String player;
    private Integer existingCredits;
    private Integer betAmount;
    private Integer winAmount;
    private Integer lostAmount;
    private BetResult betResult;
    private Integer updatedCredits;
    private LocalDateTime betTime;

    public enum BetResult {
        WIN, LOSE
    }
}
