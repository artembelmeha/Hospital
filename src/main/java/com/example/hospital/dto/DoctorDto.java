package com.example.hospital.dto;

import com.example.hospital.model.Qualification;
import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ToString
@NoArgsConstructor
public class DoctorDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Qualification qualification;

    public DoctorDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.qualification = user.getQualification();
    }
}
