package com.example.hospital.controller;

import com.example.hospital.service.MedicalCardService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.hospital.controller.Constants.*;
import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/medicalCard")
public class MedicalCardController {
    private static final Logger LOGGER  = getLogger(MedicalCardController.class);

    private final MedicalCardService medicalCardService;

    @Autowired
    public MedicalCardController(MedicalCardService medicalCardService) {
        this.medicalCardService = medicalCardService;
    }

    @GetMapping("/{id}")
    public String openMedicalCard(@PathVariable long id, Model model) {
        model.addAttribute(ASSIGNMENTS, medicalCardService.getCardById(id));
        model.addAttribute(ASSIGNMENT_DTO, medicalCardService.getCardById(id));
        model.addAttribute(MEDICAL_CARD_ID, id);
        return PAGE_MEDICAL_CARD;
    }
}
