package com.example.hospital.controller;

import com.example.hospital.service.AssignmentService;
import com.example.hospital.service.MedicalCardService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/finish/{id}")
    public String finishTreatmentIn(@PathVariable long id, @RequestParam("diagnosis") String diagnosis) {
        medicalCardService.setDiagnosisToCard(diagnosis, id);
        return PAGE_HOME;
    }

    @GetMapping("/{id}")
    public String openMedicalCard(@PathVariable long id, Model model) {
        model.addAttribute(MEDICAL_CARD, medicalCardService.getCardById(id));
        model.addAttribute(DIAGNOSIS, "");
        return PAGE_MEDICAL_CARD;
    }
}
