package com.example.springfiveflux.rest.controller;

import com.example.springfiveflux.core.application.FlightService;
import com.example.springfiveflux.core.domain.Flight;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.MediaType.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    @Inject
    public FlightController(FlightService flightService) {
        this.flightService = requireNonNull(flightService);
    }

    @GetMapping(path = "/findAll", produces = APPLICATION_JSON_VALUE)
    ResponseEntity byFlightCode() {

        Flux<Flight> flights = flightService.findAll();

        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(flights);
    }
}
