package com.tien.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Integer id;
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private Double price;
}