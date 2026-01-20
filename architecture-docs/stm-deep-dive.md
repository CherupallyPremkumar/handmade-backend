# STM: State Transition Machine (Official)

This document describes the **Chenile STM**, the heart of the framework's business process automation and lifecycle governance.

---

## 🏗️ 1. Core Architecture: The Stateless Engine

The Chenile STM is a **recursive, metadata-driven engine** that manages transitions for entities implementing the `StateEntity` interface.

### The `internalProceed` Loop:
1.  **Recursion**: The STM automatically processes all **Auto-States** (states with automatic transitions) until it reaches a **Manual State** (waiting for an event) or a **Final State**.
2.  **Hooks**: At every step, it triggers:
    *   `ExitAction` of the source state.
    *   `TransitionAction` of the event.
    *   `EntryAction` of the target state.
3.  **HATEOAS**: The `STMActionsInfoProvider` dynamically computes valid transitions for the current user's roles, enabling "smart" UIs.

---

## 🔄 2. State & Entity Models

### The `StateEntity`
Every domain object with a lifecycle (e.g., `Order`, `SellerApplication`) must implement:
```java
public interface StateEntity {
    void setCurrentState(State state);
    State getCurrentState();
}
```

### State Types:
- **Manual State**: Requires an external `eventId` (API call or Webhook) to move.
- **Auto State**: Uses `STMAutomaticStateComputation` to decide the next event automatically (e.g., checking if a payment is already authorized).
- **Retrieval Transition**: A special event triggered when an entity is loaded from persistent storage, used to "awaken" the workflow.

---

## 💾 3. Persistence Bridge: Retrieval Strategies

The STM provides a powerful bridge to persistent storage via `StateEntityRetrievalStrategy`.

- **Retrieve**: Fetches the entity from the DB.
- **Merge**: Injects the state and data from the DB into the incoming request object, ensuring the STM always works with a fully hydrated entity.
- **Result**: Allows the STM service to be stateless while preserving complex business state across request boundaries.

---

## 🛡️ 4. Security & Governance

### Access Control (ACLs)
Transitions can be protected by **ACLs** (Role-based strings). The `STMSecurityStrategy` prevents unauthorized users from triggering specific events (e.g., only a `MANAGER` can `APPROVE`).

### Internal Transition Invoker
Allows business logic to trigger secondary transitions internally. For example:
> `PaymentAction` (on `Pay` event) → internally triggers `Ship` event if payment is captured.

---

## 🎯 5. Handmade Implementation Rule
> [!IMPORTANT]
> **No Bypass**: Never update the `stateId` of an entity directly in your business logic. Always use `stm.proceed()`.
> **Action Separation**: Keep business logic in `STMTransitionAction`. Keep UI/Audit logic in `Entry/Exit` actions.
> **ACL Consistency**: Use the same ACL strings in your security interceptors and your STM transitions to ensure consistent governance.

**This document defines the lifecycle governance standard for the Handmade platform.**
