package org.zensar.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.zensar.dto.TvSeries;

import java.io.IOException;
import java.util.List;

@Component
public class TvSeriesIdProxy {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUri = "https://api.tvmaze.com";

    public TvSeries getTvSeriesById(int id) {
        String url = baseUri + "/shows/" + id;
        try {
            return restTemplate.getForObject(url, TvSeries.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("TV series with ID " + id + " not found");
            } else {
                throw ex;
            }
        }
    }

    public List<TvSeries> getTvSeriesByPersonName(String personName) throws IOException {
        String url = baseUri + "/search/person?q=" + personName;
        String response = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<TvSeries> tvSeriesList = objectMapper.readValue(response,
                new TypeReference<List<TvSeries>>() {});
        return tvSeriesList;
    }
}
