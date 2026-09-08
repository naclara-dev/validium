package dev.naclara.validium;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Validates a single field inside a validation chain.
 *
 * @param <R> type of the field value being validated
 */
public class FieldValidator<R> {
    Validator<?> validator;
    ValidationError error;
    String name;
    R value;
    boolean fieldFound;

    /**
     * Creates a field validator linked to an existing validation context.
     *
     * @param name field name used in validation errors
     * @param value field value to validate
     * @param validator validation context that collects errors
     * @param fieldFound true when the field exists and is accessible
     */
    public FieldValidator(String name, R value, Validator<?> validator, boolean fieldFound) {
        this.name = name;
        this.value = value;
        this.validator = validator;
        this.fieldFound = fieldFound;
    }

    /**
     * Overrides the default validation error message with a custom text.
     * @param message
     * @return field validator for the selected field
     */
    public FieldValidator<R> onFail(String message) {
        if (error != null) {
            error.setMessage(message);
        }

        return this;
    }

    /**
     * Selects another field value to validate in the same validation chain.
     *
     * @param name field name used in validation errors
     * @param <A> type of the next field value
     * @return field validator for the selected field
     */
    public <A> FieldValidator<A> field(String name) {
        return validator.field(name);
    }

    /**
     * Finishes the validation chain.
     *
     * @return true when no validation errors were collected
     * @throws ValidationException when one or more validation errors were collected
     */
    public Boolean validate() {
        return validator.validate();
    }

    /**
     * Validates a field based on a custom test.
     * @param test custom validation
     * @param errorMessage custom error message in case the test fails
     * @return current field validator
     */
    public FieldValidator<R> check(Predicate<R> test, String errorMessage) {
        if (!fieldFound || value == null) {
            return this;
        }

        boolean validated;

        try {
            validated = test.test(value);
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException(
                    "Invalid custom validation test for field '" + name + "'",
                    exception
            );
        }

        if (!validated) {
            error = validator.addError(name, errorMessage);
        }

        return this;
    }

    /**
     * Validates that the field value is not null.
     *
     * @return current field validator
     */
    public FieldValidator<R> notNull() {
        if (!fieldFound) {
            return this;
        }
        if (value == null) {
            error = validator.addError(name, "Cannot be null.");
        }

        return this;
    }

    // *********************
    // Text
    // *********************
    private String requireString() {
        if (value instanceof String text) {
            return text;
        } else {
            String message = String.format("Field '%s' must be a String to use this validation.", name);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that the string field is not blank.
     *
     * @return current field validator
     * @throws IllegalArgumentException when the field value is not a string
     */
    public FieldValidator<R> notBlank() {
        if (!fieldFound || value == null) {
            return this;
        }
        if (requireString().isBlank()) {
            error = validator.addError(name, "Cannot be blank.");
        }

        return this;
    }

    /**
     * Validates that the string field is not empty.
     *
     * @return current field validator
     * @throws IllegalArgumentException when the field value is not a string
     */
    public FieldValidator<R> notEmpty() {
        if (!fieldFound || value == null) {
            return this;
        }
        if (requireString().isEmpty()) {
            error = validator.addError(name, "Cannot be empty.");
        }

        return this;
    }

    /**
     * Validates that the string field has at least the given length.
     *
     * @param size minimum accepted string length
     * @return current field validator
     * @throws IllegalArgumentException when the field value is not a string
     */
    public FieldValidator<R> minLength(Integer size) {
        if (!fieldFound || value == null) {
            return this;
        }
        if (requireString().length() < size) {
            error = validator.addError(name, String.format("Must be at least %d characters.", size));
        }

        return this;
    }

    /**
     * Validates that the string field has at most the given length.
     *
     * @param size maximum accepted string length
     * @return current field validator
     * @throws IllegalArgumentException when the field value is not a string
     */
    public FieldValidator<R> maxLength(Integer size) {
        if (!fieldFound || value == null) {
            return this;
        }
        if (requireString().length() > size) {
            error = validator.addError(name, String.format("Must be at most %d characters.", size));
        }

        return this;
    }

    // *********************
    // Numeric
    // *********************
    private Number requireNumber() {
        if (value instanceof Number number) {
            return number;
        } else {
            String message = String.format("Field '%s' must be a number to use this validation.", name);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that the numeric field is greater than or equal to the given value.
     *
     * @param min minimum accepted value
     * @return current field validator
     * @throws IllegalArgumentException when the field value is not a number
     */
    public FieldValidator<R> min(Integer min) {
        if (!fieldFound || value == null) {
            return this;
        }
        if (requireNumber().doubleValue() < min.doubleValue()) {
            error = validator.addError(name, String.format("Must be at least %d.", min));
        }

        return this;
    }

    /**
     * Validates that the numeric field is lower than or equal to the given value.
     *
     * @param max maximum accepted value
     * @return current field validator
     * @throws IllegalArgumentException when the field value is not a number
     */
    public FieldValidator<R> max(Integer max) {
        if (!fieldFound || value == null) {
            return this;
        }
        if (requireNumber().doubleValue() > max.doubleValue()) {
            error = validator.addError(name, String.format("Must be at most %d.", max));
        }

        return this;
    }
}
