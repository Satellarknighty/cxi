package com.cxi.uninotes.controllers;

import com.cxi.uninotes.entities.Exercise;
import com.cxi.uninotes.services.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    @GetMapping("/all")
    public List<String> findAllExercisesOfACourse(@PathVariable("name") String courseName){
        return exerciseService.findAllExercisesOfACourse(courseName);
    }
    @PutMapping("/point")
    public String updatePointOnAnExerciseOfACourse(@PathVariable("name") String courseName,
                                                   @RequestParam Integer sheetNumber,
                                                   @RequestParam Double point){
        exerciseService.updatePointOnAnExerciseOfACourse(courseName, sheetNumber, point);
        return String.format("You scored %.1f on sheet number %d of the course %s.", point, sheetNumber, courseName);
    }
    @PutMapping("/date")
    public String updateDueDateOnAnExerciseOfACourse(@PathVariable("name") String courseName,
                                                     @RequestParam Integer sheetNumber,
                                                     @RequestParam Date newDate){
        exerciseService.updateDueDateOnAnExerciseOfACourse(courseName, sheetNumber, newDate);
        return String.format("New due date for sheet %s has been updated!", sheetNumber);
    }
}
