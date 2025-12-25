# Platform Application Layer - Design Rationale

## Why Application Layer Owns Orchestration

### 1. **Separation of Concerns**
- **Domain Layer**: Owns business rules and invariants
  - Example: `PlatformOwner.activate()` validates lifecycle transitions
  - Example: `PlatformOwner.reviseCommissionStructure()` enforces commission rules
  
- **Application Layer**: Owns workflow orchestration
  - Load aggregates from repository
  - Call domain methods in correct order
  - Persist changes
  - Publish events
  - Manage transactions

### 2. **Transaction Boundaries**
- Application services define transaction scope with `@Transactional`
- Domain layer remains transaction-agnostic
- Enables testing domain logic without database

### 3. **Authorization**
- Application layer enforces "who can do what"
- Domain layer enforces "what can be done"
- Clear separation: authentication/authorization vs business rules

### 4. **Event Publishing**
- Application layer decides when to publish events (after persistence)
- Domain layer generates events (as part of state changes)
- Infrastructure layer implements event bus

---

## Why Domain is Persistence-Agnostic

### 1. **Testability**
```java
// Domain can be tested without database
PlatformOwner platform = PlatformOwner.bootstrap(...);
platform.activate("admin-123");
assertTrue(platform.isActive());
```

### 2. **Technology Independence**
- Domain doesn't depend on JPA, Hibernate, or any ORM
- Can switch from JPA to MongoDB without changing domain
- Repository interface is in domain, implementation in infrastructure

### 3. **Focus on Business Logic**
- Domain developers think in business terms, not database terms
- No mixing of `@Entity`, `@Column` with business rules
- Pure Java objects (POJOs) with rich behavior

### 4. **Hexagonal Architecture**
```
[Domain] ← [Application] → [Infrastructure]
   ↑            ↑                ↓
   |            |          [JPA, REST, Kafka]
   |            └─── Orchestration
   └─── Business Rules
```

---

## Application Layer Pattern

Each use case follows this pattern:

1. **Authorize**: Check operator permissions
2. **Load**: Retrieve aggregate from repository
3. **Execute**: Call domain method (business logic)
4. **Persist**: Save aggregate via repository
5. **Publish**: Emit domain events to event bus

This ensures:
- Consistent transaction handling
- Proper authorization
- Event-driven architecture
- Clean separation of concerns
