package com.cxi.uninotes.exceptions;

public class ExerciseAlreadyExistsInCourseException extends InternalServerException {
    public ExerciseAlreadyExistsInCourseException(Integer sheetNumber, String courseName) {
        super(String.format("Exercise number %d already exists in course %s!", sheetNumber, courseName));
    }
}
