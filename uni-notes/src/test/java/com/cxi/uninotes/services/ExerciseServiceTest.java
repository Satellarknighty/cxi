package com.cxi.uninotes.services;

import com.cxi.uninotes.entities.Exercise;
import com.cxi.uninotes.repositories.CourseRepository;
import com.cxi.uninotes.repositories.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
        given(exerciseRepository.existsByCourse_NameAndSheetNumber(any(), any()))
                .willReturn(true);
    }
}
