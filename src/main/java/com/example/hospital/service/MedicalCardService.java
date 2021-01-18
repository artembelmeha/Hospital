package com.example.hospital.service;

import com.example.hospital.model.MedicalCard;

import java.util.Optional;

public interface MedicalCardService {
    Optional<MedicalCard> getCardById(Long id);
}
