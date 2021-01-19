package com.example.hospital.controller;

import com.example.hospital.dto.AssignmentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.hospital.controller.Constants.*;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    @GetMapping("/{medicalCardID}")
    public String newAssignment(@PathVariable long medicalCardID, Model model) {
        AssignmentDTO ass =new AssignmentDTO(medicalCardID);
        model.addAttribute(ASSIGNMENT_DTO, new AssignmentDTO(medicalCardID));
        return PAGE_ASSIGNMENT_NEW;
    }

}
