package com.cxi.uninotes.services;

import com.cxi.uninotes.entities.Exercise;

public interface ExerciseService {
    void addExerciseToACourse(String courseName, Exercise exercise);
}
