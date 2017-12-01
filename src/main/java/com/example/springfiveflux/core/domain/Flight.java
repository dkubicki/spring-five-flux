package com.example.springfiveflux.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Table("flights")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    @PrimaryKey
    private UUID id;
    private LocalDateTime date;
    private String airline;
    private Integer flightNumber;

    @JsonProperty("flightCode")
    private void unpackNestedProperty(Map<String, Object> flightCode) {
        this.airline = (String) flightCode.get("airline");
        this.flightNumber = (Integer) flightCode.get("flightNumber");
    }
}
