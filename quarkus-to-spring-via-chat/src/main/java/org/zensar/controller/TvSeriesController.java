package org.zensar.controller;

//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import jakarta.json.JsonArray;
//import jakarta.ws.rs.PathParam;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zensar.client.TvSeriesIdProxy;
import org.zensar.dto.TvSeries;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {

    @Autowired
    private TvSeriesIdProxy proxy;

    @GetMapping("/{id}")
    @CircuitBreaker(
            name = "tvSeriesCircuitBreaker",
            fallbackMethod = "getTvSeriesByIdFallback",
            requestVolumeThreshold = 4,
            failureRateThreshold = 0.5,
            delay = 1000
    )
    public ResponseEntity<?> getTvSeriesById(@PathVariable("id") int id) {
        return ResponseEntity.ok(proxy.getTvSeriesById(id));
    }

    @GetMapping("/person/{personname}")
//    public JsonArray getTvSeriesByPersonName(@PathParam("personname") String personName) {
    public List<TvSeries> getTvSeriesByPersonName(@PathParam("personname") String personName) throws IOException {
        return proxy.getTvSeriesByPersonName(personName);
    }

    public ResponseEntity<?> getTvSeriesByIdFallback(int id, Throwable throwable) {
        return ResponseEntity.ok("Site is under Maintenance. Failed with error: " + throwable.getMessage());
    }
}