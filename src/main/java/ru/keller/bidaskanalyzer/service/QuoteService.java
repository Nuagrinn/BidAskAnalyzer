package ru.keller.bidaskanalyzer.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.keller.bidaskanalyzer.dto.QuoteDto;
import ru.keller.bidaskanalyzer.entity.ElvlEntity;
import ru.keller.bidaskanalyzer.entity.QuoteEntity;
import ru.keller.bidaskanalyzer.mapper.QuoteMapper;
import ru.keller.bidaskanalyzer.repository.ElvlRepository;
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
    private final ElvlRepository elvlRepository;

    public QuoteService(QuoteMapper quoteMapper, QuoteRepository quoteRepository, ElvlService elvlService, QuoteValidator quoteValidator,
                        ElvlRepository elvlRepository) {
        this.quoteMapper = quoteMapper;
        this.quoteRepository = quoteRepository;
        this.elvlService = elvlService;
        this.quoteValidator = quoteValidator;
        this.elvlRepository = elvlRepository;
    }

    @Async("taskExecutor")
    @Transactional
    public void processQuote(QuoteDto quoteDto) {
        quoteValidator.validate(quoteDto);
        Optional<QuoteEntity> existingQuote = quoteRepository.findByIsin(quoteDto.getIsin());
        elvlService.updateOrCreateElvl(quoteDto);

        if (existingQuote.isEmpty()) {
            QuoteEntity newQuote = quoteMapper.toEntity(quoteDto);

            ElvlEntity elvlEntity = elvlRepository.findByIsin(quoteDto.getIsin())
                    .orElseThrow(() -> new RuntimeException("Elvl not found for ISIN: " + quoteDto.getIsin()));
            newQuote.setElvl(elvlEntity);

            quoteRepository.save(newQuote);
        } else {
            QuoteEntity quoteToUpdate = existingQuote.get();
            quoteToUpdate.setBid(quoteDto.getBid());
            quoteToUpdate.setAsk(quoteDto.getAsk());

            ElvlEntity elvlEntity = elvlRepository.findByIsin(quoteDto.getIsin())
                    .orElseThrow(() -> new RuntimeException("Elvl not found for ISIN: " + quoteDto.getIsin()));
            quoteToUpdate.setElvl(elvlEntity);

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
