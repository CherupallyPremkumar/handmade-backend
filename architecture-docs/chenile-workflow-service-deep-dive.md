# Chenile Workflow Service: The Implementation Engine (Official)

This document describes the **Chenile Workflow Service**, the concrete implementation of the process orchestration layer.

---

## 🚀 1. Core Orchestrator: `StateEntityServiceImpl`

The `StateEntityServiceImpl` is the primary workhorse. It coordinates three major components:
- **`STM`**: The State Machine that enforces the transition rules.
- **`STMActionsInfoProvider`**: Computes valid next steps (HATEOAS).
- **`EntityStore`**: Handles persistence.

### Logic Flow:
1.  **Create**: Wipes any provided state, calls `stm.proceed()`, and lets the STM assign the initial state and trigger entry actions.
2.  **Process**: Forwards the entity and event to `stm.proceed()`. It handles `STMException` and translates them into appropriate `ErrorNumException` (e.g., 422 for invalid transitions).
3.  **ProcessById**: Standardizes the "load → process → save" pattern for stateless API calls.

---

## 📊 2. Governance: SLA & Persistence Hooks

The service provides standardized STM actions that enforce platform governance.

### `GenericEntryAction`
This is where SLA tracking is initialized. Every time a state is entered:
- **`stateEntryTime`**: Recorded as the current timestamp.
- **SLA Computation**: Extracts `sla-late-in-hours` and `sla-getting-late-in-hours` from the **State Metadata** in `stm.xml`.
- **Checkpoint**: Calls `entityStore.store()` to ensure the state change is persisted immediately.

### `GenericRetrievalStrategy`
Automates the hydration of entities during STM internal transitions. If the STM reaches an internal point where it needs more context, this strategy fetches the latest copy from the `EntityStore`.

---

## 🛠️ 3. Extensibility:### `BaseTransitionAction` & `AbstractSTMTransitionAction`
These actions allow developers to attach complex business logic to a transition using simple metadata in the `stm.xml`.

- **`BaseTransitionAction` (v1.4.2)**: The polymorphic base available in our local source.
- **`AbstractSTMTransitionAction` (v2.0.35+)**: The modern, type-safe inheritance pattern used in the latest Chenile releases.

| Metadata Key | Effect |
| :--- | :--- |
| **`command`** | Executes a single Owiz `Command`. |
| **`orchExecutor`** | Delegates execution to an existing Owiz `Chain`. |
| **`orchestratedCommandsConfiguration`** | Dynamically loads and executes an XML-based Owiz chain specifically for this transition. |

---

## 🎯 4. Handmade Implementation Rule
> [!IMPORTANT]
> **Metadata SLAs**: Always define `sla-late-in-hours` in your `stm.xml` state metadata. This is what drives the dashboard "Late" flags.
> **Micro-Actions**: For complex transitions (e.g., "Approve Seller"), prefer using `orchestratedCommandsConfiguration` with an XML chain. This keeps the Java code clean and allows for modular "micro-actions" (emailing, ledger posting, etc.).
> **ID Foundation**: Ensure your `EntityStore` implementation is correctly injected. `StateEntityServiceImpl` relies on it for every `processById` call.

**This document defines the process implementation standard for the Handmade platform.**
