package ru.keller.bidaskanalyzer.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QuoteDto {
    private Long id;
    private String isin;
    private BigDecimal bid;
    private BigDecimal ask;
    private LocalDateTime createdAt;
    private ElvlDto elvl;
}
