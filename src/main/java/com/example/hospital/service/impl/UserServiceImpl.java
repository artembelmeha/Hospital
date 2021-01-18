package com.example.hospital.service.impl;

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
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@Service("userServiceImpl")
@Component
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }

    }

    @Override
    public User readById(long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User update(User user) {
        if (user != null) {
            User oldUser = readById(user.getId());
            if (oldUser != null) {
                return userRepository.save(user);
            }
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        User user = readById(id);
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
    public List<User> getUserByRoles(Role role) {
        return userRepository.getUsersByRoleEquals(role);
    }

    @Override
    public boolean setDoctorQualification(long id, Qualification qualification) {
        boolean result = false;
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setQualification(qualification);
            userRepository.save(user);
            result = true;
        }

        return result;
    }

    @Override
    public boolean setUserRoleById(long id, Role role) {
        boolean result = false;
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(role);
            userRepository.save(user);
            result = true;
        }

        return result;
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
    public List<User> getUsersPatientsById(long id) {
        User user = userRepository.getUsersById(id);
        if(user.getRole().equals(ADMIN)) {
            return userRepository.getUsersByRoleEquals(PATIENT);
        }
        if(user.getRole().equals(DOCTOR)) {
            return userRepository.getUserByDoctor(user);
        }
        if(user.getRole().equals(NURSE)) {
            return new ArrayList<>();  //todo: not finished for nurse
        }
        return new ArrayList<>();
    }

    @Override
    public User patientDtoToUsers(PatientDTO patientDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        User user = userRepository.getUsersById(patientDTO.getId());
        user.setFirstName(patientDTO.getFirstName());
        user.setLastName(patientDTO.getLastName());
        user.setBirthDate(LocalDate.parse(patientDTO.getBirthDate(), formatter));
        user.setTelephoneNumber(patientDTO.getTelephoneNumber());
        user.setSex(patientDTO.getSex());
        user.setDoctor(userRepository.getUsersById(patientDTO.getDoctorId()));
        user.setMedicalCard(new MedicalCard());
        user.setOnTreatment(true);
        userRepository.save(user);
        return user;
    }
}
