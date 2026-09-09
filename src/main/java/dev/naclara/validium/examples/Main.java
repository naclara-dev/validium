package dev.naclara.validium.examples;

import dev.naclara.validium.Validator;

import java.util.ArrayList;

public class Main {
    static void main(String[] args) {
        User user = new User();

        user.name = "Ana Clara";
        user.email = "";
        user.age = 20;
        user.gender = "Other";
        user.bio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        user.password = "Validium123";

        Validator.of(user).validate();
    }
}
