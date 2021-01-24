package com.example.hospital.service;

import com.example.hospital.model.Assignment;

public interface AssignmentService {

    void addNewAssignment(Assignment assignment);

    Assignment get(long id);

    void addOneExecutionToAssignment(long id);

    void addNurseToAssignment(long nurseId, long assignmentId);

}
