package com.tien.repository;

import com.tien.dto.TripDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public interface TripRepository {
    List<TripDTO> getTrips(String departure, String destination, int page, int size, Connection conn) throws Exception;
    int getTotalTrips(String departure, String destination, Connection conn) throws Exception;
}