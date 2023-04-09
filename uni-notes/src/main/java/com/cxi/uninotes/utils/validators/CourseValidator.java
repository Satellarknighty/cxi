package com.cxi.uninotes.utils.validators;

import com.cxi.uninotes.entities.Course;
import com.cxi.uninotes.exceptions.CourseValidationException;
import com.cxi.uninotes.exceptions.CourseValidationException.*;

public class CourseValidator {
    public static void validateNewCourse(Course course){
        if (course.getName() == null) {
            throw new CourseValidationException(Cause.INVALID_NAME);
        }
    }
}
