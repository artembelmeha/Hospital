package com.example.hospital.service;

import com.example.hospital.model.Assignment;
import com.example.hospital.model.MedicalCard;
import com.example.hospital.model.User;
import com.example.hospital.repository.AssignmentRepository;
import com.example.hospital.repository.MedicalCardRepository;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.service.impl.AssignmentServiceImpl;
import com.example.hospital.service.impl.MedicalCardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.example.hospital.model.Role.UNDEFINE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MedicalCardServiceImplTest {
    @Mock
    private MedicalCardRepository medicalCardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MedicalCard medicalCard;
    @Mock
    private User user;
    @Mock
    private User doctor;

    @InjectMocks
    private MedicalCardServiceImpl testInstance;

    private static final long ID = 5;
    private static final long DOCTOR_ID = 10;
    private static final int PATIENTS_NUMBER = 4;
    private static final String DIAGNOSIS = "Final diagnosis";

    @Test
    public void shouldGetCardById() {
        when(medicalCardRepository.findMedicalCardById(ID)).thenReturn(Optional.of(medicalCard));

        testInstance.getCardById(ID);
        verify(medicalCardRepository, times(1)).findMedicalCardById(ID);
    }

    @Test
    public void shouldThrowExceptionWhenGetCardById() {
        when(medicalCardRepository.findMedicalCardById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()->testInstance.getCardById(ID));

    }

    @Test
    public void shouldSetDischargeUserById() {
        when(userRepository.getUserByMedicalCardId(ID)).thenReturn(user);
        when(medicalCardRepository.findMedicalCardById(ID)).thenReturn(Optional.of(medicalCard));
        when(user.getDoctor()).thenReturn(doctor);
        when(doctor.getId()).thenReturn(DOCTOR_ID);
        when(userRepository.getUserById(DOCTOR_ID)).thenReturn(doctor);
        when(doctor.getPatientsNumber()).thenReturn(PATIENTS_NUMBER);

        testInstance.dischargePatient(DIAGNOSIS, ID);

        verify(user, times(1)).setDoctor(null);
        verify(user, times(1)).setRole(UNDEFINE);
        verify(user, times(1)).setOnTreatment(false);
        verify(doctor, times(1)).setPatientsNumber(PATIENTS_NUMBER - 1);
        verify(medicalCard, times(1)).setFinalDiagnosis(DIAGNOSIS);
        verify(medicalCardRepository, times(1)).save(medicalCard);

    }

}
