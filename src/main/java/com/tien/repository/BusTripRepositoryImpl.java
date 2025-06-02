package com.tien.repository;

import com.tien.dto.BusTripDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BusTripRepositoryImpl implements BusTripRepository {

    @Override
    public void saveBusTrip(BusTripDTO busTrip, Connection conn) throws Exception {
        String sql = "{CALL AddBusTrip(?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, busTrip.getDeparturePoint());
            stmt.setString(2, busTrip.getDestination());
            stmt.setTimestamp(3, Timestamp.valueOf(busTrip.getDepartureTime()));
            stmt.setTimestamp(4, Timestamp.valueOf(busTrip.getArrivalTime()));
            stmt.setInt(5, busTrip.getBusId());
            stmt.setInt(6, busTrip.getSeatsAvailable());
            stmt.setString(7, busTrip.getImage());
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateBusTrip(BusTripDTO busTrip, Connection conn) throws Exception {
        String sql = "{CALL UpdateBusTrip(?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, busTrip.getId());
            stmt.setString(2, busTrip.getDeparturePoint());
            stmt.setString(3, busTrip.getDestination());
            stmt.setTimestamp(4, Timestamp.valueOf(busTrip.getDepartureTime()));
            stmt.setTimestamp(5, Timestamp.valueOf(busTrip.getArrivalTime()));
            stmt.setInt(6, busTrip.getBusId());
            stmt.setInt(7, busTrip.getSeatsAvailable());
            stmt.setString(8, busTrip.getImage());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteBusTrip(Integer id, Connection conn) throws Exception {
        String sql = "{CALL DeleteBusTrip(?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public BusTripDTO getBusTripById(Integer id, Connection conn) throws Exception {
        String sql = "{CALL GetBusTripById(?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BusTripDTO(
                        rs.getInt("id"),
                        rs.getString("departurePoint"),
                        rs.getString("destination"),
                        rs.getTimestamp("departureTime").toLocalDateTime(),
                        rs.getTimestamp("arrivalTime").toLocalDateTime(),
                        rs.getInt("busId"),
                        rs.getInt("seatsAvailable"),
                        rs.getString("image")
                );
            }
            return null;
        }
    }

    @Override
    public List<BusTripDTO> getAllBusTrips(Connection conn) throws Exception {
        String sql = "{CALL GetAllBusTrips()}";
        List<BusTripDTO> busTrips = new ArrayList<>();
        try (CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                busTrips.add(new BusTripDTO(
                        rs.getInt("id"),
                        rs.getString("departurePoint"),
                        rs.getString("destination"),
                        rs.getTimestamp("departureTime").toLocalDateTime(),
                        rs.getTimestamp("arrivalTime").toLocalDateTime(),
                        rs.getInt("busId"),
                        rs.getInt("seatsAvailable"),
                        rs.getString("image")
                ));
            }
            return busTrips;
        }
    }
}