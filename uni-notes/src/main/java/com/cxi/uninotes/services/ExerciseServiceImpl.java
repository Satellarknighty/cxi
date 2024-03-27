package com.cxi.uninotes.services;

import com.cxi.uninotes.entities.Course;
import com.cxi.uninotes.entities.Exercise;
import com.cxi.uninotes.exceptions.CourseNotFoundException;
import com.cxi.uninotes.exceptions.ExerciseAlreadyExistsInCourseException;
import com.cxi.uninotes.exceptions.ExerciseNotFoundException;
import com.cxi.uninotes.repositories.CourseRepository;
import com.cxi.uninotes.repositories.ExerciseRepository;
import com.cxi.uninotes.utils.converters.ExerciseConverter;
import com.cxi.uninotes.utils.validators.ExerciseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService{
    private final ExerciseRepository exerciseRepository;
    private final CourseRepository courseRepository;


    @Override
    public void addExerciseToACourse(String courseName, Exercise exercise) {
        ExerciseValidator.validateExercise(exercise);
        Course course = findCourse(courseName);
        if (exerciseRepository.
                existsBySheetNumberAndCourseName(exercise.getSheetNumber(), courseName)){
            throw new ExerciseAlreadyExistsInCourseException(exercise.getSheetNumber(), courseName);
        }
        exercise.setCourse(course);
        exerciseRepository.saveAndFlush(exercise);
    }

    @Override
    public List<String> findAllExercisesOfACourse(String courseName) {
        Course course = findCourse(courseName);
        if (course.getExercises().isEmpty()){
            throw new ExerciseNotFoundException();
        }
        return course.getExercises().stream()
                .map(ExerciseConverter::exerciseToString)
                .toList();
    }

    @Override
    public void addPointToAnExerciseOfACourse(String courseName, Integer sheetNumber, Double point) {
        ExerciseValidator.validatePoint(point);
        if (courseRepository.existsByName(courseName)){
            throw new CourseNotFoundException(courseName);
        }
        Optional<Exercise> optionalExercise = exerciseRepository.findExerciseBySheetNumberAndCourseName(sheetNumber, courseName);
        Exercise exercise = optionalExercise.orElseThrow(() -> new ExerciseNotFoundException(sheetNumber, courseName));
        exercise.setPoint(point);
        exerciseRepository.saveAndFlush(exercise);
    }
    private Course findCourse(String courseName){
        Optional<Course> optionalCourse = courseRepository.findCourseByName(courseName);
        return optionalCourse.orElseThrow(() -> new CourseNotFoundException(courseName));
    }
}
