package com.cxi.uninotes.controllers;

import com.cxi.uninotes.entities.Exercise;
import com.cxi.uninotes.services.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercise/{name}")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;
    @PostMapping
    public String addExerciseToACourse(@PathVariable("name") String courseName,
                                       @RequestBody Exercise exercise){
        exerciseService.addExerciseToACourse(courseName, exercise);
        return String.format("Exercise number %d has been successfully added to course %s!",
                exercise.getSheetNumber(), courseName);
    }
}
