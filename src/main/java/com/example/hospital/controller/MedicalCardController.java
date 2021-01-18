package com.example.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.hospital.controller.Constants.REDIRECT_TO_PAGE_NURSES;
import static com.example.hospital.model.Role.NURSE;

@Controller
@RequestMapping("/medicalCard")
public class MedicalCardController {
    @GetMapping("/{id}")
    public String openMedicalCard(@PathVariable long id) {

        return null;
    }
}
