package com.example.hospital.service.impl;

import com.example.hospital.model.MedicalCard;
import com.example.hospital.model.User;
import com.example.hospital.repository.MedicalCardRepository;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.service.MedicalCardService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static com.example.hospital.model.Role.UNDEFINE;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MedicalCardServiceImpl implements MedicalCardService {
    private static final Logger LOGGER = getLogger(MedicalCardServiceImpl.class);

    @Resource
    private MedicalCardRepository medicalCardRepository;
    @Resource
    private UserRepository userRepository;

    @Override
    public MedicalCard getCardById(Long id) {
        return medicalCardRepository.findMedicalCardById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medical Card with id [" + id + "] not found."));
    }

    @Transactional
    @Override
    public void dischargePatient(String diagnosis, long id) {
        User user = userRepository.getUserByMedicalCardId(id);
        User doctor = userRepository.getUserById(user.getDoctor().getId());
        setDischargeUserById(user);
        doctor.setPatientsNumber(doctor.getPatientsNumber() - 1);
        setFinalDiagnosis(diagnosis, id);
        userRepository.save(doctor);
        userRepository.save(user);
    }

    private User setDischargeUserById(User user) {
        user.setDoctor(null);
        user.setRole(UNDEFINE);
        user.setOnTreatment(false);
        return user;
    }

    private void setFinalDiagnosis(String diagnosis, long id) {
        Optional<MedicalCard> medicalCard= medicalCardRepository.findMedicalCardById(id);
        if (medicalCard.isPresent()) {
            medicalCard.get().setFinalDiagnosis(diagnosis);
            medicalCardRepository.save(medicalCard.get());
        }
        LOGGER.error("MedicalCard  [{}] was not found, diagnosis [{}] did not setup.", id, diagnosis);
    }
}
