package com.example.hospital.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Table;

@Table(name = "role")
public enum Role implements GrantedAuthority {
    ADMIN,DOCTOR, NURSE, PATIENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
