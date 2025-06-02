package com.tien.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusTripDTO {
    private Integer id;
    @NotBlank(message = "Điểm khởi hành không được để trống")
    private String departurePoint;
    @NotBlank(message = "Điểm đến không được để trống")
    private String destination;
    @NotNull(message = "Thời gian khởi hành không được để trống")
    private LocalDateTime departureTime;
    @NotNull(message = "Thời gian đến không được để trống")
    private LocalDateTime arrivalTime;
    @NotNull(message = "ID xe không được để trống")
    private Integer busId;
    @NotNull(message = "Số ghế còn trống không được để trống")
    private Integer seatsAvailable;
    @NotBlank(message = "URL ảnh không được để trống")
    private String image;
}