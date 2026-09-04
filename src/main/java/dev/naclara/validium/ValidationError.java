package dev.naclara.validium;

/**
 * Represents a validation error for a field.
 *
 * @param field field name related to the validation error
 * @param message validation error message
 */
public record ValidationError(
        String field,
        String message
) {
}
