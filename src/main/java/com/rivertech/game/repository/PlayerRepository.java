package com.rivertech.game.repository;

import com.rivertech.game.entity.PlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<PlayerEntity, String> {
    @Query(value = "{ '_id' : ?0 }", fields = "{ 'credits' : 1, '_id': 0 }")
    Integer findCreditsByPlayerId(final String playerId);

    @Query(value = "{ 'id' : ?0 }")
    void updateCreditsById(final String playerId, final Integer credits);

    boolean existsByUsername(String username);

}
