package com.cxi.uninotes.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Exam extends DBEntity {
    @Column(name = "exam_name")
    private String examName;
    private Date examDate;
    private double point;
    @ManyToOne
    private Course course;
}
