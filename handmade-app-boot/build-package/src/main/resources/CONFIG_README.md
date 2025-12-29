# Spring Boot Configuration Guide

## Overview

This application uses Spring profiles to support multiple environments:
- **dev** - H2 in-memory database (quick development)
- **local** - PostgreSQL database (production-like local setup)
- **prod** - Production environment (configured via environment variables)

## How to Run

### Development Mode (H2 Database)
```bash
# Run with dev profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Or set environment variable
export SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run
```

### Local Mode (PostgreSQL)
```bash
# Set database password
export DB_PASSWORD=your_postgres_password

# Run with local profile
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Production Mode
```bash
# Set all required environment variables
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:postgresql://prod-db:5432/handmade
export DB_USERNAME=prod_user
export DB_PASSWORD=secure_password
export JPA_DDL_AUTO=validate
export KEYCLOAK_HOST=https://auth.production.com
# ... etc

mvn spring-boot:run
```

## Configuration Files

### application.yaml
Base configuration with environment variable placeholders (`${VAR_NAME}`).
All values must be provided by either:
1. Profile-specific files (application-dev.yaml, application-local.yaml)
2. Environment variables
3. System properties

### application-dev.yaml
Development profile using H2 in-memory database.
- No external database required
- H2 console available at: http://localhost:8080/api/h2-console
- Auto-creates schema on startup
- Verbose logging enabled

### application-local.yaml
Local profile using PostgreSQL database.
- Requires PostgreSQL running on localhost:5433
- Schema: `homebase_dev`
- Requires `DB_PASSWORD` environment variable
- Uses connection pooling (HikariCP)

## Environment Variables

### Required for All Profiles
- `APP_NAME` - Application name (default: handmade-ecommerce-platform)
- `SERVER_PORT` - Server port (default: 8080)

### Database (Required for prod, optional for dev/local)
- `DB_URL` - JDBC connection URL
- `DB_DRIVER` - JDBC driver class
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password

### JPA/Hibernate
- `JPA_DATABASE_PLATFORM` - Hibernate dialect
- `JPA_DDL_AUTO` - Schema generation strategy (validate/update/create)
- `HIBERNATE_DEFAULT_SCHEMA` - Default database schema

### Keycloak Security
- `KEYCLOAK_HOST` - Keycloak server URL
- `KEYCLOAK_REALM` - Keycloak realm name
- `KEYCLOAK_CLIENT_ID` - OAuth client ID
- `KEYCLOAK_CLIENT_SECRET` - OAuth client secret

## Examples

### IntelliJ IDEA
1. Edit Run Configuration
2. Add VM options: `-Dspring.profiles.active=dev`
3. Or set Environment variable: `SPRING_PROFILES_ACTIVE=dev`

### Docker
```dockerfile
ENV SPRING_PROFILES_ACTIVE=prod
ENV DB_URL=jdbc:postgresql://db:5432/handmade
ENV DB_PASSWORD=secret
```

### Kubernetes
```yaml
env:
  - name: SPRING_PROFILES_ACTIVE
    value: "prod"
  - name: DB_PASSWORD
    valueFrom:
      secretKeyRef:
        name: db-secret
        key: password
```

## Profile Hierarchy

Spring Boot loads configurations in this order:
1. `application.yaml` (base)
2. `application-{profile}.yaml` (profile-specific)
3. Environment variables (highest priority)

Profile-specific values override base values.
Environment variables override everything.
