package dev.naclara.validium;

import java.lang.reflect.Field;

public class FieldValidator<R> {
    Validator<?> validator;
    String name;
    R value;

    public FieldValidator(String name, R value, Validator<?> validator) {
        this.name = name;
        this.value = value;
        this.validator = validator;
    }

    public FieldValidator<R> notNull() {
        if (value == null) {
            new ValidationError(name, "Cannot be null.");
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
            String message = String.format("Field '%s' must be a String to use this validation", name);
            throw new IllegalArgumentException(message);
        }
    }

    public FieldValidator<R> notEmpty() {
        if (value == null) {
            return this;
        }
        if (requireString().strip().isEmpty()) {
            validator.addError(name, "Cannot be empty.");
        }

        return this;
    }

    public FieldValidator<R> minLength(Integer size) {
        if (value == null) {
            return this;
        }
        if (requireString().length() < size) {
            validator.addError(name, String.format("Must be at least %d characters.", size));
        }

        return this;
    }

    public FieldValidator<R> maxLength(Integer size) {
        if (value == null) {
            return this;
        }
        if (requireString().length() > size) {
            validator.addError(name, String.format("Must be at most %d characters.", size));
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
            String message = String.format("Field '%s' must be a number to use this validation", name);
            throw new IllegalArgumentException(message);
        }
    }

    public FieldValidator<R> min(Integer min) {
        if (value == null) {
            return this;
        }
        if (requireNumber().doubleValue() < min.doubleValue()) {
            validator.addError(name, String.format("Must be at least %d", min));
        }

        return this;
    }

    public FieldValidator<R> max(Integer max) {
        if (value == null) {
            return this;
        }
        if (requireNumber().doubleValue() > max.doubleValue()) {
            validator.addError(name, String.format("Must be at most", max));
        }

        return this;
    }
}
