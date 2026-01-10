# Chenile Core: The Framework Engine (Official)

This document describes the **Chenile Core Engine**, the heart of the framework that manages the request lifecycle, interception, and service dispatching.

---

## 🚀 1. The Entry Point: `ChenileEntryPoint`

The `ChenileEntryPoint` is the **Gateway to the Chenile Highway**. 
- Every transport (HTTP, MQTT, Batch, etc.) must eventually delegating to this class.
- It triggers the **OWIZ Orchestration Chain** (`OrchExecutor<ChenileExchange>`).

---

## 📦 2. The Context: `ChenileExchange`

The `ChenileExchange` is a bidirectional context object that carries the request and response through the entire interception chain.

### Key Components:
- **Headers**: Map of request/context headers (e.g., `eventID`, `trajectoryID`).
- **Body**: The payload expected by the service.
- **Metadata**: 
    - `ServiceDefinition`: Metadata about the target service.
    - `OperationDefinition`: Metadata about the specific operation being called.
- **Service Reference**: Dynamically resolved implementation to be invoked.
- **Execution State**: Tracks exceptions and response messages.

---

## ⛓️ 3. The Interception Chain (OWIZ)

Chenile uses a sequential pipeline of interceptors to apply cross-cutting concerns.

### Standard Pipeline Flow:
1.  **Pre-Processors**: Security checks, audit logging, and context initialization.
2.  **`ConstructServiceReference`**: 
    - Resolves the actual bean to invoke.
    - Supports **Trajectories** (env-specific/tenant-specific overrides).
    - Supports **Mocks** (if `x-chenile-mock` header is present).
3.  **OWIZ Commands**: Any number of custom interceptors (e.g., Validation, Transformation).
4.  **`ServiceInvoker`**: The final command that:
    - Extracts parameters from the `ChenileExchange` based on the method signature.
    - Invokes the service method via reflection.
    - Sets the result back into the `ChenileExchange.response`.
5.  **Post-Processors**: Result transformation (e.g., `GenericResponseBuilder`), final audit, and metric logging.

---

## 🛡️ 4. Governance & Base Interceptor

### `BaseChenileInterceptor`
The standard base class for all interceptors. It provides hooks for:
- `doPreProcessing(exchange)`
- `doContinue(exchange)`: Must be called to pass control to the next interceptor.
- `doPostProcessing(exchange)`

---

## 🎯 5. Handmade Implementation Rule
> [!IMPORTANT]
> **Transport Neutrality**: Always implement logic in the Service layer, never in the Controller/Delegator. 
> The `ChenileCore` ensures that if you call a service via HTTP or internally via a Batch job, the exact same rules (Security, Audit, STM transitions) are applied because they all pass through the same `ChenileEntryPoint`.

**This document completes the technical architectural reference for the Handmade platform.**
