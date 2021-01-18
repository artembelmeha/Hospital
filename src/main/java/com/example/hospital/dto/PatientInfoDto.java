package com.example.hospital.dto;

import com.example.hospital.model.Qualification;
import com.example.hospital.model.Sex;
import com.example.hospital.model.User;

import java.time.format.DateTimeFormatter;


public class PatientInfoDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private long doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private Qualification doctorQualification;
    private boolean isOnTreatment = false;
    private String birthDate;
    private Sex sex;
    private String telephoneNumber;

    public PatientInfoDto() {
    }

    public PatientInfoDto(User user) {
        User doctor = user.getDoctor();
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.doctorId = doctor.getId();
        this.doctorFirstName = doctor.getFirstName();
        this.doctorLastName = doctor.getLastName();
        this.doctorQualification = doctor.getQualification();
        this.isOnTreatment = user.isOnTreatment();
        this.birthDate = user.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.sex = user.getSex();
        this.telephoneNumber = user.getTelephoneNumber();
    }
}
