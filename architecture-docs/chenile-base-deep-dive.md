# Chenile Base: The Communication Contract (Official)

This document describes the **Chenile Base** module, which defines the standard data structures and exceptions used for uniform communication across the framework.

---

## ✉️ 1. The Standard Envelope: `GenericResponse<T>`

All Chenile services (governed by `@ChenileService`) return this standard envelope to ensure consistent protocol handling (HTTP, Batch, etc.).

### Fields:
- **`success`**: Boolean indicating the outcome.
- **`payload` (JSON) / `data` (Java)**: The actual domain object returned by the service.
- **`code`**: The primary HTTP-standard status code (e.g., 200, 400, 500).
- **`subErrorCode`**: A service-specific integer code for precise error mapping.
- **`errors`**: A list of `ResponseMessage` objects (includes both errors and warnings).

---

## 📝 2. Detailed Feedback: `ResponseMessage`

Encapsulates specific feedback about a request, often used for validation or business rule failures.

### Fields:
- **`description`**: The localized, human-readable message.
- **`field`**: (Optional) The specific DTO field that caused the issue.
- **`severity`**: `ERROR` or `WARN`.
- **`params`**: Array of objects for i18n message substitution (e.g., `Resource {0} not found`).

---

## ⚠️ 3. Reactive Feedback: `WarningAware`

The `WarningAware` interface allows domain entities or DTOs to "carry" warnings back through the service layer.
- **Mechanism**: If a returned object implements `WarningAware`, the framework (`ChenileExchange`) automatically extracts these warnings and attaches them to the `GenericResponse` envelope before final delivery.

---

## 🚨 4. Standard Case: `ErrorNumException`

The root of the Chenile exception hierarchy. It is a `RuntimeException` that forces standardized reporting.

### Subclasses:
- `BadRequestException` (400)
- `NotFoundException` (404)
- `ServerException` (500)
- `ConfigurationException` (500 - Framework setup issues)

### Key Benefit:
When an `ErrorNumException` is thrown anywhere in the service layer or STM, the framework catches it and translates it **exactly** into a `GenericResponse` with the correct `code` and `subErrorCode`.

---

## 🎯 5. Handmade Implementation Rule
> [!IMPORTANT]
> **No Custom Envelopes**: Never wrap your service responses in custom "Result" or "Response" objects. Chenile does this automatically.
> **Standard Exceptions**: Always throw `ErrorNumException` subclasses (like `NotFoundException`) to ensure the front-end receives a clean, standardized error payload.

**This document defines the foundational contract for the Handmade platform.**
