package ru.keller.bidaskanalyzer.quote;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.keller.bidaskanalyzer.JsonRestClient;
import ru.keller.bidaskanalyzer.dto.QuoteDto;

import java.util.List;

@Component
public class QuoteClient {

    private final JsonRestClient jsonRestClient;

    public QuoteClient(JsonRestClient jsonRestClient) {
        this.jsonRestClient = jsonRestClient;
    }

    public QuoteDto getQuoteById(Long id) throws Exception {
        return jsonRestClient.get("/quotes/%d", QuoteDto.class, id);
    }

    public List<QuoteDto> getAllQuotes() throws Exception {
        return jsonRestClient.getList("/quotes", new TypeReference<>() {});
    }

    public void processQuote(QuoteDto quoteDto) throws Exception {
        jsonRestClient.post("/quotes", quoteDto, Void.class);
    }

    public void deleteQuoteById(Long id) throws Exception {
        jsonRestClient.delete("/quotes/%d", id);
    }
}
