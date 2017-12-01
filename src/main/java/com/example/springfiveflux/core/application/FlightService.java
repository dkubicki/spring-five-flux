package com.example.springfiveflux.core.application;

import com.example.springfiveflux.core.domain.Flight;
import org.springframework.data.domain.Pageable;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlightService {

    Flux<Flight> findAll();

    Mono<Flight> findByFlightNumber(Integer id);

    Disposable save(Flight flight);

    Mono<Void> deleteAll();
}
