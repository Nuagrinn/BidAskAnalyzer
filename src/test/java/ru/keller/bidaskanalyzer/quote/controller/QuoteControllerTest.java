package ru.keller.bidaskanalyzer.quote.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.keller.bidaskanalyzer.dto.QuoteDto;
import ru.keller.bidaskanalyzer.entity.QuoteEntity;
import ru.keller.bidaskanalyzer.quote.DataGenerator;
import ru.keller.bidaskanalyzer.quote.QuoteClient;
import ru.keller.bidaskanalyzer.repository.QuoteRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class QuoteControllerTest {

    @Autowired
    private QuoteClient quoteClient;

    @Autowired
    private QuoteRepository quoteRepository;

    private QuoteEntity testQuote;

    @BeforeEach
    void setUp() {
        quoteRepository.deleteAll();
        testQuote = DataGenerator.generateQuoteEntity();
        quoteRepository.saveAndFlush(testQuote);
    }

    @Test
    void processQuote_shouldCreateQuote() throws Exception {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin("RU000A0JX0J2");
        quoteDto.setBid(testQuote.getBid());
        quoteDto.setAsk(testQuote.getAsk());

        quoteClient.processQuote(quoteDto);

        var savedQuote = quoteRepository.findByIsin(quoteDto.getIsin()).orElseThrow();
        assertThat(savedQuote).isNotNull();
        assertThat(savedQuote.getIsin()).isEqualTo(quoteDto.getIsin());
        assertThat(savedQuote.getBid()).isEqualByComparingTo(quoteDto.getBid());
        assertThat(savedQuote.getAsk()).isEqualByComparingTo(quoteDto.getAsk());
    }

    @Test
    void getQuoteById_shouldReturnQuote() throws Exception {
        QuoteDto fetchedQuote = quoteClient.getQuoteById(testQuote.getId());

        assertThat(fetchedQuote).isNotNull();
        assertThat(fetchedQuote.getIsin()).isEqualTo(testQuote.getIsin());
    }

    @Test
    void getAllQuotes_shouldReturnQuotesList() throws Exception {
        var quotes = quoteClient.getAllQuotes();

        assertThat(quotes).isNotEmpty();
        assertThat(quotes.get(0).getIsin()).isEqualTo(testQuote.getIsin());
    }

    @Test
    void deleteQuoteById_shouldDeleteQuote() throws Exception {
        quoteClient.deleteQuoteById(testQuote.getId());

        assertThat(quoteRepository.existsById(testQuote.getId())).isFalse();
    }
}
