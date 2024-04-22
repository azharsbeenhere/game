package com.rivertech.game.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(BetEntity.TYPE)
public class BetEntity {
    public static final String TYPE = "BET";
    @Id
    private String id;
    private String player;
    private Integer existingCredits;
    private Integer betAmount;
    private Integer winAmount;
    private Integer lostAmount;
    private String betResult;
    private Integer updatedCredits;
    @CreatedDate
    private LocalDateTime betTime;
}
