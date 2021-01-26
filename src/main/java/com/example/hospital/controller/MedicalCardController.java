package com.example.hospital.controller;

import static com.example.hospital.controller.Constants.*;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.dto.MedicalCardDto;
import com.example.hospital.service.MedicalCardService;


@Controller
@RequestMapping("/medicalCard")
public class MedicalCardController {

    @Resource
    private MedicalCardService medicalCardService;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/finish/{id}")
    public String finishTreatmentIn(@PathVariable long id, @RequestParam String diagnosis) {
        medicalCardService.dischargePatient(diagnosis, id);
        return PAGE_HOME;
    }


    @GetMapping("/{id}")
    public String openMedicalCard(@PathVariable long id, Model model) {
        model.addAttribute(MEDICAL_CARD, new MedicalCardDto(medicalCardService.getCardById(id)));
        model.addAttribute(DIAGNOSIS, "");
        return PAGE_MEDICAL_CARD;
    }

}
