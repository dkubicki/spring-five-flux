package com.example.springfiveflux.config;

import com.example.springfiveflux.core.DefaultCorePackage;
import com.example.springfiveflux.core.application.FlightService;
import com.example.springfiveflux.core.application.FlightServiceImpl;
import com.example.springfiveflux.core.handler.FlightMessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@EnableKafka
@Configuration
@ComponentScan(basePackageClasses = {DefaultCorePackage.class})
public class KafkaReceiverConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.topics.first-group:#{T(java.util.Arrays).asList('flights')}}")
    private List<String> firstTopicsGroup;
    @Value("${spring.kafka.consumer.topics.second-group:#{T(java.util.Arrays).asList('airports')}}")
    private List<String> secondTopicsGroup;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public FlightMessageHandler flightMessageHandler(FlightService flightService, ObjectMapper objectMapper) {
        return new FlightMessageHandler(objectMapper, flightService);
    }

    private Map<String, Object> kafkaReceiverProps() {
        Map<String, Object> receiverProps = new HashMap<>();
        receiverProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        receiverProps.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        receiverProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        receiverProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return receiverProps;
    }
    
    @Bean
    InitializingBean subscribeFirstGroup(FlightMessageHandler flightMessageHandler) {
        return () ->
            subscribe(flightMessageHandler::handleFirstGroupMessage, firstTopicsGroup);

    }

    @Bean
    InitializingBean subscribeSecondGroup(FlightMessageHandler flightMessageHandler) {
        return () ->
            subscribe(flightMessageHandler::handleSecondGroupMessage, secondTopicsGroup);
    }

    private void subscribe(Consumer<ReceiverRecord<String, String>> consumer, Collection<String> topics) {
        ReceiverOptions<String, String> receiverOptions =
                ReceiverOptions.<String, String>create(kafkaReceiverProps())
                        .subscription(topics);
        Flux<ReceiverRecord<String, String>> handler = KafkaReceiver.create(receiverOptions).receive();

        handler.subscribe(r -> {
            consumer.accept(r);
            r.receiverOffset().acknowledge();
        });
    }
}
