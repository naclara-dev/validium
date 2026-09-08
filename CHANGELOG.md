# Changelog

All notable changes to this project will be documented in this file.

This project follows semantic versioning.

## [0.2.0] - 2026-09-08

### Added

- Field lookup through reflection, allowing validation with `field("name")` without passing the field value manually.
- `notBlank()` validation for strings containing only whitespace.
- Custom validation messages through `onFail(message)`.

### Changed

- `notEmpty()` now only checks whether a string is empty; blank-string validation is handled by `notBlank()`.
- The `field(...)` API now receives the field name only.

## [0.1.1] - 2026-09-04

### Added

- Maven Central publishing configuration.
- Public API Javadocs.
- MIT license metadata.

### Fixed

- ValidationException constructor initialization order.

## [0.1.0] - 2026-09-04

### Added

- Initial fluent validation API with `Validator.of(...)` and `field(...)`.
- Common validation with `notNull()`.
- Text validations with `notEmpty()`, `minLength(size)`, and `maxLength(size)`.
- Numeric validations with `min(value)` and `max(value)`.
- Validation error collection through `ValidationException`.
- Basic usage examples.
- Initial README documentation.
