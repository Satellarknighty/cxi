package com.cxi.uninotes.services;

import com.cxi.uninotes.entities.Course;
import com.cxi.uninotes.exceptions.CourseAlreadyExistsException;
import com.cxi.uninotes.exceptions.CourseNotFoundException;
import com.cxi.uninotes.exceptions.CourseValidationException;

import java.util.List;

public interface CourseService {
    /**
     * Store a new course in the database.
     * @throws CourseValidationException if the course has either an undefined name or ects <= 0
     */
    void createCourse(Course course);

    /**
     * @param courseName    The given name.
     * @return  The course with the given name.
     * @throws CourseNotFoundException  if there is no course with the given name.
     */
    Course findCourse(String courseName);

    /**
     * @return The list of the name of all courses.
     */
    List<String> findAllCoursesName();
    /**
     * Edit the name and ects of an existing course.
     *
     * @param targetName    The name of the to be edited course.
     * @param newCourse    The new attributes of the course, in the form of a {@link Course}.
     * @throws CourseNotFoundException      if the to be edited course cannot be found.
     * @throws CourseAlreadyExistsException if the edited course have the same name with another course in the database
     */
    void editCourse(String targetName, Course newCourse);
}
