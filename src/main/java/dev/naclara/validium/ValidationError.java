package dev.naclara.validium;

public class ValidationError {
    String field;
    String message;

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public void setField(String value) {
        this.field = value;
    }

    public void setMessage(String value) {
        this.message = value;
    }
}
