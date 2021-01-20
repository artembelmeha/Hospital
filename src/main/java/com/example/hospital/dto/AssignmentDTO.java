package com.example.hospital.dto;

import com.example.hospital.model.Assignment;
import com.example.hospital.model.AssignmentType;
import com.example.hospital.model.MedicalCard;
import com.example.hospital.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
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
    private Set<User> nurses = new HashSet<>();


    public AssignmentDTO(long medicalCardID) {
        this.medicalCardID = medicalCardID;
    }

    public AssignmentDTO(Assignment assignment){
        this.id = assignment.getId();
        this.medicalCardID = assignment.getMedicalCard().getId();
        this.assignmentType = assignment.getType();
        this.name = assignment.getName();
        this.date = assignment.getDate();
        this.quantity = assignment.getQuantity();
        this.doneTimes = assignment.getDoneTimes();
        this.currentDiagnosis = assignment.getCurrentDiagnosis();
        this.notes = assignment.getNotes();
        this.isComplete = assignment.isComplete();
        this.nurses = assignment.getNurses();
    }
}
