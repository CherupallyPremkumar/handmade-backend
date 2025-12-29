# Platform Clock Abstraction

## Overview
Centralized time abstraction for all bounded contexts in the e-commerce platform.

## Design Principles

### 1. **Testability**
- No direct calls to `Instant.now()` or `LocalDate.now()` in business logic
- Inject `Clock` interface, not implementation
- Use `FixedClock` in tests for deterministic behavior

### 2. **UTC-Only**
- All time operations use UTC timezone
- Prevents timezone-related bugs in distributed systems
- Consistent time representation across all modules

### 3. **Domain-Neutral**
- Lives in `platform-core`, not domain modules
- Shared infrastructure component
- No business logic coupling

## Components

### Clock Interface
```java
public interface Clock {
    Instant instant();      // Current UTC instant
    LocalDate today();      // Current UTC date
    long millis();          // Epoch milliseconds
}
```

### SystemClock (Production)
```java
Clock clock = SystemClock.getInstance();
// or inject via Spring:
@Autowired Clock clock;
```

### FixedClock (Testing)
```java
Clock clock = FixedClock.at(Instant.parse("2025-01-15T10:00:00Z"));
Clock clock = FixedClock.at(LocalDate.of(2025, 1, 15));
```

## Usage Examples

### Production Code
```java
@Service
public class OnboardingService {
    
    private final Clock clock;
    
    public OnboardingService(Clock clock) {
        this.clock = clock;
    }
    
    public void createCase(CreateCaseRequest request) {
        SellerOnboardingCase case = new SellerOnboardingCase();
        case.setCreatedAt(clock.instant());  // ✅ Testable
        case.setExpiresAt(clock.today().plusDays(30));
    }
}
```

### Test Code
```java
@Test
void testCaseExpiration() {
    Clock clock = FixedClock.at(LocalDate.of(2025, 1, 15));
    OnboardingService service = new OnboardingService(clock);
    
    // Time is now deterministic
    assertThat(service.createCase(...).getExpiresAt())
        .isEqualTo(LocalDate.of(2025, 2, 14));
}
```

## Why This Abstraction Exists

### Problem Without Abstraction
```java
// ❌ Hard to test
case.setCreatedAt(Instant.now());  // Non-deterministic
case.setExpiresAt(LocalDate.now().plusDays(30));  // Flaky tests
```

### Solution With Abstraction
```java
// ✅ Easy to test
case.setCreatedAt(clock.instant());  // Deterministic in tests
case.setExpiresAt(clock.today().plusDays(30));  // Reliable
```

## Amazon-Style Principle
> "Time is infrastructure, not business logic. Abstract it."

At Amazon scale:
- **Consistency**: All services use the same time source
- **Testability**: Time-dependent logic is fully testable
- **Auditability**: Clock usage is explicit and traceable
- **Reliability**: No timezone bugs in production

## Integration

### Spring Boot
```java
@Configuration
public class ClockConfiguration {
    @Bean
    public Clock platformClock() {
        return SystemClock.getInstance();
    }
}
```

### Test Configuration
```java
@TestConfiguration
public class TestClockConfiguration {
    @Bean
    @Primary
    public Clock platformClock() {
        return FixedClock.at(LocalDate.of(2025, 1, 15));
    }
}
```

## Best Practices

1. **Always inject Clock, never use static time**
   ```java
   ✅ private final Clock clock;
   ❌ Instant.now()
   ```

2. **Use UTC everywhere**
   ```java
   ✅ clock.today()  // Always UTC
   ❌ LocalDate.now()  // System timezone
   ```

3. **Inject via constructor**
   ```java
   ✅ public Service(Clock clock) { ... }
   ❌ @Autowired Clock clock;  // Field injection
   ```

4. **Override in tests**
   ```java
   ✅ Clock clock = FixedClock.at(...);
   ❌ Clock clock = SystemClock.getInstance();
   ```

## Module Dependencies

```
platform-core (defines Clock)
    ↓
onboarding-management (uses Clock)
seller-management (uses Clock)
workflow-orchestrator (uses Clock)
```

All modules depend on `platform-core` for the Clock abstraction.
