package com.cxi.uninotes.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * A course in the uni.
 */
@Entity
@Getter
@Setter
public class Course extends DBEntity {
    @Column(name = "name")
    private String courseName;
    /** The ECTS (Credit point) of this course.    */
    private int ects;
    /** All the exercises of this course.    */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private final List<Exercise> exercises = new ArrayList<>();
    /** All the exams of this course.    */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private final List<Exam> exams = new ArrayList<>();
}
