package com.example.hospital.dto;


import com.example.hospital.model.Assignment;
import com.example.hospital.model.AssignmentType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;


@Data
@NoArgsConstructor
public class AssignmentDto {
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
    private boolean isComplete;
    private Set<UserDto> nurses = new HashSet<>();

    public AssignmentDto(long medicalCardID) {
        this.medicalCardID = medicalCardID;
    }

    public AssignmentDto(Assignment assignment){
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
        this.nurses = emptyIfNull(assignment.getNurses()).stream()
                .map(UserDto::new)
                .collect(Collectors.toSet());
    }
}