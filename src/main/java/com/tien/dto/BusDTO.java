package com.tien.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    private Integer id;
    @NotBlank(message = "Biển số không được để trống")
    private String licensePlate;
    @NotBlank(message = "Loại xe không được để trống")
    private String busType;
    @NotNull(message = "Số hàng ghế không được để trống")
    private Integer rowSeat;
    @NotNull(message = "Số cột ghế không được để trống")
    private Integer colSeat;
    private Integer totalSeat;
    private String image;
}