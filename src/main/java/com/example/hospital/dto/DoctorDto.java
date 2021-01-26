package com.example.hospital.dto;

import com.example.hospital.model.Qualification;
import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DoctorDto extends UserDto{

    private Role role;
    private Qualification qualification;
    private int patientsNumber;

    public DoctorDto(User user) {
        super(user);
        this.role = user.getRole();
        this.qualification = user.getQualification();
        this.patientsNumber = user.getPatients().size();
    }

}
