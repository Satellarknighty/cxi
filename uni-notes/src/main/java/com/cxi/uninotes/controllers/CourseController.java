package com.cxi.uninotes.controllers;

import com.cxi.uninotes.models.Course;
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
    @PostMapping("/create")
    public String createCourse(@RequestBody @NonNull Course course){
        courseService.createCourse(course);
        return "Course created successfully!";
    }

    /**
     * GET-Request for retrieving a course from the database.
     *
     * @param name  The name of the wanted course.
     * @return  The wanted course, if found.
     */
    @GetMapping
    public Course findCourse(@RequestParam String name){
        return courseService.findCourse(name);
    }

    /**
     *
     * @return  All the name of the courses.
     */
    @GetMapping("/all")
    public List<String> findAllCoursesName(){
        return courseService.findAllCoursesName();
    }
}
