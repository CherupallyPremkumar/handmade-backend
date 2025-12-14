# Scalable Seller Onboarding Architecture

## Overview
Design for implementing **MVP now** with **scalable architecture** for future enhancements without breaking changes.

## Current MVP Scope

### Phase 1 (MVP) - Implemented
```
✅ Owner Information (name, email, phone, address)
✅ Business Information (name, GSTIN, PAN, address)
✅ Store Setup (name, description, categories)
✅ Payment (account number, IFSC)
```

### Future Phases (Scalable)
```
📋 Phase 2: Document upload, store branding
📋 Phase 3: Video KYC, advanced verification
📋 Phase 4: Multi-warehouse, international sellers
```

---

## Scalable Architecture Patterns

### 1. **Command Pattern (Extensible DTOs)**

#### Current: `CreateSellerCommand` (MVP)
```java
public class CreateSellerCommand {
    // Owner Info
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;
    private String ownerAddress;
    
    // Business Info
    private String businessName;
    private String taxId;  // GSTIN
    private String businessType;
    private String businessAddress;
    private String businessPhone;
    
    // Store Info
    private String storeName;
    private String storeDescription;
    private List<String> productCategories;
    
    // Payment Info
    private String bankAccountNumber;
    private String ifscCode;
    private String accountHolderName;
    
    // Extensibility: Use Map for future fields
    private Map<String, Object> additionalData = new HashMap<>();
    
    // Getters/Setters...
    
    // Helper methods for extensibility
    public void setAdditionalField(String key, Object value) {
        additionalData.put(key, value);
    }
    
    public Object getAdditionalField(String key) {
        return additionalData.get(key);
    }
}
```

**Benefits:**
- ✅ Add new fields without breaking existing code
- ✅ Backward compatible
- ✅ Easy to version

#### Future: Enhanced Command (Phase 2+)
```java
public class CreateSellerCommandV2 extends CreateSellerCommand {
    // Phase 2 fields
    private String kycDocumentId;
    private String storeLogo;
    private String storeBanner;
    
    // Phase 3 fields
    private String videoKycId;
    private String warehouseAddress;
    
    // Still backward compatible with V1
}
```

---

### 2. **Strategy Pattern (Pluggable Validators)**

#### Interface: `SellerValidator`
```java
public interface SellerValidator {
    /**
     * Validates a specific aspect of seller data.
     * @return ValidationResult with errors (if any)
     */
    ValidationResult validate(CreateSellerCommand command);
    
    /**
     * Priority order (lower = higher priority)
     */
    int getOrder();
    
    /**
     * Whether this validator is enabled
     */
    boolean isEnabled();
}
```

#### Implementation: Validator Chain
```java
@Service
public class SellerValidationOrchestrator {
    
    @Autowired
    private List<SellerValidator> validators;
    
    public ValidationResult validateAll(CreateSellerCommand command) {
        ValidationResult result = new ValidationResult();
        
        // Sort by order
        validators.stream()
            .filter(SellerValidator::isEnabled)
            .sorted(Comparator.comparingInt(SellerValidator::getOrder))
            .forEach(validator -> {
                ValidationResult vr = validator.validate(command);
                result.merge(vr);
            });
        
        return result;
    }
}
```

#### MVP Validators
```java
@Component
@Order(1)
public class BasicFieldValidator implements SellerValidator {
    @Override
    public ValidationResult validate(CreateSellerCommand command) {
        // Validate required fields
    }
}

@Component
@Order(2)
public class EmailValidator implements SellerValidator {
    @Override
    public ValidationResult validate(CreateSellerCommand command) {
        // Validate email format + uniqueness
    }
}

@Component
@Order(3)
public class GstinFormatValidator implements SellerValidator {
    @Override
    public ValidationResult validate(CreateSellerCommand command) {
        // Validate GSTIN format (no API call in MVP)
    }
}
```

#### Future Validators (Easy to add)
```java
@Component
@Order(10)
@ConditionalOnProperty("feature.gstin-api-verification.enabled")
public class GstinApiValidator implements SellerValidator {
    @Override
    public ValidationResult validate(CreateSellerCommand command) {
        // Call GST API (Phase 2)
    }
}

@Component
@Order(20)
@ConditionalOnProperty("feature.video-kyc.enabled")
public class VideoKycValidator implements SellerValidator {
    @Override
    public ValidationResult validate(CreateSellerCommand command) {
        // Validate video KYC (Phase 3)
    }
}
```

**Benefits:**
- ✅ Add validators without modifying existing code
- ✅ Enable/disable via feature flags
- ✅ Easy to test individually
- ✅ Clear separation of concerns

---

### 3. **Feature Flags (Gradual Rollout)**

#### Configuration: `application.yml`
```yaml
features:
  seller-onboarding:
    # MVP features (always enabled)
    basic-validation: true
    email-verification: true
    
    # Phase 2 features (disabled in MVP)
    gstin-api-verification: false
    document-upload: false
    penny-drop-verification: false
    
    # Phase 3 features (disabled)
    video-kyc: false
    auto-approval: false
    
    # Phase 4 features (disabled)
    multi-warehouse: false
    international-sellers: false
```

#### Usage in Code
```java
@Service
public class VerifyGstinApiService extends BaseSellerOrchService {
    
    @Value("${features.seller-onboarding.gstin-api-verification:false}")
    private boolean gstinApiEnabled;
    
    @Override
    protected void doProcess(SellerContext context) {
        if (!gstinApiEnabled) {
            System.out.println("GSTIN API verification disabled, skipping");
            return;
        }
        
        // Call GST API
        // ...
    }
}
```

**Benefits:**
- ✅ Enable features without code deployment
- ✅ A/B testing
- ✅ Gradual rollout
- ✅ Easy rollback

---

### 4. **Repository Pattern (Data Access Abstraction)**

#### Interface: `SellerRepository`
```java
public interface SellerRepository extends JpaRepository<Seller, String> {
    
    // MVP methods
    boolean existsByContactEmail(String email);
    Optional<Seller> findByContactEmail(String email);
    
    // Future methods (add as needed)
    List<Seller> findByState(String state);
    List<Seller> findByStatus(SellerStatus status);
    
    // Phase 3: Advanced queries
    @Query("SELECT s FROM Seller s WHERE s.annualRevenue > :revenue")
    List<Seller> findHighValueSellers(@Param("revenue") BigDecimal revenue);
}
```

**Benefits:**
- ✅ Easy to add new queries
- ✅ Database-agnostic
- ✅ Testable with mocks

---

### 5. **Service Layer (Business Logic Abstraction)**

#### Interface: `SellerOnboardingService`
```java
public interface SellerOnboardingService {
    
    // MVP methods
    Seller createSeller(CreateSellerCommand command);
    void validateSeller(CreateSellerCommand command);
    
    // Future methods
    void uploadDocuments(String sellerId, List<Document> documents);
    void scheduleVideoKyc(String sellerId, LocalDateTime scheduledTime);
    void approveSeller(String sellerId, String approvedBy);
    void rejectSeller(String sellerId, String reason);
}
```

#### Implementation: Delegate to OWIZ
```java
@Service
public class SellerOnboardingServiceImpl implements SellerOnboardingService {
    
    @Autowired
    private SellerOrchestrationEngine orchestrationEngine;
    
    @Override
    public Seller createSeller(CreateSellerCommand command) {
        SellerContext context = new SellerContext();
        context.setRequestSeller(command);
        
        // Execute orchestration flow
        orchestrationEngine.execute("seller-onboarding", context);
        
        return (Seller) context.getData("createdSeller");
    }
}
```

**Benefits:**
- ✅ Clean API for controllers
- ✅ Easy to mock for testing
- ✅ Hides orchestration complexity

---

### 6. **Event-Driven Architecture (Async Processing)**

#### MVP: Synchronous Flow
```java
validate → create → email → done
```

#### Future: Event-Driven Flow
```java
validate → create → publish(SellerCreatedEvent)
                      ↓
                    Listeners:
                      - SendWelcomeEmailListener
                      - TriggerGstinVerificationListener
                      - NotifyAdminListener
                      - AnalyticsListener
```

#### Event Definition
```java
@Data
public class SellerCreatedEvent {
    private String sellerId;
    private String sellerName;
    private String email;
    private LocalDateTime createdAt;
    private Map<String, Object> metadata;
}
```

#### Event Publisher
```java
@Service
public class CreateSellerCommandService extends BaseSellerOrchService {
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Override
    protected void doProcess(SellerContext context) {
        // Create seller
        Seller seller = createSeller(context);
        
        // Publish event
        SellerCreatedEvent event = new SellerCreatedEvent();
        event.setSellerId(seller.getId());
        event.setSellerName(seller.getSellerName());
        eventPublisher.publishEvent(event);
    }
}
```

#### Event Listeners (Easy to add)
```java
@Component
public class SendWelcomeEmailListener {
    
    @EventListener
    @Async
    public void handleSellerCreated(SellerCreatedEvent event) {
        // Send email
    }
}

@Component
@ConditionalOnProperty("feature.gstin-api-verification.enabled")
public class GstinVerificationListener {
    
    @EventListener
    @Async
    public void handleSellerCreated(SellerCreatedEvent event) {
        // Trigger GSTIN verification
    }
}
```

**Benefits:**
- ✅ Decouple services
- ✅ Easy to add new listeners
- ✅ Async processing
- ✅ Better scalability

---

### 7. **API Versioning (Backward Compatibility)**

#### V1 API (MVP)
```java
@RestController
@RequestMapping("/api/v1/sellers")
public class SellerControllerV1 {
    
    @PostMapping
    public ResponseEntity<SellerResponse> createSeller(
        @RequestBody CreateSellerRequestV1 request
    ) {
        // MVP implementation
    }
}
```

#### V2 API (Future)
```java
@RestController
@RequestMapping("/api/v2/sellers")
public class SellerControllerV2 {
    
    @PostMapping
    public ResponseEntity<SellerResponseV2> createSeller(
        @RequestBody CreateSellerRequestV2 request
    ) {
        // Enhanced implementation with new fields
    }
}
```

**Benefits:**
- ✅ Support old clients
- ✅ Gradual migration
- ✅ No breaking changes

---

## Database Schema (Extensible)

### MVP Schema
```sql
-- Seller table (core fields)
CREATE TABLE hm_seller (
    id VARCHAR(50) PRIMARY KEY,
    seller_code VARCHAR(50) UNIQUE,
    seller_name VARCHAR(255) NOT NULL,
    contact_email VARCHAR(255) UNIQUE NOT NULL,
    contact_phone VARCHAR(20),
    business_type VARCHAR(50),
    state VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    
    -- Extensibility: JSON column for future fields
    additional_data JSONB
);

-- Payment info (separate table for normalization)
CREATE TABLE hm_seller_payment_info (
    id VARCHAR(50) PRIMARY KEY,
    seller_id VARCHAR(50) REFERENCES hm_seller(id),
    preferred_method VARCHAR(50),
    currency VARCHAR(10),
    created_at TIMESTAMP,
    
    -- Extensibility
    additional_data JSONB
);

-- Payment methods (supports multiple accounts)
CREATE TABLE seller_payment_method (
    id VARCHAR(50) PRIMARY KEY,
    payment_info_id VARCHAR(50) REFERENCES hm_seller_payment_info(id),
    account_number VARCHAR(20),
    ifsc_code VARCHAR(11),
    account_holder_name VARCHAR(255),
    status VARCHAR(50),
    is_primary BOOLEAN,
    
    -- Extensibility
    additional_data JSONB
);
```

### Future: Add columns without breaking
```sql
-- Phase 2: Add document tracking
ALTER TABLE hm_seller ADD COLUMN kyc_document_id VARCHAR(50);
ALTER TABLE hm_seller ADD COLUMN kyc_status VARCHAR(50);

-- Phase 3: Add video KYC
ALTER TABLE hm_seller ADD COLUMN video_kyc_id VARCHAR(50);
ALTER TABLE hm_seller ADD COLUMN video_kyc_completed_at TIMESTAMP;
```

**Benefits:**
- ✅ JSONB for flexible fields
- ✅ Normalized structure
- ✅ Easy to add columns
- ✅ Backward compatible

---

## Orchestration Flow (Scalable)

### MVP Flow
```xml
<flow id="seller-onboarding-v1">
    <seller-chain>
        <validate-seller-service/>
        <verification-chain>
            <verify-gstin-format-service/>  <!-- Format only, no API -->
            <verify-bank-format-service/>   <!-- Format only, no API -->
        </verification-chain>
        <create-seller-service/>
        <send-welcome-email-service/>
    </seller-chain>
</flow>
```

### Phase 2 Flow (Add services)
```xml
<flow id="seller-onboarding-v2">
    <seller-chain>
        <validate-seller-service/>
        <verification-chain>
            <verify-gstin-format-service/>
            <verify-gstin-api-service/>     <!-- NEW: API verification -->
            <verify-bank-format-service/>
            <verify-bank-penny-drop-service/> <!-- NEW: Penny drop -->
        </verification-chain>
        <create-seller-service/>
        <upload-documents-service/>         <!-- NEW: Document upload -->
        <send-welcome-email-service/>
    </seller-chain>
</flow>
```

**Benefits:**
- ✅ Add services without breaking V1
- ✅ Reuse existing services
- ✅ Clear versioning

---

## Testing Strategy (Scalable)

### Unit Tests (Per Validator)
```java
@Test
public void testGstinFormatValidator() {
    GstinFormatValidator validator = new GstinFormatValidator();
    CreateSellerCommand command = new CreateSellerCommand();
    command.setTaxId("22AAAAA0000A1Z5");
    
    ValidationResult result = validator.validate(command);
    assertTrue(result.isValid());
}
```

### Integration Tests (Per Flow)
```java
@Test
public void testSellerOnboardingFlowV1() {
    CreateSellerCommand command = createValidCommand();
    Seller seller = sellerOnboardingService.createSeller(command);
    
    assertNotNull(seller.getId());
    assertEquals("DRAFT", seller.getState());
}
```

### Feature Flag Tests
```java
@Test
@TestPropertySource(properties = "features.seller-onboarding.gstin-api-verification=true")
public void testGstinApiVerificationEnabled() {
    // Test with feature enabled
}
```

---

## Migration Path (MVP → Phase 2)

### Step 1: Enable Feature Flag
```yaml
features:
  seller-onboarding:
    gstin-api-verification: true  # Enable for 10% of sellers
```

### Step 2: Monitor Metrics
```java
@Component
public class GstinApiMetrics {
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    public void recordVerification(boolean success) {
        meterRegistry.counter("gstin.verification", 
            "status", success ? "success" : "failure").increment();
    }
}
```

### Step 3: Gradual Rollout
- 10% of sellers → Monitor for 1 week
- 50% of sellers → Monitor for 1 week
- 100% of sellers → Full rollout

---

## Summary: Scalability Checklist

✅ **Extensible DTOs** - `additionalData` map for future fields  
✅ **Pluggable Validators** - Strategy pattern, easy to add  
✅ **Feature Flags** - Enable/disable without deployment  
✅ **Event-Driven** - Decouple services, async processing  
✅ **API Versioning** - Backward compatibility  
✅ **JSONB Columns** - Flexible schema evolution  
✅ **Modular OWIZ Flows** - Add services without breaking existing  
✅ **Comprehensive Testing** - Unit, integration, feature flag tests  

---

## Next Steps

1. ✅ **Implement MVP** with scalable patterns
2. 📋 **Add feature flags** for Phase 2 features
3. 📋 **Create metrics dashboard** for monitoring
4. 📋 **Document API** with OpenAPI/Swagger
5. 📋 **Setup CI/CD** for gradual rollout
