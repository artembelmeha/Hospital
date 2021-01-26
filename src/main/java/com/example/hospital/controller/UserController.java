package com.example.hospital.controller;

import com.example.hospital.dto.DoctorDto;
import com.example.hospital.dto.PatientDto;
import com.example.hospital.dto.RegistrationInfo;
import com.example.hospital.model.User;
import com.example.hospital.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.example.hospital.controller.Constants.*;
import static com.example.hospital.model.Role.*;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = getLogger(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Value("${default.page.size}")
    private int defaultPageSize;

    @PreAuthorize("hasAuthority('ADMIN') or isAnonymous()")
    @GetMapping()
    public String register(Model model) {
        model.addAttribute(REGISTRATION_INFO, new RegistrationInfo());
        return PAGE_REGISTRATION;
    }

    @PreAuthorize("hasAuthority('ADMIN') or isAnonymous()")
    @PostMapping()
    public String create( @ModelAttribute @Valid RegistrationInfo registrationInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.error("Error during binding registrationInfo.");
            bindingResult.getAllErrors().forEach(error -> LOGGER.error(error.getDefaultMessage()));
            return "registration";
        }
        if (userService.isSuchUserExist(registrationInfo.getEmail())) {
            FieldError error = new FieldError("registrationInfo", "email",
                    "User with such email already exist.");
            bindingResult.addError(error);
            LOGGER.error("User with such email already exist.");
            return "registration";
        }
        registrationInfo.setPassword(passwordEncoder.encode(registrationInfo.getPassword()));
        userService.create(registrationInfo);
        return REDIRECT_PREFIX;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/undefine")
    public String showUndefinedUsers(Model model) {
        model.addAttribute(USERS, userService.getUsersByRole(UNDEFINE));
        return PAGE_USERS_UNDEFINE;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/appoint/nurse/{id}")
    public String setAsNurse(@PathVariable long id) {
        userService.setUserRole(id, NURSE);
        return REDIRECT_TO_PAGE_NURSES;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/nurses")
    public String getAllNurses(Model model) {
        model.addAttribute(USERS, userService.getUsersByRole(NURSE));
        return PAGE_NURSES;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/appoint/doctor/{id}")
    public String setAsDoctor(@PathVariable long id, Model model) {
        model.addAttribute(USER, new DoctorDto(userService.findById(id)));
        return PAGE_DOCTOR_REGISTRATION;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/doctor/define/qualification/{id}")
    public String setDoctorQualification(@PathVariable long id, @ModelAttribute DoctorDto user) {
        userService.setDoctorQualification(id, user.getQualification());
        return REDIRECT_TO_PAGE_DOCTORS;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/doctors")
    public String getAllDoctors(Model model) {
        model.addAttribute(USERS, userService.getUsersByRole(DOCTOR));
        return PAGE_DOCTORS;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/appoint/patient/{id}")
    public String setAsPatient(@PathVariable long id, Model model) {
        model.addAttribute(PATIENT_DTO, new PatientDto(userService.findById(id)));
        model.addAttribute(DOCTORS, getAllDoctors());
        return PAGE_PATIENT_REGISTRATION;
    }

    private List<DoctorDto> getAllDoctors() {
        return userService.getUsersByRole(DOCTOR).stream()
                .map(DoctorDto::new)
                .collect(toList());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/patient")
    public String updatePatientInfo(@ModelAttribute @Valid PatientDto patientDto) {
        userService.updatePatientInfo(patientDto);
        return REDIRECT_TO_PAGE_PATIENTS+patientDto.getId()+PREFIX_INFO;
    }

    @GetMapping("/{id}/patients/page/{pageNo}")
    public String getAllPatients(@PathVariable long id,
                                 @PathVariable int pageNo,
                                 @RequestParam(required = false) String sortField,
                                 @RequestParam(required = false) String sortDir,
                                 Model model) {
        sortField = sortField == null ? FIELD_FIRST_NAME : sortField;
        sortDir = sortDir == null ? PAGN_DESC : sortDir;
        Page<User> page = userService.getPatientsByEmployeesId(id, pageNo, defaultPageSize, sortField, sortDir);
        setupModel(model, page, pageNo, sortField, sortDir);
        model.addAttribute(ID_OF_USER, id);
        return PAGE_PATIENTS;
    }

    @GetMapping("/patients/{id}/info")
    public String getPatientInfo(@PathVariable long id, Model model) {
        model.addAttribute(PATIENT_INFO_DTO, new PatientDto(userService.getUserById(id), id));
        return PAGE_PATIENT_INFO;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/doctors/page/{pageNo}")
    public String viewPaginationDoctor(@PathVariable int pageNo,
                                       @RequestParam(required = false) String sortField,
                                       @RequestParam(required = false) String sortDir,
                                       Model model) {
        sortField = sortField == null ? FIELD_FIRST_NAME : sortField;
        sortDir = sortDir == null ? PAGN_DESC : sortDir;
        Page<User> page = userService.findPaginatedUser(pageNo, defaultPageSize, sortField, sortDir, DOCTOR);
        setupModel(model, page, pageNo, sortField, sortDir);
        return PAGE_DOCTORS;
    }

    private void setupModel(Model model, Page<User> page, int pageNo, String sortField, String sortDir) {
        model.addAttribute(PAGN_CURRENT_PAGE, pageNo);
        model.addAttribute(PAGN_TOTAL_PAGES, page.getTotalPages());
        model.addAttribute(PAGN_TOTAL_USER, page.getTotalElements());
        model.addAttribute(PAGN_SORT_FIELD, sortField);
        model.addAttribute(PAGN_SORT_DIRECTION, sortDir);
        model.addAttribute(PAGN_REVERSE_SORT_DIR, sortDir.equals(PAGN_ASC) ? PAGN_DESC : PAGN_ASC);
        model.addAttribute(USERS, page.getContent());
    }


}
