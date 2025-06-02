package com.tien.controller;

import com.tien.dto.BusDTO;
import com.tien.service.BusService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping("/admin/buses")
    public String showBuses(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }
        model.addAttribute("buses", busService.getAllBuses());
        return "buses";
    }

    @GetMapping("/admin/buses/add")
    public String showAddForm(Model model) {
        model.addAttribute("bus", new BusDTO());
        return "add-bus";
    }

    @PostMapping("/admin/buses/add")
    public String addBus(@Valid @ModelAttribute("bus") BusDTO bus, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-bus";
        }
        busService.saveBus(bus);
        return "redirect:/admin/buses";
    }

    @GetMapping("/admin/buses/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        BusDTO bus = busService.getBusById(id);
        if (bus == null) {
            return "redirect:/admin/buses";
        }
        model.addAttribute("bus", bus);
        return "edit-bus";
    }

    @PostMapping("/admin/buses/update")
    public String updateBus(@Valid @ModelAttribute("bus") BusDTO bus, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-bus";
        }
        busService.updateBus(bus);
        return "redirect:/admin/buses";
    }

    @GetMapping("/admin/buses/delete/{id}")
    public String deleteBus(@PathVariable Integer id) {
        busService.deleteBus(id);
        return "redirect:/admin/buses";
    }
}