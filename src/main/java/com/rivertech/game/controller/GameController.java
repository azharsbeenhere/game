package com.rivertech.game.controller;

import com.rivertech.game.dto.BetDto;
import com.rivertech.game.dto.BetRequestDto;
import com.rivertech.game.service.GameService;
import com.rivertech.game.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rivertech/api/game")
public class GameController {

    private final GameService gameService;

    @PostMapping("/bet")
    public ResponseEntity<?> placeBet(@RequestBody @Valid final BetRequestDto betRequestDto) {
        ValidationUtil.validateNotNull(betRequestDto, "BetRequestDto");
        try {
            final BetDto betResultsDto = gameService.placeBet(betRequestDto.getPlayerId(), betRequestDto.getBetAmount());
            return ResponseEntity.ok(betResultsDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
