package com.cxi.uninotes.utils.converters;

import com.cxi.uninotes.entities.Exercise;

public class ExerciseConverter {
    public static String exerciseToString(Exercise e){
        return String.format("""
                Sheet number: %d
                Due Date: %s
                Point: %.1f
                
                """, e.getSheetNumber(), e.getDueDate(), e.getPoint());
    }
}
