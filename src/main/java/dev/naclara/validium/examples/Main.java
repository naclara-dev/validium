package dev.naclara.validium.examples;

import dev.naclara.validium.Validator;

import java.util.ArrayList;

public class Main {
    static void main(String[] args) {
        User user = new User();

        user.name = "Ana Clara";
        user.email = "contato@naclara.com";
        user.age = 20;
        user.gender = "Other";
        user.bio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        user.password = "Validium123";

        Validator.of(user)
                .field("name")
                    .required()
                    .notBlank()

                .field("email")
                    .required()
                    .matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
                    .check(
                            value -> value instanceof String email && email.endsWith("naclara.dev"),
                            "E-mail must belong to naclara.dev domain."
                    )

                .field("age")
                    .min(21)
                    .max(99)

                .field("gender")
                    .in("Female", "Male", "Non-Binary")

                .field("bio")
                    .maxLength(100)

                .field("password")
                    .minLength(10)

                .validate();

    }
}
