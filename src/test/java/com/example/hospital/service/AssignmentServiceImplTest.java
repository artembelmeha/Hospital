package com.example.hospital.service;

import com.example.hospital.model.Assignment;
import com.example.hospital.model.AssignmentType;
import com.example.hospital.repository.AssignmentRepository;
import com.example.hospital.service.impl.AssignmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AssignmentServiceImplTest {

    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private Assignment assignment2;

    @InjectMocks
    private AssignmentServiceImpl testInstance;


    private static final Assignment ASSIGNMENT = new Assignment();
    private static final long ASSIGNMENT_ID = 1L;
    private static final String ASSIGNMENT_NAME = "Find something";
    private static AssignmentType ASSIGNMENT_TYPE = AssignmentType.DRUGS;
    private static LocalDate ASSIGNMENT_DATE = LocalDate.of(2020, Month.JANUARY, 8);
    private static int ASSIGNMENT_QUANTITY = 5;
    private static int QUANTITY_EQUALS_TO_DONE_TIMES = 3;
    private static int DONE_TIMES = 2;
    private static int DONE_TIMES_PLUS_ONE = 3;
    private static String ASSIGNMENT_CURRENT_DIAGNOSIS = "Something";
    private static String ASSIGNMENT_NOTES = "-";
    private static boolean ASSIGNMENT_IS_COMPLETE = false;

    @BeforeEach
    void setUp() {
        ASSIGNMENT.setId(ASSIGNMENT_ID);
        ASSIGNMENT.setName(ASSIGNMENT_NAME);
        ASSIGNMENT.setType(ASSIGNMENT_TYPE);
        ASSIGNMENT.setDate(ASSIGNMENT_DATE);
        ASSIGNMENT.setQuantity(ASSIGNMENT_QUANTITY);
        ASSIGNMENT.setDoneTimes(DONE_TIMES);
        ASSIGNMENT.setCurrentDiagnosis(ASSIGNMENT_CURRENT_DIAGNOSIS);
        ASSIGNMENT.setNotes(ASSIGNMENT_NOTES);
        ASSIGNMENT.setComplete(ASSIGNMENT_IS_COMPLETE);

        testInstance.addNewAssignment(ASSIGNMENT);
    }

    @Test
    public void throwExceptionWhenGetByNotRealId() {
        assertThrows(EntityNotFoundException.class, () -> testInstance.get(100L));
    }

    @Test
    public void addNewAssignment() {
        when(assignmentRepository.findById(ASSIGNMENT_ID)).thenReturn(Optional.of(ASSIGNMENT));

        Assignment assignment = testInstance.get(ASSIGNMENT_ID);
        assertEquals(ASSIGNMENT_CURRENT_DIAGNOSIS, assignment.getCurrentDiagnosis());
        assertEquals(ASSIGNMENT_NAME, assignment.getName());
        assertEquals(ASSIGNMENT_DATE, assignment.getDate());
        assertEquals(DONE_TIMES, assignment.getDoneTimes());
        assertEquals(ASSIGNMENT_NOTES, assignment.getNotes());
        assertEquals(ASSIGNMENT_TYPE, assignment.getType());
        assertEquals(ASSIGNMENT_IS_COMPLETE, assignment.isComplete());
        assertEquals(ASSIGNMENT_QUANTITY, assignment.getQuantity());

    }

    @Test
    public void shouldAddNewAssignment() {

        testInstance.addNewAssignment(assignment2);

        verify(assignmentRepository, times(1)).save(assignment2);
    }

    @Test
    public void shouldAddOneExecutionToAssignment() {
        when(assignmentRepository.findById(ASSIGNMENT_ID)).thenReturn(Optional.of(assignment2));
        when(assignment2.getDoneTimes()).thenReturn(DONE_TIMES);
        when(assignment2.getQuantity()).thenReturn(ASSIGNMENT_QUANTITY);

        testInstance.addOneExecutionToAssignment(ASSIGNMENT_ID);

        verify(assignment2, times(1)).setDoneTimes(DONE_TIMES_PLUS_ONE);
        verify(assignmentRepository, times(1)).save(assignment2);
    }

    @Test
    public void shouldCompleteAssignmentDuringAddingOneExecution() {
        when(assignmentRepository.findById(ASSIGNMENT_ID)).thenReturn(Optional.of(assignment2));
        when(assignment2.getDoneTimes()).thenReturn(DONE_TIMES, DONE_TIMES_PLUS_ONE);
        when(assignment2.getQuantity()).thenReturn(QUANTITY_EQUALS_TO_DONE_TIMES);

        testInstance.addOneExecutionToAssignment(ASSIGNMENT_ID);

        verify(assignment2, times(1)).setDoneTimes(DONE_TIMES_PLUS_ONE);
        verify(assignment2, times(1)).setComplete(true);
        verify(assignment2, times(1)).setNurses(anySet());
        verify(assignmentRepository, times(1)).save(assignment2);
    }
    @Test
    public void throwExceptionWhenDuringAddingExecution() {
        when(assignmentRepository.findById(ASSIGNMENT_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                testInstance.addOneExecutionToAssignment(ASSIGNMENT_ID));
    }




}
