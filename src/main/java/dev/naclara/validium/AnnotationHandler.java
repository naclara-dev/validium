package dev.naclara.validium;

import java.lang.annotation.Annotation;

/**
 * Handles a validation annotation for a selected field.
 */
@FunctionalInterface
public interface AnnotationHandler {
    /**
     * Applies the validation represented by an annotation.
     *
     * @param annotation annotation found on the field
     * @param fieldValidator validator for the annotated field
     */
    void handle(
            Annotation annotation,
            FieldValidator<?> fieldValidator
    );
}
