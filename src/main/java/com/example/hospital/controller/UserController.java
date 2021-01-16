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
        model.addAttribute("users", userService.getAllUserWithoutRole());
        return "/users-list";

    }
    @GetMapping("/nurses/{id}")
    public String makeNurse(@PathVariable long id) {
        User user = userService.readById(id);
        user.setRole(Role.NURSE);
        userService.update(user);
        return "redirect:/users/nurses";
    }

    @GetMapping("/nurses")
    public String makeNurse(Model model) {
        model.addAttribute("users", userService.getAllNurse());
        return "/users-nurse";
    }
    @GetMapping("/doctors/{id}")
    public String makeDoctor(@PathVariable long id, Model model) {
        User user = userService.readById(id);
        user.setRole(Role.DOCTOR);
        userService.update(user);
        model.addAttribute("doctor", user);
        model.addAttribute("qualifications", Qualification.values());
        return "doctor-registration";
    }

}
