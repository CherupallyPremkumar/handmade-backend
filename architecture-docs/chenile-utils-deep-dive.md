# Chenile Utils: The Foundational Layer (Official)

This document describes the **Chenile Utils** module, which provides the foundational structures and cross-cutting utilities required for building modular, multi-tenant applications.

---

## 🏗️ 1. The Entity Core: `ChenileEntity` & `BaseEntity`

Every domain object in a Chenile-based system should adhere to the `ChenileEntity` contract.

### Key Attributes:
- **ID Management**: Standardizes the use of `String` IDs.
- **Audit Trail**: Built-in support for `createdBy`, `createdTime`, `lastModifiedBy`, and `lastModifiedTime`.
- **Concurrency**: Includes a `version` field for optimistic locking.

> [!TIP]
> Use `BaseEntity` as your parent class for all JPA entities to automatically include these fields and remain compatible with other Chenile modules (like `jpa-utils`).

---

## 🆔 2. Deterministic ID Generation: `IDGenerator`

The `IDGenerator` is a critical utility for systems requiring **distributed consistency** (e.g., replicating data between an edge "Store" and the "Cloud").

### How it Works:
1.  **Request Bound**: It uses the `requestId` from the `ContextContainer`.
2.  **Deterministic**: For a given `requestId` and `prefix`, it generates the same ID every time it is called in a specific order.
3.  **Pattern**: `[prefix]-[requestId]-[counter]`.

This ensures that if the same request is replayed on a different machine, the generated entity IDs will match perfectly.

---

## 🏢 3. Multi-Tenancy Utilities

### `TenantRouter` & `HeadersAwareContext`
- **Routing**: Extracts the `x-chenile-tenant` header to determine the routing target in an `Owiz` chain.
- **Context**: Allows any object that is "Headers Aware" to participating in tenant-based routing.

### `TenantSpecificResourceLoader`
Implements a **"Fall-through Strategy"** for resource discovery:
1.  **Tenant Path**: `templates/%{tenantId}/%{name}.xml`
2.  **Fallback**: If not found, it loads from the **Generic Path**: `templates/generic/%{name}.xml`

This allows you to override configurations or templates for specific customers without duplicating the entire resource set.

---

## 🛠️ 4. Miscellaneous Utilities

- **`StrSubstitutor`**: A lightweight utility for template variable replacement (e.g., replacing `%{tenantId}` in a path).
- **`EntityStore`**: A generic interface for basic CRUD operations, abstracting the underlying storage mechanism.

---

## 🎯 5. Handmade Implementation Rule
> [!IMPORTANT]
> **Audit Integrity**: Always use the built-in audit fields. Never create your own `creation_date` columns.
> **Predictable IDs**: If you are building a system that requires offline/online synchronization, use `IDGenerator.generateID()` instead of DB sequences.
> **Tenant Overrides**: Use `TenantSpecificResourceLoader` for XML-based STM definitions or report templates to support custom-tailored customer experiences.

**This document defines the foundational standards for the Handmade platform.**
