package com.example.hospital.model;

import javax.persistence.Table;

@Table(name = "role")
public enum Role {
    ADMIN,DOCTOR, NURSE, PATIENT;
}
