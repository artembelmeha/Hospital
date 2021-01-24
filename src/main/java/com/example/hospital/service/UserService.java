package com.example.hospital.service;

import com.example.hospital.dto.PatientDto;
import com.example.hospital.dto.RegistrationInfo;
import com.example.hospital.model.Qualification;
import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    User create(RegistrationInfo user);

    User findById(long id);

    List<User> getUsersByRole(Role role);

    Page<User> getPatientsByEmployeesId(long id,int pageNo, int pageSize, String sortField, String sortDirection);

    void setDoctorQualification(long id, Qualification qualification);

    void setUserRole(long id, Role role);

    User updatePatientInfo(PatientDto patientDTO);

    User getUserById(long id);

    List<User> getAvailableNurses(Set<Long> busyNursesIds);

    Page<User> findPaginatedUser(int pageNo, int pageSize, String sortField, String sortDirection, Role role);

    boolean isSuchUserExist(String email);
}

