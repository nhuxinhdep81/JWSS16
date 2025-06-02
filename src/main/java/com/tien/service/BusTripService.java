package com.tien.service;

import com.tien.dto.BusTripDTO;

import java.util.List;

public interface BusTripService {
    void saveBusTrip(BusTripDTO busTrip);
    void updateBusTrip(BusTripDTO busTrip);
    void deleteBusTrip(Integer id);
    BusTripDTO getBusTripById(Integer id);
    List<BusTripDTO> getAllBusTrips();
}