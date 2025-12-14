# Handmade E-commerce - Architecture Design Document

Version: 1.0  
Last Updated: 2025-12-01  
Author: Cherukupally Premkumar

---

## Table of Contents
1. Architecture Philosophy
2. Why Modular Monolith?
3. Why Chenile Framework?
4. System Architecture
5. Module Structure
6. Payment Management Deep Dive
7. Data Architecture
8. Deployment Strategy
9. Migration Path to Microservices

---

## 1. Architecture Philosophy

### 1.1 Core Principles

For MVP (Current Phase):
- Single Developer Friendly - Easy to develop and debug
- Single Deployment Unit - One JAR/WAR to deploy
- Clear Module Boundaries - Logical separation, physical together
- Rapid Development - Focus on features, not infrastructure

For Future (Scale Phase):
- Microservice Ready - Modules can be extracted independently
- Team Scalability - Each module can be owned by a team
- Independent Deployment - Deploy modules separately when needed

### 1.2 Trade-offs Accepted

Development Speed:
- Monolith: Fast
- Microservices: Slow
- Our Choice: Monolith for MVP

Deployment:
- Monolith: Simple
- Microservices: Complex
- Our Choice: Monolith for MVP

Debugging:
- Monolith: Easy
- Microservices: Hard
- Our Choice: Monolith for MVP

Performance:
- Monolith: Shared resources (concern for later)
- Microservices: Isolated
- Our Choice: Accept monolith limitation for now

Scalability:
- Monolith: Scale all together
- Microservices: Scale parts independently
- Our Choice: Accept monolith limitation for now

Team Size:
- Monolith: Best for 1-5 developers
- Microservices: Best for 5+ developers
- Our Choice: Monolith (1 developer currently)

Verdict: For a solo developer building an MVP, Modular Monolith is the optimal choice.

---

## 2. Why Modular Monolith?

### 2.1 Advantages for MVP

Easy to Develop:
- Single codebase to navigate
- No network calls between modules (in-process)
- Shared libraries (no duplication)
- Single IDE project

Easy to Deploy:
- One artifact (handmade-backend.jar)
- One server to manage
- No service discovery needed
- No API gateway needed

Clear Module Boundaries:
- Maven multi-module structure
- Each module has -api and -service
- Enforced dependencies (Maven)
- Can extract to microservice later

### 2.2 Drawbacks (and Why We Accept Them)

Performance Issue:
- Problem: All modules share same JVM/CPU/Memory
- Why OK: MVP has small user base (less than 1000 users)
- Future Plan: Extract high-load modules (e.g., Payment) to separate service

Scalability Issue:
- Problem: Cannot scale individual modules
- Why OK: MVP traffic is low (less than 100 TPS)
- Future Plan: Horizontal scaling (multiple instances) or extract modules

Fault Isolation Issue:
- Problem: One module crash equals entire app crash
- Why OK: Proper error handling and circuit breakers
- Future Plan: Extract critical modules (Payment, Order)

---

## 3. Why Chenile Framework?

### 3.1 Framework Selection Criteria

Spring Boot:
- Pros: Industry standard, huge ecosystem
- Cons: Boilerplate code, no standards
- Score: 7/10

Quarkus:
- Pros: Fast startup, native compilation
- Cons: Smaller ecosystem
- Score: 6/10

Micronaut:
- Pros: Low memory, fast
- Cons: Learning curve
- Score: 6/10

Chenile:
- Pros: RAD, standards, modular monolith
- Cons: Smaller community
- Score: 9/10 (Selected)

### 3.2 Chenile Framework Benefits

Rapid Application Development (RAD):
- Provides conventions, not configuration
- Auto-wired, auto-configured components
- Less boilerplate code

Built for Modular Monoliths:
- Module isolation (API + Service pattern)
- Workflow orchestration (OWIZ)
- State machine support (STM)
- Security interceptors
- Cucumber testing utilities

Industrial Strength:
- Used in production systems
- Proven patterns (by @RajaShankar)
- Spring Boot underneath (familiar)
- Microservice migration path

Learn More: https://chenile.org

---

## 4. System Architecture

### 4.1 High-Level Architecture

The system is organized as a single JAR file containing multiple modules:

Top Layer: API Gateway Layer
- Spring MVC Controllers
- Chenile Interceptors

Middle Layer: Business Modules (Orchestrated by Chenile)
- Seller Management
- Customer Management
- Catalog Management
- Order Management
- Payment Management
- Inventory Management
- Shipping Management
- Dispute Management
- Analytics Management
- Messaging Management
- Content Management
- Event Management

Bottom Layer: Data Access Layer
- JPA/Hibernate

Database: PostgreSQL (Single Database)

### 4.2 Module Communication

Within Monolith (In-Process):
- Direct method calls (fast)
- No network overhead
- Example: walletService.credit(sellerId, amount)

External Systems (HTTP/REST):
- PSPs, Email, SMS
- Network calls
- Example: razorpayClient.createCheckoutSession(...)

---

## 5. Module Structure

### 5.1 Standard Module Pattern

Every module follows this structure:

module-name/
- module-api/ (Interfaces and DTOs)
  - dto/ (Request/Response DTOs)
  - model/ (Domain entities)
  - service/ (Service interfaces)
  
- module-service/ (Implementation)
  - service/impl/ (Service implementations)
  - dao/ (Repositories)
  - controller/ (REST controllers)
  - resources/module-core.xml (Chenile workflows)

### 5.2 Current Modules

Seller Management:
- Purpose: Seller onboarding, KYC
- Status: Active

Customer Management:
- Purpose: User accounts, profiles
- Status: Active

Catalog Management:
- Purpose: Products, categories
- Status: Active

Order Management:
- Purpose: Cart, checkout, orders
- Status: Active

Payment Management:
- Purpose: Payments, wallet, ledger
- Status: In Progress

Inventory Management:
- Purpose: Stock, warehouses
- Status: Planned

Shipping Management:
- Purpose: Logistics, tracking
- Status: Planned

Analytics Management:
- Purpose: Reports, dashboards
- Status: Planned

---

## 6. Payment Management Deep Dive

### 6.1 Payment Module Architecture

payment-management/
- payment/ (Core payment logic)
  - payment-api/ (DTOs, Entities, Interfaces)
  - payment-service/ (Core payment logic)

- wallet/ (Seller balance management)
  - wallet-api/
  - wallet-service/

- ledger/ (Double-entry accounting)
  - ledger-api/
  - ledger-service/

- reconciliation/ (Daily settlement verification)
  - reconciliation-api/
  - reconciliation-service/

- razorpay/ (Razorpay PSP adapter)
  - razorpay-api/
  - razorpay-service/

- stripe/ (Stripe PSP adapter)
  - stripe-api/
  - stripe-service/

- cash/ (Cash on delivery)
  - cash-api/
  - cash-service/

- tax/ (Tax calculation)
  - tax-api/
  - tax-service/

- gstin/ (India tax validation)
  - gstin-api/
  - gstin-service/

- ein/ (US tax validation)
  - ein-api/
  - ein-service/

- orchestrator/ (Chenile workflow orchestration)
  - orchestrator-api/
  - orchestrator-service/

### 6.2 Payment Flow (Hosted Payment Page)

Phase 1: Checkout Session Creation (Synchronous)
1. Frontend sends request to PaymentController
2. PaymentController calls PaymentProcessingService
3. PaymentProcessingService calls PaymentExecutor
4. PaymentExecutor calls RazorpayService or StripeService
5. PSP Service calls PSP API
6. PSP returns checkoutUrl
7. Frontend receives checkoutUrl

Phase 2: Payment Confirmation (Asynchronous)
1. PSP sends webhook to WebhookController
2. WebhookController updates PaymentOrderRepository
3. WalletService credits seller account
4. LedgerService records transaction
5. Database updated

### 6.3 Chenile Orchestration (payment-core.xml)

Flow: initiate-payment-flow
Steps:
1. validate-payment-request
2. risk-check-service
3. create-payment-event-service
4. psp-registration-router (based on currency)
   - razorpay-checkout-service (for INR)
   - stripe-checkout-service (for USD)
5. persist-payment-token-service
6. construct-checkout-response

---

## 7. Data Architecture

### 7.1 Database Strategy

Current (Modular Monolith):
- Single PostgreSQL Database
- Logical separation using schemas:
  - seller_management schema
  - customer_management schema
  - catalog_management schema
  - order_management schema
  - payment_management schema

Future (Microservices):
- Each module gets its own database
- Physical separation:
  - seller_db (PostgreSQL)
  - customer_db (PostgreSQL)
  - catalog_db (PostgreSQL)
  - order_db (PostgreSQL)
  - payment_db (PostgreSQL)

### 7.2 Payment Schema (Simplified)

Table: payment
- id (Primary Key)
- checkout_id
- buyer_id
- total_amount
- is_payment_done
- created_at

Table: payment_order
- id (Primary Key, Idempotency key)
- payment_event_id (Foreign Key to payment)
- seller_id
- amount
- currency
- status (NOT_STARTED, PENDING_USER_ACTION, SUCCESS, FAILED)
- psp_reference_id
- wallet_updated
- ledger_updated

Table: wallet
- seller_id (Primary Key)
- balance
- currency

Table: ledger_entry
- id (Primary Key)
- transaction_id
- account_id
- amount
- type (DEBIT or CREDIT)
- created_at

---

## 8. Deployment Strategy

### 8.1 Current Deployment (MVP)

Single Server (AWS EC2 or GCP):
- handmade-backend.jar (All modules in one JAR)
- Port: 8080
- PostgreSQL database
- Port: 5432

Deployment Command:
```
Build: mvn clean package
Deploy: java -jar app-boot/build-package/target/handmade-backend.jar
```

### 8.2 Future Deployment (Microservices)

Load Balancer at the top
Multiple services below:
- Seller Service
- Order Service
- Catalog Service
- Payment Service
- Customer Service

Each service has its own database

---

## 9. Migration Path to Microservices

### 9.1 When to Migrate?

Triggers:
- Team grows to 5+ developers
- Traffic exceeds 1000 TPS
- Need independent scaling (e.g., Payment module)
- Need independent deployment (e.g., hot-fix Payment)

### 9.2 Migration Strategy (Strangler Pattern)

Step 1: Extract Payment Module

Before:
- Monolith JAR contains:
  - Seller
  - Order
  - Payment (extract this first)
  - Catalog

After:
- Monolith JAR contains:
  - Seller
  - Order
  - Catalog
- Payment Service (Separate JAR)
- Monolith calls Payment Service via HTTP

Step 2: Extract Other Modules
- Repeat for Order, Seller, Catalog, etc.

### 9.3 Code Changes Required

Minimal changes because of clean module boundaries

Before (Monolith):
```java
@Autowired
private PaymentService paymentService;
paymentService.processPayment(request); // In-process call
```

After (Microservice):
```java
@Autowired
private PaymentServiceClient paymentServiceClient;
paymentServiceClient.processPayment(request); // HTTP call
```

The API contract (payment-api) remains the same.

---

## 10. Technology Stack

### 10.1 Core Technologies

Framework: Chenile (Latest)
Base: Spring Boot 3.x
Language: Java 17+
Build: Maven 3.9+
Database: PostgreSQL 15+
ORM: JPA/Hibernate 6.x
Testing: JUnit + Cucumber 5.x

### 10.2 Chenile Components Used

chenile-http: REST controllers
chenile-owiz: Workflow orchestration
chenile-stm: State machine
chenile-security: Authentication/Authorization
chenile-jpa-utils: JPA utilities
cucumber-utils: BDD testing

---

## 11. Development Workflow

### 11.1 Daily Development

1. Start database: docker-compose up -d postgres
2. Run application: mvn spring-boot:run
3. Test endpoint: curl http://localhost:8080/v1/payments
4. Run tests: mvn test

### 11.2 Adding a New Module

1. Create module structure:
   - mkdir -p new-module/new-module-api
   - mkdir -p new-module/new-module-service

2. Add to parent pom.xml:
   - Add module tag

3. Follow the pattern:
   - new-module-api/ (DTOs, Entities, Interfaces)
   - new-module-service/ (Implementation)

---

## 12. Key Decisions

Decision: Modular Monolith
Rationale: Solo developer, MVP speed
Date: 2025-11-01

Decision: Chenile Framework
Rationale: RAD, proven patterns, modular
Date: 2025-11-01

Decision: Single Database
Rationale: Simplicity, ACID transactions
Date: 2025-11-01

Decision: Hosted Payment Pages
Rationale: PCI DSS compliance
Date: 2025-11-28

Decision: PostgreSQL
Rationale: ACID, JSON support, mature
Date: 2025-11-01

---

## 13. Success Metrics

### 13.1 MVP Success Criteria

Development Speed: Launch MVP in 3 months (solo dev)
Deployment: Single command deployment
Performance: Less than 500ms API response time
Reliability: 99% uptime
Scalability: Support 1000 concurrent users

### 13.2 Migration Readiness

Module Independence: Each module can run standalone
API Contracts: Well-defined DTOs
Database Schemas: Logically separated
Testing: 80%+ code coverage

---

## 14. Conclusion

The Modular Monolith architecture using Chenile Framework is the optimal choice for the Handmade e-commerce MVP because:

1. Solo Developer Friendly - Fast development, easy debugging
2. Production Ready - Industrial-strength framework
3. Future Proof - Clear migration path to microservices
4. Cost Effective - Single server, simple infrastructure

Next Steps:
1. Review this architecture
2. Complete payment module implementation
3. Add remaining modules (Inventory, Shipping, etc.)
4. Launch MVP
5. Scale to microservices (when needed)

---

Author: Cherukupally Premkumar  
Framework: Chenile by @RajaShankar  
Learn More: https://chenile.org
