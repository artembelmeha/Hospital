package com.example.hospital.service;

import com.example.hospital.dto.PatientDTO;
import com.example.hospital.model.Qualification;
import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    User create(User user);

    User findById(long id);

    User update(User user);

    void delete(long id);

    List<User> getAll();

    List<User> getUsersByRole(Role role);

    List<User> getPatientsByEmployeesId(long id);

    void setDoctorQualification(long id, Qualification qualification);

    void setUserRole(long id, Role role);

    User patientDtoToUsers(PatientDTO patientDTO);

    User getUserById(long id);

    Set<User> getAvailableNurse(Set<User> busyNurse);
}

