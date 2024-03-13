package com.cxi.uninotes.entities;

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
    private Integer attempt;
    /** When the exam will take place     */
    @Column(name = "exam_date")
    private Date examDate;
    /** Point scored in this exam. */
    private Double point;
    /** The course that this exam belongs to. */
    @ManyToOne
    private Course course;
}
