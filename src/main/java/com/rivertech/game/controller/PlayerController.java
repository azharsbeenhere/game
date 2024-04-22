package com.rivertech.game.controller;

import com.rivertech.game.dto.PlayerDto;
import com.rivertech.game.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rivertech/api/player")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPlayer(@RequestBody final PlayerDto playerDto) {
        Objects.requireNonNull(playerDto, "PlayerDto cannot be null");
        try {
            final PlayerDto registeredPlayer = playerService.registerPlayer(playerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredPlayer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
