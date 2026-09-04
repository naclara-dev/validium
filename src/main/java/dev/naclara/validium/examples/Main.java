package dev.naclara.validium.examples;

import dev.naclara.validium.Validator;

public class Main {
    static void main(String[] args) {
        User user = new User("Ana", 22);

        Validator.of(user)
                .field("name", user.getName())
                    .notEmpty()
                .field("age", user.getAge())
                    .min(18)
                .validate();

    }
}
