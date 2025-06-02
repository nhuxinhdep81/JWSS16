package com.tien.repository;

import com.tien.dto.BusDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

public interface BusRepository {
    void saveBus(BusDTO bus, Connection conn) throws Exception;
    void updateBus(BusDTO bus, Connection conn) throws Exception;
    void deleteBus(Integer id, Connection conn) throws Exception;
    BusDTO getBusById(Integer id, Connection conn) throws Exception;
    List<BusDTO> getAllBuses(Connection conn) throws Exception;
}