package com.cxi.uninotes.services;

import com.cxi.uninotes.entities.Course;
import com.cxi.uninotes.entities.Exercise;
import com.cxi.uninotes.exceptions.ExerciseAlreadyExistsInCourseException;
import com.cxi.uninotes.repositories.ExerciseRepository;
import com.cxi.uninotes.utils.validators.ExerciseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService{
    private final ExerciseRepository exerciseRepository;
    private final CourseService courseService;

    @Override
    public void addExerciseToACourse(String courseName, Exercise exercise) {
        Course course = courseService.findCourse(courseName);
        ExerciseValidator.validateExercise(exercise);
        if (exerciseRepository.
                existsBySheetNumberAndCourseName(exercise.getSheetNumber(), courseName)){
            throw new ExerciseAlreadyExistsInCourseException(exercise.getSheetNumber(), courseName);
        }
        exercise.setCourse(course);
        exerciseRepository.saveAndFlush(exercise);
    }
}
