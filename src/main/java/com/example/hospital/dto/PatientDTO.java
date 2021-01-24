package com.example.hospital.dto;

import com.example.hospital.model.Qualification;
import com.example.hospital.model.Sex;
import com.example.hospital.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PatientDto extends UserDto {

    private long doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private Qualification doctorQualification;
    private boolean isOnTreatment = false;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private Sex sex;
    private String telephoneNumber;
    private Long medicalCardId;

    public PatientDto(User user) {
        super(user);
    }
    public PatientDto(User user, long id) {
        super(user);
        User doctor = user.getDoctor();
        this.doctorId = doctor.getId();
        this.doctorFirstName = doctor.getFirstName();
        this.doctorLastName = doctor.getLastName();
        this.isOnTreatment = user.isOnTreatment();
        this.birthDate = user.getBirthDate();
        this.sex = user.getSex();
        this.telephoneNumber = user.getTelephoneNumber();
        this.doctorQualification = doctor.getQualification();
        this.medicalCardId = user.getMedicalCard().getId();
    }

}
