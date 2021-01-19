package com.example.hospital.repository;

import com.example.hospital.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Assignment getAssignmentById(long id);

    @Transactional
    @Modifying
    @Query(value ="UPDATE assignment SET assignment.done_times =?1, " +
            " assignment.is_complete =?2 WHERE assignment.id =?3", nativeQuery = true)
    void updateDoneTimesAndIsCompleteById(int doteTimes, int isComplete, long id);

}
