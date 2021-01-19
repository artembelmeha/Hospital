package com.example.hospital.controller;

import com.example.hospital.dto.AssignmentDTO;
import com.example.hospital.service.AssignmentService;
import com.example.hospital.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.hospital.controller.Constants.*;
import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    private static final Logger LOGGER  = getLogger(AssignmentController.class);

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/{medicalCardID}")
    public String newAssignment(@PathVariable long medicalCardID, Model model) {
        AssignmentDTO ass =new AssignmentDTO(medicalCardID);
        model.addAttribute(ASSIGNMENT_DTO, new AssignmentDTO(medicalCardID));
        return PAGE_ASSIGNMENT_NEW;
    }

    @PostMapping("/add")
    public String createNewAssignment(@ModelAttribute @Valid AssignmentDTO assignmentDTO) {
         assignmentService.addNewAssignment(assignmentDTO);
        return REDIRECT_TO_MEDICAL_CARD+assignmentDTO.getMedicalCardID();
    }
    @GetMapping("/{medicalCardID}/{id}")
    public String viewAssignment(@PathVariable long medicalCardID, @PathVariable long id, Model model) {
        model.addAttribute(ASSIGNMENT_DTO, new AssignmentDTO(assignmentService.getAssignmentById(id)));
        return PAGE_ASSIGNMENT;
    }

    @GetMapping("/{medicalCardID}/{id}/addOne")
    public String addOneExecution(@PathVariable long medicalCardID, @PathVariable long id) {
        assignmentService.addOneExecutionToAssignmentById(id);
        return REDIRECT_TO_ASSIGNMENT+"/"+medicalCardID+"/"+id;
    }


}
