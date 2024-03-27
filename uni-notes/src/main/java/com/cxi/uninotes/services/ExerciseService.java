package com.cxi.uninotes.services;

import com.cxi.uninotes.entities.Exercise;
import com.cxi.uninotes.exceptions.CourseNotFoundException;
import com.cxi.uninotes.exceptions.ExerciseAlreadyExistsInCourseException;
import com.cxi.uninotes.exceptions.ExerciseValidationException;

import java.util.List;

public interface ExerciseService {
    /**
     * Add a new exercise to a course. This exercise must have a due date that is later than the current date.
     * The new exercise might or might not have a score already.
     * @param courseName    The course to add the exercise into.
     * @param exercise  The exercise itself.
     * @throws CourseNotFoundException  If there exists no course with the given name.
     * @throws ExerciseValidationException If the new exercise has an invalid due date or sheet number.
     * @throws ExerciseAlreadyExistsInCourseException   If there already exists an exercise with the same sheet number
     * as the given one.
     */
    void addExerciseToACourse(String courseName, Exercise exercise);

    List<String> findAllExercisesOfACourse(String courseName);

    void addPointToAnExerciseOfACourse(String courseName, Integer sheetNumber, Double point);
}
