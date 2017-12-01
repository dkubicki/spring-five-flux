package com.example.springfiveflux

import com.example.springfiveflux.base.IntegrationSpec
import com.example.springfiveflux.core.application.FlightService
import com.example.springfiveflux.domain.FlightsGen
import com.example.springfiveflux.domain.SampleFlight
import groovy.util.logging.Slf4j
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser

import javax.inject.Inject
import java.time.Duration

import static java.time.Duration.*
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec

@Slf4j
class FlightCodeHandlerAcceptanceSpec extends IntegrationSpec implements SampleFlight, FlightsGen{

    @Inject
    FlightService flightService

    @WithMockUser
    def "should get user hello"() {
        given: 'Proper configuration'

        when: 'I go to /'
        ResponseSpec client = client.get()
                .uri("/")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()

        then: 'All configuration set up correctly'
        client
                .expectStatus().isOk()
    }

    @WithMockUser
    def "should get flight by flightCode from Cassandra"() {
        given: 'database has "polish flight"'
        flightService.deleteAll()
        flightService.save(polishFlight)
        flightService.save(germanFlight)

        when: 'I go to /flights/{flightCode} - put my polish flightCode as param'
        ResponseSpec getFlight = client.get()
                .uri("/flights/{flightCode}", polishFlight.flightNumber)
                .exchange()

        then: "I get 200 and a response"
        getFlight.expectStatus().isOk()
    }

    @WithMockUser
    def "should get 400 and empty response on wrong query"() {
        given: 'database no flights'
        flightService.deleteAll()

        when: 'I go to /flights/{flightCode} - put my polish flightCode as param'
        ResponseSpec getFlight = client.get()
                .uri("/flights/{flightCode}", polishFlight.flightNumber)
                .exchange()

        then: "I get 400 and no response"
        getFlight.expectStatus().isNotFound()
    }

    @WithMockUser
    def "should emmit flights flux"() {
        given: 'database no flights'
        flightService.deleteAll()

        when: 'generate Sse on endpoints'
        ResponseSpec getFlight = client.get()
                .uri("/flights")
                .exchange()
        then: 'Message should be received'
        getFlight.expectStatus().isOk()
    }
}
