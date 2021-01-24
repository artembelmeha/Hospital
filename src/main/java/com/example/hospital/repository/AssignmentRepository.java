package com.example.hospital.repository;

import com.example.hospital.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Assignment getAssignmentById(long id);


}
