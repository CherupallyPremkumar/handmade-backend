# Scalability-Focused Domain Models & Best Practices

## 🎯 Overview

Beyond basic domain models, these **scalability-specific patterns and models** are essential for high-performance e-commerce platforms.

---

## 1. 🔄 Event Sourcing Models

**Purpose**: Store all changes as events instead of current state - enables time travel, audit trails, and scalability

### **Event Store Aggregate**
```java
@Entity
public class DomainEventStore {
    @Id
    private String eventId;
    
    private String aggregateId;        // Which entity (Order-123)
    private String aggregateType;      // Which type (Order, Payment)
    private String eventType;          // What happened (OrderCreated)
    private String eventData;          // JSON payload
    private Long version;              // Event sequence number
    private Instant occurredAt;
    private String userId;             // Who triggered it
    
    // Metadata for scalability
    private String partitionKey;       // For sharding
    private String streamId;           // For event streaming
}
```

### **Event Stream**
```java
public class EventStream {
    private String streamId;
    private String aggregateId;
    private List<DomainEvent> events;
    private Long currentVersion;
    
    // Rebuild aggregate from events
    public Order rebuildOrder() {
        Order order = new Order();
        for (DomainEvent event : events) {
            order.apply(event);
        }
        return order;
    }
}
```

**Benefits**:
- ✅ Complete audit trail
- ✅ Time travel (rebuild state at any point)
- ✅ Event replay for debugging
- ✅ CQRS enablement

---

## 2. 📖 CQRS (Command Query Responsibility Segregation)

**Purpose**: Separate read and write models for scalability

### **Write Model (Command Side)**
```java
// Optimized for writes
@Entity
@Table(name = "orders_write")
public class OrderWriteModel {
    @Id
    private String orderId;
    private String customerId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    // Normalized structure
}
```

### **Read Model (Query Side)**
```java
// Optimized for reads - denormalized
@Entity
@Table(name = "orders_read")
public class OrderReadModel {
    @Id
    private String orderId;
    
    // Denormalized for fast reads
    private String customerName;
    private String customerEmail;
    private String shippingAddress;  // Flattened
    private String productNames;     // Comma-separated
    private Integer itemCount;
    private BigDecimal totalAmount;
    private String statusDisplay;
    
    // Pre-calculated for dashboards
    private BigDecimal commissionAmount;
    private String sellerName;
    
    @Indexed
    private Instant createdAt;
}
```

**Benefits**:
- ✅ Read queries don't impact writes
- ✅ Denormalized reads = faster queries
- ✅ Can scale read/write databases independently
- ✅ Optimize each side separately

---

## 3. 🗄️ Materialized Views

**Purpose**: Pre-computed aggregations for instant queries

### **Product Search View**
```java
@Entity
@Table(name = "product_search_view")
public class ProductSearchView {
    @Id
    private String productId;
    
    // Denormalized for search
    private String name;
    private String description;
    private String categoryPath;      // "Home > Decor > Pottery"
    private String sellerName;
    private BigDecimal price;
    private Double averageRating;
    private Integer reviewCount;
    private Integer salesCount;
    private Boolean inStock;
    
    // Pre-calculated for facets
    private String priceRange;        // "$10-$25"
    private String ratingBucket;      // "4+ stars"
    
    // Full-text search
    @FullTextField
    private String searchableText;    // name + description + tags
    
    @Indexed
    private Instant lastUpdated;
}
```

### **Seller Dashboard View**
```java
@Entity
@Table(name = "seller_dashboard_view")
public class SellerDashboardView {
    @Id
    private String sellerId;
    
    // Pre-aggregated metrics
    private Integer totalOrders;
    private Integer pendingOrders;
    private BigDecimal totalRevenue;
    private BigDecimal monthlyRevenue;
    private BigDecimal averageOrderValue;
    private Integer totalProducts;
    private Integer activeProducts;
    private Double averageRating;
    private Integer totalReviews;
    
    // Updated periodically (not real-time)
    private Instant lastCalculatedAt;
}
```

**Benefits**:
- ✅ Instant dashboard queries
- ✅ No complex joins at query time
- ✅ Reduced database load

---

## 4. 🔢 Snapshot Pattern

**Purpose**: Avoid replaying thousands of events

### **Aggregate Snapshot**
```java
@Entity
public class OrderSnapshot {
    @Id
    private String snapshotId;
    
    private String orderId;
    private Long version;              // Event version at snapshot
    private String snapshotData;       // Serialized order state
    private Instant createdAt;
    
    // Rebuild order from snapshot + recent events
    public Order rebuild(List<DomainEvent> eventsAfterSnapshot) {
        Order order = deserialize(snapshotData);
        for (DomainEvent event : eventsAfterSnapshot) {
            order.apply(event);
        }
        return order;
    }
}
```

**Strategy**: Create snapshot every N events (e.g., every 100 events)

**Benefits**:
- ✅ Fast aggregate reconstruction
- ✅ Don't replay 10,000 events
- ✅ Scalable event sourcing

---

## 5. 🎯 Saga Pattern (Distributed Transactions)

**Purpose**: Manage long-running transactions across services

### **Order Fulfillment Saga**
```java
@Entity
public class OrderFulfillmentSaga {
    @Id
    private String sagaId;
    
    private String orderId;
    private SagaStatus status;         // STARTED, COMPLETED, COMPENSATING, FAILED
    private SagaStep currentStep;      // RESERVE_INVENTORY, PROCESS_PAYMENT, CREATE_SHIPMENT
    
    // Saga state
    private String inventoryReservationId;
    private String paymentTransactionId;
    private String shipmentId;
    
    // Compensation data (for rollback)
    private Map<String, Object> compensationData;
    
    private Instant startedAt;
    private Instant completedAt;
}
```

### **Saga Step**
```java
public enum SagaStep {
    RESERVE_INVENTORY(
        "ReserveInventoryCommand",
        "ReleaseInventoryCommand"  // Compensation
    ),
    PROCESS_PAYMENT(
        "ProcessPaymentCommand",
        "RefundPaymentCommand"     // Compensation
    ),
    CREATE_SHIPMENT(
        "CreateShipmentCommand",
        "CancelShipmentCommand"    // Compensation
    );
    
    private String command;
    private String compensationCommand;
}
```

**Benefits**:
- ✅ Handle distributed transactions
- ✅ Automatic rollback on failure
- ✅ Eventual consistency

---

## 6. 📦 Outbox Pattern

**Purpose**: Reliable event publishing (no lost events)

### **Outbox Table**
```java
@Entity
@Table(name = "outbox_events")
public class OutboxEvent {
    @Id
    private String eventId;
    
    private String aggregateId;
    private String eventType;
    private String eventPayload;       // JSON
    
    // Publishing state
    private OutboxStatus status;       // PENDING, PUBLISHED, FAILED
    private Integer retryCount;
    private Instant createdAt;
    private Instant publishedAt;
    
    // For ordering
    @Indexed
    private Long sequenceNumber;
}
```

**How it works**:
1. Save entity + outbox event in **same transaction**
2. Background job publishes events from outbox
3. Mark as published after successful publish

**Benefits**:
- ✅ No lost events (transactional)
- ✅ At-least-once delivery
- ✅ Event ordering guaranteed

---

## 7. 🔐 Idempotency Key Pattern

**Purpose**: Handle duplicate requests safely

### **Idempotency Record**
```java
@Entity
@Table(name = "idempotency_keys")
public class IdempotencyKey {
    @Id
    private String idempotencyKey;     // Client-provided UUID
    
    private String resourceType;       // "Order", "Payment"
    private String resourceId;         // Created resource ID
    private String requestHash;        // Hash of request body
    private String responseData;       // Cached response
    private Integer statusCode;
    
    private Instant createdAt;
    
    @Indexed
    private Instant expiresAt;         // Auto-delete after 24h
}
```

**Usage**:
```java
@PostMapping("/orders")
public ResponseEntity<Order> createOrder(
    @RequestHeader("Idempotency-Key") String idempotencyKey,
    @RequestBody OrderRequest request
) {
    // Check if already processed
    IdempotencyKey existing = repository.findById(idempotencyKey);
    if (existing != null) {
        return ResponseEntity.ok(deserialize(existing.getResponseData()));
    }
    
    // Process request
    Order order = orderService.createOrder(request);
    
    // Save idempotency record
    saveIdempotencyKey(idempotencyKey, order);
    
    return ResponseEntity.ok(order);
}
```

**Benefits**:
- ✅ Safe retries
- ✅ No duplicate orders
- ✅ Network failure resilience

---

## 8. 🚦 Rate Limiting Models

**Purpose**: Prevent abuse and ensure fair usage

### **Rate Limit Bucket**
```java
@Entity
@Table(name = "rate_limit_buckets")
public class RateLimitBucket {
    @Id
    private String bucketKey;          // "user:123:api" or "ip:1.2.3.4:search"
    
    private Integer tokenCount;        // Remaining requests
    private Integer maxTokens;         // Bucket capacity
    private Double refillRate;         // Tokens per second
    
    private Instant lastRefillAt;
    private Instant windowStartAt;
    
    // Check if request allowed
    public boolean allowRequest() {
        refillTokens();
        if (tokenCount > 0) {
            tokenCount--;
            return true;
        }
        return false;
    }
}
```

**Benefits**:
- ✅ Prevent API abuse
- ✅ Fair resource allocation
- ✅ DDoS protection

---

## 9. 📊 Metrics & Monitoring Models

**Purpose**: Track performance and health

### **Performance Metric**
```java
@Entity
@Table(name = "performance_metrics")
public class PerformanceMetric {
    @Id
    private String metricId;
    
    private String metricName;         // "order.creation.time"
    private String metricType;         // COUNTER, GAUGE, HISTOGRAM, TIMER
    private Double value;
    private String unit;               // "ms", "count", "bytes"
    
    private Map<String, String> tags;  // {service: "order", region: "us-east"}
    
    @Indexed
    private Instant timestamp;
}
```

### **Health Check**
```java
@Entity
public class HealthCheck {
    @Id
    private String checkId;
    
    private String serviceName;
    private String checkType;          // DATABASE, CACHE, EXTERNAL_API
    private HealthStatus status;       // UP, DOWN, DEGRADED
    private Long responseTimeMs;
    private String errorMessage;
    
    @Indexed
    private Instant checkedAt;
}
```

---

## 10. 🗂️ Partitioning & Sharding Models

**Purpose**: Distribute data across multiple databases

### **Partition Key Strategy**
```java
public interface Partitionable {
    String getPartitionKey();
}

@Entity
public class Order implements Partitionable {
    @Id
    private String orderId;
    
    private String customerId;
    
    // Partition by customer for data locality
    @Override
    public String getPartitionKey() {
        return "customer:" + customerId;
    }
}
```

### **Shard Mapping**
```java
@Entity
@Table(name = "shard_mappings")
public class ShardMapping {
    @Id
    private String partitionKey;
    
    private String shardId;            // "shard-1", "shard-2"
    private String databaseUrl;
    private String region;
    
    // Consistent hashing
    private Long hashValue;
}
```

**Benefits**:
- ✅ Horizontal scalability
- ✅ Data locality
- ✅ Reduced database load

---

## 11. 💾 Cache Models

**Purpose**: Reduce database queries

### **Cache Entry**
```java
@RedisHash("product_cache")
public class ProductCacheEntry {
    @Id
    private String productId;
    
    private String productData;        // Serialized product
    private Set<String> tags;          // For cache invalidation
    
    @TimeToLive
    private Long ttl = 3600L;          // 1 hour
    
    private Instant cachedAt;
}
```

### **Cache Invalidation Event**
```java
public class CacheInvalidationEvent {
    private String cacheKey;
    private Set<String> tags;          // Invalidate by tag
    private InvalidationType type;     // SINGLE, PATTERN, TAG
}
```

---

## 📊 Scalability Models Summary

| Pattern | Purpose | Impact |
|---------|---------|--------|
| **Event Sourcing** | Store all changes as events | Audit trail, time travel, CQRS |
| **CQRS** | Separate read/write models | Independent scaling, optimized queries |
| **Materialized Views** | Pre-computed aggregations | Instant dashboards, no joins |
| **Snapshots** | Avoid replaying all events | Fast aggregate reconstruction |
| **Saga** | Distributed transactions | Handle cross-service workflows |
| **Outbox** | Reliable event publishing | No lost events, transactional |
| **Idempotency** | Handle duplicate requests | Safe retries, no duplicates |
| **Rate Limiting** | Prevent abuse | Fair usage, DDoS protection |
| **Metrics** | Track performance | Monitoring, alerting |
| **Partitioning** | Distribute data | Horizontal scaling |
| **Caching** | Reduce DB queries | Fast reads, reduced load |

---

## ✅ Implementation Priority

### **Phase 1 (Must Have)**:
1. ✅ **Outbox Pattern** - Reliable events
2. ✅ **Idempotency Keys** - Safe retries
3. ✅ **CQRS Read Models** - Fast queries
4. ✅ **Caching** - Reduce DB load

### **Phase 2 (Should Have)**:
5. ✅ **Event Sourcing** - Audit trail
6. ✅ **Saga Pattern** - Distributed transactions
7. ✅ **Rate Limiting** - API protection
8. ✅ **Metrics** - Monitoring

### **Phase 3 (Nice to Have)**:
9. ✅ **Snapshots** - Event sourcing optimization
10. ✅ **Partitioning** - Extreme scale
11. ✅ **Materialized Views** - Complex analytics

---

These patterns are **essential for production-scale e-commerce**! 🚀
