package com.tien.repository;

import com.tien.dto.TripDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TripRepositoryImpl implements TripRepository {


    @Override
    public List<TripDTO> getTrips(String departure, String destination, int page, int size, Connection conn) throws Exception {
        String sql = "{CALL GetTrips(?, ?, ?, ?, ?)}";
        List<TripDTO> trips = new ArrayList<>();
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, departure);
            stmt.setString(2, destination);
            stmt.setInt(3, page);
            stmt.setInt(4, size);
            stmt.registerOutParameter(5, Types.INTEGER);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TripDTO trip = new TripDTO(
                        rs.getInt("id"),
                        rs.getString("departure"),
                        rs.getString("destination"),
                        rs.getObject("departureTime", LocalDateTime.class),
                        rs.getDouble("price")
                );
                trips.add(trip);
            }
            return trips;
        }
    }

    @Override
    public int getTotalTrips(String departure, String destination, Connection conn) throws Exception {
        String sql = "{CALL GetTrips(?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, departure);
            stmt.setString(2, destination);
            stmt.setInt(3, 1);
            stmt.setInt(4, 1);
            stmt.registerOutParameter(5, Types.INTEGER);
            stmt.execute();
            return stmt.getInt(5);
        }
    }
}