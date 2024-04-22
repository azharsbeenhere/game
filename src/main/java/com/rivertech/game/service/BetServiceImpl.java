package com.rivertech.game.service;

import com.rivertech.game.dto.BetDto;
import com.rivertech.game.entity.BetEntity;
import com.rivertech.game.exception.NoRecordFoundException;
import com.rivertech.game.mapper.BetMapper;
import com.rivertech.game.repository.BetRepository;
import com.rivertech.game.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {

    private final BetRepository betRepository;
    public static final String ID_DELIMITER = "--";

    @Override
    public BetDto addBet(final BetDto betDto) {
        ValidationUtil.validateNotNull(betDto, "BetDto");
        betDto.setId(BetEntity.TYPE + ID_DELIMITER + UUID.randomUUID());
        betDto.setBetTime(LocalDateTime.now());
        final BetEntity betEntity = BetMapper.INSTANCE.toEntity(betDto);
        return BetMapper.INSTANCE.toDto(betRepository.save(betEntity));
    }

    @Override
    public List<BetDto> getAllBetsByPlayerId(final String playerId) {
        ValidationUtil.validateNotNull(playerId, "PlayerId");
        final List<BetEntity> betEntityList = betRepository.findAllByPlayer(playerId);
        if (CollectionUtils.isEmpty(betEntityList)) {
            throw new NoRecordFoundException("No bets found");
        }
        return BetMapper.INSTANCE.toDtoList(betEntityList);
    }

}
