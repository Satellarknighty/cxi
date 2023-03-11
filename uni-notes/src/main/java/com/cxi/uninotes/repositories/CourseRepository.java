package com.cxi.uninotes.repositories;

import com.cxi.uninotes.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findCourseByCourseName(String courseName);
    @Override
    <S extends Course> S saveAndFlush(S entity);
    @Query("SELECT c FROM Course c")
    List<Course> findCourses();
}
