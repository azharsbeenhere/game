package com.rivertech.game.service;

import com.rivertech.game.dto.PlayerDto;
import com.rivertech.game.entity.PlayerEntity;
import com.rivertech.game.exception.DuplicationException;
import com.rivertech.game.exception.NullNotAllowedException;
import com.rivertech.game.repository.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    public void testRegisterPlayer_successScenario() {
        String username = "testUserName";
        String name = "Test Name";

        PlayerDto playerDto = new PlayerDto();
        playerDto.setUsername(username);
        playerDto.setName(name);

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setUsername(username);
        playerEntity.setName(name);

        Mockito.when(playerRepository.existsByUsername(username)).thenReturn(false);
        Mockito.when(playerRepository.save(Mockito.any(PlayerEntity.class))).thenReturn(playerEntity);

        PlayerDto result = playerService.registerPlayer(playerDto);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(username, result.getUsername());
        Assertions.assertEquals(name, result.getName());

        Mockito.verify(playerRepository, Mockito.times(1)).existsByUsername(username);
        Mockito.verify(playerRepository, Mockito.times(1)).save(Mockito.any(PlayerEntity.class));

    }

    @Test
    public void testRegisterPlayer_usernameExists() {
        String username = "testUserName";
        String name = "Test Name";

        PlayerDto playerDto = new PlayerDto();
        playerDto.setUsername(username);
        playerDto.setName(name);

        Mockito.when(playerRepository.existsByUsername(username)).thenReturn(true);

        Assertions.assertThrows(DuplicationException.class, () -> playerService.registerPlayer(playerDto));

        Mockito.verify(playerRepository, Mockito.times(1)).existsByUsername(username);
        Mockito.verify(playerRepository, Mockito.times(0)).save(Mockito.any(PlayerEntity.class));
    }

    @Test
    public void testRegisterPlayer_nullUsername() {
        String name = "Test Name";

        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(name);

        Assertions.assertThrows(NullNotAllowedException.class, () -> playerService.registerPlayer(playerDto));

        Mockito.verify(playerRepository, Mockito.times(0)).existsByUsername(Mockito.anyString());
        Mockito.verify(playerRepository, Mockito.times(0)).save(Mockito.any(PlayerEntity.class));
    }

    @Test
    public void testRegisterPlayer_nullName() {
        String username = "testUserName";

        PlayerDto playerDto = new PlayerDto();
        playerDto.setUsername(username);

        Assertions.assertThrows(NullNotAllowedException.class, () -> playerService.registerPlayer(playerDto));

        Mockito.verify(playerRepository, Mockito.times(0)).existsByUsername(Mockito.anyString());
        Mockito.verify(playerRepository, Mockito.times(0)).save(Mockito.any(PlayerEntity.class));
    }

    @Test
    public void testRegisterPlayer_nullPlayerDto() {
        Assertions.assertThrows(NullNotAllowedException.class, () -> playerService.registerPlayer(null));

        Mockito.verify(playerRepository, Mockito.times(0)).existsByUsername(Mockito.anyString());
        Mockito.verify(playerRepository, Mockito.times(0)).save(Mockito.any(PlayerEntity.class));
    }

    @Test
    public void testGetCredits_successScenario() {
        String playerId = "testPlayerId";
        Integer credits = 100;

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setCredits(credits);

        Mockito.when(mongoTemplate.findOne(Mockito.any(Query.class), Mockito.eq(PlayerEntity.class))).thenReturn(playerEntity);

        Integer result = playerService.getCredits(playerId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(credits, result);

        Mockito.verify(mongoTemplate, Mockito.times(1)).findOne(Mockito.any(Query.class), Mockito.eq(PlayerEntity.class));
    }

    @Test
    public void testGetCredits_nullPlayerId() {
        Assertions.assertThrows(NullNotAllowedException.class, () -> playerService.getCredits(null));

        Mockito.verify(mongoTemplate, Mockito.times(0)).findOne(Mockito.any(Query.class), Mockito.eq(PlayerEntity.class));
    }

    @Test
    public void testGetCredits_playerNotFound() {
        String playerId = "testPlayerId";

        Mockito.when(mongoTemplate.findOne(Mockito.any(Query.class), Mockito.eq(PlayerEntity.class))).thenReturn(null);

        Integer result = playerService.getCredits(playerId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result);

        Mockito.verify(mongoTemplate, Mockito.times(1)).findOne(Mockito.any(Query.class), Mockito.eq(PlayerEntity.class));
    }

    @Test
    public void testUpdateCredits_successScenario() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId("testPlayerId");
        playerEntity.setCredits(100);

        Mockito.when(mongoTemplate.findAndModify(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.eq(PlayerEntity.class))).thenReturn(playerEntity);

        playerService.updateCredits(playerEntity.getId(), playerEntity.getCredits());

        Mockito.verify(mongoTemplate, Mockito.times(1)).findAndModify(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.eq(PlayerEntity.class));
    }

    @Test
    public void testUpdateCredits_nullPlayerId() {
        Assertions.assertThrows(NullNotAllowedException.class, () -> playerService.updateCredits(null, 100));

        Mockito.verify(mongoTemplate, Mockito.times(0)).findAndModify(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.eq(PlayerEntity.class));
    }

    @Test
    public void testUpdateCredits_nullCredits() {
        Assertions.assertThrows(NullNotAllowedException.class, () -> playerService.updateCredits("testPlayerId", null));

        Mockito.verify(mongoTemplate, Mockito.times(0)).findAndModify(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.eq(PlayerEntity.class));
    }

}
