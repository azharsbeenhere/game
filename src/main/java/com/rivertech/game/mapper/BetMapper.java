package com.rivertech.game.mapper;

import com.rivertech.game.dto.BetDto;
import com.rivertech.game.entity.BetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BetMapper {

    BetMapper INSTANCE = Mappers.getMapper(BetMapper.class);

    BetDto toDto(BetEntity betEntity);
    List<BetDto> toDtoList(List<BetEntity> betEntityList);
    BetEntity toEntity(BetDto betDto);
}
