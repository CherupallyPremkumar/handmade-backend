# Chenile Query: Governed Data Retrieval (Official)

This document describes the **Chenile Query** framework, which provides a standardized, metadata-driven approach to complex data retrieval and pagination.

---

## 🌉 1. The Retrieval Boundary: `QueryController`

The `QueryController` provides a generic RESTful interface for all search operations across the platform.

### Standard Endpoint:
`POST /q/{queryName}`
- **`queryName`**: The unique identifier for the query (mapped to MyBatis).
- **Consumes**: `SearchRequest<Map<String, Object>>`
- **Produces**: `GenericResponse<SearchResponse>`

---

## 📦 2. The Universal Models

Chenile standardizes the request and response structure to ensure a consistent experience for front-end developers.

### `SearchRequest<T>`
- **Pagination**: `pageNum`, `numRowsInPage`.
- **Sorting**: `sortCriteria` (List of field + direction).
- **Filtering**: 
    - `filters`: Business-specific criteria.
    - `systemFilters`: Automatically populated metadata (Tenant, User).
- **Metadata**: `queryName`, `fields` (to return).

### `SearchResponse`
- **Pagination Context**: `currentPage`, `maxPages`, `numRowsReturned`.
- **Data**: List of `ResponseRow` objects.
- **`ResponseRow`**: Contains the actual record and **`allowedActions`** (HATEOAS links based on the record's current state).

---

## ⚙️ 3. Internal Orchestration: `AbstractSearchServiceImpl`

The `AbstractSearchServiceImpl` is the implementation engine that bridges high-level search requests to database execution.

### Key Logic:
- **Filter Enhancement**: It automatically transforms user filters into query-ready parameters:
    - **Like Queries**: Prepends/appends `%` based on `ColumnMetadata`.
    - **Between Queries**: Handles ranges for Dates and Numbers.
    - **Contains Queries**: Handles List/Collection-based filtering.
- **HATEOAS Injection**: For every row returned, it performs a **State Lookup**:
    1.  Identifies the `workflowName` from metadata.
    2.  Extracts the `currentState` from the row.
    3.  Invokes the STM's `STMActionsInfoProvider` to fetch "Allowed Actions."
    4.  Attaches these actions (e.g., `APPROVE`, `REJECT`) directly to the `ResponseRow`.
- **Dynamic Sorting**: Builds the `ORDER BY` clause using either field names or column indices.

---

## 📥 4. The "Work Inbox" Pattern: To-Do Lists

Chenile Query has built-in support for the "To-Do List" pattern, essential for enterprise workflows.

- **`isToDoList = true`**: When this flag is set in the `SearchRequest`, the service:
    1.  Determines which states are "allowed" for the current user's roles.
    2.  Automatically injects a `CONTAINS` filter on the `stateColumn` using these allowed states.
    3.  **Result**: The user only sees records they are currently authorized to act upon.

---

## 🛡️ 5. Governance & Security Interceptors

As a governed framework, Chenile automatically applies cross-cutting concerns:
- **`QuerySAASInterceptor`**: Enforces multi-tenancy by injecting `tenantId`, `appType`, and `userId` into the `systemFilters`.
- **`QueryUserFilterInterceptor`**: Restricts data visibility based on the organizational hierarchy (Manager vs. Reportee).

---

## ⚙️ 6. Metadata-Driven Config: `QueryDefinitions`

Queries are defined in **JSON files**, decoupling query behavior from code:
- **`acls`**: Define who can execute the query.
- **`workflowName`**: Links the query to an STM for HATEOAS.
- **`isPaginated`**: Toggles the automatic count query logic.

---

## 🎯 7. Handmade Implementation Rule
> [!IMPORTANT]
> **No Custom Search Logic**: Do not write manual filtering or pagination logic in your services. Configure it in `queries.json` and let Chenile handle the expansion.
> **Convention-Based MyBatis**: Name your count queries as `[id]-count` to leverage automatic pagination.

**This document defines the standardized retrieval layer for the Handmade platform.**
