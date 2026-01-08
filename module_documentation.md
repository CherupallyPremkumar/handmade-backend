# Handmade Platform: Liquibase Management Documentation

## Overview
The `handmade-liquibase-management` module is the centralized source of truth for all database schema definitions, migrations, and test data across the Handmade e-commerce platform. It ensures consistency, reproducibility, and auditability of the database state.

## Architecture & Organization

### 1. Directory Structure
The schema is organized by bounded contexts (domains) for modularity:

- `src/main/resources/db/changelog/`
    - `adtech/`: Advertising and sponsored product entities.
    - `analytics/`: Clickstream, tracking, and aggregation tables.
    - `catalog/`: Global product catalog and taxonomy.
    - `inventory/`: Global and seller-specific inventory management.
    - `offer/`: Seller-specific offers, pricing, and availability.
    - `platform/`: Core platform governance and identity.
    - `platform-policies/`: **[Centralized]** All 18 specialized platform policies (Onboarding, Promotion, Compliance, etc.).
    - `seller/`: Seller accounts, stores, and configuration.
    - `test-data/`: Comprehensive test data sets for all domains.

### 2. Entry Points
- **Master Changelog**: `db.changelog-master.yaml`
  - Used for production and staging environments.
  - Contains only schema definitions (tables, indexes, constraints).
- **Test Changelog**: `db.changelog-test.yaml`
  - Used for development and automated testing.
  - Includes the master changelog + all `test-data/` inserts.

## Engineering Standards (Amazon-Grade)

### 1. Table Prefixing
All tables must follow the `hm_` prefix standard to avoid namespace collisions and ensure clear platform ownership.
> **Correct**: `hm_platform`, `hm_seller_account`
> **Incorrect**: `platform_owner`, `seller_account`

### 2. Data Types & Syntax
To ensure cross-db compatibility (PostgreSQL, H2, etc.), follow these rules:
- **DECIMAL types**: Must be explicitly quoted to prevent Liquibase parsing errors in complex expressions.
  - `type: "DECIMAL(19, 4)"`
- **JSON**: Use `TEXT` or specific JSON types depending on the domain requirements (default to `TEXT` for portability).

### 3. Centralized Policy Governance
All platform policies are moved into `platform-policies/`. This ensures that governance logic is isolated from business aggregates like `Offer` or `Seller`.

## Testing & Verification

### 1. Automated Tests
The module includes a robust test suite in `src/test/java`:
- **LiquibaseValidationTest**: Verifies that `db.changelog-test.yaml` executes without syntax or foreign key errors against an H2 database.
- **LiquibasePostgreSQLTest**: Uses **Testcontainers** to spin up a real PostgreSQL instance and verify that the schema is correctly applied and data can be queried.

### 2. Running Verification
To verify the schema and run all tests:
```bash
mvn clean install -DskipTests=false
```

## Contribution Guidelines
1. **Never use `db.changelog-master.yaml` for data**: Only schema changes go here.
2. **Always update test data**: If you add a table, add matching test data in `test-data/`.
3. **Follow naming convention**: `0XX-description.yaml` (e.g., `011-policy-promotion.yaml`).
4. **No God Modules**: If a domain grows too large, split it into functional sub-folders.
