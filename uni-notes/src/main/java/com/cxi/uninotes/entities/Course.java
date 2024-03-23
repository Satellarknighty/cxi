package com.cxi.uninotes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * A course in the uni.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course extends DBEntity {

    @Column(name = "name")
    private String name;
    /** The ECTS (Credit point) of this course.    */
    private Integer ects;
    /** All the exercises of this course.    */
    @OneToMany(mappedBy = "course")
    private final List<Exercise> exercises = new ArrayList<>();
    /** All the exams of this course.    */
    @OneToMany(mappedBy = "course")
    private final List<Exam> exams = new ArrayList<>();
}
