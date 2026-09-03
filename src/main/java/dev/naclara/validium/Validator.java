package dev.naclara.validium;

import java.util.ArrayList;
import java.util.List;

public class Validator<T> {
    T object;
    List<ValidationError> errors;

    public Validator(T object) {
        this.object = object;
        this.errors = new ArrayList<>();
    }

    public static <T> Validator<T> of(T object) {
        return new Validator<>(object);
    }

    public <R> FieldValidator<R> field(String name, R value) {
        return new FieldValidator<>(name, value, this);
    }

    public void addError(String field, String message) {
        errors.add(new ValidationError(field, message));
    }

    public Boolean validate() {
        if (errors.isEmpty()) {
            return true;
        } else {
            throw new ValidationException(errors);
        }
    }
}
