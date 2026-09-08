package dev.naclara.validium.examples;

import dev.naclara.validium.Validator;

public class Main {
    static void main(String[] args) {
        User user = new User(null, 22, "contato@naclara.dev");

        Validator.of(user)
                // Validium native validation
                .field("name")
                    .required()
                // Validium native validation + custom error message
                .field("age")
                    .min(25).onFail("Custom Message!")
                // Custom validation + custom error message
                .field("email")
                    .check(
                            value -> value instanceof String text && text.contains("@"),
                            "E-mail must contains '@'."
                    )
                .validate();

    }
}
