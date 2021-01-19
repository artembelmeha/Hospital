package com.example.hospital.service.impl;

import com.example.hospital.dto.AssignmentDTO;
import com.example.hospital.model.Assignment;
import com.example.hospital.repository.AssignmentRepository;
import com.example.hospital.repository.MedicalCardRepository;
import com.example.hospital.service.AssignmentService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.HashSet;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class AssignmentServiceImpl implements AssignmentService {
    private static final Logger LOGGER = getLogger(AssignmentService.class);

    @Resource
    private AssignmentRepository assignmentRepository;
    @Resource
    private MedicalCardRepository medicalCardRepository;

    @Override
    public void addNewAssignment(AssignmentDTO assignmentDTO) {
        System.out.println(assignmentDTO);
        Assignment assignment = new Assignment();
        assignment.setComplete(false);
        assignment.setCurrentDiagnosis(assignmentDTO.getCurrentDiagnosis());
        assignment.setDoneTimes(0);
        assignment.setMedicalCard(medicalCardRepository.findMedicalCardById(assignmentDTO.getMedicalCardID()));
        assignment.setDate(assignmentDTO.getDate());
        assignment.setName(assignmentDTO.getName());
        assignment.setNotes(assignmentDTO.getNotes());
        assignment.setQuantity(assignmentDTO.getQuantity());
        assignment.setType(assignmentDTO.getAssignmentType());
        assignment.setNurses(new HashSet<>());
        assignmentRepository.save(assignment);

    }
}
