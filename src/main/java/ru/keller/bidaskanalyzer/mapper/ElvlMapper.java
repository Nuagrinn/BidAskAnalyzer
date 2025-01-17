package ru.keller.bidaskanalyzer.mapper;

import org.mapstruct.*;
import ru.keller.bidaskanalyzer.dto.ElvlDto;
import ru.keller.bidaskanalyzer.entity.ElvlEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ElvlMapper {

    @Mapping(target = "id", ignore = true)
    public abstract ElvlEntity toEntity(ElvlDto dto);

    public abstract ElvlDto toDto(ElvlEntity entity);

    @Condition
    public <T> boolean isNotNull(T value) {
        return value != null;
    }

    public void updateEntity(ElvlDto dto, @MappingTarget ElvlEntity entity) {
        if (isNotNull(dto.getElvl())) {
            entity.setElvl(dto.getElvl());
        }
    }
}
