package com.tien.service;

import com.tien.config.ConnectionDB;
import com.tien.dto.TripDTO;
import com.tien.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;


    @Override
    public List<TripDTO> getTrips(String departure, String destination, int page, int size) {
        try (Connection conn = ConnectionDB.openConnection()) {
            return tripRepository.getTrips(departure, destination, page, size, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public int getTotalTrips(String departure, String destination) {
        try (Connection conn = ConnectionDB.openConnection()) {
            return tripRepository.getTotalTrips(departure, destination, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}