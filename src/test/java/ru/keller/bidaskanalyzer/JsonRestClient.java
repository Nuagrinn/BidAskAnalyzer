package ru.keller.bidaskanalyzer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class JsonRestClient {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public JsonRestClient(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public <T> T get(String url, Class<T> responseType, Object... uriVariables) throws Exception {
        String formattedUrl = formatUrl(url, uriVariables);
        var response = mockMvc.perform(MockMvcRequestBuilders.get(formattedUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(response.getResponse().getContentAsString(), responseType);
    }

    public <T> List<T> getList(String url, TypeReference<List<T>> typeReference, Object... uriVariables) throws Exception {
        String formattedUrl = formatUrl(url, uriVariables);
        var response = mockMvc.perform(MockMvcRequestBuilders.get(formattedUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(response.getResponse().getContentAsString(), typeReference);
    }

    public <T> T post(String url, Object request, Class<T> responseType) throws Exception {
        String formattedUrl = formatUrl(url);
        var response = mockMvc.perform(MockMvcRequestBuilders.post(formattedUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        if (responseType == Void.class) {
            return null;
        }
        return objectMapper.readValue(response.getResponse().getContentAsString(), responseType);
    }

    public void delete(String url, Object... uriVariables) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(formatUrl(url, uriVariables))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private String formatUrl(String url, Object... uriVariables) {
        return String.format(url, uriVariables);
    }
}
