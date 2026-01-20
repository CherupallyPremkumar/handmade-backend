# Authoritative Chenile Class Mapping (6-Layer MMA)

This document defines the strict, source-code-accurate mapping of Chenile Framework core classes to the **Handmade 6-Layer Architecture**. 

---

## 🏗️ Exact Mapping: Chenile Classes → 6 Layers

### 1. `domain` Layer (Pure Data Model)
- ✅ **Allowed**:
    - `AbstractJpaStateEntity`: Base for stateful JPA entities.
    - `StateEntity`: Marker interface for lifecycle management.
- ❌ **Forbidden**: `STM`, `STMEngine`, `@ChenileService`, `OWIZ`, `@Operation`, or any XML logic.

---

### 2. `api` Layer (Contract Layer)
- ✅ **Allowed**:
    - `GenericResponse`: Standard response wrapper.
    - Custom DTOs and Service Interfaces.
- ❌ **Forbidden**: Chenile annotations (`@ChenileService`, `@Operation`), STM calls, or persistence logic.

---

### 3. `service` Layer (Chenile Runtime Zone)

#### Service Implementation
```java
@ChenileService
public class OrderServiceImpl implements OrderService {

    @Autowired
    private STM<Order> orderFlow; // Named as {entity}Flow by convention

    @Override
    @Transactional
    @Operation("shipOrder")
    public void shipOrder(@Parameter String orderId, @Parameter OrderPayload payload) {
        // ✅ CORRECT: Load and mutate via STM.proceed()
        // The service layer orchestrates by calling the state machine.
        orderFlow.proceed(orderId, "SHIP", payload);
    }
}
```

#### STM Transition Action
```java
@Component("orderShippedAction")
public class OrderShippedAction implements STMTransitionAction<Order> {

    @Override
    public void doTransition(Order order, Object payload, State startState, 
                            String eventId, State endState, 
                            STMInternalTransitionInvoker<?> stm, Transition transition) {
        // ✅ CORRECT: Side effects ONLY
        // Entity is already validated and loaded.
        publishShipmentEvent(order);
    }
}
```

- ✅ **Mandatory Chenile Classes**:
    - `@ChenileService`: Enables governance and OWIZ chain.
    - `@Operation`: Declares framework metadata and security.
    - `@Parameter`: Declares parameters for the exchange.
    - `STM<T>`: The actual interface for state mutations.
    - `STMTransitionAction<T>`: The hook for business logic during transitions.
    - `ChenileExchange`: Full request context access (if needed).

---

### 4. `infrastructure` Layer (Chenile-Blind)
- ✅ **Allowed**: Standard JpaRepositories, HTTP clients.
- ❌ **Forbidden**: Chenile framework imports.

---

### 5. `delegator` Layer (HTTP Adapter)
- ✅ **Responsibility**: Map HTTP verbs to Service methods via Chenile governance.
- ✅ **Implementation**: Extend `ControllerSupport` and use `@ChenileController`.
- ✅ **Calling**: Use `process(request, args)` to delegate to the Chenile OWIZ chain.
- ❌ **Forbidden**: Manual `ChenileExchange` creation or bypassing the service layer.

---

## 🔄 Core STM Logic Truths

| Concept | Chenile Source Code Reality |
| :--- | :--- |
| **State Mutation** | `STM.proceed(entityId, eventId, payload)` is the entry point. |
| **Governance** | `@ChenileService` and `@ChenileController` are mandatory for policy/audit. |
| **HTTP Entry** | `ControllerSupport.process()` is the canonical REST bridge. |
| **Purity** | `AbstractJpaStateEntity` is strictly for ORM mapping of state. |
| **Reactivity** | `STMTransitionAction.doTransition()` is the standard signature. |

---

## 🛠️ Summary for Principal Engineers
We **host** Chenile within a 6-layer modular monolith.
1. The **Domain** stays pristine.
2. The **Infrastructure** stays swappable.
3. The **Service** layer is where Chenile governs business intent via the `STM` interface.

**This is the officially corrected reference architecture for Handmade.**
