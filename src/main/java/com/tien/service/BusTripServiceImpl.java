package com.tien.service;

import com.tien.config.ConnectionDB;
import com.tien.dto.BusTripDTO;
import com.tien.repository.BusTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class BusTripServiceImpl implements BusTripService {

    @Autowired
    private BusTripRepository busTripRepository;



    @Override
    public void saveBusTrip(BusTripDTO busTrip) {
        try (Connection conn = ConnectionDB.openConnection()) {
            conn.setAutoCommit(false);
            try {
                busTripRepository.saveBusTrip(busTrip, conn);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("Lỗi khi lưu chuyến xe: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kết nối database: " + e.getMessage());
        }
    }

    @Override
    public void updateBusTrip(BusTripDTO busTrip) {
        try (Connection conn = ConnectionDB.openConnection()) {
            conn.setAutoCommit(false);
            try {
                busTripRepository.updateBusTrip(busTrip, conn);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("Lỗi khi cập nhật chuyến xe: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kết nối database: " + e.getMessage());
        }
    }

    @Override
    public void deleteBusTrip(Integer id) {
        try (Connection conn = ConnectionDB.openConnection()) {
            conn.setAutoCommit(false);
            try {
                busTripRepository.deleteBusTrip(id, conn);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("Lỗi khi xóa chuyến xe: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kết nối database: " + e.getMessage());
        }
    }

    @Override
    public BusTripDTO getBusTripById(Integer id) {
        try (Connection conn = ConnectionDB.openConnection()) {
            return busTripRepository.getBusTripById(id, conn);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy thông tin chuyến xe: " + e.getMessage());
        }
    }

    @Override
    public List<BusTripDTO> getAllBusTrips() {
        try (Connection conn = ConnectionDB.openConnection()) {
            return busTripRepository.getAllBusTrips(conn);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách chuyến xe: " + e.getMessage());
        }
    }
}