package com.example.hospital.service.impl;

import com.example.hospital.dto.PatientDto;
import com.example.hospital.dto.RegistrationInfo;
import com.example.hospital.model.MedicalCard;
import com.example.hospital.model.Qualification;
import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.service.UserService;
import org.slf4j.Logger;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import static com.example.hospital.model.Role.*;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.domain.Page.empty;
import static org.springframework.data.domain.Sort.Direction.ASC;


@Component
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = getLogger(UserServiceImpl.class);

    @Resource
    private UserRepository userRepository;

    @Override
    public User create(RegistrationInfo registrationInfo) {
        User user = new User();
        user.setPassword(registrationInfo.getPassword());
        user.setRole(UNDEFINE);
        user.setEmail(registrationInfo.getEmail());
        user.setLastName(registrationInfo.getLastName());
        user.setFirstName(registrationInfo.getFirstName());
        user.setOnTreatment(false);
        User savedUser = userRepository.save(user);
        LOGGER.debug("User with id [{}] successfully saved.", savedUser.getId());
        return savedUser;
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException("User with id [" + id + "] not found."));
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.getUsersByRoleEquals(role);
    }

    @Transactional
    @Override
    public void setDoctorQualification(long id, Qualification qualification) {
        User user = findById(id);
        user.setRole(DOCTOR);
        user.setPatientsNumber(0);
        user.setQualification(qualification);
        userRepository.save(user);
        LOGGER.debug("Qualification [{}] successfully updated for doctor [{}].", qualification.name(), id);
    }

    @Transactional
    @Override
    public void setUserRole(long id, Role role) {
        User user = findById(id);
        user.setRole(role);
        userRepository.save(user);
        LOGGER.debug("Role [{}] successfully updated for user [{}].", role.name(), id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email [" + email +"] not found!");
        }
        return user;
    }

    @Transactional
    @Override
    public Page<User> getPatientsByEmployeesId(long id, int pageNo, int pageSize, String sortField, String sortDirection) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        User user = userRepository.getUserById(id);

        switch (user.getRole()) {
            case ADMIN:
                return userRepository.findAllByRole(PATIENT, pageable);
            case DOCTOR:
                return userRepository.getUserByDoctor(user, pageable);
            case NURSE:
                return new PageImpl<>(userRepository.getUsersByNurseId(id));
            default:
                return empty();
        }
    }

    @Transactional
    @Override
    public User updatePatientInfo(PatientDto patientDto) {
        User user = userRepository.getUserById(patientDto.getId());
        user.setFirstName(patientDto.getFirstName());
        user.setLastName(patientDto.getLastName());
        user.setBirthDate(patientDto.getBirthDate());
        user.setTelephoneNumber(patientDto.getTelephoneNumber());
        user.setSex(patientDto.getSex());
        user.setRole(PATIENT);

        User doctor = userRepository.getUserById(patientDto.getDoctorId());
        doctor.setPatientsNumber(doctor.getPatientsNumber() + 1);

        user.setDoctor(doctor);
        user.setMedicalCard(new MedicalCard());
        user.setOnTreatment(true);

        userRepository.save(doctor);
        userRepository.save(user);
        LOGGER.debug("Patient info successfully updated for user [{}].", patientDto.getId());
        return user;
    }


    @Override
    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public List<User> getAvailableNurses(Set<Long> busyNursesIds) {
        return busyNursesIds.isEmpty() ?
                userRepository.getUsersByRoleEquals(NURSE) :
                userRepository.getUsersByRoleEqualsAndIdNotIn(NURSE, busyNursesIds);
    }

    @Override
    public Page<User> findPaginatedUser(int pageNo, int pageSize, String sortField, String sortDirection, Role role) {
        Pageable pageable = getPageable(pageNo, pageSize, sortField, sortDirection);
        return userRepository.findAllByRole(role, pageable);
    }

    private Pageable getPageable(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = getSort(sortField, sortDirection);
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }

    private Sort getSort(String sortField, String sortDirection) {
        return sortDirection.equalsIgnoreCase(ASC.name()) ?
              Sort.by(sortField).ascending() :
              Sort.by(sortField).descending();
    }

    @Override
    public boolean isSuchUserExist(String email) {
        return userRepository.existsByEmail(email);
    }
}
