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
    private final UserService userService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService, UserService userService) {
        this.assignmentService = assignmentService;
        this.userService = userService;
    }

    @GetMapping("/{medicalCardID}")
    public String newAssignment(@PathVariable long medicalCardID, Model model) {
        model.addAttribute(ASSIGNMENT_DTO, new AssignmentDTO(medicalCardID));
        return PAGE_ASSIGNMENT_NEW;
    }

    @PostMapping("/add")
    public String createNewAssignment(@ModelAttribute @Valid AssignmentDTO assignmentDTO) {
         assignmentService.addNewAssignment(assignmentDTO);
        return REDIRECT_TO_MEDICAL_CARD+assignmentDTO.getMedicalCardID();
    }
    @GetMapping("/view/{id}")
    public String viewAssignment(@PathVariable long id, Model model) {
        AssignmentDTO assignmentDTO = new AssignmentDTO(assignmentService.getAssignmentById(id));
        model.addAttribute(ASSIGNMENT_DTO, assignmentDTO);
        model.addAttribute(USERS, userService.getAvailableNurse(assignmentDTO.getNurses()));
        return PAGE_ASSIGNMENT;
    }

    @GetMapping("/{id}/addOne")
    public String addOneExecution(@PathVariable long id) {
        assignmentService.addOneExecutionToAssignmentById(id);
        return REDIRECT_TO_ASSIGNMENT+"/view/"+id;
    }

    @GetMapping("/nurse/{assignmentId}/{id}")
    public String assignNurseToAssignment(@PathVariable long id, @PathVariable long assignmentId) {
        assignmentService.addNurseByIdToAssignment(id,assignmentId);
        return REDIRECT_TO_ASSIGNMENT+"/view/"+assignmentId;
    }


}
