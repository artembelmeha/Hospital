package com.example.hospital.controller;

import static com.example.hospital.controller.Constants.*;
import static com.example.hospital.model.Role.*;
import static org.slf4j.LoggerFactory.getLogger;

import javax.validation.Valid;

import com.example.hospital.dto.PatientDTO;
import com.example.hospital.dto.PatientInfoDto;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.model.User;
import com.example.hospital.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER  = getLogger(UserController.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasAuthority('ADMIN') or isAnonymous()")
    @GetMapping()
    public String register(Model model) {
        model.addAttribute("user", new User());
        return PAGE_REGISTRATION;
    }

    @PreAuthorize("hasAuthority('ADMIN') or isAnonymous()")
    @PostMapping()
    public String create(@ModelAttribute("user")  @Valid User user,
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
        model.addAttribute(USERS, userService.getUsersByRole(null));
        return PAGE_USERS_RECENT;
    }

    @GetMapping("/nurses/{id}")
    public String setAsNurse(@PathVariable long id) {
        userService.setUserRole(id, NURSE);
        return REDIRECT_TO_PAGE_NURSES;
    }

    @GetMapping("/nurses")
    public String getAllNurses(Model model) {
        model.addAttribute(USERS, userService.getUsersByRole(NURSE)); //todo: nurseDTO
        return PAGE_NURSES;
    }

    @GetMapping("/doctors/{id}")
    public String setAsDoctor(@PathVariable long id, Model model) {
        userService.setUserRole(id, DOCTOR);
        model.addAttribute(USER, userService.findById(id)); //todo: doctorDTO
        return PAGE_DOCTOR_REGISTRATION;
    }

    @PostMapping("/doctors/{id}")
    public String setDoctorQualification(@PathVariable long id, @ModelAttribute User user) {
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

    @GetMapping("/patients/of/{id}")
    public String getAllPatients(@PathVariable long id, Model model) {
        model.addAttribute(USERS, userService.getPatientsByEmployeesId(id));
        return PAGE_PATIENTS;
    }
    @GetMapping("/patients/{id}/info")
    public String getPatientInfo(@PathVariable long id, Model model) {
        model.addAttribute(PATIENT_INFO_DTO, new PatientInfoDto(userService.getUserById(id)));
        return PAGE_PATIENT_INFO;
    }

}
