package com.cxi.uninotes.controllers;

import com.cxi.uninotes.entities.Course;
import com.cxi.uninotes.services.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handle each any every request from the web concerning a course. Each request will be sent to
 * {@link CourseService} for processing.
 */
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    /** Every request will be processed here.     */
    private final CourseService courseService;

    /**
     * POST-Request for creating a new course. The course mustn't be null and the name of the course
     * mustn't be a duplicate in the database.
     *
     * @param course    The course to be persisted in the database.
     * @return  A message informing that the course had been created successfully.
     */
    @PostMapping
    public String createCourse(@RequestBody @NonNull Course course){
        courseService.createCourse(course);
        return "Course created successfully!";
    }

    /**
     * GET-Request for retrieving a course from the database.
     *
     * @param courseName  The name of the wanted course.
     * @return  The wanted course, if found.
     */
    @GetMapping
    public Course findCourse(@RequestParam String courseName){
        return courseService.findCourse(courseName);
    }

    /**
     *
     * @return  All the name of the courses.
     */
    @GetMapping("/all")
    public List<String> findAllCoursesName(){
        return courseService.findAllCoursesName();
    }

    /**
     * Edit the name and ects of an existing course.
     *
     * @param courseName      The name of the course to be edited.
     * @param course    The course containing the new attributes.
     * @return  A message saying the course was successfully edited.
     */
    @PutMapping
    public String editCourse(@RequestParam String courseName,
                             @RequestBody Course course){
        courseService.editCourse(courseName, course);
        return "Course edited successfully!";
    }

    /**
     * Delete an existing course.
     *
     * @param courseName    The name of the to be deleted course.
     * @return A message saying the course was successfully deleted.
     */
    @DeleteMapping
    public String deleteCourse(@RequestParam String courseName){
        courseService.deleteCourse(courseName);
        return "Course deleted successfully!";
    }
}
