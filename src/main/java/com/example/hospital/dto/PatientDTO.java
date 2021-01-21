package com.example.hospital.dto;

import com.example.hospital.model.*;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Pattern;


@Data
@ToString
public class PatientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private long doctorId;
    private boolean isOnTreatment = false;
    @Pattern(regexp = "[dddd-dd-dd]", message = "Invalid data")
    private String birthDate;
    private Sex sex;
    private String telephoneNumber;

    public PatientDTO() {
    }

    public PatientDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
