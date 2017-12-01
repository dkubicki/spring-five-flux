package com.example.springfiveflux.core.handler;

import com.example.springfiveflux.core.application.FlightService;
import com.example.springfiveflux.core.domain.Flight;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.ReceiverRecord;

import javax.inject.Inject;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

@Slf4j
public class FlightMessageHandler {

    private final ObjectMapper objectMapper;
    private final FlightService flightService;

    @Inject
    public FlightMessageHandler(ObjectMapper objectMapper, FlightService flightService) {
        this.objectMapper = requireNonNull(objectMapper);
        this.flightService = requireNonNull(flightService);
    }

    public void handleFirstGroupMessage(ReceiverRecord<String, String> message) {
        log.info(message.value());
        try {
            Flight flight = objectMapper.readValue(message.value(), Flight.class);
            flightService
                    .save(flight);
        } catch (IOException e) {
            log.info("Unable write to Cassandra");
            //TODO Exception handler need to be added
        }
    }

    public void handleSecondGroupMessage(ReceiverRecord<String, String> message) {
            //TODO Example of another topic message handler
            log.info(message.value());
    }
}
