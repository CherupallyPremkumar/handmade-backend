# Frontend Platform Architecture

**Platform**: Handmade Admin Console  
**Style**: Amazon Seller Central / Stripe Dashboard  
**Backend**: Already exists (microservices)  
**Frontend Stack**: React/Next.js + BFF (Backend-For-Frontend)

---

## 🎯 Core Principle

> **Frontend observes and controls. It does not decide.**

The UI governs money, compliance, and legal risk. All business logic, policy resolution, and workflow execution happens in the backend. The frontend is a **state-driven visualization layer**.

---

## 1️⃣ Architecture Rules (Non-Negotiable)

### ❌ What Frontend MUST NOT Do
- ❌ No direct DB access
- ❌ No direct domain logic
- ❌ No workflow execution
- ❌ No policy resolution logic
- ❌ No country/seller rule computation
- ❌ No state machine transitions
- ❌ No compliance calculations

### ✅ What Frontend MUST Do
- ✅ Call BFF APIs only
- ✅ BFF calls backend platform services
- ✅ BFF aggregates multiple backend APIs
- ✅ UI is state-driven, not logic-driven
- ✅ Render workflows visually
- ✅ Display audit trails
- ✅ Provide admin controls

---

## 2️⃣ High-Level Architecture

```
┌─────────────────────────────────────────┐
│         Browser UI (React/Next.js)      │
│  - Rendering                            │
│  - Form validation (UI-level)          │
│  - Role-based UI                        │
│  - UX workflows                         │
└──────────────────┬──────────────────────┘
                   │
                   ↓
┌─────────────────────────────────────────┐
│    Frontend BFF (Node/NestJS/Next API)  │
│  - API aggregation                      │
│  - DTO shaping                          │
│  - Pagination normalization             │
│  - Version compatibility                │
│  - Caching (UI-friendly)                │
│  - Permission checks (UI scope)         │
│  - Workflow visualization mapping       │
└──────────────────┬──────────────────────┘
                   │
                   ↓
┌─────────────────────────────────────────┐
│         Backend Services (Java)         │
├─────────────────────────────────────────┤
│ • Platform Management Service           │
│ • Onboarding Policy Service             │
│ • Commission Policy Service             │
│ • Payout Policy Service                 │
│ • Compliance Policy Service             │
│ • Seller Management Service             │
│ • Workflow Orchestrator                 │
└─────────────────────────────────────────┘
```

---

## 3️⃣ Responsibilities Breakdown

### Frontend (React / Next.js)
**Location**: `/Users/premkumar/Desktop/homebase-admin-suite`

**Responsibilities**:
- Rendering admin dashboards
- Form validation (UI-level only)
- Role-based UI (show/hide based on permissions)
- UX workflows (multi-step forms, wizards)
- Calling BFF endpoints
- Real-time updates (WebSocket/SSE from BFF)
- Client-side state management (React Query, Zustand)

**Does NOT**:
- Decide policy applicability
- Execute workflow transitions
- Calculate commissions/payouts
- Validate business rules

### BFF (Backend-For-Frontend)
**Location**: `/Users/premkumar/Desktop/homebase-bff`

**Responsibilities**:
- API aggregation (combine multiple backend calls)
- DTO shaping (transform backend DTOs to UI-friendly format)
- Pagination normalization
- Version compatibility (handle backend API changes)
- Caching (UI-friendly, short-lived)
- Permission checks (UI scope only)
- Workflow visualization mapping
- GraphQL gateway (optional)

**Example Aggregation**:
```typescript
// BFF aggregates seller + policy + workflow state
GET /bff/sellers/{id}/onboarding
→ Calls:
  - GET /seller-management/sellers/{id}
  - GET /onboarding-policy/policies/{policyId}
  - GET /workflow-orchestrator/workflows/{workflowId}/state
  - GET /audit-service/events?sellerId={id}
→ Returns single UI DTO
```

### Backend (Already Exists)
**Location**: `/Users/premkumar/Desktop/handmade`

**Responsibilities**:
- Policy governance (create, version, lock)
- State machines (seller onboarding, order fulfillment)
- Compliance enforcement
- Audit logs
- Regulatory logic
- Workflow orchestration
- Domain events
- Data persistence

---

## 4️⃣ BFF API Design Rules

### Principle
**BFF endpoints are UI-specific, not domain-specific.**

### Examples

#### ❌ BAD (Domain-style)
```
GET /onboarding-policy/resolve
POST /seller-management/create
GET /workflow-orchestrator/execute
```

#### ✅ GOOD (UI-style)
```
GET  /bff/platform/policies/onboarding
GET  /bff/sellers/{id}/onboarding-timeline
GET  /bff/workflows/seller-onboarding/graph
GET  /bff/sellers/{id}/audit-trail
POST /bff/policies/onboarding/create-version
GET  /bff/dashboard/metrics
```

### BFF Endpoint Categories

| Category | Examples |
|----------|----------|
| **Dashboard** | `/bff/dashboard/metrics`, `/bff/dashboard/alerts` |
| **Policies** | `/bff/policies/onboarding`, `/bff/policies/commission/versions` |
| **Sellers** | `/bff/sellers/{id}/overview`, `/bff/sellers/{id}/timeline` |
| **Workflows** | `/bff/workflows/{type}/graph`, `/bff/workflows/{id}/state` |
| **Audit** | `/bff/audit/events`, `/bff/audit/changes` |
| **Approvals** | `/bff/approvals/pending`, `/bff/approvals/{id}/history` |

---

## 5️⃣ Policy Management Flow (UI)

### When Admin Edits a Policy

```
┌─────────┐
│ Admin   │
│ UI      │
└────┬────┘
     │ 1. Edit policy form
     ↓
┌─────────────────┐
│ BFF             │
│ POST /bff/      │
│ policies/       │
│ onboarding/     │
│ create-version  │
└────┬────────────┘
     │ 2. Validate payload shape
     │ 3. Call backend
     ↓
┌─────────────────────────┐
│ Backend Policy Service  │
│ POST /onboarding-policy │
│ /policies               │
└────┬────────────────────┘
     │ 4. Create new version
     │ 5. Lock previous version
     │ 6. Audit log
     ↓
┌─────────────────┐
│ BFF             │
│ Returns UI DTO  │
└────┬────────────┘
     │ 7. UI-ready response
     ↓
┌─────────┐
│ Admin   │
│ UI      │
│ Refresh │
└─────────┘
```

**⚠️ UI never decides policy behavior**

---

## 6️⃣ Seller Onboarding Visualization Flow

### When Admin Views Seller

```typescript
// 1. UI Request
GET /bff/sellers/SEL-123/onboarding

// 2. BFF Aggregates
const seller = await sellerClient.getSeller(sellerId);
const policy = await policyClient.getPolicy(seller.lockedPolicyId);
const workflow = await workflowClient.getWorkflowState(seller.workflowId);
const events = await auditClient.getEvents({ sellerId });

// 3. BFF Returns Single DTO
{
  seller: { id, name, country, status },
  policy: { id, version, requirements },
  timeline: [
    { step: "Identity Verification", status: "completed", timestamp },
    { step: "Tax Verification", status: "in_progress", timestamp },
    { step: "Bank Verification", status: "pending", timestamp }
  ],
  auditTrail: [...]
}

// 4. UI Renders Timeline
<SellerOnboardingTimeline data={response.timeline} />
```

---

## 7️⃣ Folder Structure

### Frontend (`/Users/premkumar/Desktop/homebase-admin-suite`)
```
homebase-admin-suite/
├── app/                      # Next.js app router
│   ├── (auth)/
│   ├── (dashboard)/
│   ├── policies/
│   ├── sellers/
│   └── workflows/
├── components/
│   ├── ui/                   # shadcn/ui components
│   ├── policies/
│   ├── sellers/
│   └── workflows/
├── features/
│   ├── policies/
│   │   ├── components/
│   │   ├── hooks/
│   │   └── types/
│   ├── sellers/
│   └── workflows/
├── lib/
│   ├── api-client.ts         # BFF client
│   ├── auth.ts
│   └── utils.ts
├── auth/
└── config/
```

### BFF (`/Users/premkumar/Desktop/homebase-bff`)
```
homebase-bff/
├── src/
│   ├── routes/
│   │   ├── policies.ts
│   │   ├── sellers.ts
│   │   ├── workflows.ts
│   │   └── dashboard.ts
│   ├── controllers/
│   ├── mappers/              # Backend DTO → UI DTO
│   │   ├── policy-mapper.ts
│   │   ├── seller-mapper.ts
│   │   └── workflow-mapper.ts
│   ├── clients/              # Backend service clients
│   │   ├── platform-client.ts
│   │   ├── policy-client.ts
│   │   ├── seller-client.ts
│   │   └── workflow-client.ts
│   ├── dto/                  # UI-specific DTOs
│   │   ├── policy-dto.ts
│   │   ├── seller-dto.ts
│   │   └── workflow-dto.ts
│   ├── middleware/
│   └── config/
├── package.json
└── tsconfig.json
```

---

## 8️⃣ Key UX Screens

### 1. Platform Selector
**Purpose**: Multi-platform support (India, USA, etc.)  
**API**: `GET /bff/platforms`

### 2. Policy Version Manager
**Purpose**: Create, view, compare policy versions  
**APIs**:
- `GET /bff/policies/{type}/versions`
- `POST /bff/policies/{type}/create-version`
- `GET /bff/policies/{type}/compare?v1={v1}&v2={v2}`

### 3. Seller Onboarding Timeline
**Purpose**: Visual workflow progress  
**API**: `GET /bff/sellers/{id}/onboarding-timeline`

**Components**:
- Timeline stepper
- Step details (requirements, status, timestamps)
- Action buttons (approve, reject, request info)

### 4. Workflow Graph Viewer
**Purpose**: Visualize state machine  
**API**: `GET /bff/workflows/{type}/graph`

**Visualization**: Mermaid/D3.js graph

### 5. Audit Log Viewer
**Purpose**: Full audit trail  
**API**: `GET /bff/audit/events?entityId={id}&entityType={type}`

### 6. Approval Queue (Maker-Checker)
**Purpose**: Two-person approval workflow  
**APIs**:
- `GET /bff/approvals/pending`
- `POST /bff/approvals/{id}/approve`
- `POST /bff/approvals/{id}/reject`

---

## 9️⃣ Non-Functional Requirements

### Zero Business Logic in UI
- ✅ UI displays data
- ✅ UI triggers actions
- ❌ UI does NOT compute results

### BFF is Replaceable
- BFF can be rewritten without changing backend
- Backend APIs are stable contracts
- UI depends on BFF DTOs, not backend DTOs

### Backend Changes Must Not Break UI
- BFF acts as anti-corruption layer
- Backend versioning handled by BFF
- UI only knows BFF contract

### Full Auditability
- Every admin action logged
- Audit trail visible in UI
- Maker-checker for critical operations

### Production-Safe UX
- Confirmation dialogs for destructive actions
- Read-only mode for viewers
- Role-based access control
- Optimistic UI updates with rollback

---

## 🔟 Data Flow Examples

### Example 1: Admin Creates New Onboarding Policy

```typescript
// UI Component
const handleCreatePolicy = async (formData) => {
  const response = await bffClient.post('/bff/policies/onboarding/create-version', {
    country: 'IN',
    requirements: {
      identityVerification: { provider: 'DIGILOCKER', required: true },
      taxVerification: { provider: 'GSTIN', required: true },
      bankVerification: { required: true }
    }
  });
  
  toast.success('Policy version created');
  router.push(`/policies/onboarding/${response.policyId}`);
};

// BFF Controller
POST /bff/policies/onboarding/create-version
→ Validates request
→ Calls: POST /onboarding-policy/policies
→ Maps response to UI DTO
→ Returns: { policyId, version, createdAt, createdBy }

// Backend Service
POST /onboarding-policy/policies
→ Creates new policy version
→ Locks previous version
→ Publishes PolicyCreatedEvent
→ Audit log entry
→ Returns domain DTO
```

### Example 2: Admin Views Seller Onboarding Progress

```typescript
// UI Component
const { data, isLoading } = useQuery({
  queryKey: ['seller-onboarding', sellerId],
  queryFn: () => bffClient.get(`/bff/sellers/${sellerId}/onboarding`)
});

// BFF Aggregation
GET /bff/sellers/{sellerId}/onboarding
→ Parallel calls:
  1. GET /seller-management/sellers/{sellerId}
  2. GET /onboarding-policy/policies/{policyId}
  3. GET /workflow-orchestrator/workflows/{workflowId}/state
  4. GET /audit-service/events?sellerId={sellerId}
→ Aggregates into single DTO
→ Returns timeline + current state + next actions

// UI Renders
<SellerOnboardingDashboard>
  <Timeline steps={data.timeline} />
  <CurrentState state={data.currentState} />
  <NextActions actions={data.nextActions} />
  <AuditTrail events={data.auditTrail} />
</SellerOnboardingDashboard>
```

---

## 1️⃣1️⃣ Backend Service Endpoints (Reference)

### Platform Management
```
GET  /platform-management/platforms
GET  /platform-management/platforms/{id}
POST /platform-management/platforms
```

### Onboarding Policy
```
GET  /onboarding-policy/policies
GET  /onboarding-policy/policies/{id}
POST /onboarding-policy/policies
GET  /onboarding-policy/policies/{id}/versions
```

### Seller Management
```
GET  /seller-management/sellers
GET  /seller-management/sellers/{id}
POST /seller-management/sellers
PUT  /seller-management/sellers/{id}
```

### Workflow Orchestrator
```
GET  /workflow-orchestrator/workflows/{id}/state
POST /workflow-orchestrator/workflows/{id}/transition
GET  /workflow-orchestrator/workflows/{id}/history
```

---

## 1️⃣2️⃣ Technology Stack

### Frontend
- **Framework**: Next.js 14 (App Router)
- **UI Library**: React 18
- **Styling**: Tailwind CSS + shadcn/ui
- **State**: React Query + Zustand
- **Forms**: React Hook Form + Zod
- **Charts**: Recharts / Chart.js
- **Graphs**: Mermaid / React Flow

### BFF
- **Runtime**: Node.js 20
- **Framework**: NestJS / Next.js API Routes
- **HTTP Client**: Axios
- **Validation**: Zod
- **Caching**: Redis (optional)
- **Auth**: JWT validation

### Backend (Existing)
- **Language**: Java 17
- **Framework**: Spring Boot + Chenile
- **Database**: PostgreSQL
- **Message Queue**: RabbitMQ / Kafka
- **Cache**: Redis

---

## 1️⃣3️⃣ Security & Auth

### Authentication Flow
```
1. Admin logs in → Auth service
2. Auth service returns JWT
3. Frontend stores JWT (httpOnly cookie)
4. BFF validates JWT on every request
5. BFF passes user context to backend
6. Backend enforces permissions
```

### Role-Based Access Control

| Role | Permissions |
|------|-------------|
| **Super Admin** | All operations |
| **Platform Admin** | Manage policies, view all sellers |
| **Seller Support** | View sellers, trigger workflows |
| **Auditor** | Read-only access to audit logs |

---

## 1️⃣4️⃣ Final Instruction

> **Build this UI as if it governs money, compliance, and legal risk.**  
> **UI observes and controls. It does not decide.**

### Checklist for Every Feature
- [ ] Does UI call BFF only?
- [ ] Does BFF aggregate backend APIs?
- [ ] Is business logic in backend?
- [ ] Is audit trail captured?
- [ ] Is role-based access enforced?
- [ ] Can UI be replaced without changing backend?
- [ ] Is workflow visualization clear?
- [ ] Are destructive actions confirmed?

---

## ✅ Result

If implemented correctly:
- ✅ Backend stays clean and authoritative
- ✅ Frontend stays simple and maintainable
- ✅ Policies remain the source of truth
- ✅ Workflows are visual, not embedded in UI
- ✅ Platform can scale like Amazon/Stripe
- ✅ Compliance and audit requirements met
- ✅ Multi-platform support is seamless
- ✅ Admin experience is world-class
