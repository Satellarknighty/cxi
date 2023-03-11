package com.cxi.uninotes.controllers;

import com.cxi.uninotes.models.Course;
import com.cxi.uninotes.services.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/create")
    public String createCourse(@RequestBody @NonNull Course course){
        courseService.createCourse(course);
        return "Course created successfully!";
    }
    @GetMapping
    public Course findCourse(@RequestParam String name){
        return courseService.findCourse(name);
    }
    @GetMapping("/all")
    public List<String> findAllCoursesName(){
        return courseService.findAllCoursesName();
    }
}
