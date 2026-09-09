package dev.naclara.validium.examples;

import dev.naclara.validium.annotation.*;

public class User {
    @Required
    @NotBlank
    String name;

    @Required
    @Matches(pattern = "^[^@\\\\s]+@[^@\\\\s]+\\\\.[^@\\\\s]+$")
    String email;

    @Min(size = 21, message = "You must be of legal age.")
    Integer age;

    @OneOf(values = {"Female", "Male", "Non-Binary"})
    String gender;

    @MaxLength(size = 100)
    String bio;

    @MinLength(size = 10)
    String password;

    public User() {
    }
}
