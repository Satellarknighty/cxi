package com.cxi.uninotes.controllers;

import com.cxi.uninotes.exceptions.CourseAlreadyExistsException;
import com.cxi.uninotes.exceptions.CourseNotFoundException;
import com.cxi.uninotes.entities.Course;
import com.cxi.uninotes.services.CourseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {
    @Mock
    private CourseService courseService;
    @InjectMocks
    private CourseController courseController;
    private static Course testCourse;
    @BeforeAll
    static void beforeAll(){
        testCourse = new Course();
        testCourse.setName("test course");
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
        willThrow(new CourseAlreadyExistsException(testCourse.getName()))
                .given(courseService)
                .createCourse(any(Course.class));
        assertThrows(CourseAlreadyExistsException.class, () ->
                courseController.createCourse(testCourse));
    }

    @Test
    void testFoundOneCourse() {
        given(courseService.findCourse(anyString()))
                .willReturn(testCourse);
        var result = courseController.findCourse(testCourse.getName());
        assertEquals(testCourse, result);
    }

    @Test
    void testFoundZeroCourse() {
        String decoyCourseName = "A course name that doesn't exist.";
        given(courseService.findCourse(anyString()))
                .willThrow(new CourseNotFoundException(decoyCourseName));
        assertThrows(CourseNotFoundException.class, () ->
                courseController.findCourse(decoyCourseName));
    }

    @Test
    void testFoundSomeCoursesName() {
        List<String> nonEmptyList = Collections.singletonList("non empty");
        given(courseService.findAllCoursesName())
                .willReturn(nonEmptyList);
        assertEquals(nonEmptyList,
                courseController.findAllCoursesName());
    }

    @Test
    void testFoundZeroCourseName() {
        given(courseService.findAllCoursesName())
                .willThrow(new CourseNotFoundException());
        assertThrows(CourseNotFoundException.class, () ->
                courseController.findAllCoursesName());
    }

    @Test
    void testEditCourseSuccessfully() {
        willDoNothing()
                .given(courseService)
                .editCourse(any(), any());
        assertEquals("Course edited successfully!",
                courseController.editCourse("target course", new Course()));
    }
}
