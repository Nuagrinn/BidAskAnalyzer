package ru.keller.bidaskanalyzer.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.keller.bidaskanalyzer.dto.QuoteDto;
import ru.keller.bidaskanalyzer.entity.QuoteEntity;
import ru.keller.bidaskanalyzer.mapper.QuoteMapper;
import ru.keller.bidaskanalyzer.repository.QuoteRepository;
import ru.keller.bidaskanalyzer.validator.QuoteValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final QuoteMapper quoteMapper;
    private final QuoteRepository quoteRepository;
    private final ElvlService elvlService;
    private final QuoteValidator quoteValidator;

    public QuoteService(QuoteMapper quoteMapper, QuoteRepository quoteRepository, ElvlService elvlService, QuoteValidator quoteValidator) {
        this.quoteMapper = quoteMapper;
        this.quoteRepository = quoteRepository;
        this.elvlService = elvlService;
        this.quoteValidator = quoteValidator;
    }

    /**
     * Обработка новой котировки (создание или обновление).
     */

    @Async("taskExecutor")
    @Transactional
    public void processQuote(QuoteDto quoteDto) {
        quoteValidator.validate(quoteDto);

        Optional<QuoteEntity> existingQuote = quoteRepository.findByIsin(quoteDto.getIsin());

        if (existingQuote.isEmpty()) {
            QuoteEntity newQuote = quoteMapper.toEntity(quoteDto);
            quoteRepository.save(newQuote);
            elvlService.updateOrCreateElvl(quoteDto);
        } else {
            elvlService.updateOrCreateElvl(quoteDto);

            QuoteEntity quoteToUpdate = existingQuote.get();
            quoteToUpdate.setBid(quoteDto.getBid());
            quoteToUpdate.setAsk(quoteDto.getAsk());
            quoteRepository.save(quoteToUpdate);
        }
    }
    /**
     * Получение котировки по ID.
     */
    @Transactional(readOnly = true)
    public QuoteDto getQuoteById(Long id) {
        QuoteEntity quoteEntity = quoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote not found for ID: " + id));
        return quoteMapper.toDto(quoteEntity);
    }

    /**
     * Получение всех котировок.
     */
    @Transactional(readOnly = true)
    public List<QuoteDto> getAllQuotes() {
        return quoteRepository.findAll().stream()
                .map(quoteMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Удаление котировки по ID.
     */
    @Transactional
    public void deleteQuoteById(Long id) {
        QuoteEntity quoteEntity = quoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote not found for ID: " + id));
        quoteRepository.delete(quoteEntity);
    }
}
