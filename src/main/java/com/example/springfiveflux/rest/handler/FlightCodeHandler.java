package com.example.springfiveflux.rest.handler;

import com.example.springfiveflux.core.application.FlightService;
import com.example.springfiveflux.core.domain.Flight;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

@Component
public class FlightCodeHandler {


    private final FlightService flightService;

    @Inject
    public FlightCodeHandler(FlightService flightService) {
        this.flightService = flightService;
    }

    public Mono<ServerResponse> getFlight(ServerRequest request) {
        int flightCode = Integer.valueOf(request.pathVariable("flightCode"));
        Mono<ServerResponse> noFound = ServerResponse.notFound().build();
        Mono<Flight> flight = flightService.findByFlightNumber(flightCode);

        return flight.flatMap(f -> ServerResponse.ok()
                .body(BodyInserters.fromObject(f)))
                .switchIfEmpty(noFound);
    }


}
