package com.stepup.controller;

import com.stepup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String mobileNumber, 
                           @RequestParam String password, @RequestParam String name) {
        try {
            userService.registerUser(email, mobileNumber, password, name);
            return "redirect:/login?success";
        } catch (Exception e) {
            return "redirect:/login?error_register";
        }
    }
}
