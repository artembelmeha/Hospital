package com.example.hospital.service.impl;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashSet;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.example.hospital.model.Assignment;
import com.example.hospital.repository.AssignmentRepository;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.service.AssignmentService;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AssignmentServiceImpl implements AssignmentService {
    private static final Logger LOGGER = getLogger(AssignmentServiceImpl.class);
    private static final int ONE_EXECUTION = 1;

    @Resource
    private AssignmentRepository assignmentRepository;
    @Resource
    private UserRepository userRepository;

    @Override
    public void addNewAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
        LOGGER.debug("Assignment [{}] was successfully saved.", assignment.getId());
    }

    @Override
    public Assignment get(long assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment with id [" + assignmentId + "] not found."));
    }

    @Override
    @Transactional
    public void addOneExecutionToAssignment(long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment with id [" + assignmentId + "] not found."));

        assignment.setDoneTimes(assignment.getDoneTimes() + ONE_EXECUTION);
        if(assignment.getDoneTimes() == assignment.getQuantity()) {
            assignment.setComplete(true);
            assignment.setNurses(new HashSet<>());
            LOGGER.info("Assignment [{}] completed. Nurses were cleared.", assignmentId);
        }
        assignmentRepository.save(assignment);
    }

    @Transactional
    @Override
    public void addNurseToAssignment(long nurseId, long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment with id [" + assignmentId + "] not found."));
        assignment.getNurses().add(userRepository.getUserById(nurseId));
        assignmentRepository.save(assignment);

        LOGGER.debug("Nurse [{}] was added to assignment [{}].", nurseId, assignmentId);
    }
}
