package com.tien.service;

import com.tien.config.ConnectionDB;
import com.tien.dto.BusDTO;
import com.tien.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;


    @Override
    public void saveBus(BusDTO bus) {
        try (Connection conn = ConnectionDB.openConnection()) {
            conn.setAutoCommit(false);
            busRepository.saveBus(bus, conn);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBus(BusDTO bus) {
        try (Connection conn = ConnectionDB.openConnection()) {
            conn.setAutoCommit(false);
            busRepository.updateBus(bus, conn);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBus(Integer id) {
        try (Connection conn = ConnectionDB.openConnection()) {
            conn.setAutoCommit(false);
            busRepository.deleteBus(id, conn);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public BusDTO getBusById(Integer id) {
        try (Connection conn = ConnectionDB.openConnection()) {
            return busRepository.getBusById(id, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BusDTO> getAllBuses() {
        try (Connection conn = ConnectionDB.openConnection()) {
            return busRepository.getAllBuses(conn);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching buses: " + e.getMessage());
            return List.of();
        }
    }
}