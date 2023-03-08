package com.cxi.uninotes.repositories;

import com.cxi.uninotes.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findCourseByCourseName(String courseName);
    @Override
    <S extends Course> S saveAndFlush(S entity);
}
