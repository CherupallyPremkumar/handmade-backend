# Chenile Workflow API: The Process Orchestration Contract (Official)

This document describes the **Chenile Workflow API**, which provides the high-level service contract for managing business processes (workflows) in the framework.

---

## 🏗️ 1. The Core Service: `StateEntityService`

This interface is the primary entry point for all operations on "State Entities" (entities with a managed lifecycle). It abstracts the underlying State Machine (STM) into a standard CRUD-like API with process-specific extensions.

### Key Methods:
- **`create(T entity)`**: Initializes a new entity, setting it to the initial state defined in the STM and populating mandatory audit fields.
- **`process(T entity, String event, Object payload)`**: Processes an event on a provided entity, returning the mutated entity and its new allowed actions.
- **`processById(String id, String event, Object payload)`**: Same as `process`, but fetches the entity from persistent storage first using its ID.
- **`retrieve(String id)`**: Fetches an entity and its valid HATEOAS actions without triggering a transition.

---

## 🗄️ 2. Global Discovery: `WorkflowRegistry`

The `WorkflowRegistry` is a static catalog that keeps track of the `STMActionsInfoProvider` for every active workflow in the system.

- **Purpose**: It allows the Query layer (and other modules) to dynamically look up valid actions for an entity based just on the `workflowName`.
- **HATEOAS Injection**: When a search result is returned, Chenile looks up the corresponding provider in this registry to inject button/action metadata into each row.

---

## 🔄 3. Communication Models: DTOs

### `StateEntityServiceResponse<T>`
The standard wrapper for all workflow service calls.
- **`mutatedEntity`**: The domain object after the state transition and business logic execution.
- **`allowedActionsAndMetadata`**: A list of maps containing the next valid events (e.g., `Approve`, `Cancel`) and their associated UI metadata (icons, labels, ACLs).

### `MinimalPayload`
A standardized payload used for simple events like `CANCEL` or `APPROVE` that only require an optional **comment** or reason.

---

## 🛡️ 4. Governance & Abstraction

By using `workflow-api` instead of calling the STM directly:
1.  **Uniformity**: All business processes (Order, Onboarding, Payment) expose the same API signature.
2.  **Statelessness**: The `processById` method handles the hydration of stateful entities automatically.
3.  **UI Decoupling**: The frontend only needs to understand the `process` call and the `allowedActions` list to build dynamic, state-aware interfaces.

---

## 🎯 5. Handmade Implementation Rule
> [!IMPORTANT]
> **Service First**: Your domain services (e.g., `SellerAccountService`) should implement or delegate to `StateEntityService`. Never expose raw STM methods to the transport layer.
> **Payload Discipline**: Use `MinimalPayload` for simple state changes. For complex data intake during a transition (e.g., `UpdateShippingInfo`), create a specific DTO but ensure it is serializable.
> **Register Your Workflows**: Ensure that your service implementation registers its `STMActionsInfoProvider` with the `WorkflowRegistry` during startup, or HATEOAS buttons will disappear from search results.

**This document defines the process orchestration standard for the Handmade platform.**
