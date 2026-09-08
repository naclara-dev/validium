package dev.naclara.validium;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for validating an object through a fluent field-based API.
 *
 * @param <T> type of the object being validated
 */
public class Validator<T> {
    T object;
    List<ValidationError> errors;

    /**
     * Creates a validator for the given object.
     *
     * @param object object to validate
     */
    public Validator(T object) {
        this.object = object;
        this.errors = new ArrayList<>();
    }

    /**
     * Starts a validation chain for the given object.
     *
     * @param object object to validate
     * @param <T> type of the object being validated
     * @return validator instance for the object
     */
    public static <T> Validator<T> of(T object) {
        return new Validator<>(object);
    }

    /**
     * Selects a field value to validate.
     *
     * @param name field name used in validation errors
     * @param <R> type of the field value
     * @return field validator for the selected field
     */
    public <R> FieldValidator<R> field(String name) {
        try {
            Class<?> objectClass = object.getClass();
            Field field = objectClass.getDeclaredField(name);
            field.setAccessible(true);

            R value = (R) field.get(object);

            return new FieldValidator<>(name, value, this, true);
        } catch (NoSuchFieldException e) {
            this.addError(name, "Field does not exist.");
            return new FieldValidator<>(name, null, this, false);
        } catch (IllegalAccessException e) {
            this.addError(name, "Field cannot be accessed.");
            return new FieldValidator<>(name, null, this, false);
        }
    }

    /**
     * Adds a validation error to the current validation context.
     *
     * @param field field name related to the error
     * @param message validation error message
     * @return new error
     */
    public ValidationError addError(String field, String message) {
        ValidationError error = new ValidationError(field, message);
        errors.add(error);
        return error;
    }

    /**
     * Finishes the validation chain.
     *
     * @return true when no validation errors were collected
     * @throws ValidationException when one or more validation errors were collected
     */
    public Boolean validate() {
        if (errors.isEmpty()) {
            return true;
        } else {
            throw new ValidationException(errors);
        }
    }
}
