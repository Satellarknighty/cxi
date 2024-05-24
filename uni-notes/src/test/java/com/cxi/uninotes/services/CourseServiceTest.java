package com.cxi.uninotes.services;

import com.cxi.uninotes.exceptions.CourseAlreadyExistsException;
import com.cxi.uninotes.exceptions.CourseNotFoundException;
import com.cxi.uninotes.entities.Course;
import com.cxi.uninotes.exceptions.CourseValidationException;
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
        given(courseRepository.findCourseByName(any()))
                .willReturn(Optional.empty());
        courseService.createCourse(new Course());
        verify(courseRepository).saveAndFlush(any(Course.class));
    }

    @Test
    void testCreateCourseUnsuccessful() {
        given(courseRepository.findCourseByName(any()))
                .willReturn(Optional.of(new Course()));
        assertThrows(CourseAlreadyExistsException.class, () -> courseService.createCourse(new Course()));
        verify(courseRepository, times(0)).saveAndFlush(any(Course.class));
    }

    @Test
    void testCreateCourse_WithInvalidName() {
        assertThrows(CourseValidationException.class, () -> courseService.createCourse(new Course(null,6)));
    }

    @Test
    void testCreateCourse_WithInvalidEcts() {
        assertThrows(CourseValidationException.class, () -> courseService.createCourse(new Course("example", 0)));
    }

    @Test
    void testFoundOneCourse() {
        Course decoyCourse = new Course();
        given(courseRepository.findCourseByName(anyString()))
                .willReturn(Optional.of(decoyCourse));
        Course result = courseService.findCourse("decoy course");
        assertEquals(decoyCourse, result);
    }

    @Test
    void testFoundZeroCourse() {
        given(courseRepository.findCourseByName(any()))
                .willReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () ->
                courseService.findCourse("doesn't exist"));
    }

    @Test
    void testFoundSomeCoursesName() {
        Course decoyCourse = new Course();
        decoyCourse.setName("decoy");
        List<Course> nonEmptyList = Collections.singletonList(decoyCourse);
        given(courseRepository.findAll())
                .willReturn(nonEmptyList);
        assertEquals(Collections.singletonList("decoy"),
                courseService.findAllCoursesName());
    }

    @Test
    void testFoundZeroCourseName() {
        given(courseRepository.findAll())
                .willReturn(Collections.emptyList());
        assertThrows(CourseNotFoundException.class, () ->
                courseService.findAllCoursesName());
    }

    @Test
    void testEditCourse_Successfully() {
        String decoyTargetName = "decoy target";
        Course decoyTarget = new Course();
        Course decoyAttr = new Course();
        decoyAttr.setName("decoy attr");
        given(courseRepository.findCourseByName(decoyTargetName))
                .willReturn(Optional.of(decoyTarget));
        given(courseRepository.findCourseByName(decoyAttr.getName()))
                .willReturn(Optional.empty());
        courseService.editCourse(decoyTargetName, decoyAttr);
        verify(courseRepository).saveAndFlush(any());
    }
}
