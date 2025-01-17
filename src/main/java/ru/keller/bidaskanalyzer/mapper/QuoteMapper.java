package ru.keller.bidaskanalyzer.mapper;

import org.mapstruct.*;
import ru.keller.bidaskanalyzer.dto.ElvlDto;
import ru.keller.bidaskanalyzer.dto.QuoteDto;
import ru.keller.bidaskanalyzer.entity.QuoteEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class QuoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract QuoteEntity toEntity(QuoteDto dto);

    public abstract QuoteDto toDto(QuoteEntity entity);

    @Condition
    public <T> boolean isNotNull(T value) {
        return value != null;
    }

    @Mapping(source = "isin", target = "isin")
    @Mapping(expression = "java(dto.getBid() != null ? dto.getBid() : dto.getAsk())", target = "elvl")
    public abstract ElvlDto toElvlDto(QuoteDto dto);

    public void updateEntity(QuoteDto dto, @MappingTarget QuoteEntity entity) {
        if (isNotNull(dto.getBid())) {
            entity.setBid(dto.getBid());
        }
        if (isNotNull(dto.getAsk())) {
            entity.setAsk(dto.getAsk());
        }
    }
}
