package com.cxi.uninotes.exceptions;

public class CourseAlreadyExistsException extends RuntimeException{
    public CourseAlreadyExistsException(String courseName) {
        super(String.format("A course with the name %s already exists.", courseName));
    }
}
