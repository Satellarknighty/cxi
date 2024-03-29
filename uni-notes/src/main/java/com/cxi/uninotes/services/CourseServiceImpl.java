package com.cxi.uninotes.services;

import com.cxi.uninotes.exceptions.CourseAlreadyExistsException;
import com.cxi.uninotes.exceptions.CourseNotFoundException;
import com.cxi.uninotes.entities.Course;
import com.cxi.uninotes.repositories.CourseRepository;
import com.cxi.uninotes.utils.validators.CourseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;

    @Override
    public void createCourse(Course course) {
        CourseValidator.validateCourse(course);
        var optionalCourse = courseRepository.findCourseByName(course.getName());
        if (optionalCourse.isPresent()){
            throw new CourseAlreadyExistsException(course.getName());
        }
        courseRepository.saveAndFlush(course);
    }

    @Override
    public Course findCourse(String courseName) {
        var optionalCourse = courseRepository.findCourseByName(courseName);
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
                .map(Course::getName)
                .toList();
    }
    @Override
    public void editCourse(String targetName, Course courseAttr) {
        var targetCourse = courseRepository.findCourseByName(targetName);
        if (targetCourse.isEmpty()){
            throw new CourseNotFoundException(targetName);
        }
        String attrCourseName = courseAttr.getName();
        Optional<Course> editedCourse = Optional.empty();
        if (!targetName.equals(attrCourseName)) {
            editedCourse = courseRepository.findCourseByName(attrCourseName);
        }
        if (editedCourse.isPresent()){
            throw new CourseAlreadyExistsException(attrCourseName);
        }
        Course target = setAttrToTarget(courseAttr, targetCourse.get());
        courseRepository.saveAndFlush(target);
    }

    @Override
    public void deleteCourse(String courseName) {
        var optionalCourse = courseRepository.findCourseByName(courseName);
        if (optionalCourse.isEmpty()){
            throw new CourseNotFoundException(courseName);
        }
        courseRepository.delete(optionalCourse.get());
    }

    private Course setAttrToTarget(Course courseAttr, Course target) {
        String nameAttr = courseAttr.getName();
        if (nameAttr != null){
            target.setName(nameAttr);
        }
        Integer ectsAttr = courseAttr.getEcts();
        if (ectsAttr != null){
            target.setEcts(ectsAttr);
        }
        return target;
    }
}
