package com.example.springfiveflux.core.domain;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FlightRepository extends ReactiveCassandraRepository<Flight, String> {

    Mono<Flight> findByFlightNumber(Integer flightNumber);

}
