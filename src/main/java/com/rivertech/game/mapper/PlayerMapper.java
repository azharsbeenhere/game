package com.rivertech.game.mapper;

import com.rivertech.game.dto.PlayerDto;
import com.rivertech.game.entity.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);
    PlayerDto toDto(PlayerEntity playerEntity);
    PlayerEntity toEntity(PlayerDto playerDto);
}
