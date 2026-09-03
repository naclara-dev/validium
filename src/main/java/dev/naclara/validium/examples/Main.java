package dev.naclara.validium.examples;

import dev.naclara.validium.Validator;

public class Main {
    static void main(String[] args) {
        User user = new User("Ana", 17);

        Validator.of(user)
                .field("name", user.getName())
                    .maxLength(2)
                .field("age", user.getAge())
                    .min(18)
                .validate();

    }
}
