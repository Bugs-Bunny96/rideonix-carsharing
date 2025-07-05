package com.example.carsharing.controller;

import com.example.carsharing.model.User;
import com.example.carsharing.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model) {

        boolean usernameExists = userService.usernameExists(user.getUsername());
        boolean emailExists = userService.emailExists(user.getEmail());

        if (usernameExists) {
            bindingResult.rejectValue("username", "error.user", "Username already exists!");
        }

        if (emailExists) {
            bindingResult.rejectValue("email", "error.user", "Email already registered!");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
