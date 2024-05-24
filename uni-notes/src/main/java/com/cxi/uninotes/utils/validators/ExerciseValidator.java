package com.cxi.uninotes.utils.validators;

import com.cxi.uninotes.entities.Exercise;
import com.cxi.uninotes.exceptions.ExerciseValidationException;

import java.util.Calendar;
import java.util.Date;

public class ExerciseValidator {
    @Deprecated
    public static void validateExercise(Exercise exercise){
        Integer sheetNumber = exercise.getSheetNumber();
        if (sheetNumber == null || sheetNumber < 0){
            throw new ExerciseValidationException(ExerciseValidationException.Cause.INVALID_SHEET_NUMBER);
        }
        Date dueDate = exercise.getDueDate();
        if (dueDate == null || dueDate.before(Calendar.getInstance().getTime())){
            throw new ExerciseValidationException(ExerciseValidationException.Cause.DATE_TOO_LATE);
        }
    }
    public static void validateSheetNumber(Integer sheetNumber){
        if (sheetNumber == null || sheetNumber < 0){
            throw new ExerciseValidationException(ExerciseValidationException.Cause.INVALID_SHEET_NUMBER);
        }
    }
    public static void validateDate(Date dueDate){
        if (dueDate == null || dueDate.before(Calendar.getInstance().getTime())){
            throw new ExerciseValidationException(ExerciseValidationException.Cause.DATE_TOO_LATE);
        }
    }
    public static void validatePoint(Double point){
        if (point == null || point < 1.0 || point > 5.0){
            throw new ExerciseValidationException(ExerciseValidationException.Cause.POINT_OUT_OF_BOUNDS);
        }
    }
}
