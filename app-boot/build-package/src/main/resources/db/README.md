# Database Migration Design

This project uses Liquibase for database schema management across multiple environments (DEV, SIT, UAT, PROD) on AWS RDS PostgreSQL.

## Folder Structure
```
src/main/resources/db/changelog/
├── db.changelog-master.yaml      # Master entry point
├── changes/                      # Individual changesets
│   ├── 001-init.yaml             # Extensions & global setup
│   ├── 010-hm-users.yaml         # User management tables
│   ├── 020-hm-orders.yaml        # Order management tables
│   └── ...
```

## Naming Conventions
- **Tables**: Must start with `hm_` (e.g., `hm_users`, `hm_orders`).
- **Changelogs**: Numbered prefixes for ordering (`010-...`, `020-...`).
- **IDs**: Descriptive and unique (e.g., `010-create-hm-users-table`).

## Multi-Tenancy / Schemas
This setup supports Schema-Based Multi-Tenancy (or environment separation) within a single database instance.
- **NO hardcoded schemas** in changelog files.
- Schema is determined at runtime via `spring.liquibase.default-schema` property.
- Configurations provided for:
  - `dev` -> `homebase_dev`
  - `sit` -> `homebase_sit`
  - `uat` -> `homebase_uat`
  - `prod` -> `homebase_prod`

## Best Practices
1. **Preconditions**: All `createTable` changesets include `preConditions` checking for table existence.
   - `onFail: MARK_RAN` allows smooth operation if objects exist (e.g., during initial synchronization).
2. **Immutability**: Never modify a changeset after it has been deployed (except `runOnChange: true` for views/procedures).
3. **Rollback**: Design changesets to be reversible where possible (default `createTable` has implicit rollback).
4. **Production Safety**: Hibernate DDL is disabled (`ddl-auto: none`). All schema changes must go through Liquibase.

## Running Migrations
The migration runs automatically on application startup.
Ensure the database user configured in `application-{profile}.yaml` has DDL privileges on the target schema.
