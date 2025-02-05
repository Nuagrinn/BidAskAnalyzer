package ru.keller.bidaskanalyzer.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ElvlDto {

    @NotNull(message = "ID не может быть null")
    @Positive(message = "ID должен быть положительным числом")
    private Long id;

    @NotBlank(message = "ISIN не может быть пустым")
    @Size(min = 12, max = 12, message = "ISIN должен содержать ровно 12 символов")
    @Pattern(regexp = "^[A-Z0-9]{12}$", message = "ISIN должен содержать только буквы и цифры")
    private String isin;

    @DecimalMin(value = "0.0", message = "ELVL не может быть отрицательным")
    private BigDecimal elvl;
}
