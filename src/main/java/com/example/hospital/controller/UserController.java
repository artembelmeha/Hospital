package com.example.hospital.controller;

import com.example.hospital.model.Qualification;
import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import com.example.hospital.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping()
    public String create(@ModelAttribute("user")  @Valid User user,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "redirect:/people";
        }
//        user.setRole(Role.PATIENT);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
        return "redirect:/users";
    }

    @GetMapping()
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getUserByRoles(null));
        return "/users-list";
    }
    @GetMapping("/nurses/{id}")
    public String makeNurse(@PathVariable long id) {
        userService.setUserRoleById(id, Role.NURSE);
        return "redirect:/users/nurses";
    }

    @GetMapping("/nurses")
    public String makeNurse(Model model) {
        model.addAttribute("users", userService.getUserByRoles(Role.NURSE));
        return "/users-nurse";
    }
    @GetMapping("/doctors/{id}")
    public String makeDoctor(@PathVariable long id, Model model) {
        userService.setUserRoleById(id, Role.DOCTOR);
        model.addAttribute("user", userService.readById(id));
        return "doctor-registration";
    }

    @PostMapping("/doctors/{id}")
    public String setQualification(@PathVariable long id,@ModelAttribute("user") User user) {
        userService.setDoctorQualification(id,user.getQualification());
        return "redirect:/users/doctors";
    }
    @GetMapping("/doctors")
    public String showDoctors(Model model) {
        model.addAttribute("users", userService.getUserByRoles(Role.DOCTOR));
        return "/users-doctors";
    }
    @GetMapping("/patients/{id}")
    public String makePatient(@PathVariable long id, Model model) {
        userService.setUserRoleById(id, Role.PATIENT);
        model.addAttribute("user", userService.readById(id));
        return "patient-registration";
    }
    @PostMapping("/patients/{id}")
    public String setPatient(@PathVariable long id,@ModelAttribute("user") User user) {
        //
        //
        //
        return "redirect:/users/patients";
    }
    @GetMapping("/patients")
    public String showPatients(Model model) {
        model.addAttribute("users", userService.getUserByRoles(Role.PATIENT));
        return "/users-patients";
    }

}
