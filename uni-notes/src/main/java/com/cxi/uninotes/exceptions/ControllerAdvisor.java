package com.cxi.uninotes.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles all the exceptions that aren't yet caught at the controller layer.
 */
@ControllerAdvice
@ResponseBody
public class ControllerAdvisor {
    @ExceptionHandler(CourseAlreadyExistsException.class)
    public String handleCourseAlreadyExistsException(CourseAlreadyExistsException e){
        return e.getMessage();
    }
    @ExceptionHandler(NullPointerException.class)
    public String handleNPE(){
        return "An unexpected error has occurred";
    }
}
