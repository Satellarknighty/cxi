package com.cxi.uninotes.repositories;

import com.cxi.uninotes.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
