package com.rivertech.game.service;

import com.rivertech.game.dto.BetDto;
import com.rivertech.game.exception.InsufficientCreditsException;
import com.rivertech.game.util.GameUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private PlayerService playerService;
    @Mock
    private BetService betService;
    @InjectMocks
    private GameServiceImpl gameService;

    private MockedStatic<GameUtil> mockedStatic;

    @BeforeEach
    public void initMocks() {
        mockedStatic = Mockito.mockStatic(GameUtil.class);
    }

    @AfterEach
    public void closeMocks() {
        mockedStatic.close();
    }

    @Test
    public void testPlaceBet_successScenario_bet_5_generatedNumber_5() {

        Integer betAmount = 5;
        Integer generatedNumber = 5;
        Integer credits = 100;


        Mockito.when(playerService.getCredits(Mockito.anyString())).thenReturn(credits);
        mockedStatic.when(GameUtil::getGeneratedNumber).thenReturn(generatedNumber);

        Mockito.when(playerService.updateCredits(Mockito.anyString(), Mockito.anyInt())).thenReturn((credits - betAmount) + betAmount * 10);
        Mockito.when(betService.addBet(Mockito.any(BetDto.class))).thenReturn(null);

        BetDto betResultsDto = gameService.placeBet(Mockito.anyString(), betAmount);

        Assertions.assertNotNull(betResultsDto);
        Assertions.assertEquals(betResultsDto.getBetAmount(), betAmount);
        Assertions.assertEquals(145, betResultsDto.getUpdatedCredits());

        Mockito.verify(playerService, Mockito.times(1)).getCredits(Mockito.anyString());
        Mockito.verify(playerService, Mockito.times(1)).updateCredits(Mockito.anyString(), Mockito.anyInt());

    }

    @Test
    public void testPlaceBet_successScenario_bet_5_generatedNumber_4() {

        Integer betAmount = 5;
        Integer generatedNumber = 4;
        Integer credits = 100;

        Mockito.when(playerService.getCredits(Mockito.anyString())).thenReturn(credits);
        mockedStatic.when(GameUtil::getGeneratedNumber).thenReturn(generatedNumber);

        Mockito.when(playerService.updateCredits(Mockito.anyString(), Mockito.anyInt())).thenReturn((credits - betAmount) + betAmount * 5);
        Mockito.when(betService.addBet(Mockito.any(BetDto.class))).thenReturn(null);

        BetDto betResultsDto = gameService.placeBet(Mockito.anyString(), betAmount);

        Assertions.assertNotNull(betResultsDto);
        Assertions.assertEquals(betResultsDto.getBetAmount(), betAmount);
        Assertions.assertEquals(120, betResultsDto.getUpdatedCredits());

        Mockito.verify(playerService, Mockito.times(1)).getCredits(Mockito.anyString());
        Mockito.verify(playerService, Mockito.times(1)).updateCredits(Mockito.anyString(), Mockito.anyInt());

    }

    @Test
    public void testPlaceBet_successScenario_bet_5_generatedNumber_7() {

        Integer betAmount = 5;
        Integer generatedNumber = 7;
        Integer credits = 100;

        Mockito.when(playerService.getCredits(Mockito.anyString())).thenReturn(credits);
        mockedStatic.when(GameUtil::getGeneratedNumber).thenReturn(generatedNumber);

        Mockito.when(playerService.updateCredits(Mockito.anyString(), Mockito.anyInt())).thenReturn((credits - betAmount) + betAmount / 2);
        Mockito.when(betService.addBet(Mockito.any(BetDto.class))).thenReturn(null);

        BetDto betResultsDto = gameService.placeBet(Mockito.anyString(), betAmount);

        Assertions.assertNotNull(betResultsDto);
        Assertions.assertEquals(betResultsDto.getBetAmount(), betAmount);
        Assertions.assertEquals(97, betResultsDto.getUpdatedCredits());

        Mockito.verify(playerService, Mockito.times(1)).getCredits(Mockito.anyString());
        Mockito.verify(playerService, Mockito.times(1)).updateCredits(Mockito.anyString(), Mockito.anyInt());

    }

    @Test
    public void testPlaceBet_successScenario_bet_5_generatedNumber_2() {

        Integer betAmount = 5;
        Integer generatedNumber = 2;
        Integer credits = 100;

        Mockito.when(playerService.getCredits(Mockito.anyString())).thenReturn(credits);
        mockedStatic.when(GameUtil::getGeneratedNumber).thenReturn(generatedNumber);

        Mockito.when(playerService.updateCredits(Mockito.anyString(), Mockito.anyInt())).thenReturn(credits - betAmount);
        Mockito.when(betService.addBet(Mockito.any(BetDto.class))).thenReturn(null);

        BetDto betResultsDto = gameService.placeBet(Mockito.anyString(), betAmount);

        Assertions.assertNotNull(betResultsDto);
        Assertions.assertEquals(betResultsDto.getBetAmount(), betAmount);
        Assertions.assertEquals(95, betResultsDto.getUpdatedCredits());

        Mockito.verify(playerService, Mockito.times(1)).getCredits(Mockito.anyString());
        Mockito.verify(playerService, Mockito.times(1)).updateCredits(Mockito.anyString(), Mockito.anyInt());

    }

    @Test
    public void testPlaceBet_betAmount_0() {

        Integer betAmount = 0;
        String playerId = "playerId";

        Assertions.assertThrows(IllegalArgumentException.class, () -> gameService.placeBet(playerId, betAmount));

        Mockito.verify(playerService, Mockito.times(0)).getCredits(Mockito.anyString());
        Mockito.verify(playerService, Mockito.times(0)).updateCredits(Mockito.anyString(), Mockito.anyInt());

    }

    @Test
    public void testPlaceBet_betAmountNegative() {

        Integer betAmount = -5;
        String playerId = "playerId";

        Assertions.assertThrows(IllegalArgumentException.class, () -> gameService.placeBet(playerId, betAmount));

        Mockito.verify(playerService, Mockito.times(0)).getCredits(Mockito.anyString());
        Mockito.verify(playerService, Mockito.times(0)).updateCredits(Mockito.anyString(), Mockito.anyInt());

    }

    @Test
    public void testPlaceBet_betAmountGreaterThan_10() {

        Integer betAmount = 11;
        String playerId = "playerId";

        Assertions.assertThrows(IllegalArgumentException.class, () -> gameService.placeBet(playerId, betAmount));

        Mockito.verify(playerService, Mockito.times(0)).getCredits(Mockito.anyString());
        Mockito.verify(playerService, Mockito.times(0)).updateCredits(Mockito.anyString(), Mockito.anyInt());

    }

    @Test
    public void testPlaceBet_insufficientCredits() {

        Integer betAmount = 5;
        Integer credits = 3;

        Mockito.when(playerService.getCredits(Mockito.anyString())).thenReturn(credits);

        Assertions.assertThrows(InsufficientCreditsException.class, () -> gameService.placeBet(Mockito.anyString(), betAmount));

        Mockito.verify(playerService, Mockito.times(1)).getCredits(Mockito.anyString());
        Mockito.verify(playerService, Mockito.times(0)).updateCredits(Mockito.anyString(), Mockito.anyInt());

    }

    @Test
    public void testPlaceBet_insufficientCredits_zeroCredits() {

        Integer betAmount = 5;
        Integer credits = 0;

        Mockito.when(playerService.getCredits(Mockito.anyString())).thenReturn(credits);

        Assertions.assertThrows(InsufficientCreditsException.class, () -> gameService.placeBet(Mockito.anyString(), betAmount));

        Mockito.verify(playerService, Mockito.times(1)).getCredits(Mockito.anyString());
        Mockito.verify(playerService, Mockito.times(0)).updateCredits(Mockito.anyString(), Mockito.anyInt());

    }

}
