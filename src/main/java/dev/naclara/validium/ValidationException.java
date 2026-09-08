package dev.naclara.validium;

import java.util.List;

import static java.lang.System.lineSeparator;

/**
 * Exception thrown when a validation chain finishes with one or more errors.
 */
public class ValidationException extends RuntimeException {
    List<ValidationError> errors;

    /**
     * Creates a validation exception with the collected errors.
     *
     * @param errors validation errors collected during validation
     */
    public ValidationException(List<ValidationError> errors) {
        super(buildMessage(errors));
        this.errors = errors;
    }

    private static String buildMessage(List<ValidationError> errors) {
        StringBuilder message = new StringBuilder();
        message.append(lineSeparator() + String.format("Validation failed with %d error(s):", errors.size()) + lineSeparator());

        errors.forEach(error -> {
            message.append(error.getField() + ": " + error.getMessage() + lineSeparator());
        });

        return message.toString();
    }
}
