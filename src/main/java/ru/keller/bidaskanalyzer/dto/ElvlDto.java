package ru.keller.bidaskanalyzer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ElvlDto {
    private Long id;
    private String isin;
    private BigDecimal elvl;
}
