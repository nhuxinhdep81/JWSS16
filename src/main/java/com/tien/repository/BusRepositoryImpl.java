package com.tien.repository;

import com.tien.dto.BusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BusRepositoryImpl implements BusRepository {


    @Override
    public void saveBus(BusDTO bus, Connection conn) throws Exception {
        String sql = "{CALL AddBusWithSeats(?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, bus.getLicensePlate());
            stmt.setString(2, bus.getBusType());
            stmt.setInt(3, bus.getRowSeat());
            stmt.setInt(4, bus.getColSeat());
            stmt.setString(5, bus.getImage());
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateBus(BusDTO bus, Connection conn) throws Exception {
        String sql = "{CALL UpdateBus(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, bus.getId());
            stmt.setString(2, bus.getLicensePlate());
            stmt.setString(3, bus.getBusType());
            stmt.setInt(4, bus.getRowSeat());
            stmt.setInt(5, bus.getColSeat());
            stmt.setString(6, bus.getImage());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteBus(Integer id, Connection conn) throws Exception {
        String sql = "{CALL DeleteBus(?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public BusDTO getBusById(Integer id, Connection conn) throws Exception {
        String sql = "{CALL GetBusById(?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BusDTO(
                        rs.getInt("id"),
                        rs.getString("licensePlate"),
                        rs.getString("busType"),
                        rs.getInt("rowSeat"),
                        rs.getInt("colSeat"),
                        rs.getInt("totalSeat"),
                        rs.getString("image")
                );
            }
            return null;
        }
    }

    @Override
    public List<BusDTO> getAllBuses(Connection conn) throws Exception {
        String sql = "{CALL GetAllBuses()}";
        List<BusDTO> buses = new ArrayList<>();
        try (CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                buses.add(new BusDTO(
                        rs.getInt("id"),
                        rs.getString("licensePlate"),
                        rs.getString("busType"),
                        rs.getInt("rowSeat"),
                        rs.getInt("colSeat"),
                        rs.getInt("totalSeat"),
                        rs.getString("image")
                ));
            }
            return buses;
        }
    }
}