package dev.naclara.validium;

import java.util.List;

import static java.lang.System.lineSeparator;

public class ValidationException extends RuntimeException {
    private static ValidationError error;
    List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        this.errors = errors;
        super(buildMessage(errors));
    }

    private static String buildMessage(List<ValidationError> errors) {
        StringBuilder message = new StringBuilder();
        message.append(lineSeparator() + String.format("Validation failed with %d error(s):", errors.size()) + lineSeparator());

        errors.forEach(error -> {
            message.append(error.field() + ": " + error.message() + lineSeparator());
        });

        return message.toString();
    }
}
