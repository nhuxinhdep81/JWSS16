package com.tien.controller;

import com.tien.dto.TripDTO;
import com.tien.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping("/home")
    public String showHome(
            @RequestParam(required = false) String departure,
            @RequestParam(required = false) String destination,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        List<TripDTO> trips = tripService.getTrips(departure, destination, page, size);
        int total = tripService.getTotalTrips(departure, destination);
        int totalPages = (int) Math.ceil((double) total / size);

        model.addAttribute("trips", trips);
        model.addAttribute("departure", departure);
        model.addAttribute("destination", destination);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "home";
    }
}