# Seller Implementation Requirements (Chenile OWIZ + STM)

## Overview
This document outlines the requirements for implementing the Seller module using **Chenile OWIZ** (Orchestration Workflow) and **STM** (State Machine) patterns.

## Architecture Components

### 1. State Machine Definition
**File**: `seller-stm.xml` (in seller-service module)

```xml
<state-machine>
    <states>
        <state name="DRAFT" initial="true"/>
        <state name="PENDING_VERIFICATION"/>
        <state name="ACTIVE"/>
        <state name="SUSPENDED"/>
        <state name="REJECTED"/>
        <state name="INACTIVE"/>
    </states>
    
    <events>
        <event name="submitApplication">
            <transition from="DRAFT" to="PENDING_VERIFICATION"/>
        </event>
        
        <event name="approve">
            <transition from="PENDING_VERIFICATION" to="ACTIVE"/>
        </event>
        
        <event name="reject">
            <transition from="PENDING_VERIFICATION" to="REJECTED"/>
        </event>
        
        <event name="suspend">
            <transition from="ACTIVE" to="SUSPENDED"/>
        </event>
        
        <event name="reactivate">
            <transition from="SUSPENDED" to="ACTIVE"/>
        </event>
        
        <event name="deactivate">
            <transition from="ACTIVE" to="INACTIVE"/>
        </event>
    </events>
</state-machine>
```

### 2. Orchestration Flow
**File**: `seller-orchestration-flow.xml` (in seller-orchestration-service module)

```xml
<flows>
    <flow id="seller-orchestration" defaultFlow="true">
        <seller-chain>
            <!-- Step 1: Validate Input -->
            <validate-seller-service/>
            
            <!-- Step 2: Create Seller + Map Bank Details -->
            <create-seller-service/>
            
            <!-- Step 3: Verify KYC Documents -->
            <verify-kyc-service/>
            
            <!-- Step 4: Parallel Actions -->
            <seller-parallel-chain>
                <send-welcome-email-service/>
                <!-- Future: Add bank verification service -->
            </seller-parallel-chain>
        </seller-chain>
    </flow>
</flows>
```

## Implementation Requirements

### Module 1: Seller API (seller-api)

#### 1.1 Entity: `Seller.java`
**Status**: ✅ Already exists

**Required Fields**:
- `sellerCode` (unique)
- `sellerName`
- `contactEmail`
- `businessType`
- `currency`, `locale`
- `paymentInfos` (One-to-Many relationship)

#### 1.2 Entity: `SellerPaymentInfo.java`
**Status**: ✅ Updated with getters/setters

**Required Fields**:
- `sellerId`
- `preferredMethod`
- `currency`
- `paymentMethods` (One-to-Many)

#### 1.3 Entity: `PaymentMethodd.java`
**Status**: ✅ Already exists

**Required Fields**:
- `accountNumber`
- `ifscCode`
- `accountHolderName`
- `bankName`
- `status` (PENDING_VERIFICATION, ACTIVE, FAILED)
- `isPrimary`

#### 1.4 DTOs/Commands
**Required**:
- `SubmitApplicationPayload.java` - For state transitions
- Event payloads for approve/reject/suspend

### Module 2: Seller Service (seller-service)

#### 2.1 State Entity Service
**File**: `SellerStateEntityServiceImpl.java`

**Requirements**:
```java
@Service
public class SellerStateEntityServiceImpl extends StateEntityServiceImpl<Seller> {
    
    @Autowired
    private SellerRepository repository;
    
    @Override
    protected Seller findById(String id) {
        return repository.findById(id).orElse(null);
    }
    
    @Override
    protected Seller save(Seller entity) {
        return repository.save(entity);
    }
}
```

**Configuration**: Register as `_sellerStateEntityService_` bean

#### 2.2 State Machine Configuration
**File**: `seller-stm.xml`

**Location**: `src/main/resources/stm/`

**Requirements**:
- Define all states (DRAFT, PENDING_VERIFICATION, ACTIVE, etc.)
- Define all events (submitApplication, approve, reject, suspend, etc.)
- Define valid transitions

### Module 3: Seller Orchestration API (seller-orchestration-api)

#### 3.1 Command Object: `CreateSellerCommand.java`
**Status**: ✅ Updated

**Required Fields**:
```java
- String sellerName
- String email
- String businessName
- String taxId
- String bankAccountNumber
- String ifscCode
- String accountHolderName (optional)
- String kycDocumentId
```

#### 3.2 Context: `SellerContext.java`
**Status**: ✅ Already exists

**Required**:
- Extends `HashMap<String, Object>`
- Implements `ChainContextContainer<SellerContext>`
- Contains `CreateSellerCommand requestSeller`

### Module 4: Seller Orchestration Service (seller-orchestration-service)

#### 4.1 Base Service: `BaseSellerOrchService.java`
**Status**: ✅ Fixed (import added)

**Requirements**:
- Implements `Command<SellerContext>`
- Provides template methods: `doProcess`, `doPreProcessing`, `doPostProcessing`

#### 4.2 Validation Service: `ValidateSellerService.java`
**Status**: ⚠️ Needs implementation

**Requirements**:
```java
@Override
protected void doProcess(SellerContext context) {
    CreateSellerCommand command = context.getRequestSeller();
    
    // 1. Validate business name (not empty, min 3 chars)
    if (command.getSellerName() == null || command.getSellerName().length() < 3) {
        throw new ValidationException("Business name must be at least 3 characters");
    }
    
    // 2. Validate email format
    if (!isValidEmail(command.getEmail())) {
        throw new ValidationException("Invalid email format");
    }
    
    // 3. Check email uniqueness
    if (sellerRepository.existsByEmail(command.getEmail())) {
        throw new ValidationException("Email already registered");
    }
    
    // 4. Validate bank details (if provided)
    if (command.getBankAccountNumber() != null) {
        validateBankDetails(command);
    }
}
```

#### 4.3 Creation Service: `CreateSellerCommandService.java`
**Status**: ✅ Implemented

**Current Implementation**:
- Maps `CreateSellerCommand` to `Seller` entity
- Creates `SellerPaymentInfo` and `PaymentMethodd` for bank details
- Calls `sellerService.create(seller)` → State: DRAFT
- Triggers `submitApplication` event → State: PENDING_VERIFICATION

#### 4.4 KYC Verification Service: `VerifyKycCommandService.java`
**Status**: ⚠️ Needs implementation

**Requirements**:
```java
@Override
protected void doProcess(SellerContext context) {
    Seller seller = (Seller) context.getData("createdSeller");
    CreateSellerCommand command = context.getRequestSeller();
    
    // 1. Verify KYC document exists
    if (command.getKycDocumentId() == null) {
        throw new ValidationException("KYC document is required");
    }
    
    // 2. Call external KYC verification service (mock for now)
    boolean kycVerified = kycVerificationService.verify(command.getKycDocumentId());
    
    if (!kycVerified) {
        // Trigger reject event
        sellerService.processById(seller.getId(), "reject", null);
        throw new ValidationException("KYC verification failed");
    }
    
    // 3. Store verification result in context
    context.putData("kycVerified", true);
}
```

#### 4.5 Welcome Email Service: `SendWelcomeEmailCommandService.java`
**Status**: ⚠️ Needs implementation

**Requirements**:
```java
@Override
protected void doProcess(SellerContext context) {
    Seller seller = (Seller) context.getData("createdSeller");
    
    // 1. Prepare email template
    EmailTemplate template = emailTemplateService.getTemplate("SELLER_WELCOME");
    
    // 2. Populate variables
    Map<String, Object> variables = new HashMap<>();
    variables.put("sellerName", seller.getSellerName());
    variables.put("loginUrl", generateLoginUrl(seller));
    
    // 3. Send email
    emailService.send(
        seller.getContactEmail(),
        "Welcome to Our Platform",
        template.render(variables)
    );
    
    System.out.println("Welcome email sent to: " + seller.getContactEmail());
}
```

#### 4.6 Bank Verification Service (Future): `VerifyBankAccountService.java`
**Status**: ❌ Not implemented (commented out)

**Requirements**:
```java
@Override
protected void doProcess(SellerContext context) {
    Seller seller = (Seller) context.getData("createdSeller");
    
    // Get primary payment method
    PaymentMethodd paymentMethod = seller.getPaymentInfos().get(0)
        .getPaymentMethods().stream()
        .filter(PaymentMethodd::getPrimary)
        .findFirst()
        .orElseThrow(() -> new ValidationException("No payment method found"));
    
    // 1. Initiate Penny Drop (₹1 deposit)
    PennyDropResult result = bankVerificationService.initiatePennyDrop(
        paymentMethod.getAccountNumber(),
        paymentMethod.getIfscCode()
    );
    
    // 2. Update payment method status
    if (result.isSuccess()) {
        paymentMethod.setStatus("ACTIVE");
        paymentMethod.setAccountHolderName(result.getBeneficiaryName());
        paymentMethod.setBankName(result.getBankName());
    } else {
        paymentMethod.setStatus("FAILED");
    }
    
    sellerRepository.save(seller);
}
```

### Module 5: Configuration

#### 5.1 Spring Configuration
**File**: `SellerOrchestrationConfig.java`

**Requirements**:
```java
@Configuration
public class SellerOrchestrationConfig {
    
    @Bean
    public ValidateSellerService validateSellerService() {
        return new ValidateSellerService();
    }
    
    @Bean
    public CreateSellerCommandService createSellerService() {
        return new CreateSellerCommandService();
    }
    
    @Bean
    public VerifyKycCommandService verifyKycService() {
        return new VerifyKycCommandService();
    }
    
    @Bean
    public SendWelcomeEmailCommandService sendWelcomeEmailService() {
        return new SendWelcomeEmailCommandService();
    }
}
```

#### 5.2 OWIZ Flow Registration
**File**: `seller-orchestration-flow.xml`

**Location**: `src/main/resources/owiz/`

## Implementation Checklist

### Phase 1: Core Setup ✅
- [x] Update `CreateSellerCommand` with bank fields
- [x] Add getters/setters to `SellerPaymentInfo`
- [x] Fix `BaseSellerOrchService` imports
- [x] Implement bank mapping in `CreateSellerCommandService`

### Phase 2: State Machine 🔄
- [ ] Create `seller-stm.xml` with all states and events
- [ ] Implement `SellerStateEntityServiceImpl`
- [ ] Test state transitions (DRAFT → PENDING_VERIFICATION → ACTIVE)

### Phase 3: Orchestration Services 🔄
- [ ] Implement `ValidateSellerService`
- [ ] Implement `VerifyKycCommandService`
- [ ] Implement `SendWelcomeEmailCommandService`
- [ ] Create `seller-orchestration-flow.xml`

### Phase 4: Bank Verification 📋
- [ ] Uncomment/refactor `SetupBankAccountCommandService`
- [ ] Integrate with Penny Drop API (Razorpay/Cashfree)
- [ ] Add to orchestration flow

### Phase 5: Testing 📋
- [ ] Unit tests for each service
- [ ] Integration tests for full flow
- [ ] Test state machine transitions
- [ ] Test error handling and rollback

## API Endpoints

### Create Seller
```
POST /api/admin/sellers
Content-Type: application/json

{
  "sellerName": "Artisan Crafts",
  "email": "contact@artisancrafts.com",
  "businessName": "Artisan Crafts Pvt Ltd",
  "taxId": "GST123456789",
  "bankAccountNumber": "1234567890",
  "ifscCode": "SBIN0001234",
  "accountHolderName": "Artisan Crafts",
  "kycDocumentId": "KYC_DOC_123"
}
```

### Approve Seller
```
POST /api/admin/sellers/{id}/approve
```

### Reject Seller
```
POST /api/admin/sellers/{id}/reject
{
  "reason": "Invalid KYC documents"
}
```

## Database Schema

### Table: `hm_seller`
- `id` (PK)
- `seller_code` (UNIQUE)
- `seller_name`
- `contact_email`
- `business_type`
- `state` (DRAFT, PENDING_VERIFICATION, ACTIVE, etc.)
- `created_at`, `updated_at`

### Table: `hm_seller_payment_info`
- `id` (PK)
- `seller_id` (FK)
- `preferred_method`
- `currency`
- `created_at`, `updated_at`

### Table: `seller_payment_method`
- `id` (PK)
- `payment_info_id` (FK)
- `account_number`
- `ifsc_code`
- `account_holder_name`
- `bank_name`
- `status` (PENDING_VERIFICATION, ACTIVE, FAILED)
- `is_primary`
- `added_at`, `updated_at`

## Next Steps

1. **Implement State Machine**: Create `seller-stm.xml` and configure it
2. **Complete Orchestration Services**: Implement the missing services (Validate, KYC, Email)
3. **Test End-to-End Flow**: From frontend submission to seller activation
4. **Add Bank Verification**: Integrate Penny Drop API
5. **Error Handling**: Add proper exception handling and rollback mechanisms

## References
- [Chenile OWIZ Documentation](https://github.com/rajakolluru/chenile)
- [State Machine Pattern](https://github.com/rajakolluru/chenile/wiki/State-Machine)
- Sequence Diagram: `/documentation/docs/diagrams/seller-onboarding-flow.puml`
