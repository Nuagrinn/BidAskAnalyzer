package ru.keller.bidaskanalyzer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.keller.bidaskanalyzer.dto.ElvlDto;
import ru.keller.bidaskanalyzer.dto.QuoteDto;
import ru.keller.bidaskanalyzer.entity.ElvlEntity;
import ru.keller.bidaskanalyzer.exception.ElvlNotFoundException;
import ru.keller.bidaskanalyzer.mapper.ElvlMapper;
import ru.keller.bidaskanalyzer.repository.ElvlRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElvlService {

    private final ElvlMapper elvlMapper;
    private final ElvlRepository elvlRepository;

    public ElvlService(ElvlMapper elvlMapper, ElvlRepository elvlRepository) {
        this.elvlMapper = elvlMapper;
        this.elvlRepository = elvlRepository;
    }

    /**
     * Создание или обновление elvl на основе новой котировки.
     */
    @Transactional
    public void updateOrCreateElvl(QuoteDto quoteDto) {
        Optional<ElvlEntity> existingElvl = elvlRepository.findByIsin(quoteDto.getIsin());

        ElvlEntity elvlEntity = existingElvl.orElseGet(() -> {
            ElvlEntity newElvl = new ElvlEntity();
            newElvl.setIsin(quoteDto.getIsin());
            newElvl.setElvl(quoteDto.getBid() != null ? quoteDto.getBid() : quoteDto.getAsk());
            return newElvl;
        });

        BigDecimal currentElvl = elvlEntity.getElvl();
        if (quoteDto.getBid() != null && quoteDto.getBid().compareTo(currentElvl) > 0) {
            elvlEntity.setElvl(quoteDto.getBid());
        } else if (quoteDto.getAsk() != null && quoteDto.getAsk().compareTo(currentElvl) < 0) {
            elvlEntity.setElvl(quoteDto.getAsk());
        }

        elvlRepository.save(elvlEntity);
    }

    /**
     * Получение elvl по ISIN.
     */
    @Transactional(readOnly = true)
    public ElvlDto getElvlByIsin(String isin) {
        ElvlEntity elvlEntity = elvlRepository.findByIsin(isin)
                .orElseThrow(() -> new ElvlNotFoundException("Elvl not found for ISIN: " + isin));
        return elvlMapper.toDto(elvlEntity);
    }

    /**
     * Получение всех elvl.
     */
    @Transactional(readOnly = true)
    public List<ElvlDto> getAllElvls() {
        return elvlRepository.findAll().stream()
                .map(elvlMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Удаление elvl по ISIN.
     */
    @Transactional
    public void deleteElvlByIsin(String isin) {
        ElvlEntity elvlEntity = elvlRepository.findByIsin(isin)
                .orElseThrow(() -> new ElvlNotFoundException("Elvl not found for ISIN: " + isin));
        elvlRepository.delete(elvlEntity);
    }
}
