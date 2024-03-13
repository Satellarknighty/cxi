package com.cxi.uninotes.services;

import com.cxi.uninotes.entities.Course;

import java.util.List;

public interface CourseService {
    void createCourse(Course course);
    Course findCourse(String courseName);
    List<String> findAllCoursesName();
    void editCourse(String targetName, Course newCourse);
}
