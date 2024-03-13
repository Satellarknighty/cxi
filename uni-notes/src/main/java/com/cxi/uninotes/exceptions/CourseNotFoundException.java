package com.cxi.uninotes.exceptions;

/**
 * Will be thrown when the wanted course can't be found in the database, or when there isn't any course.
 */
public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String name) {
        super(String.format("The course %s doesn't exist!", name));
    }

    public CourseNotFoundException() {
        super("There is no course in the database!");
    }
}
