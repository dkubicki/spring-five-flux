package com.example.springfiveflux.core;

import com.example.springfiveflux.core.domain.Flight;
import com.example.springfiveflux.core.domain.FlightRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Component
public class SampleDataInitializer {

    private final FlightRepository flightRepository;

    @Inject
    SampleDataInitializer(FlightRepository fr) {
        this.flightRepository = requireNonNull(fr);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run(ApplicationReadyEvent evn) throws Exception {
        flightRepository
                .deleteAll()
                .thenMany(Flux.just(Pair.of("PL", 890), Pair.of("PL", 891),
                        Pair.of("CZ", 7272), Pair.of("DE", 2383)))
                .flatMap(d -> this.flightRepository.save(new Flight(UUID.randomUUID(), LocalDateTime.now(), d.getLeft(), d.getRight())))
                .subscribe();
    }
}
