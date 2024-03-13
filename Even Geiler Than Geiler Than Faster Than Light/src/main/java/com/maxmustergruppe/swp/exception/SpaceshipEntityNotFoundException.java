package com.maxmustergruppe.swp.exception;

/**
 * For when the entity in the database doesn't exist.
 *
 * @author Hai Trinh
 */
public class SpaceshipEntityNotFoundException extends RuntimeException {
    /**
     * Called when there doesn't exist an entity with the id in the param.
     *
     * @param saveGameNo    The id.
     */
    public SpaceshipEntityNotFoundException(Integer saveGameNo) {
        super(String.format("The save file %d doesn't exist.", saveGameNo));
    }
}
