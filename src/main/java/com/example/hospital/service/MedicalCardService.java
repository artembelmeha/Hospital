package com.example.hospital.service;

import com.example.hospital.model.MedicalCard;

import java.util.Optional;

public interface MedicalCardService {

    MedicalCard getCardById(Long id);

    void setDiagnosisToCard(String diagnosis, long id);
}
