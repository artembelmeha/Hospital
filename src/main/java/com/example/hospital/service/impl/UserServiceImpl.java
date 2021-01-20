package com.example.hospital.service.impl;

import static org.slf4j.LoggerFactory.getLogger;

import com.example.hospital.dto.PatientDTO;
import com.example.hospital.exception.NullEntityReferenceException;
import com.example.hospital.model.MedicalCard;
import com.example.hospital.model.Qualification;
import static com.example.hospital.model.Role.*;

import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Component
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = getLogger(UserServiceImpl.class);

    @Resource
    private UserRepository userRepository;

    @Override
    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during saving user.", e);
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException("User with id [" + id + "] not found."));
    }

    @Override
    public User update(User user) {
        if (user != null) {
            User oldUser = findById(user.getId());
            if (oldUser != null) {
                return userRepository.save(user);
            }
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        User user = findById(id);
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.getUsersByRoleEquals(role);
    }

    @Override
    public void setDoctorQualification(long id, Qualification qualification) {
        User user = findById(id);
        user.setRole(DOCTOR);
        user.setQualification(qualification);
        userRepository.save(user);
    }

    @Override
    public void setUserRole(long id, Role role) {
        User user = findById(id);
        user.setRole(role);
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not Found!");
        }
        return user;
    }

    @Override
    public List<User> getPatientsByEmployeesId(long id) {
        User user = userRepository.getUserById(id);
        if(user.getRole().equals(ADMIN)) {
            return userRepository.getUsersByRoleEquals(PATIENT);
        }
        if(user.getRole().equals(DOCTOR)) {
            return userRepository.getUserByDoctor(user);
        }
        if(user.getRole().equals(NURSE)) {
            return userRepository.getUsersByNurseId(id);  ///
        }
        return new ArrayList<>();
    }

    @Override
    public User patientDtoToUsers(PatientDTO patientDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        User user = userRepository.getUserById(patientDTO.getId());
        user.setFirstName(patientDTO.getFirstName());
        user.setLastName(patientDTO.getLastName());
        user.setBirthDate(LocalDate.parse(patientDTO.getBirthDate(), formatter));
        user.setTelephoneNumber(patientDTO.getTelephoneNumber());
        user.setSex(patientDTO.getSex());
        user.setDoctor(userRepository.getUserById(patientDTO.getDoctorId()));
        user.setMedicalCard(new MedicalCard());
        user.setOnTreatment(true);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public Set<User> getAvailableNurse(Set<User> busyNurse) {
        Set<User> result = new HashSet<>(getUsersByRole(NURSE));
        result.removeAll(busyNurse);
        return result;
    }

}
