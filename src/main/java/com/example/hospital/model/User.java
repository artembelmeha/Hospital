package com.example.hospital.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@ToString
@Table(name = "users")
public class User implements UserDetails {
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

//    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
//            message = "Must be minimum 6 characters, at least one letter and one number")
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


    @Column(name = "on_treatment", nullable = false)
    private boolean isOnTreatment = false;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "sex")
    private Sex sex;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private MedicalCard medicalCard;

    @ManyToMany
    @JoinTable(name = "assignment_nursehelper",
            joinColumns = @JoinColumn(name = "nursehelper_id"),
            inverseJoinColumns = @JoinColumn(name = "assignment_id"))
    private Set<Assignment> assignmentList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}