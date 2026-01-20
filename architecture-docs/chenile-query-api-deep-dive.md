# Chenile Query API: The Retrieval Contract (Official)

This document describes the **Chenile Query API**, which defines the standardized contract for data retrieval, filtering, and reporting across the framework.

---

## 🏗️ 1. The Blueprint: `QueryMetadata`

Every query in Chenile is described by `QueryMetadata`. This is the "blueprint" that the generic search services use to execute queries and the UI uses to render them.

### Key Attributes:
- **`workflowName`**: Links the query results to a State Machine, enabling dynamic action buttons (HATEOAS).
- **`stateColumn` / `flowColumn`**: Instructs the service where to find the entity's state for HATEOAS lookups.
- **SLA Tracking**: `lateColumn` and `tendingLateColumn` identify fields used to flag delayed processes.
- **Security**: `acls` define which user roles/groups are authorized to run the query.

---

## 📊 2. Column Specification: `ColumnMetadata`

`ColumnMetadata` provides deep introspection into each field in the result set.

### Features:
- **UI Hints**: `columnType` (Date, Number, DropDown, etc.) and `group` (for column grouping).
- **Filtering Logic**: Flags like `likeQuery`, `betweenQuery` (Date range), and `containsQuery` (List filtering) drive the automatic expansion of MyBatis parameters.
- **Reference Data**: `dropDownQuery` specifies a sub-query ID used to fetch values for search filter dropdowns.

---

## 🔄 3. Search Contract: `SearchRequest` & `SearchResponse`

The API enforces a consistent request/response structure for all retrieval operations.

### `SearchRequest`:
- **Pagination**: `pageNum` and `numRowsInPage`.
- **Filtering**: `filters` (user-provided) vs. `systemFilters` (injected by security/TENANT interceptors).
- **Work Inbox**: `isToDoList` toggles automatic filtering based on user-allowable states.

### `SearchResponse`:
- **`ResponseRow`**: Wraps the data and attaches `allowedActions` (HATEOAS).
- **Metadata Leakage**: Returns the `columnMetadata` back to the UI, allowing the frontend to be completely table-driven.

---

## 💾 4. Reporting: Canned Reports

The API defines a system for "Saving Searches."
- **`CannedReport`**: Stores a named `SearchRequest` against a `userId`.
- **`isApplicableToAll`**: Allows administrators to share global reports across the organization.

---

## 🎯 5. Handmade Implementation Rule
> [!IMPORTANT]
> **Metadata Driven UI**: Your frontend should read the `columnMetadata` from the search response to build dynamic tables and filters. Do not hardcode table columns.
> **Secure the Metadata**: Ensure that query IDs in `queries.json` have restricted `acls` to prevent unauthorized data discovery.
> **HATEOAS Alignment**: Ensure your `stateColumn` in metadata matches the physical column in your MyBatis result map.

**This document defines the retrieval contract for the Handmade platform.**
