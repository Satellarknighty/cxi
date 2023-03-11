package com.cxi.uninotes.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * A model representing an exam of a course.
 */
@Entity
@Getter
@Setter
public class Exam extends DBEntity {
    /** The number of attempt at this exam.     */
    private int attempt;
    /** When the exam will take place     */
    @Column(name = "exam_date")
    private Date examDate;
    /** Point scored in this exam. */
    private double point;
    /** The course that this exam belongs to. */
    @ManyToOne
    private Course course;
}
