package com.example.hospital.controller;

import static com.example.hospital.controller.Constants.*;
import static com.example.hospital.model.Role.*;
import static org.slf4j.LoggerFactory.getLogger;

import javax.validation.Valid;

import com.example.hospital.dto.DoctorDto;
import com.example.hospital.dto.PatientDTO;
import com.example.hospital.dto.PatientInfoDto;
import com.example.hospital.dto.RegistrationUserDto;
import com.example.hospital.model.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.hospital.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER  = getLogger(UserController.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasAuthority('ADMIN') or isAnonymous()")
    @GetMapping()
    public String register(Model model) {
        model.addAttribute(USER, new RegistrationUserDto());
        return PAGE_REGISTRATION;
    }

    @PreAuthorize("hasAuthority('ADMIN') or isAnonymous()")
    @PostMapping()
    public String create(@ModelAttribute("user")  @Valid RegistrationUserDto user,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            LOGGER.error("Error during binding user.");
            bindingResult.getAllErrors().forEach(error -> LOGGER.error(error.getDefaultMessage()));
            return PAGE_REGISTRATION;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
        return REDIRECT_PREFIX;
    }

    @GetMapping("/recent")
    public String showUsers(Model model) {
        model.addAttribute(USERS, userService.getUsersByRole(UNDEFINE));
        return PAGE_USERS_RECENT;
    }

    @GetMapping("/nurses/{id}")
    public String setAsNurse(@PathVariable long id) {
        userService.setUserRole(id, NURSE);
        return REDIRECT_TO_PAGE_NURSES;
    }

    @GetMapping("/nurses")
    public String getAllNurses(Model model) {
        model.addAttribute(USERS, userService.getUsersByRole(NURSE));
        return PAGE_NURSES;
    }

    @GetMapping("/doctors/{id}")
    public String setAsDoctor(@PathVariable long id, Model model) {
        model.addAttribute(USER, new DoctorDto(userService.findById(id)));
        return PAGE_DOCTOR_REGISTRATION;
    }

    @PostMapping("/doctors/{id}")
    public String setDoctorQualification(@PathVariable long id, @ModelAttribute DoctorDto user) {
        userService.setDoctorQualification(id, user.getQualification());
        return REDIRECT_TO_PAGE_DOCTORS;
    }

    @GetMapping("/doctors")
    public String getAllDoctors(Model model) {
        model.addAttribute(USERS, userService.getUsersByRole(DOCTOR));
        return PAGE_DOCTORS;
    }

    @GetMapping("/patients/{id}")
    public String setAsPatient(@PathVariable long id, Model model) {
        userService.setUserRole(id, PATIENT);
        model.addAttribute(PATIENT_DTO, new PatientDTO(userService.findById(id)));
        model.addAttribute(DOCTORS, userService.getUsersByRole(DOCTOR));
        return PAGE_PATIENT_REGISTRATION;
    }

    @PostMapping("/patients/{id}")
    public String setPatient(@PathVariable long id,
                             @ModelAttribute("patientDTO") PatientDTO patientDTO) {
        userService.patientDtoToUsers(patientDTO);
        return REDIRECT_TO_PAGE_PATIENTS_OF+id;
    }

    @GetMapping("/patients/of/{id}/{pageNo}")
    public String getAllPatients(@PathVariable long id,
                                 @PathVariable int pageNo,
                                 @RequestParam String sortField,
                                 @RequestParam String sortDir,
                                 Model model) {
        int pageSize = PAGN_NOTE_PER_PAGE;
        Page<User> page = userService.getPatientsByEmployeesId(id,pageNo,pageSize, sortField, sortDir);
        addToModel(page, model,pageNo,sortField,sortDir);
        return PAGE_PATIENTS;
    }


    @GetMapping("/patients/{id}/info")
    public String getPatientInfo(@PathVariable long id, Model model) {
        model.addAttribute(PATIENT_INFO_DTO, new PatientInfoDto(userService.getUserById(id)));
        return PAGE_PATIENT_INFO;
    }

    @GetMapping("/doctors/page/{pageNo}")
    public String viewPaginationDoctor(@PathVariable int pageNo,
                                 @RequestParam String sortField,
                                 @RequestParam String sortDir,
                                 Model model) {
        int pageSize = PAGN_NOTE_PER_PAGE;
        Page<User> page = userService.findPaginatedUser(pageNo,pageSize, sortField, sortDir, DOCTOR);
        addToModel(page, model,pageNo,sortField,sortDir);
        return PAGE_DOCTORS;
    }

    Model addToModel(Page<User> page, Model model, int pageNo, String sortField, String sortDir) {
        model.addAttribute(PAGN_CURRENT_PAGE, pageNo);
        model.addAttribute(PAGN_TOTAL_PAGES, page.getTotalPages());
        model.addAttribute(PAGN_TOTAL_USER, page.getTotalElements());
        model.addAttribute(PAGN_SORT_FIELD, sortField);
        model.addAttribute(PAGN_SORT_DIRECTION, sortDir);
        model.addAttribute(PAGN_REVERSE_SORT_DIR,
                sortDir.equals(PAGN_ASC) ? PAGN_DESC : PAGN_ASC);
        model.addAttribute(USERS, page.getContent());
        return model;
    }

}
