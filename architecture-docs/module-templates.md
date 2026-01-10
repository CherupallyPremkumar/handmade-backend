# Chenile Module Templates (Authoritative)

## Quick Reference
Replace placeholders:
- `{ENTITY}` → Entity name (e.g., Order)
- `{entity}` → camelCase (e.g., order)
- `{DOMAIN}` → Package (e.g., order)
- `{FLOW_ID}` → Workflow ID (e.g., ORDER_FLOW)

---

## 1. Domain Layer (`-domain`)

```java
package com.handmade.ecommerce.{DOMAIN}.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Table(name = "hm_{entity}")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class {ENTITY} extends AbstractJpaStateEntity {
    // Business fields only
}
```

---

## 2. API Layer (`-api`)

#### Service Interface
```java
package com.handmade.ecommerce.{DOMAIN}.service;

import com.handmade.ecommerce.{DOMAIN}.dto.*;

public interface {ENTITY}Service {
    void {event}(String id, {EVENT}Payload payload);
    {ENTITY}Response getById(String id);
}
```

---

## 3. Service Layer (`-service`)

#### Implementation (Governed)
```java
package com.handmade.ecommerce.{DOMAIN}.service.impl;

import org.chenile.core.annotation.*;
import org.chenile.stm.STM;
import org.springframework.transaction.annotation.Transactional;

@ChenileService
public class {ENTITY}ServiceImpl implements {ENTITY}Service {

    @Autowired
    private STM<{ENTITY}> {entity}Flow;

    @Override
    @Transactional
    @Operation("{event}")
    public void {event}(@Parameter String id, @Parameter {EVENT}Payload payload) {
        // ✅ CORRECT: Load and mutate via STM.proceed
        {entity}Flow.proceed(id, "{event}", payload);
    }
}
```

#### STM Transition Action
```java
@Component("{entity}{EVENT}Action")
public class {EVENT}Action implements STMTransitionAction<{ENTITY}> {

    @Override
    public void doTransition({ENTITY} entity, Object payload, 
                             State startState, String eventId, State endState,
                             STMInternalTransitionInvoker<?> stm, Transition transition) {
        // ✅ Reactive side effects only
    }
}
```

---

## 4. Delegator Layer (`-delegator`)

```java
package com.handmade.ecommerce.{DOMAIN}.controller;

import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/{entity}")
@ChenileController("{DOMAIN}.{entity}Service")
public class {ENTITY}Controller extends ControllerSupport {

    @PostMapping("/{id}/{event}")
    public ResponseEntity<Void> trigger(
            @PathVariable String id, 
            @RequestBody {EVENT}Payload payload, 
            HttpServletRequest request) {
        
        // ✅ Canonical Chenile delegation
        // Automates Exchange creation, Audit, and Governance
        return process("{event}", request, id, payload);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<{ENTITY}Response> getById(
            @PathVariable String id, 
            HttpServletRequest request) {
        return process("getById", request, id);
    }
}
```
