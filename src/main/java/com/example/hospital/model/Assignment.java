package com.example.hospital.model;

import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;


@Entity
@ToString
@Table(name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssignmentType type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "done_times", nullable = false)
    private int doneTimes;

    @Column(name = "currentDiagnosis", nullable = false)
    private String currentDiagnosis;

    @Column(name = "notes", nullable = false)
    private String notes;

    @Column(name = "is_complete", nullable = false)
    private  boolean isComplete = false;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private MedicalCard medicalCard;

    @ManyToMany
    @JoinTable(name = "assignment_nursehelper",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "nurse_id"))
    private Set<User> nurses;

}
