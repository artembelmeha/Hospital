package com.example.hospital.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.example.hospital.controller.Constants.*;

@Controller
public class HomeController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/", "/home"})
    public String home(){
        return PAGE_HOME;
    }
}