package com.rivertech.game.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(PlayerEntity.TYPE)
public class PlayerEntity {
    public static final String TYPE = "PLAYER";
    @Id
    private String id;
    private String username;
    private String name;
    private String surname;
    private Integer credits;
}
