package com.tien.controller;

import com.tien.dto.UserDTO;
import com.tien.service.UserService;
import com.tien.utils.ERole;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", ERole.values());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", ERole.values());
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserDTO user, Model model, HttpSession session) {
        String role = userService.findRoleByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (role != null) {
            session.setAttribute("role", role);
            if (role.equals(ERole.ADMIN.name())) {
                return "redirect:/admin/buses";
            } else {
                return "redirect:/home";
            }
        }
        model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
        return "login";
    }
}