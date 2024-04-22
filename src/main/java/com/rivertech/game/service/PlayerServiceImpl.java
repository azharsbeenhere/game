package com.rivertech.game.service;

import com.rivertech.game.dto.PlayerDto;
import com.rivertech.game.entity.PlayerEntity;
import com.rivertech.game.exception.DuplicationException;
import com.rivertech.game.mapper.PlayerMapper;
import com.rivertech.game.repository.PlayerRepository;
import com.rivertech.game.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final MongoTemplate mongoTemplate;
    public static final String ID_DELIMITER = "--";

    @Override
    public PlayerDto registerPlayer(final PlayerDto playerDto) {
        ValidationUtil.validateNotNull(playerDto, "PlayerDto");
        ValidationUtil.validateNotNull(playerDto.getUsername(), "Username");
        ValidationUtil.validateNotNull(playerDto.getName(), "Name");
        playerDto.setId(PlayerEntity.TYPE + ID_DELIMITER + UUID.randomUUID());
        playerDto.setCredits(1000);

        if (playerRepository.existsByUsername(playerDto.getUsername())) {
            throw new DuplicationException("Username");
        }

        final PlayerEntity playerEntity = PlayerMapper.INSTANCE.toEntity(playerDto);
        return PlayerMapper.INSTANCE.toDto(playerRepository.save(playerEntity));
    }

    @Override
    public Integer getCredits(final String playerId) {
        ValidationUtil.validateNotNull(playerId, "PlayerId");

        final Criteria criteria = Criteria.where("id").is(playerId);
        final Query query = new Query().addCriteria(criteria);
        final PlayerEntity playerEntity = mongoTemplate.findOne(query, PlayerEntity.class);
        return playerEntity != null ? playerEntity.getCredits() : 0;
    }

    @Override
    public Integer updateCredits(final String playerId, final Integer credits) {
        ValidationUtil.validateNotNull(playerId, "PlayerId");
        ValidationUtil.validateNotNull(credits, "Credits");

        final Criteria criteria = Criteria.where("id").is(playerId);
        final Query query = new Query().addCriteria(criteria);
        final Update update = new Update().set("credits", credits);
        mongoTemplate.findAndModify(query, update, PlayerEntity.class);
        return credits;

    }

}
