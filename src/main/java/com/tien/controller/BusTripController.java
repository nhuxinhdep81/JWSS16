package com.tien.controller;

import com.tien.dto.BusTripDTO;
import com.tien.service.BusTripService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BusTripController {

    @Autowired
    private BusTripService busTripService;

    @GetMapping("/admin/trips")
    public String showTrips(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }
        model.addAttribute("trips", busTripService.getAllBusTrips());
        return "trips";
    }

    @GetMapping("/admin/trips/add")
    public String showAddForm(Model model) {
        model.addAttribute("trip", new BusTripDTO());
        return "add-trip";
    }

    @PostMapping("/admin/trips/add")
    public String addTrip(@Valid @ModelAttribute("trip") BusTripDTO busTrip, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-trip";
        }
        busTripService.saveBusTrip(busTrip);
        return "redirect:/admin/trips";
    }

    @GetMapping("/admin/trips/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        BusTripDTO busTrip = busTripService.getBusTripById(id);
        if (busTrip == null) {
            return "redirect:/admin/trips";
        }
        model.addAttribute("trip", busTrip);
        return "edit-trip";
    }

    @PostMapping("/admin/trips/update")
    public String updateTrip(@Valid @ModelAttribute("trip") BusTripDTO busTrip, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-trip";
        }
        busTripService.updateBusTrip(busTrip);
        return "redirect:/admin/trips";
    }

    @GetMapping("/admin/trips/delete/{id}")
    public String deleteTrip(@PathVariable Integer id) {
        busTripService.deleteBusTrip(id);
        return "redirect:/admin/trips";
    }
}