package com.example.springfiveflux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

@SpringBootApplication
public class SpringFiveFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFiveFluxApplication.class, args);
    }

    interface TicketRepository extends ReactiveCassandraRepository {


    }


    @Table
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Ticket {

        @Id
        private String id;
        private String destination;

    }


}
