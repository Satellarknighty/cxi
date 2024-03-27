package com.cxi.uninotes.repositories;

import com.cxi.uninotes.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("select (count(e) > 0) from Exercise e where e.sheetNumber = ?1 and e.course.name = ?2")
    boolean existsBySheetNumberAndCourseName(Integer sheetNumber, String courseName);
    @Query("select e from Exercise e where e.sheetNumber = ?1 and e.course.name = ?2")
    Optional<Exercise> findExerciseBySheetNumberAndCourseName(Integer sheetNumber, String courseName);
}
