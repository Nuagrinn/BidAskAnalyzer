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
        validateIsinExists(quoteDto);
        validateElvlExists(quoteDto);
    }

    /**
     * Проверка: Bid < Ask.
     */
    private void validateBidAndAsk(QuoteDto quoteDto) {
        if (quoteDto.getBid().compareTo(quoteDto.getAsk()) >= 0) {
            throw new BusinessValidationException("Bid должен быть меньше Ask.");
        }
    }

    /**
     * Проверка, что ISIN уже существует в системе.
     */
    private void validateIsinExists(QuoteDto quoteDto) {
        if (quoteRepository.findByIsin(quoteDto.getIsin()).isPresent()) {
            throw new BusinessValidationException("Котировка с таким ISIN уже существует.");
        }
    }

    /**
     * Проверка, что ElvlEntity уже создано для этого ISIN.
     */
    private void validateElvlExists(QuoteDto quoteDto) {
        Optional<ElvlEntity> elvl = elvlRepository.findByIsin(quoteDto.getIsin());
        if (elvl.isEmpty()) {
            throw new BusinessValidationException("Elvl не найдено для ISIN: " + quoteDto.getIsin());
        }
    }
}
