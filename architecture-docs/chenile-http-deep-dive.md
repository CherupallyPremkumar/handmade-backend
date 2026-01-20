# Chenile HTTP: The REST Bridge (Official)

This document describes the **Chenile HTTP** module, which provides the protocol-specific boundary for RESTful communication.

---

## 🌉 1. The Controller Boundary: `ControllerSupport`

In the Chenile pattern, controllers are extremely thin delegators. This is achieved via the `ControllerSupport` base class.

### Key Logic:
- **`process()`**: The primary method called by your controller. It automatically:
    - Identifies the calling operation (`opName`) using stack trace inspection.
    - Bridges the `HttpServletRequest` to a `ChenileExchange`.
    - Delegates to `chenileEntryPoint.execute()`.
    - Handles the conversion of the `ChenileExchange` result back into a Spring `ResponseEntity`.
- **Automatic Metadata Extraction**:
    - URL Path Variables (e.g., `{id}`).
    - Request Headers.
    - Multi-part file maps.

---

## 🚦 2. The Generic Handler: `HttpEntryPoint`

While `ControllerSupport` is for custom code, `HttpEntryPoint` is used for **Generic Services** (like `StateEntityService`).

### Responsibilities:
- **Request Marshalling**: Converts incoming JSON bodies into the type specified by the `OperationDefinition`'s `BodyTypeSelector`.
- **Response Marshalling**: Uses a pre-configured `ObjectMapper` to ensure clean, consistent JSON output (ignoring nulls/empty beans).
- **MimeType Support**: Automatically handles different `produces` types (JSON, HTML, TEXT, and even **PDF**).

---

## 🛠️ 3. Automation: `AnnotationChenileServiceInitializer`

Chenile eliminates manual configuration through its intelligent boot-time scanning.

### The "@ChenileController" Magic:
When you annotate a class with `@ChenileController`, this initializer:
1.  **Scans for Bindings**: Finds all methods annotated with `@GetMapping`, `@PostMapping`, etc.
2.  **Extracts Chenile Metadata**:
    - `InterceptedBy`: Custom OWIZ chains for specific operations.
    - `BodyTypeSelector`: Logic for poly-body parsing.
    - `ChenileResponseCodes`: Custom success/warning HTTP statuses.
3.  **Binds to Core**: Registers the resulting `ChenileServiceDefinition` and `OperationDefinition`s in the `ChenileConfiguration` registry.

---

## 🎯 4. Handmade Implementation Rule
> [!IMPORTANT]
> **Use ControllerSupport**: All delegators (controllers) must extend `ControllerSupport`. 
> **Call process()**: Do not write logic in the controller. Simply call `return process(request, ...args)`.
> **Leverage Annotations**: Use `@ChenileController` and standard Spring `@XMapping` annotations to define the contract. Chenile will handle the rest.

**This document completes the technical mapping of the protocol boundary for the Handmade platform.**
