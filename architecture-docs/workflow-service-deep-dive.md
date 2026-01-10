# Chenile Workflow Service: High-Level Governance (Official)

This document describes the **Workflow Service Layer**, which provides enterprise governance and HATEOAS capabilities on top of the base State Transition Machine (STM).

---

## 🏛️ 1. The Generic Service: `StateEntityService<T>`

While the `STM` focuses on state transitions, the `StateEntityService` focuses on **Managing the Entity Lifecycle** within a governed environment.

### Core Operations
- **`create(entity)`**: Initializes the entity and triggers the initial state machine transition.
- **`retrieve(id)`**: Fetches the entity and computes **Allowed Actions** based on its current state.
- **`processById(id, event, payload)`**: The standard REST-to-STM bridge. Loads the entity, triggers the transition, and returns the result with allowed next steps.

---

## 🛡️ 2. Metadata-Driven Governance

Chenile uses the STM XML as a **System of Record** for service configuration via metadata.

### A. Dynamic Security (ACLs)
Using `StmAuthoritiesBuilder`, Chenile dynamically restricts API access based on the event:
```xml
<event-information eventId="SHIP" >
    <meta name="acls" value="ROLE_ADMIN,ROLE_LOGISTICS"/>
</event-information>
```

### B. Payload Validation (BodyType)
`StmBodyTypeSelector` ensures the incoming HTTP body is correctly deserialized:
```xml
<event-information eventId="SHIP" >
    <meta name="bodyType" value="com.handmade.dto.ShipmentRequest"/>
</event-information>
```

---

## 🚦 3. HATEOAS & "Allowed Actions"

A key feature of `StateEntityServiceImpl` is its ability to tell the client **what can be done next**.
- **`STMActionsInfoProvider`**: Inspects the STM configurations to find all valid transitions from the current state.
- **`StateEntityServiceResponse`**: Wraps the entity and includes a list of `AllowedActionsAndMetadata`.
- **UI Benefit**: The front-end can dynamically show/hide buttons (e.g., "Cancel", "Ship") based on the response without hardcoding state logic.

---

## 🛠️ 4. Standard Lifecycle Actions

Chenile provides "Generic" components for common patterns:
- **`GenericEntryAction`**: Automatically stamps `stateEntryTime` and computes SLA dates (`slaLate`, `slaTendingLate`) if the entity extends `ExtendedStateEntity`.
- **`BaseTransitionAction`**: Allows a state transition to be implemented as a chain of **OWIZ Commands**. This is how a single state change can trigger "Charge Credit Card" -> "Create Invoice" -> "Notify Customer".

---

## 🎯 5. Handmade Implementation Rule
> [!IMPORTANT]
> For any module involving a workflow (Order, Onboarding, Payout), DO NOT implement custom service logic. 
> Instead, extend/use `StateEntityServiceImpl`. 
> All logic should reside in **STM XML** and **Command Actions**.

**This document provides the definitive technical mapping for the Workflow layer.**
