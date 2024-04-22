package com.rivertech.game.controller;

import com.rivertech.game.dto.BetDto;
import com.rivertech.game.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rivertech/api/bet")
@RequiredArgsConstructor
public class BetController {

    private final BetService betService;

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getAllBetsByPlayerId(@PathVariable String playerId) {
        try {
            final List<BetDto> betDtoList = betService.getAllBetsByPlayerId(playerId);
            return ResponseEntity.ok(betDtoList);
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

}
