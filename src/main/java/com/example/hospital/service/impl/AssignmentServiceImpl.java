package com.example.hospital.service.impl;

import com.example.hospital.dto.AssignmentDTO;
import com.example.hospital.model.Assignment;
import com.example.hospital.model.User;
import com.example.hospital.repository.AssignmentRepository;
import com.example.hospital.repository.MedicalCardRepository;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.service.AssignmentService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.HashSet;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class AssignmentServiceImpl implements AssignmentService {
    private static final Logger LOGGER = getLogger(AssignmentService.class);

    @Resource
    private AssignmentRepository assignmentRepository;
    @Resource
    private MedicalCardRepository medicalCardRepository;
    @Resource
    private UserRepository userRepository;


    @Override
    public void addNewAssignment(AssignmentDTO assignmentDTO) {
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
        assignment.setNurses(assignmentDTO.getNurses());
        assignmentRepository.save(assignment);

    }

    @Override
    public Assignment getAssignmentById(long id) {
        return assignmentRepository.getAssignmentById(id);
    }

    @Override
    public void addOneExecutionToAssignmentById(long id) {
        Assignment assignment = assignmentRepository.getAssignmentById(id);
        assignment.setDoneTimes(assignment.getDoneTimes() + 1); //todo
        if(assignment.getDoneTimes() == assignment.getQuantity()) {
            assignment.setComplete(true);
            assignment.setNurses(new HashSet<>());
        }
        assignmentRepository.save(assignment);
    }

    @Override
    public void addNurseByIdToAssignment(long nurseId, long assignmentId) {
        Assignment assignment = assignmentRepository.getAssignmentById(assignmentId);
        assignment.getNurses().add(userRepository.getUserById(nurseId));
        assignmentRepository.save(assignment);
    }
}
