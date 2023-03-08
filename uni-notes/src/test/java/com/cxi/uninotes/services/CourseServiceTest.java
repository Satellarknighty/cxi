package com.cxi.uninotes.services;

import com.cxi.uninotes.models.Course;
import com.cxi.uninotes.repositories.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseServiceTest {
    @MockBean
    private CourseRepository courseRepository;
    @Autowired
    private CourseService courseService;

    @Test
    void testCreateCourseSuccessful() {
        given(courseRepository.findCourseByCourseName(any()))
                .willReturn(Optional.empty());
        courseService.createCourse(new Course());
        verify(courseRepository).saveAndFlush(any(Course.class));
    }

    @Test
    void testCreateCourseUnsuccessful() {
        given(courseRepository.findCourseByCourseName(any()))
                .willReturn(Optional.of(new Course()));
        assertThrows(IllegalStateException.class, () -> courseService.createCourse(new Course()));
        verify(courseRepository, times(0)).saveAndFlush(any(Course.class));
    }
}
