package com.rivertech.game.service;

import com.rivertech.game.dto.BetDto;
import com.rivertech.game.exception.InsufficientCreditsException;
import com.rivertech.game.util.GameUtil;
import com.rivertech.game.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final PlayerService playerService;
    private final BetService betService;

    @Override
    public BetDto placeBet(final String playerId, final Integer betAmount) {

        ValidationUtil.validateNotNull(playerId, "PlayerId");
        ValidationUtil.validateNotNull(betAmount, "BetAmount");

        if (betAmount <= 0 || betAmount > 10) {
            throw new IllegalArgumentException("Bet amount must be between 1 and 10 inclusive.");
        }

        int credits = playerService.getCredits(playerId);
        final Integer availableCredits = credits;
        if (credits <= 0 || credits < betAmount) {
            throw new InsufficientCreditsException(credits);
        }
        credits = credits - betAmount;
        final int payOut = getPayOut(betAmount);
        final Integer updatedCredits = playerService.updateCredits(playerId, credits + payOut);

        final BetDto betDto = BetDto.builder()
                .player(playerId)
                .existingCredits(availableCredits)
                .betAmount(betAmount)
                .winAmount(payOut)
                .lostAmount(payOut > 0 ? 0 : betAmount)
                .betResult(payOut > 0 ? BetDto.BetResult.WIN : BetDto.BetResult.LOSE)
                .updatedCredits(updatedCredits)
                .build();

        betService.addBet(betDto);
        return betDto;
    }

    private static int getPayOut(final Integer betAmount) {
        final int generatedNumber = GameUtil.getGeneratedNumber();
        final int difference = generatedNumber - betAmount;
        final int payOut;

        if (difference == 0) {
            payOut = betAmount * 10;
        } else if (difference == 1 || difference == -1) {
            payOut = betAmount * 5;
        } else if (difference == 2 || difference == -2) {
            payOut = betAmount / 2;
        } else {
            payOut = 0;
        }
        return payOut;
    }


}
