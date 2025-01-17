package ru.keller.bidaskanalyzer.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.keller.bidaskanalyzer.dto.QuoteDto;
import ru.keller.bidaskanalyzer.service.QuoteService;

import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    /**
     * Обработка новой котировки (создание или обновление).
     */
    @PostMapping
    public ResponseEntity<Void> processQuote(@Valid @RequestBody QuoteDto quoteDto) {
        quoteService.processQuote(quoteDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Получение котировки по ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuoteDto> getQuoteById(@PathVariable Long id) {
        return ResponseEntity.ok(quoteService.getQuoteById(id));
    }

    /**
     * Получение всех котировок.
     */
    @GetMapping
    public ResponseEntity<List<QuoteDto>> getAllQuotes() {
        return ResponseEntity.ok(quoteService.getAllQuotes());
    }

    /**
     * Удаление котировки по ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuoteById(@PathVariable Long id) {
        quoteService.deleteQuoteById(id);
        return ResponseEntity.noContent().build();
    }
}
