package com.example.hospital.dto;

import com.example.hospital.model.AssignmentType;
import com.example.hospital.model.MedicalCard;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ToString
public class AssignmentDTO {
    private long id;
    private long medicalCardID;
    private AssignmentType assignmentType;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private int quantity;
    private int doneTimes;
    private String currentDiagnosis;
    private String notes;
    private  boolean isComplete = false;

    public AssignmentDTO(long medicalCardID) {
        this.medicalCardID = medicalCardID;
    }
}
