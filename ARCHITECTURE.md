# Handmade E-Commerce Platform Architecture

## System Overview

### Observed System Purpose
Multi-vendor e-commerce platform for handmade products, supporting seller onboarding, product catalog management, pricing, payments, and order fulfillment.

### Technology Stack
- **Language**: Java 17
- **Framework**: Spring Boot
- **Parent Framework**: Chenile Framework (org.chenile:chenile-parent:2.0.35)
- **Build Tool**: Maven (multi-module)
- **Database**: JPA/Hibernate with Liquibase migrations
- **Architecture Style**: Modular Monolith

### Architecture Pattern
Modules follow a consistent layered structure:
- `{module}-api`: Public interfaces, DTOs, commands
- `{module}-domain`: JPA entities, domain models
- `{module}-service`: Business logic, controllers, repositories, STM actions
- `{module}-delegator`: Inter-module communication clients
- `{module}-async`: Event listeners (optional)

---

## Module Breakdown

### Core Infrastructure Modules

#### handmade-app-core

**Observed Responsibility**: Shared domain models, utilities, and cross-cutting concerns

**Owned Domain Objects**:
- `Money`: Monetary value representation
- `Region`: Geographic region model
- `IdempotencyKey`: Request deduplication
- `ProductAttribute`, `VariantAttribute`: Product metadata
- `ShippingDimensions`: Physical product dimensions
- `HSNCode`: Tax classification codes
- `AbstractSeller`: Base seller abstraction
- `OutboxEvent`, `DomainEventStore`: Event sourcing infrastructure
- `RateLimitBucket`: API rate limiting
- `Common`: Shared utilities

**Public Entry Points**: NOT DERIVABLE FROM CODE (utility module)

**Internal Components**: Utility classes, shared models

**Incoming Dependencies**: All domain modules

**Outgoing Dependencies**: None (foundation layer)

---

#### handmade-app-boot

**Observed Responsibility**: Application assembly and bootstrap configuration

**Owned Domain Objects**: None

**Public Entry Points**: Spring Boot application entry point

**Internal Components**:
- `build-package`: Deployment packaging

**Incoming Dependencies**: All modules (aggregates for deployment)

**Outgoing Dependencies**: All active modules

---

#### handmade-event-management

**Observed Responsibility**: Event-driven communication infrastructure

**Owned Domain Objects**:
- Event abstractions (in `event-domain`)
- Event API contracts (in `event-api`)

**Public Entry Points**: Event publishing and subscription APIs

**Internal Components**:
- `invm-event-starter`: In-memory event bus
- `q-based-event-starter`: Queue-based event bus

**Incoming Dependencies**: All modules that publish or consume events

**Outgoing Dependencies**: `handmade-app-core`

---

#### handmade-liquibase-management

**Observed Responsibility**: Database schema versioning and migrations

**Owned Domain Objects**: None

**Public Entry Points**: Liquibase changelog files

**Internal Components**: `liquibase-changelogs`

**Incoming Dependencies**: `handmade-app-boot`

**Outgoing Dependencies**: None

---

### Product Domain Modules

#### handmade-product-management

**Observed Responsibility**: Product catalog lifecycle management with state machines

**Owned Domain Objects**:
- `Product`: Core product entity
- `Variant`: Product variant (SKU)
- `ProductVariant`: Product-variant relationship
- `ProductCategoryMapping`: Category associations
- `ProductImage`: Product imagery
- `ProductActivityLog`, `VariantActivityLog`: Audit trails
- `ProductStatus`, `VariantStatus`: State enumerations
- `ShippingProfile`: Shipping configuration

**Public Entry Points**:
- `ProductController`: Product CRUD operations
- `VariantController`: Variant management

**Internal Components**:
- `ProductRepository`, `VariantRepository`, `ProductCategoryMappingRepository`
- `ProductHealthChecker`, `VariantHealthChecker`
- State machine actions: `ActivateVariantAction`, `ArchiveProductAction`, `OnUnhideProductService`
- Post-save hooks: `ACTIVEProductPostSaveHook`, `DRAFTProductPostSaveHook`, `ARCHIVEDProductPostSaveHook`
- `ProductConfiguration`, `VariantConfiguration`: STM wiring

**Incoming Dependencies**:
- `handmade-catalog-management` (via events)
- `handmade-workflow-orchestrator/product-orchestration`

**Outgoing Dependencies**:
- `handmade-app-core`
- `handmade-event-management`

**Events**:
- Product lifecycle events (published via `product-async`)

**State Machines**:
- Product STM: DRAFT → ACTIVE → ARCHIVED
- Variant STM: DRAFT → ACTIVE → INACTIVE → DISCONTINUED

---

#### handmade-catalog-management

**Observed Responsibility**: Product catalog aggregation, indexing, and scheduling

**Owned Domain Objects**:
- Catalog aggregates (NOT DERIVABLE FROM CODE - need to examine catalog-api)
- Collection-product mappings

**Public Entry Points**:
- Catalog API endpoints
- `ProductEventListener`: Reacts to product changes

**Internal Components**:
- `catalog-service`: Catalog business logic
- `catalog-scheduler`: Batch processing for catalog updates
- `MappingWriter`: Batch job component

**Incoming Dependencies**:
- `handmade-product-management` (via events)

**Outgoing Dependencies**:
- `handmade-app-core`
- `handmade-event-management`
- `handmade-search-management`

**Events**:
- Listens to: Product lifecycle events
- Publishes: Catalog update events

---

#### handmade-pricing-management

**Observed Responsibility**: Multi-region pricing, tax calculation, and discount rules

**Owned Domain Objects**:
- `Price`: Base pricing entity with STM
- `RegionalPrice`: Region-specific price overrides
- `PriceRule`: Pricing rules (discounts, adjustments)
- `PriceHistory`: Price change audit
- `Tax`: Tax configuration
- `SystemDiscountRule`, `TaxRule`: Rule engine entities

**Public Entry Points**:
- `PriceController`: Price management
- `PricingService`: Price calculation API
- `TaxService`: Tax calculation API

**Internal Components**:
- `price-service`: Core pricing logic with OWIZ orchestration
- `pricing-api`: Pricing contracts
- `pricing-usd`, `pricing-inr`: Region-specific strategies
- `rules-service`: Discount and tax rule engine
- `tax-service`: Tax calculation service
- `PriceEntityStore`: Price persistence
- `PricingStrategyFactory`: Strategy pattern for regional pricing
- `RegionContext`: Thread-local region management
- OWIZ commands: `GetBasePriceCommand`, `CheckRegionalOverrideCommand`, `ApplyPriceRulesCommand`, `ConvertCurrencyCommand`, `CalculatePriceTaxCommand`, `BuildPriceResultCommand`

**Incoming Dependencies**:
- `handmade-product-management` (variant pricing)
- `handmade-workflow-orchestrator/product-orchestration`

**Outgoing Dependencies**:
- `handmade-app-core`
- `handmade-rules-api`

**State Machines**:
- Price STM: DRAFT → ACTIVE → ON_SALE → EXPIRED → ARCHIVED

---

#### handmade-search-management

**Observed Responsibility**: Search indexing and query execution

**Owned Domain Objects**:
- Search indices
- Query models

**Public Entry Points**:
- `SearchController`: Search API

**Internal Components**:
- `catalog-search`: Catalog-specific search
- `handmade-elasticsearch-provider`: Elasticsearch integration
- `handmade-query-service`: Query processing
- `search-core`: Core search logic
- `search-api`: Search contracts

**Incoming Dependencies**:
- `handmade-catalog-management` (indexing)

**Outgoing Dependencies**:
- `handmade-app-core`
- Elasticsearch (external)

---

### Seller Domain Modules

#### handmade-seller-management

**Observed Responsibility**: Seller lifecycle management with state machines

**Owned Domain Objects**:
- `Seller`: Core seller entity
- `SellerProfile`: Seller profile information
- `SellerConfiguration`: Seller settings
- `SellerMetrics`: Performance metrics
- `SellerPaymentInfo`: Payment details
- `BankingInfo`: Banking details
- `TaxInfo`, `TaxRegistration`: Tax information
- `BusinessDetails`: Business information
- `SellerActivityLog`: Audit trail
- `SellerTier`: Seller tier/level
- `PaymentMethodd`: Payment method configuration

**Public Entry Points**:
- Seller management API (controllers in `seller-service`)

**Internal Components**:
- `seller-service`: Business logic, STM actions
- `seller-domain`: Domain entities
- `seller-api`: Public contracts
- `seller-delegator`: Client for inter-module calls
- `seller-async`: Event listeners
- STM actions: `ApproveReinstatementAction`, etc.
- `SellerValidationServiceImpl`: Validation logic
- `SellerManagerClient`: Delegator client

**Incoming Dependencies**:
- `handmade-seller-account-management`
- `handmade-onboarding-policy`
- `handmade-workflow-orchestrator/seller-orchestration`

**Outgoing Dependencies**:
- `handmade-app-core`
- `handmade-event-management`

**Events**:
- Seller lifecycle events

**State Machines**:
- Seller STM (states NOT DERIVABLE FROM CODE without examining STM config)

---

#### handmade-seller-account-management

**Observed Responsibility**: Seller account and identity verification

**Owned Domain Objects**:
- Seller account entities
- Identity verification records

**Public Entry Points**:
- Seller account APIs

**Internal Components**:
- `seller-account-service`: Account management
- `seller-account-identity/identity-service`: Identity verification
- `seller-account-domain`: Domain entities
- `seller-account-api`: Public contracts
- `seller-account-delegator`: Client proxies
- `seller-account-async`: Event listeners
- `IdentityVerificationServiceTest`: Test evidence of identity verification

**Incoming Dependencies**:
- `handmade-seller-management`
- `handmade-onboarding-policy`

**Outgoing Dependencies**:
- `handmade-app-core`
- External identity verification providers

---

### Policy Modules

#### handmade-onboarding-policy

**Observed Responsibility**: Seller onboarding workflow and policy enforcement

**Owned Domain Objects**:
- Onboarding case entities
- Policy acceptance records

**Public Entry Points**:
- Onboarding API

**Internal Components**:
- `onboarding-service`: Onboarding logic
- `onboarding-domain`: Domain entities
- `onboarding-api`: Public contracts
- `onboarding-delegator`: Client proxies
- `onboarding-async`: Event listeners (`PolicyOnboardingListener`)

**Incoming Dependencies**:
- `handmade-seller-management`
- `handmade-seller-account-management`
- `handmade-compliance-policy`

**Outgoing Dependencies**:
- `handmade-app-core`
- `handmade-event-management`

**Events**:
- Listens to: Policy acceptance events
- Publishes: Onboarding state changes

---

#### handmade-compliance-policy

**Observed Responsibility**: Compliance rules and policy management

**Owned Domain Objects**:
- Compliance policy entities
- Policy rules

**Public Entry Points**:
- Compliance API

**Internal Components**:
- `compliance-service`
- `compliance-domain`
- `compliance-api`
- `compliance-delegator`
- `compliance-async`

**Incoming Dependencies**:
- `handmade-seller-management`
- `handmade-onboarding-policy`

**Outgoing Dependencies**:
- `handmade-app-core`

---

#### handmade-payout-policy

**Observed Responsibility**: Payout rules and scheduling

**Owned Domain Objects**:
- Payout policy entities
- Payout schedules

**Public Entry Points**:
- Payout policy API

**Internal Components**:
- `payout-service`
- `payout-domain`
- `payout-api`
- `payout-delegator`
- `payout-async`

**Incoming Dependencies**:
- `handmade-seller-management`
- `handmade-payment-management`

**Outgoing Dependencies**:
- `handmade-app-core`

---

#### handmade-commission-policy

**Observed Responsibility**: Commission calculation and policy enforcement

**Owned Domain Objects**:
- Commission policy entities
- Commission tiers

**Public Entry Points**:
- Commission API

**Internal Components**:
- `commission-service`
- `commission-domain`
- `commission-api`
- `commission-delegator`
- `commission-async`

**Incoming Dependencies**:
- `handmade-seller-management`
- `handmade-payment-management`

**Outgoing Dependencies**:
- `handmade-app-core`

---

#### handmade-promotion-policy

**Observed Responsibility**: Promotional campaigns and discount policies

**Owned Domain Objects**:
- Promotion entities
- Discount rules

**Public Entry Points**:
- Promotion API

**Internal Components**:
- `promotion-service`
- `promotion-domain`
- `promotion-api`
- `promotion-delegator`
- `promotion-async`

**Incoming Dependencies**:
- `handmade-product-management`
- `handmade-pricing-management`

**Outgoing Dependencies**:
- `handmade-app-core`

---

### Payment Domain Modules

#### handmade-payment-management

**Observed Responsibility**: Payment processing and transaction management

**Owned Domain Objects**:
- Payment entities
- Transaction records

**Public Entry Points**:
- Payment API

**Internal Components**:
- `payment-service`
- `payment-api`

**Incoming Dependencies**:
- Order management (NOT IN ACTIVE MODULES)
- `handmade-wallet-management`

**Outgoing Dependencies**:
- `handmade-app-core`
- `handmade-ledger-management`

---

#### handmade-ledger-management

**Observed Responsibility**: Financial ledger and accounting

**Owned Domain Objects**:
- Ledger entries
- Account balances

**Public Entry Points**:
- Ledger API

**Internal Components**:
- `ledger-service`
- `ledger-api`

**Incoming Dependencies**:
- `handmade-payment-management`
- `handmade-wallet-management`

**Outgoing Dependencies**:
- `handmade-app-core`

---

#### handmade-wallet-management

**Observed Responsibility**: Digital wallet management

**Owned Domain Objects**:
- Wallet entities
- Wallet transactions

**Public Entry Points**:
- Wallet API

**Internal Components**:
- `wallet-service`
- `wallet-api`

**Incoming Dependencies**:
- `handmade-payment-management`
- `handmade-seller-management`

**Outgoing Dependencies**:
- `handmade-app-core`
- `handmade-ledger-management`

---

### Platform & Orchestration Modules

#### handmade-platform-management

**Observed Responsibility**: Platform-wide configuration, tenancy, and access control

**Owned Domain Objects**:
- Platform configuration entities
- Tenant entities
- Access control models

**Public Entry Points**:
- Platform API

**Internal Components**:
- `platform-service`: Platform logic
- `platform-domain`: Domain entities
- `platform-core`: Core platform utilities
- `platform-api`: Public contracts
- `platform-delegator`: Client proxies
- `platform-async`: Event listeners
- `GlobalExceptionHandler`: Exception handling

**Incoming Dependencies**:
- All modules (platform services)

**Outgoing Dependencies**:
- `handmade-app-core`

---

#### handmade-workflow-orchestrator

**Observed Responsibility**: Cross-module workflow orchestration

**Owned Domain Objects**:
- Orchestration state entities

**Public Entry Points**:
- Orchestration APIs

**Internal Components**:
- `product-orchestration`: Product workflow orchestration
- `seller-orchestration`: Seller workflow orchestration
  - `SellerOnboardingServiceImpl`
  - `CheckExistingCaseCommand`
- `payout-orchestration`: Payout workflow orchestration

**Incoming Dependencies**:
- `handmade-product-management`
- `handmade-seller-management`
- `handmade-pricing-management`

**Outgoing Dependencies**:
- `handmade-app-core`
- Multiple domain modules (orchestrates across boundaries)

---

## Inter-Module Communication

### Communication Patterns

#### Delegator Pattern
- Modules expose `{module}-delegator` for synchronous inter-module calls
- Delegators contain client interfaces (e.g., `SellerManagerClient`)
- Used for request-response interactions

#### Event-Driven Communication
- Modules publish domain events via `{module}-async`
- Events consumed by listeners in other modules
- Examples:
  - Product events → Catalog listeners
  - Policy acceptance events → Onboarding listeners
  - Seller events → Account management listeners

#### Orchestration Pattern
- `handmade-workflow-orchestrator` coordinates multi-module workflows
- Orchestrators call multiple modules via delegators
- Used for complex business processes (onboarding, product creation)

### Observed Event Flows

1. **Product Lifecycle**:
   - `handmade-product-management` publishes product events
   - `handmade-catalog-management` listens and updates catalog
   - `handmade-search-management` indexes for search

2. **Seller Onboarding**:
   - `handmade-onboarding-policy` coordinates onboarding
   - `handmade-compliance-policy` validates compliance
   - `handmade-seller-account-management` manages account creation
   - `handmade-seller-management` activates seller

3. **Pricing Calculation**:
   - `handmade-pricing-management` uses OWIZ orchestration internally
   - Regional strategies (`pricing-usd`, `pricing-inr`) provide localization
   - Rules engine applies discounts and taxes

---

## Architectural Rules (Observed)

### Module Structure Enforcement
- Consistent layering: api → domain → service → delegator → async
- API modules contain only interfaces and DTOs (no implementations)
- Domain modules contain only JPA entities (no business logic)
- Service modules contain controllers, repositories, and STM actions

### Dependency Rules
- All modules depend on `handmade-app-core` (shared foundation)
- No circular dependencies between domain modules
- Delegators used for synchronous cross-module calls
- Events used for asynchronous cross-module communication

### State Machine (STM) Pattern
- Chenile STM framework used for entity lifecycle management
- Observed in: Product, Variant, Price, Seller (likely others)
- STM actions implement state transitions
- Post-save hooks trigger side effects

### Event Infrastructure
- Two event bus implementations: in-memory and queue-based
- Outbox pattern via `OutboxEvent` and `DomainEventStore`
- Event listeners in `{module}-async` modules

### Database Management
- Liquibase for schema migrations
- JPA/Hibernate for ORM
- Repositories follow Spring Data JPA patterns

---

## Unknowns / Gaps

### Modules Present in Filesystem but NOT in Active Build
The following modules exist in the filesystem but are NOT listed in the root POM (as of the provided codebase state):
- `handmade-artisan-management`
- `handmade-cart-management`
- `handmade-cash-management`
- `handmade-customer-management`
- `handmade-dispute-management`
- `handmade-ein-management`
- `handmade-fulfillment-management`
- `handmade-gstin-management`
- `handmade-inventory-management`
- `handmade-kyc-verification`
- `handmade-notification-management`
- `handmade-offer-management`
- `handmade-onboarding-management` (distinct from `handmade-onboarding-policy`)
- `handmade-order-management`
- `handmade-payment-executor`
- `handmade-payment-orchestrator`
- `handmade-paymentorder-management`
- `handmade-razorpay-integration`
- `handmade-reconciliation-management`
- `handmade-review-management`
- `handmade-risk-management`
- `handmade-security-management`
- `handmade-shipping-management`
- `handmade-stripe-integration`
- `handmade-user-management`

**Status**: These modules are NOT DERIVABLE as active components from the current build configuration.

### Specific Module Details NOT DERIVABLE FROM CODE
- Exact STM state transitions for each entity (requires examining XML/config files)
- Complete API endpoint mappings (requires examining all controllers)
- Exact event schemas and contracts (requires examining event classes)
- Database schema details (requires examining Liquibase changelogs)
- External service integrations (Stripe, Razorpay, etc.) - modules exist but not active

### Cross-Cutting Concerns
- Authentication and authorization mechanisms: NOT DERIVABLE FROM CODE
- API gateway or routing layer: NOT DERIVABLE FROM CODE
- Monitoring and observability: NOT DERIVABLE FROM CODE
- Caching strategy: NOT DERIVABLE FROM CODE

---

## Verification Statement

**This ARCHITECTURE.md was generated strictly from the provided codebase. No assumptions, redesigns, or new modules were introduced.**

All information was derived from:
- Root POM module declarations
- Directory and package structures
- Maven POM dependencies
- Java class and interface names
- Controller, service, and repository patterns
- Event listener implementations
- Domain entity discoveries

Where information could not be conclusively determined from code, it was explicitly marked as "NOT DERIVABLE FROM CODE".
