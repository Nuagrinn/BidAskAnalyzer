package ru.keller.bidaskanalyzer.validator;

import org.springframework.stereotype.Component;
import ru.keller.bidaskanalyzer.dto.QuoteDto;
import ru.keller.bidaskanalyzer.entity.ElvlEntity;
import ru.keller.bidaskanalyzer.exception.BusinessValidationException;
import ru.keller.bidaskanalyzer.repository.ElvlRepository;
import ru.keller.bidaskanalyzer.repository.QuoteRepository;

import java.util.Optional;

@Component
public class QuoteValidator {

    private final QuoteRepository quoteRepository;
    private final ElvlRepository elvlRepository;

    public QuoteValidator(QuoteRepository quoteRepository, ElvlRepository elvlRepository) {
        this.quoteRepository = quoteRepository;
        this.elvlRepository = elvlRepository;
    }

    public void validate(QuoteDto quoteDto) {
        validateBidAndAsk(quoteDto);
    }

    /**
     * Проверка: Bid < Ask.
     */
    private void validateBidAndAsk(QuoteDto quoteDto) {
        if (quoteDto.getBid().compareTo(quoteDto.getAsk()) >= 0) {
            throw new BusinessValidationException("Bid должен быть меньше Ask.");
        }
    }


}
