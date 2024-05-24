package com.cxi.uninotes.exceptions;

public class ExerciseNotFoundException extends InternalServerException{

    public ExerciseNotFoundException() {
        super("There is no exercise in this course.");
    }
    public ExerciseNotFoundException(Integer sheetNumber, String courseName){
        super(String.format("The exercise sheet number %d doesn't exist in course %s.", sheetNumber, courseName));
    }
}
