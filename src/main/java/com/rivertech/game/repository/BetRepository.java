package com.rivertech.game.repository;

import com.rivertech.game.entity.BetEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BetRepository extends MongoRepository<BetEntity, String> {
    List<BetEntity> findAllByPlayer(String playerId);
}
