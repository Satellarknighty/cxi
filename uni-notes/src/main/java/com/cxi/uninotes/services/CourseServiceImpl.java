package com.cxi.uninotes.services;

import com.cxi.uninotes.exceptions.CourseAlreadyExistsException;
import com.cxi.uninotes.exceptions.CourseNotFoundException;
import com.cxi.uninotes.models.Course;
import com.cxi.uninotes.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;

    @Override
    public void createCourse(Course course) {
        var optionalCourse = courseRepository.findCourseByCourseName(course.getCourseName());
        if (optionalCourse.isPresent()){
            throw new CourseAlreadyExistsException(course.getCourseName());
        }
        courseRepository.saveAndFlush(course);
    }

    @Override
    public Course findCourse(String courseName) {
        var optionalCourse = courseRepository.findCourseByCourseName(courseName);
        if (optionalCourse.isEmpty()){
            throw new CourseNotFoundException(courseName);
        }
        return optionalCourse.get();
    }

    @Override
    public List<String> findAllCoursesName() {
        var allCourses = courseRepository.findAll();
        if (allCourses.isEmpty()){
            throw new CourseNotFoundException();
        }
        return allCourses.stream()
                .map(Course::getCourseName)
                .toList();
    }
}
