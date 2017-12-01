package com.example.springfiveflux.core.application;

import com.example.springfiveflux.core.domain.Flight;
import com.example.springfiveflux.core.domain.FlightRepository;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

import static java.util.Objects.requireNonNull;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Inject
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = requireNonNull(flightRepository);
    }


    @Override
    public Flux<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public Mono<Flight> findByFlightNumber(Integer flightCode) {
        return flightRepository.findByFlightNumber(flightCode);
    }

    @Override
    public Disposable save(Flight f) {
        return flightRepository.save(f).subscribe();

    }

    @Override
    public Mono<Void> deleteAll() {
        return flightRepository.deleteAll();
    }


}
