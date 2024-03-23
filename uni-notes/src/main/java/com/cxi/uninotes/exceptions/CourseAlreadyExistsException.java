package com.cxi.uninotes.exceptions;

/**
 * Will be thrown when a course with the same name already exists in the database.
 */
public class CourseAlreadyExistsException extends InternalServerException{
    public CourseAlreadyExistsException(String courseName) {
        super(String.format("A course with the name %s already exists.", courseName));
    }
}
