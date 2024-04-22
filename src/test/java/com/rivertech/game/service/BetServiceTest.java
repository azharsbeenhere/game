package com.rivertech.game.service;

import com.rivertech.game.dto.BetDto;
import com.rivertech.game.entity.BetEntity;
import com.rivertech.game.exception.NoRecordFoundException;
import com.rivertech.game.exception.NullNotAllowedException;
import com.rivertech.game.mapper.BetMapper;
import com.rivertech.game.repository.BetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BetServiceTest {
    @Mock
    BetRepository betRepository;

    @InjectMocks
    BetServiceImpl betService;

    @Test
    public void testAddBet_successScenario() {

        BetDto betDto = BetDto.builder()
                .player("testPlayerId")
                .existingCredits(100)
                .betAmount(5)
                .winAmount(50)
                .lostAmount(0)
                .betResult(BetDto.BetResult.WIN)
                .updatedCredits(145)
                .build();

        BetEntity betEntity = BetMapper.INSTANCE.toEntity(betDto);

        Mockito.when(betRepository.save(Mockito.any(BetEntity.class))).thenReturn(betEntity);

        BetDto result = betService.addBet(betDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(betDto.getPlayer(), result.getPlayer());
        Mockito.verify(betRepository, Mockito.times(1)).save(Mockito.any(BetEntity.class));

    }

    @Test
    public void testAddBet_nullBetDto() {
        Assertions.assertThrows(NullNotAllowedException.class, () -> {
            betService.addBet(null);
        });

        Mockito.verify(betRepository, Mockito.times(0)).save(Mockito.any(BetEntity.class));
    }

    @Test
    public void testGetAllBetsByPlayerId_successScenario() {
        String playerId = "testPlayerId";

        BetDto betDto = BetDto.builder()
                .player(playerId)
                .existingCredits(100)
                .betAmount(5)
                .winAmount(50)
                .lostAmount(0)
                .betResult(BetDto.BetResult.WIN)
                .updatedCredits(145)
                .build();

        BetEntity betEntity = BetMapper.INSTANCE.toEntity(betDto);

        Mockito.when(betRepository.findAllByPlayer(playerId)).thenReturn(List.of(betEntity));

        BetDto result = betService.getAllBetsByPlayerId(playerId).get(0);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(betDto.getPlayer(), result.getPlayer());
        Mockito.verify(betRepository, Mockito.times(1)).findAllByPlayer(playerId);

    }

    @Test
    public void testGetAllBetsByPlayerId_noRecordFound() {
        String playerId = "testPlayerId";

        Mockito.when(betRepository.findAllByPlayer(playerId)).thenReturn(null);

        Assertions.assertThrows(NoRecordFoundException.class, () -> {
            betService.getAllBetsByPlayerId(playerId);
        });

        Mockito.verify(betRepository, Mockito.times(1)).findAllByPlayer(playerId);

    }

    @Test
    public void testGetAllBetsByPlayerId_nullPlayerId() {
        Assertions.assertThrows(NullNotAllowedException.class, () -> {
            betService.getAllBetsByPlayerId(null);
        });

        Mockito.verify(betRepository, Mockito.times(0)).findAllByPlayer(Mockito.anyString());
    }

}
