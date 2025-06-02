package com.tien.service;

import com.tien.dto.BusDTO;
import java.util.List;

public interface BusService {
    void saveBus(BusDTO bus);
    void updateBus(BusDTO bus);
    void deleteBus(Integer id);
    BusDTO getBusById(Integer id);
    List<BusDTO> getAllBuses();
}