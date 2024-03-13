package com.cxi.uninotes.services;

import com.cxi.uninotes.entities.Course;
import com.cxi.uninotes.entities.Exercise;
import com.cxi.uninotes.exceptions.ExerciseAlreadyExistsInCourseException;
import com.cxi.uninotes.exceptions.ExerciseValidationException;
import com.cxi.uninotes.repositories.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {
    @Mock
    private CourseService courseService;
    @Mock
    private ExerciseRepository exerciseRepository;
    private ExerciseService exerciseService;

    @BeforeEach
    public void beforeEach(){
        exerciseService = new ExerciseServiceImpl(exerciseRepository, courseService);
    }

    @Test
    void testAddExToACourse_ButExAlreadyExistsInThatCourse() {
        given(exerciseRepository.existsBySheetNumberAndCourseName(any(), any()))
                .willReturn(true);
        Exercise exercise = new Exercise();
        exercise.setSheetNumber(1);
        exercise.setDueDate(new Date(Long.MAX_VALUE));
        assertThrows(ExerciseAlreadyExistsInCourseException.class, () ->
                exerciseService.addExerciseToACourse("a course", exercise));
    }

    @Test
    void testAddExToACourse_Ok() {
        given(exerciseRepository.existsBySheetNumberAndCourseName(any(), any()))
                .willReturn(false);
        Exercise exercise = new Exercise();
        exercise.setSheetNumber(1);
        exercise.setDueDate(new Date(Long.MAX_VALUE));
        exerciseService.addExerciseToACourse("a course", exercise);
        verify(exerciseRepository).saveAndFlush(any());
    }

    @Test
    void testAddExToACourse_ButSheetNumberIsNull() {
        Exercise exercise = new Exercise();
        exercise.setSheetNumber(null);
        assertThrows(ExerciseValidationException.class, () ->
                exerciseService.addExerciseToACourse("a course", exercise));
    }
    @Test
    void testAddExToACourse_ButSheetNumberIsInvalid() {
        Exercise exercise = new Exercise();
        exercise.setSheetNumber(-1);
        assertThrows(ExerciseValidationException.class, () ->
                exerciseService.addExerciseToACourse("a course", exercise));
    }

    @Test
    void testAddExToACourse_ButDueDateIsNull() {
        Exercise exercise = new Exercise();
        exercise.setDueDate(null);
        assertThrows(ExerciseValidationException.class, () ->
                exerciseService.addExerciseToACourse("a course", exercise));
    }
    @Test
    void testAddExToACourse_ButDueDateIsInvalid() {
        Exercise exercise = new Exercise();
        exercise.setDueDate(Date.from(Instant.EPOCH));
        assertThrows(ExerciseValidationException.class, () ->
                exerciseService.addExerciseToACourse("a course", exercise));
    }
}
