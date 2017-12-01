package com.example.springfiveflux.domain

import com.example.springfiveflux.core.domain.Flight
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink

import java.time.Duration

import static java.time.LocalDateTime.now
import static java.util.Arrays.asList
import static java.util.UUID.randomUUID
import static java.util.concurrent.ThreadLocalRandom.current

trait FlightsGen {

    private static final List<Flight> flights = asList(
            new Flight(randomUUID(), now(), "CH", current().nextInt(300, 999)),
            new Flight(randomUUID(), now(), "DE", current().nextInt(300, 999)),
            new Flight(randomUUID(), now(), "CC", current().nextInt(300, 999)),
            new Flight(randomUUID(), now(), "YT", current().nextInt(300, 999)),
            new Flight(randomUUID(), now(), "HP", current().nextInt(300, 999)))


    static Flux<Flight> fetchFlightsStream(Duration period) {
        return Flux.generate({ 0 },
                { index, SynchronousSink<Flight> sink ->
                    sink.next(flights.get(index))
                    (index + 1) % flights.size()
                }).zipWith(Flux.interval(period))
                .map { __ -> __.getT1() }
                .share() as Flux<Flight>
    }
}