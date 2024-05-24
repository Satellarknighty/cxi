package com.cxi.uninotes.exceptions;

public class CourseValidationException extends InternalServerException {
    public enum Cause {
        INVALID_NAME,
        INVALID_ECTS
    }
    public CourseValidationException(Cause cause) {
        super(getErrorMessage(cause));
    }

    private static String getErrorMessage(Cause cause) {
        return switch (cause){
            case INVALID_NAME -> "This course contains an invalid name!";
            case INVALID_ECTS -> "This course contains an invalid ects!";
        };
    }
}
