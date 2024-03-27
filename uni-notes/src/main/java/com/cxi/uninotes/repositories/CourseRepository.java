package com.cxi.uninotes.repositories;

import com.cxi.uninotes.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for all things course-related.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findCourseByName(String courseName);
    boolean existsByName(String name);
}
