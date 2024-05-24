package com.cxi.uninotes.exceptions;

public class ExerciseValidationException extends InternalServerException{
    public enum Cause {
        INVALID_SHEET_NUMBER,
        DATE_TOO_LATE,
        POINT_OUT_OF_BOUNDS
    }

    public ExerciseValidationException(Cause cause) {
        super(getErrorMessage(cause));
    }

    private static String getErrorMessage(Cause cause) {
        return switch (cause){
            case INVALID_SHEET_NUMBER -> "Sheet number should not be empty or negative.";
            case DATE_TOO_LATE -> "The deadline is already over. (The date inputted is sooner than today.)";
            case POINT_OUT_OF_BOUNDS -> "Point should be between 1.0 and 5.0";
        };
    }
}
