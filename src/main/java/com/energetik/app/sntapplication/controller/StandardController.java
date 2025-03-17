package com.energetik.app.sntapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StandardController {

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/user")
    public String userPage() {
        return "/user";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "/admin";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }

}
