package com.cxi.uninotes.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * A model representing an exercise sheet of a course.
 */
@Entity
@Getter
@Setter
public class Exercise extends DBEntity {
    @Column(name = "sheet_number")
    private int sheetNumber;
    @Column(name = "due_date")
    private Date dueDate;
    private double point;
    @ManyToOne
    private Course course;
}
