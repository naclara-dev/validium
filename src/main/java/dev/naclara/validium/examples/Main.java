package dev.naclara.validium.examples;

import dev.naclara.validium.Validator;

public class Main {
    static void main(String[] args) {
        User user = new User("", 17);

        Validator.of(user)
                .field("name")
                    .notEmpty().onFail("Custom Message!")
                .field("age")
                    .min(18)
                .validate();

    }
}
