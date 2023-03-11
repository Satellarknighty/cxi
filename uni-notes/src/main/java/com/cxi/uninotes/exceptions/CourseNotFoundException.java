package com.cxi.uninotes.exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String name) {
        super(String.format("The course %s doesn't exist!", name));
    }

    public CourseNotFoundException() {
        super("There is no course in the database!");
    }
}
