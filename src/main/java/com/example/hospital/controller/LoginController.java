package com.example.hospital.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.example.hospital.controller.Constants.*;

@Controller
public class LoginController {

    @GetMapping("/login-form")
    public String login() {
        if (isAuthorizedUser()) {
            return REDIRECT_TO_PAGE_HOME;
        }
        return PAGE_LOGIN;
    }

    private boolean isAuthorizedUser() {
        return !SecurityContextHolder.getContext()
              .getAuthentication()
              .getPrincipal()
              .toString()
              .equals(ANONYMOUS);
    }

}
