package dev.naclara.validium;

public record ValidationError(
        String field,
        String message
) {
}
