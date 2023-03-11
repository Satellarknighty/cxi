package com.cxi.uninotes.services;

import com.cxi.uninotes.exceptions.CourseAlreadyExistsException;
import com.cxi.uninotes.exceptions.CourseNotFoundException;
import com.cxi.uninotes.models.Course;
import com.cxi.uninotes.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    private CourseService courseService;
    @BeforeEach
    void beforeEach(){
        courseService = new CourseServiceImpl(courseRepository);
    }
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
        assertThrows(CourseAlreadyExistsException.class, () -> courseService.createCourse(new Course()));
        verify(courseRepository, times(0)).saveAndFlush(any(Course.class));
    }

    @Test
    void testFoundOneCourse() {
        Course decoyCourse = new Course();
        given(courseRepository.findCourseByCourseName(anyString()))
                .willReturn(Optional.of(decoyCourse));
        Course result = courseService.findCourse("decoy course");
        assertEquals(decoyCourse, result);
    }

    @Test
    void testFoundZeroCourse() {
        given(courseRepository.findCourseByCourseName(any()))
                .willReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () ->
                courseService.findCourse("doesn't exist"));
    }

    @Test
    void testFoundSomeCoursesName() {
        Course decoyCourse = new Course();
        decoyCourse.setCourseName("decoy");
        List<Course> nonEmptyList = Collections.singletonList(decoyCourse);
        given(courseRepository.findCourses())
                .willReturn(nonEmptyList);
        assertEquals(Collections.singletonList("decoy"),
                courseService.findAllCoursesName());
    }

    @Test
    void testFoundZeroCourseName() {
        given(courseRepository.findCourses())
                .willReturn(Collections.emptyList());
        assertThrows(CourseNotFoundException.class, () ->
                courseService.findAllCoursesName());
    }
}
