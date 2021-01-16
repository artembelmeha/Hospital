package com.example.hospital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

//@Component
public class WebAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    @Autowired
//    public WebAuthenticationFilter(AuthenticationManager webAuthenticationManager) {
//        setAuthenticationManager(webAuthenticationManager);
//    }
}
