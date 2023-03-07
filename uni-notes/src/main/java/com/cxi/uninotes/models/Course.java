package com.cxi.uninotes.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course extends DBEntity {
    @Column(name = "course_name")
    private String courseName;
    private int ects;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private final List<Exercise> exercises = new ArrayList<>();
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private final List<Exam> exams = new ArrayList<>();
}
