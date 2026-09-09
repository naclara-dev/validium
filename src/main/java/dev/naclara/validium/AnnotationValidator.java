package dev.naclara.validium;

import dev.naclara.validium.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Validates object fields using runtime validation annotations.
 *
 * <p>Annotation handlers delegate the actual validation rules to the
 * corresponding field validators and share the parent validator's error
 * collection.</p>
 */
public class AnnotationValidator {
    private final Validator<?> validator;
    private final Map<Class<? extends Annotation>, AnnotationHandler> handlers;

    /**
     * Creates an annotation validator for an existing validation context.
     *
     * @param validator validator that owns the validation context and error collection
     */
    public AnnotationValidator(Validator<?> validator) {
        this.validator = validator;
        this.handlers = new HashMap<>();

        registerHandlers();
    }

    /**
     * Validates the annotations declared on the object's fields.
     *
     * @param object object whose fields should be validated
     */
    public void validate(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            for (Annotation annotation : field.getAnnotations()) {
                AnnotationHandler handler = handlers.get(annotation.annotationType());

                if (handler != null) {
                    FieldValidator<?> fieldValidator = validator.field(field.getName());
                    handler.handle(annotation, fieldValidator);
                }
            }
        }
    }

    /**
     * Registers the handlers that map annotation types to field validations.
     */
    private void registerHandlers() {
        // @Required
        handlers.put(
                Required.class,
                (annotation, fieldValidator) -> {
                    Required required = (Required) annotation;
                    apply(fieldValidator.required(), required.message());
                }
        );
        // @OneOf
        handlers.put(
                OneOf.class,
                (annotation, fieldValidator) -> {
                    OneOf oneOf = (OneOf) annotation;
                    apply(fieldValidator.in(oneOf.values()), oneOf.message());
                }
        );
        // @NotEmpty
        handlers.put(
                NotEmpty.class,
                (annotation, fieldValidator) -> {
                    NotEmpty notEmpty = (NotEmpty) annotation;
                    apply(fieldValidator.notEmpty(), notEmpty.message());
                }
        );
        // @NotBlank
        handlers.put(
                NotBlank.class,
                (annotation, fieldValidator) -> {
                    NotBlank notBlank = (NotBlank) annotation;
                    apply(fieldValidator.notBlank(), notBlank.message());
                }
        );
        // @Matches
        handlers.put(
                Matches.class,
                (annotation, fieldValidator) -> {
                    Matches matches = (Matches) annotation;
                    apply(fieldValidator.matches(matches.pattern()), matches.message());
                }
        );
        // @MinLength
        handlers.put(
                MinLength.class,
                (annotation, fieldValidator) -> {
                    MinLength minLength = (MinLength) annotation;
                    apply(fieldValidator.minLength(minLength.size()), minLength.message());
                }
        );
        // @MaxLength
        handlers.put(
                MaxLength.class,
                (annotation, fieldValidator) -> {
                    MaxLength maxLength = (MaxLength) annotation;
                    apply(fieldValidator.maxLength(maxLength.size()), maxLength.message());
                }
        );
        // @Min
        handlers.put(
                Min.class,
                (annotation, fieldValidator) -> {
                    Min min = (Min) annotation;
                    apply(fieldValidator.min(min.size()), min.message());
                }
        );
        // @Max
        handlers.put(
                Max.class,
                (annotation, fieldValidator) -> {
                    Max max = (Max) annotation;
                    apply(fieldValidator.max(max.size()), max.message());
                }
        );
    }

    private void apply(FieldValidator<?> fieldValidator, String message) {
        if (!message.isBlank()) {
            fieldValidator.onFail(message);
        }
    }
}
