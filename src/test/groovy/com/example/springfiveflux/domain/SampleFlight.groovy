package com.example.springfiveflux.domain

import com.example.springfiveflux.core.domain.Flight
import groovy.transform.CompileStatic

import java.time.LocalDateTime

import static java.util.UUID.randomUUID

@CompileStatic
trait SampleFlight {

            Flight polishFlight = createFlight(randomUUID(), LocalDateTime.now(),"PL", Integer.valueOf(779))
            Flight germanFlight = createFlight(randomUUID(), LocalDateTime.now(),"DE", Integer.valueOf(778))

    static private Flight createFlight(UUID id, LocalDateTime date, String airline, Integer flightNumber){
        return Flight.builder()
                .id(id)
                .date(date)
                .airline(airline)
                .flightNumber(flightNumber)
                .build()
    }

}