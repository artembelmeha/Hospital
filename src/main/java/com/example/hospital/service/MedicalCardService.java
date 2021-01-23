package com.example.hospital.service;

import com.example.hospital.model.MedicalCard;

public interface MedicalCardService {

    MedicalCard getCardById(Long id);

    void dischargePatient(String diagnosis, long id);
}
