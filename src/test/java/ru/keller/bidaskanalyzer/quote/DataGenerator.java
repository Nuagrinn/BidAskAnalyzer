package ru.keller.bidaskanalyzer.quote;

import ru.keller.bidaskanalyzer.entity.QuoteEntity;

import java.math.BigDecimal;

public class DataGenerator {

    public static QuoteEntity generateQuoteEntity() {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setIsin("RU000A0JX0J2");
        quoteEntity.setBid(BigDecimal.valueOf(100.2));
        quoteEntity.setAsk(BigDecimal.valueOf(101.9));
        return quoteEntity;
    }
}