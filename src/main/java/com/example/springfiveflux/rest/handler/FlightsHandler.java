package com.example.springfiveflux.rest.handler;

import com.example.springfiveflux.core.application.FlightService;
import com.example.springfiveflux.core.domain.Flight;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class FlightsHandler {

    private final FlightService flightService;

    @Inject
    public FlightsHandler(FlightService flightService) {
        this.flightService = flightService;
    }

    public Mono<ServerResponse> getFlight(ServerRequest request) {
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(flightService.findAll(), Flight.class);
    }

}
