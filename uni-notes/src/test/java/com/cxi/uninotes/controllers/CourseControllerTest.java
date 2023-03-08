package com.cxi.uninotes.controllers;

import com.cxi.uninotes.exceptions.CourseAlreadyExistsException;
import com.cxi.uninotes.models.Course;
import com.cxi.uninotes.services.CourseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class CourseControllerTest {
    private static CourseService courseService;
    private static CourseController courseController;
    private static Course testCourse;
    @BeforeAll
    static void beforeAll(){
        courseService = mock(CourseService.class);
        courseController = new CourseController(courseService);
        testCourse = new Course();
        testCourse.setCourseName("test course");
    }
    @Test
    void testCourseAddedSuccessfully() {
        willDoNothing()
                .given(courseService)
                .createCourse(any(Course.class));
        String result = courseController.createCourse(testCourse);
        assertEquals("Course created successfully!", result);
    }

    @Test
    void testCourseAddedUnsuccessfully() {
        willThrow(new CourseAlreadyExistsException(testCourse.getCourseName()))
                .given(courseService)
                .createCourse(any(Course.class));
        assertThrows(CourseAlreadyExistsException.class, () ->
                courseController.createCourse(testCourse));
    }

    @Test
    void testFoundOneCourse() {
        //given(courseRepository.findCourseByCourseName(anyString())).willReturn(Optional.of(testCourse));
        var result = courseController.findCourse(testCourse.getCourseName());
        assertEquals(testCourse, result);
    }

    @Test
    void testFoundZeroCourse() {

    }
}
