package com.example.springfiveflux.rest.config;

import com.example.springfiveflux.rest.DefaultRestPackage;
import com.example.springfiveflux.rest.handler.FlightCodeHandler;
import com.example.springfiveflux.rest.handler.FlightsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@ComponentScan(basePackageClasses = DefaultRestPackage.class)
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> flight(FlightCodeHandler flightCodeHandler) {
        return route(GET("/flights/{flightCode}").and(accept(APPLICATION_JSON)), flightCodeHandler::getFlight);

    }

    @Bean
    public RouterFunction<ServerResponse> flights(FlightsHandler flightsHandler) {
        return route(GET("/flights").and(accept(TEXT_EVENT_STREAM)), flightsHandler::getFlight);

    }
}
