package com.example.hospital.controller;

import com.example.hospital.dto.AssignmentDto;
import com.example.hospital.dto.UserDto;
import com.example.hospital.model.Assignment;
import com.example.hospital.model.User;
import com.example.hospital.service.AssignmentService;
import com.example.hospital.service.MedicalCardService;
import com.example.hospital.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import static com.example.hospital.controller.Constants.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;


@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    @Resource
    private AssignmentService assignmentService;
    @Resource
    private UserService userService;
    @Resource
    private MedicalCardService medicalCardService;

    @GetMapping("/{medicalCardID}")
    public String newAssignment(@PathVariable long medicalCardID, Model model) {
        model.addAttribute(ASSIGNMENT_DTO, new AssignmentDto(medicalCardID));
        return PAGE_ASSIGNMENT_NEW;
    }

    @PostMapping("/add")
    public String createNewAssignment(@ModelAttribute @Valid AssignmentDto assignmentDTO) {
        assignmentService.addNewAssignment(convertToEntity(assignmentDTO));
        return REDIRECT_TO_MEDICAL_CARD + assignmentDTO.getMedicalCardID();
    }

    @GetMapping("/view/{assignmentId}")
    public String viewAssignment(@PathVariable long assignmentId, Model model) {
        AssignmentDto assignmentDTO = new AssignmentDto(assignmentService.get(assignmentId));
        model.addAttribute(ASSIGNMENT_DTO, assignmentDTO);
        model.addAttribute(NURSES, getAvailableNurses(assignmentDTO));
        return PAGE_ASSIGNMENT;
    }

    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('NURSE')")
    @GetMapping("/{assignmentId}/addOne")
    public String addOneExecution(@PathVariable long assignmentId) {
        assignmentService.addOneExecutionToAssignment(assignmentId);
        return REDIRECT_TO_ASSIGNMENT_VIEW + assignmentId;
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/nurse/{assignmentId}/{nurseId}")
    public String assignNurseToAssignment(@PathVariable long nurseId, @PathVariable long assignmentId) {
        assignmentService.addNurseToAssignment(nurseId, assignmentId);
        return REDIRECT_TO_ASSIGNMENT_VIEW + assignmentId;
    }

    private Assignment convertToEntity(AssignmentDto assignmentDTO) {
        Assignment assignment = new Assignment();
        assignment.setCurrentDiagnosis(assignmentDTO.getCurrentDiagnosis());
        assignment.setComplete(false);
        assignment.setDoneTimes(0);
        assignment.setDate(assignmentDTO.getDate());
        assignment.setName(assignmentDTO.getName());
        assignment.setNotes(assignmentDTO.getNotes());
        assignment.setQuantity(assignmentDTO.getQuantity());
        assignment.setType(assignmentDTO.getAssignmentType());
        assignment.setNurses( convertNurses(assignmentDTO.getNurses()));
        assignment.setMedicalCard(medicalCardService.getCardById(assignmentDTO.getMedicalCardID()));
        return assignment;
    }

    private Set<User> convertNurses(Set<UserDto> nurses) {
        return emptyIfNull(nurses).stream()
              .map(nurseDto -> {
                  User nurse = new User();
                  nurse.setId(nurseDto.getId());
                  nurse.setFirstName(nurseDto.getFirstName());
                  nurse.setLastName(nurseDto.getLastName());
                  nurse.setEmail(nurseDto.getEmail());
                  nurse.setRole(nurseDto.getRole());
                  return nurse;})
              .collect(toSet());
    }

    private List<UserDto> getAvailableNurses(AssignmentDto assignmentDTO) {
        Set<Long> nursesIds = assignmentDTO.getNurses().stream()
              .map(UserDto::getId)
              .collect(toSet());
        List<User> availableNurses = userService.getAvailableNurses(nursesIds);
        return availableNurses.stream()
              .map(UserDto::new)
              .collect(toList());
    }


}
