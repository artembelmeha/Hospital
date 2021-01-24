package com.example.hospital.service;

import com.example.hospital.dto.PatientDTO;
import com.example.hospital.dto.RegistrationUserDto;
import com.example.hospital.model.Qualification;
import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    User create(RegistrationUserDto user);

    User findById(long id);

    User update(User user);

    void delete(long id);

    List<User> getAll();

    List<User> getUsersByRole(Role role);

    Page<User> getPatientsByEmployeesId(long id,int pageNo, int pageSize, String sortField, String sortDirection);

    void setDoctorQualification(long id, Qualification qualification);

    void setUserRole(long id, Role role);

    User patientDtoToUsers(PatientDTO patientDTO);

    User getUserById(long id);

    Set<User> getAvailableNurse(Set<User> busyNurse);

    Page<User> findPaginatedUser(int pageNo, int pageSize, String sortField, String sortDirection, Role role);

    boolean isSuchUserExist(String email);
}

