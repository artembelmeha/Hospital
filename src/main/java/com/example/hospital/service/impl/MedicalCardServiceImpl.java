package com.example.hospital.service.impl;

import com.example.hospital.model.MedicalCard;
import com.example.hospital.repository.MedicalCardRepository;
import com.example.hospital.service.MedicalCardService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.sql.SQLDataException;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MedicalCardServiceImpl implements MedicalCardService {
    private static final Logger LOGGER = getLogger(MedicalCardServiceImpl.class);

    @Resource
    private MedicalCardRepository medicalCardRepository;

    @Override
    public Optional<MedicalCard> getCardById(Long id) {
        return medicalCardRepository.findMedicalCardById(id); //todo: add Exception
    }
}
