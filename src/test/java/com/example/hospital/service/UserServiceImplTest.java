package com.example.hospital.service;

import com.example.hospital.dto.RegistrationInfo;
import com.example.hospital.model.Qualification;
import com.example.hospital.model.User;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.example.hospital.model.Role.DOCTOR;
import static com.example.hospital.model.Role.UNDEFINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RegistrationInfo registrationInfo;
    @Mock
    private User user1;
    @InjectMocks
    private UserServiceImpl testInstance;

    private static final User USER = new User();
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@ukr.com";
    private static final long ID = 5;
    private static final String FIRST_NAME = "First";
    private static final String LAST_NAME = "Last";

    @BeforeEach
    void setUp() {
        USER.setPassword(PASSWORD);
        USER.setRole(UNDEFINE);
        USER.setEmail(EMAIL);
        USER.setLastName(FIRST_NAME);
        USER.setFirstName(LAST_NAME);
        USER.setOnTreatment(true);
    }

    @Test
    public void shouldCreateUser() {
        when(registrationInfo.getEmail()).thenReturn(EMAIL);
        when(registrationInfo.getPassword()).thenReturn(PASSWORD);
        when(registrationInfo.getFirstName()).thenReturn(FIRST_NAME);
        when(registrationInfo.getLastName()).thenReturn(LAST_NAME);
        when(userRepository.save(any())).thenReturn(USER);

        testInstance.create(registrationInfo);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void shouldFindById() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(user1));

        testInstance.findById(ID);

        verify(userRepository, times(1)).findById(ID);
    }

    @Test
    public void shouldGetUsersByRoles(){
        testInstance.getUsersByRole(DOCTOR);

        verify(userRepository, times(1)).getUsersByRoleEquals(DOCTOR);
    }

    @Test
    public void shouldSetDoctorQualification() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(user1));

        testInstance.setDoctorQualification(ID, Qualification.SURGEON);

        verify(user1, times(1)).setQualification(Qualification.SURGEON);
        verify(user1, times(1)).setRole(DOCTOR);
        verify(userRepository, times(1)).save(user1);

    }

    @Test
    public void shouldSetUserRole() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(user1));

        testInstance.setUserRole(ID, DOCTOR);

        verify(user1, times(1)).setRole(DOCTOR);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void shouldLoadUserByUsername(){
        when(userRepository.getUserByEmail(EMAIL)).thenReturn(user1);

        testInstance.loadUserByUsername(EMAIL);

        verify(userRepository, times(1)).getUserByEmail(EMAIL);
    }

    @Test
    public void shouldThrowExceptionWhenLoadUserByUsername(){
        when(userRepository.getUserByEmail(EMAIL)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () ->
                testInstance.loadUserByUsername(EMAIL));
    }

    @Test
    public void shouldSuchUserExist() {
        when(testInstance.isSuchUserExist(EMAIL)).thenReturn(true);

        assertEquals(true, userRepository.existsByEmail(EMAIL));
    }

}
