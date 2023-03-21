package com.cxi.uninotes.entities;

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
    /** The number of this sheet. */
    @Column(name = "sheet_number")
    private Integer sheetNumber;
    /** When this sheet must be submitted. */
    @Column(name = "due_date")
    private Date duedate;
    /** Point scored for this sheet. */
    private Double point;
    /** The course that this sheet belongs to. */
    @ManyToOne
    private Course course;
}
