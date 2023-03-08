package com.cxi.uninotes.services;

import com.cxi.uninotes.exceptions.CourseAlreadyExistsException;
import com.cxi.uninotes.models.Course;
import com.cxi.uninotes.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void createCourse(Course course) {
        var optionalCourse = courseRepository.findCourseByCourseName(course.getCourseName());
        if (optionalCourse.isPresent()){
            throw new CourseAlreadyExistsException(course.getCourseName());
        }
        courseRepository.saveAndFlush(course);
    }
}
