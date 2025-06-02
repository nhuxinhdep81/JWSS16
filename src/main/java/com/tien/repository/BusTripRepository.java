package com.tien.repository;

import com.tien.dto.BusTripDTO;

import java.sql.Connection;
import java.util.List;

public interface BusTripRepository {
    void saveBusTrip(BusTripDTO busTrip, Connection conn) throws Exception;
    void updateBusTrip(BusTripDTO busTrip, Connection conn) throws Exception;
    void deleteBusTrip(Integer id, Connection conn) throws Exception;
    BusTripDTO getBusTripById(Integer id, Connection conn) throws Exception;
    List<BusTripDTO> getAllBusTrips(Connection conn) throws Exception;
}