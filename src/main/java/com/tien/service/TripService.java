package com.tien.service;

import com.tien.dto.TripDTO;
import java.util.List;

public interface TripService {
    List<TripDTO> getTrips(String departure, String destination, int page, int size);
    int getTotalTrips(String departure, String destination);
}