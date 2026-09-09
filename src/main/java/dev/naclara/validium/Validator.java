package dev.naclara.validium;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entry point for validating an object through a fluent field-based API.
 *
 * @param <T> type of the object being validated
 */
public class Validator<T> {
    T object;
    AnnotationValidator annotations;
    List<ValidationError> errors;

    /**
     * Creates a validator for the given object.
     *
     * @param object object to validate
     */
    public Validator(T object) {
        this.object = object;
        this.annotations = new AnnotationValidator(this);
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
        return new Validator<>(Objects.requireNonNull(
                object,
                () -> "Validium cannot validate a null object."
        ));
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
            R value = (R) readField(name);

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
        annotations.validate(object);

        if (errors.isEmpty()) {
            return true;
        } else {
            throw new ValidationException(errors);
        }
    }

    /**
     * Resolves an object's field by its name.
     * @param name field name to search
     * @return found field
     * @throws NoSuchFieldException
     */
    private Field resolveField(String name) throws NoSuchFieldException {
        Class<?> currentClass = object.getClass();

        while (currentClass != null) {
            try {
                return currentClass.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }

        throw new NoSuchFieldException(name);
    }

    /**
     * Reads the value of a field.
     * @param name
     * @return Object field's value
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Object readField(String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = resolveField(name);
        field.setAccessible(true);

        return field.get(object);
    }
}
