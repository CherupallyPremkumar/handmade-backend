# Platform Management - Clean Architecture Implementation

## Overview

This document describes the complete implementation of the Platform Management bounded context following **Clean Architecture** and **Domain-Driven Design (DDD)** principles.

---

## Architecture Layers

```
┌─────────────────────────────────────────────────────────────┐
│                      platform-api                            │
│                    (REST Controllers)                        │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                  platform-application                        │
│         (Use Cases, Commands, Orchestration)                 │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                    platform-domain                           │
│        (Pure Business Logic - NO Framework Dependencies)     │
└─────────────────────────────────────────────────────────────┘
                              ↑
┌─────────────────────────────────────────────────────────────┐
│                platform-infrastructure                       │
│    (JPA, Persistence, Events, Security, Database)            │
└─────────────────────────────────────────────────────────────┘
```

---

## 1. Domain Layer (`platform-domain`)

### ✅ Pure Domain Model (Zero Framework Dependencies)

**Aggregate Root:**
- `PlatformOwner` - Singleton aggregate representing the marketplace platform

**Entities:**
- `CommissionPolicy` - Platform revenue model
- `PlatformAuditLog` - Audit trail
- `PlatformActivityLog` - Activity logging
- `PlatformFeatureConfig` - Feature flags
- `FeatureConfiguration` - Feature snapshot

**Value Objects:**
- `BrandIdentity` - Visual identity (logo, colors, email)
- `CorporateIdentity` - Legal identity (tax ID, registration)
- `ComplianceMandate` - Regulatory requirements
- `OperationalLimits` - Safety limits (max transaction, rate limits)
- `LocalizationPolicy` - Multi-region support (currencies, languages)
- `PlatformLifecycle` - Lifecycle states (PROVISIONING, LIVE, RESTRICTED, SUNSET)
- `PlatformStatus` - Status enum

**Domain Events:**
- `PlatformStatusChanged` - Lifecycle transitions
- `CommissionStructureRevised` - Revenue model changes
- `LegalTermsPublished` - Terms & conditions updates
- `PlatformLockdownInitiated` - Emergency restrictions

**Repository Interface:**
- `PlatformOwnerRepository` - Port for persistence (interface only)

### Key Domain Behaviors

```java
// Singleton pattern with bootstrap factory
PlatformOwner platform = PlatformOwner.bootstrap(name, brand, corporate, ...);

// Business operations
platform.activate(operatorId);
platform.reviseCommissionStructure(newPolicy, operatorId);
platform.imposeSanctions(reason);
platform.rebrand(newBrand);
platform.publishLegalTerms(termsUrl, privacyUrl);
```

---

## 2. Application Layer (`platform-application`)

### Commands (DTOs)
- `ActivatePlatformCommand`
- `ReviseCommissionCommand`
- `LockPlatformCommand`
- `UpdateBrandIdentityCommand`
- `PublishLegalTermsCommand`

### Ports

**Input Port:**
- `PlatformGovernanceUseCase` - Contract exposed by application layer

**Output Ports:**
- `DomainEventPublisher` - Event publishing abstraction
- `AuthorizationService` - Permission checking abstraction

### Service

**`PlatformGovernanceService`** - Implements all use cases

**Orchestration Pattern:**
```java
1. Authorize → Check operator permissions
2. Load      → Get aggregate from repository
3. Execute   → Call domain method (business logic)
4. Persist   → Save aggregate
5. Publish   → Emit domain events
```

**Example:**
```java
@Override
public void activatePlatform(ActivatePlatformCommand command) {
    // 1. Authorization
    checkPlatformAdminPermission(command.getOperatorId(), "ACTIVATE_PLATFORM");
    
    // 2. Load aggregate
    PlatformOwner platform = repository.get()
        .orElseThrow(() -> new IllegalStateException("Platform not bootstrapped"));
    
    // 3. Execute domain logic
    platform.activate(command.getOperatorId());
    
    // 4. Persist changes
    repository.save(platform);
    
    // 5. Publish domain events
    publishDomainEvents(platform);
}
```

### Exception
- `PlatformAccessDeniedException` - Authorization failures

---

## 3. Infrastructure Layer (`platform-infrastructure`)

### Persistence

**JPA Entities:**
- `PlatformOwnerJpaEntity` - Persistence model for PlatformOwner
- `CommissionPolicyJpaEntity` - Persistence model for CommissionPolicy

**Embeddables:**
- `BrandIdentityEmbeddable` - Embedded brand data
- `CorporateIdentityEmbeddable` - Embedded corporate data

**Mappers:**
- `PlatformOwnerMapper` - Bidirectional Domain ↔ JPA conversion

**Spring Data Repositories:**
- `PlatformOwnerJpaRepository extends JpaRepository`

**Repository Adapters:**
- `JpaPlatformOwnerRepository implements PlatformOwnerRepository`

### Event Publishing

**`SpringDomainEventPublisher`**
- Implements `DomainEventPublisher` port
- Uses Spring `ApplicationEventPublisher`
- Publishes events after transaction commit

### Security

**`SpringAuthorizationService`**
- Implements `AuthorizationService` port
- Stub implementation (returns true for development)
- Integration point for Spring Security/IAM

### Database Schema (Liquibase)

**Migrations:**
- `db.changelog-master.yaml` - Master changelog
- `001-create-platform-owner-table.yaml` - Platform owner schema
- `002-create-commission-policy-table.yaml` - Commission policy schema

**Tables:**
```sql
platform_owner:
  - id (PK)
  - name, lifecycle_state
  - brand_* (logo, favicon, color, email)
  - corporate_* (legal_name, tax_id, registration, jurisdiction)
  - compliance_* (requires_kyc, requires_tax_id, allowed_jurisdictions)
  - operational_* (max_transaction, max_sellers, rate_limit)
  - localization_* (currencies, languages, timezone)
  - current_version, current_state_id, flow_id
  - active_commission_policy_id, active_feature_config_id

platform_commission_policy:
  - id (PK)
  - platform_id (FK)
  - policy_version
  - effective_from, effective_to
  - global_take_rate, flat_transaction_fee, flat_fee_currency
  - change_reason, trace_id
```

### Configuration

**`JpaConfig`**
- Entity scanning: `com.handmade.ecommerce.platform.infrastructure.persistence.entity`
- Repository scanning: `com.handmade.ecommerce.platform.infrastructure.persistence.jpa`

---

## Key Design Principles

### 1. Dependency Rule
```
Domain ← Application → Infrastructure
  ↑                        ↓
  └────── Ports ──────────┘
```

- **Domain** has ZERO dependencies on frameworks
- **Application** depends on Domain (via interfaces)
- **Infrastructure** depends on Domain & Application (implements ports)

### 2. Separation of Concerns

| Layer | Responsibility | Contains |
|-------|---------------|----------|
| **Domain** | Business rules | Aggregates, Entities, Value Objects, Events |
| **Application** | Orchestration | Use cases, Commands, Transaction management |
| **Infrastructure** | Technical details | JPA, Database, Events, Security |

### 3. Persistence Agnostic Domain

**Domain:**
```java
public class PlatformOwner implements Serializable {
    private String id;
    private String name;
    // Pure POJOs - NO @Entity, @Column, etc.
}
```

**Infrastructure:**
```java
@Entity
@Table(name = "platform_owner")
public class PlatformOwnerJpaEntity extends AbstractJpaStateEntity {
    @Column(name = "name")
    private String name;
    // JPA annotations ONLY in infrastructure
}
```

### 4. Mapper Pattern

```java
// Domain → JPA
PlatformOwnerJpaEntity entity = PlatformOwnerMapper.toEntity(domain);

// JPA → Domain
PlatformOwner domain = PlatformOwnerMapper.toDomain(entity);
```

---

## Benefits of This Architecture

### ✅ Testability
```java
// Test domain without database
PlatformOwner platform = PlatformOwner.bootstrap(...);
platform.activate("admin-123");
assertTrue(platform.isActive());
```

### ✅ Technology Independence
- Switch from JPA to MongoDB without changing domain
- Change event bus (Kafka → RabbitMQ) without changing application layer

### ✅ Clear Boundaries
- **Domain**: "What can be done" (business rules)
- **Application**: "Who can do what" (authorization + orchestration)
- **Infrastructure**: "How it's done" (technical implementation)

### ✅ Maintainability
- Each layer has single responsibility
- Changes isolated to appropriate layer
- Easy to understand and modify

---

## Usage Example

```java
// 1. Application layer receives command
ActivatePlatformCommand command = new ActivatePlatformCommand(
    "admin-123", 
    "Initial platform launch"
);

// 2. Use case orchestrates
platformGovernanceService.activatePlatform(command);

// 3. Behind the scenes:
//    - Authorization check
//    - Load PlatformOwner from database (via mapper)
//    - Call domain method: platform.activate()
//    - Save to database (via mapper)
//    - Publish PlatformStatusChanged event
```

---

## Next Steps

1. **Implement REST API** (`platform-api` module)
2. **Add Integration Tests** (test full flow with real database)
3. **Implement Event Consumers** (react to domain events)
4. **Add Monitoring** (metrics, logging, tracing)
5. **Production Hardening** (error handling, retries, circuit breakers)
