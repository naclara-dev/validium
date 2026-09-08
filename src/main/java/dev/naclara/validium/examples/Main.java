package dev.naclara.validium.examples;

import dev.naclara.validium.Validator;

public class Main {
    static void main(String[] args) {
        User user = new User("Ana", 22, "contato@naclara.dev");

        Validator.of(user)
                // Validium native validation
                .field("name")
                    .required()
                // Validium native validation + custom error message
                .field("age")
                    .min(21).onFail("Custom Message!")
                // Custom validation + custom error message
                .field("email")
                    .matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")
                .validate();

    }
}
