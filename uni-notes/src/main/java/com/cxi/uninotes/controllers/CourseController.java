package com.cxi.uninotes.controllers;

import com.cxi.uninotes.models.Course;
import com.cxi.uninotes.services.CourseService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    public String createCourse(@RequestBody @NonNull Course course){
        courseService.createCourse(course);
        return "Course created successfully!";
    }
    @GetMapping("/{name}")
    public Course findCourse(@PathVariable String name){
        return null;
    }
}
