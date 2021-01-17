package com.example.hospital.controller;


import com.example.hospital.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/", "/home"})
    public String home(){
        return "index";
    }


//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("user", new User());
//        return "login";
//    }


}
