# Validium

Validium is a lightweight Java validation library with a fluent API for validating object fields and collecting validation errors.

## Requirements

- Java 25
- Maven

## Installation

Validium is available on Maven Central. Add the dependency to your Maven project:

```xml
<dependency>
    <groupId>dev.naclara</groupId>
    <artifactId>validium</artifactId>
    <version>0.1.1</version>
</dependency>
```

## Usage

```java
import dev.naclara.validium.Validator;

public class Main {
    public static void main(String[] args) {
        User user = new User("Ana", 22);

        Validator.of(user)
                .field("name", user.getName())
                    .notEmpty()
                    .maxLength(100)
                .field("age", user.getAge())
                    .min(18)
                .validate();
    }
}
```

When validation fails, `validate()` throws a `ValidationException` with the collected validation errors.

## Available validations

### Common

| Validation | Description |
| --- | --- |
| `notNull()` | Fails when the field value is `null`. |

### Text

| Validation | Description |
| --- | --- |
| `notEmpty()` | Fails when the string is empty or blank. |
| `minLength(size)` | Fails when the string length is lower than `size`. |
| `maxLength(size)` | Fails when the string length is greater than `size`. |

### Numeric

| Validation | Description |
| --- | --- |
| `min(value)` | Fails when the number is lower than `value`. |
| `max(value)` | Fails when the number is greater than `value`. |

## Example

The repository includes a basic example in:

```text
src/main/java/dev/naclara/validium/examples
```

## License

Under MIT license.
