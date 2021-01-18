package com.example.hospital.repository;


import com.example.hospital.model.MedicalCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalCardRepository extends JpaRepository<MedicalCard, Long> {

    Optional<MedicalCard> findMedicalCardById(long id);
}
