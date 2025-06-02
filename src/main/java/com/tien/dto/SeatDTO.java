package com.tien.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private Integer id;
    @NotBlank(message = "Tên ghế không được để trống")
    private String nameSeat;
    @NotNull(message = "Giá ghế không được để trống")
    private Double price;
    private Integer busId;
    private String status;
}