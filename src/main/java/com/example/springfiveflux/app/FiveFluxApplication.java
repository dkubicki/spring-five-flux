package com.example.springfiveflux.app;

import com.example.springfiveflux.config.CassandraConfig;
import com.example.springfiveflux.config.KafkaReceiverConfig;
import com.example.springfiveflux.config.SecurityConfig;
import com.example.springfiveflux.rest.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CassandraConfig.class, KafkaReceiverConfig.class, WebConfig.class, SecurityConfig.class})
public class FiveFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiveFluxApplication.class, args);


    }
}

