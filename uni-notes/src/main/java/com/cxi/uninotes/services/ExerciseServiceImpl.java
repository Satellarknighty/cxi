package com.cxi.uninotes.services;

import com.cxi.uninotes.entities.Course;
import com.cxi.uninotes.entities.Exercise;
import com.cxi.uninotes.exceptions.ExerciseAlreadyExistsInCourseException;
import com.cxi.uninotes.repositories.ExerciseRepository;
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
        if (exerciseRepository.
                existsByCourse_NameAndSheetNumber(courseName, exercise.getSheetNumber())){
            throw new ExerciseAlreadyExistsInCourseException(exercise.getSheetNumber(), courseName);
        }
        exercise.setCourse(course);
        exerciseRepository.saveAndFlush(exercise);
    }
}
