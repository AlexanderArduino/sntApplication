package com.energetik.app.sntapplication.controller;

import com.energetik.app.sntapplication.entity.Gardener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.extras.springsecurity6.util.SpringSecurityContextUtils;

import java.security.Principal;

@Controller
public class StandardController {

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/user")
    public ModelAndView userPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ModelAndView modelAndView = new ModelAndView("/user");
        modelAndView.addObject("username", username);
        return modelAndView;
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
