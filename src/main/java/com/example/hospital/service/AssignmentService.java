package com.example.hospital.service;

import com.example.hospital.dto.AssignmentDTO;
import com.example.hospital.model.Assignment;

public interface AssignmentService {

    void addNewAssignment(AssignmentDTO assignmentDTO);

    Assignment getAssignmentById(long id);

    void addOneExecutionToAssignmentById(long id);

    void addNurseByIdToAssignment(long nurseId, long assignmentId);
}
