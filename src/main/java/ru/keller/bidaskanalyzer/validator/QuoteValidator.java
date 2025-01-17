package ru.keller.bidaskanalyzer.validator;

import org.springframework.stereotype.Component;
import ru.keller.bidaskanalyzer.dto.QuoteDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuoteValidator {

    private final List<ValidationRule<QuoteDto>> rules = new ArrayList<>();

    public QuoteValidator() {
        rules.add(this::validateIsin);
        rules.add(this::validateBidAndAsk);
    }

    /**
     * Выполняет все правила валидации.
     *
     */
    public void validate(QuoteDto quoteDto) {
        for (ValidationRule<QuoteDto> rule : rules) {
            rule.validate(quoteDto);
        }
    }

    /**
     * Проверка ISIN.
     */
    private void validateIsin(QuoteDto quoteDto) {
        if (quoteDto.getIsin() == null || quoteDto.getIsin().length() != 12) {
            throw new IllegalArgumentException("ISIN должен содержать ровно 12 символов.");
        }
    }

    /**
     * Проверка: Bid < Ask.
     */
    private void validateBidAndAsk(QuoteDto quoteDto) {
        if (quoteDto.getBid() != null && quoteDto.getAsk() != null &&
                quoteDto.getBid().compareTo(quoteDto.getAsk()) >= 0) {
            throw new IllegalArgumentException("Bid должен быть меньше Ask.");
        }
    }
}
