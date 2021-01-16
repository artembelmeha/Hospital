package com.example.hospital.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString
@Table(name = "users")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="role",
//        discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
            message = "Must be minimum 6 characters, at least one letter and one number")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "qualification")
    @Enumerated(EnumType.STRING)
    private Qualification qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    private User doctor;

    @OneToMany(mappedBy = "doctor")
    private List<User> patients = new ArrayList<>();


}