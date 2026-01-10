# Chenile JPA: Persistence & SLA Governance (Official)

This document describes the **Chenile JPA Utils** module, which provides a standard foundation for persistent entities, ensuring consistent multi-tenancy, audit trails, and state machine integration.

---

## 🏗️ 1. The Foundation: `BaseJpaEntity`

All JPA entities in a Chenile-based application should extend `BaseJpaEntity`. It provides the essential plumbing for enterprise-grade data management.

### Built-in Governance:
- **Automatic ID Generation**: Uses `IDGenerator` to create prefixed, unique IDs automatically if not provided.
- **Multi-Tenancy**: The `tenant` field is automatically populated from the global `ContextContainer` during `@PrePersist`.
- **Audit Trails**: `createdBy`, `createdTime`, `lastModifiedBy`, and `lastModifiedTime` are managed automatically.
- **Optimistic Locking**: Includes a `@Version` field to prevent lost updates in concurrent environments.
- **Test Mode Awareness**: Automatically flags entities created during tests via the `testEntity` boolean.

---

## 🔄 2. Stateful Entities: `AbstractJpaStateEntity`

For entities that represent a business process (managed by STM), Chenile provides `AbstractJpaStateEntity`.

### STM Integration:
- **Embedded State**: Holds the `flowId` and `stateId` directly in the database table, mapped from the Chenile `State` object.
- **State Lifecycle**: Implements `setCurrentState` and `getCurrentState` for seamless interaction with the Chenile State Machine.

### SLA Management:
Chenile JPA includes built-in fields to track Service Level Agreements (SLAs) per state:
- **`stateEntryTime`**: Captured automatically when an entity enters a new state.
- **`slaYellowDate` / `slaRedDate`**: Dynamic timestamps used to determine if a process is "Tending Late" or "Late."
- **`slaTendingLate` / `slaLate`**: Boolean flags used for efficient query filtering in "To-Do List" screens.

---

## 🛡️ 3. Persistence Rules & Hooks

The module leverages JPA lifecycle hooks to ensure logic is enforced regardless of how the entity is saved.

- **`initializeIfRequired()`**: Triggered by `@PrePersist` and `@PreUpdate`. It pulls metadata from `ContextContainer.CONTEXT_CONTAINER`, ensuring that even direct repository saves are correctly governed by the current request context.

---

## 🎯 4. Handmade Implementation Rule
> [!IMPORTANT]
> **Extending the Right Class**: Use `BaseJpaEntity` for simple data. Use `AbstractJpaStateEntity` for any entity that has a lifecycle (e.g., Orders, Seller Applications).
> **Timestamp Format**: All dates are standardized to `yyyy-MM-dd HH:mm a z` for consistent JSON serialization.
> **Prefixing IDs**: Override `getPrefix()` in your entity to give your IDs a meaningful prefix (e.g., `ORD-` for Orders).

**This document defines the persistence standard for the Handmade platform.**
