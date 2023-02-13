package de.unibremen.swp.matti.util;

public class Validator {
    public static <T> T checkNotNullOrBlank(final T object, final String varName) {
        if (object == null) {
            throw new IllegalArgumentException(
                    String.format("%s must not be null!", varName));
        }
        if (object instanceof String string) {
            if (string.isBlank()) {
                throw new IllegalArgumentException(
                        String.format("%s must not be empty or blank!", varName));
            }
        }
        return object;
    }
}
