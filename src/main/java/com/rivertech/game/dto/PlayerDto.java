package com.rivertech.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDto {
    private String id;
    private String username;
    private String name;
    private String surname;
    private Integer credits;
    private String errorMessage;
}
