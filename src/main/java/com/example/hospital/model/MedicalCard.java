package com.example.hospital.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@ToString
@Table(name = "cards")
public class MedicalCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "medicalCard")
    private User user;

    @Column(name = "final_diagnosis")
    private String finalDiagnosis;

    @OneToMany(mappedBy = "medicalCard", cascade = CascadeType.REMOVE)
    private List<Assignment> assignmentList;


}
