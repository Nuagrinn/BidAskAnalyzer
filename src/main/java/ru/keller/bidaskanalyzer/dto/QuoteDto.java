package ru.keller.bidaskanalyzer.dto;

import lombok.Data;
import ru.keller.bidaskanalyzer.validator.ValidIsin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QuoteDto {
    private Long id;

    @ValidIsin
    private String isin;

    private BigDecimal bid;
    private BigDecimal ask;
    private LocalDateTime createdAt;
    private ElvlDto elvl;
}
