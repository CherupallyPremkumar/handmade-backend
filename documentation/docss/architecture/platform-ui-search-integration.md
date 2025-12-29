# Platform UI Search Integration

**Scope**: Search for Platforms, Policies, Sellers, and Seller Approvals  
**Architecture**: Backend Search Service → BFF Aggregation → Frontend UI

---

## Overview

```
┌─────────────────────────────────────────┐
│   Frontend (React/Next.js)              │
│   - Platform search                     │
│   - Policy search                       │
│   - Seller search                       │
│   - Seller approval search              │
└──────────────────┬──────────────────────┘
                   │
                   ↓
┌─────────────────────────────────────────┐
│   BFF (Backend-For-Frontend)            │
│   - /bff/search/platforms               │
│   - /bff/search/policies                │
│   - /bff/search/sellers                 │
│   - /bff/search/seller-approvals        │
│   - Aggregates backend search + details │
└──────────────────┬──────────────────────┘
                   │
                   ↓
┌─────────────────────────────────────────┐
│   Backend Search Service                │
│   - Elasticsearch provider              │
│   - Index: platforms, policies,         │
│     sellers, seller-approvals           │
└─────────────────────────────────────────┘
```

---

## 1. Backend Search DTOs

### Platform Search Document
```java
// In platform-service
package com.handmade.ecommerce.platform.service.search;

@Data
@Builder
public class PlatformSearchDocument {
    private String id;
    private String name;
    private String code;  // e.g., "IN", "US"
    private String country;
    private String currency;
    private String status;  // ACTIVE, INACTIVE
    private String timezone;
    private LocalDateTime createdAt;
    private Map<String, Object> metadata;
}
```

### Policy Search Document
```java
// In onboarding-service (similar for other policies)
package com.handmade.ecommerce.onboarding.service.search;

@Data
@Builder
public class PolicySearchDocument {
    private String id;
    private String policyType;  // ONBOARDING, COMMISSION, PAYOUT, COMPLIANCE, PROMOTION
    private String version;
    private String platformId;
    private String platformName;
    private String status;  // DRAFT, ACTIVE, LOCKED, ARCHIVED
    private String country;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private String createdBy;
    private LocalDateTime createdAt;
    private Map<String, Object> requirements;  // Policy-specific requirements
}
```

### Seller Search Document
```java
// In seller-service
package com.handmade.ecommerce.seller.service.search;

@Data
@Builder
public class SellerSearchDocument {
    private String id;
    private String businessName;
    private String email;
    private String phone;
    private String country;
    private String platformId;
    private String status;  // PENDING, ACTIVE, SUSPENDED, REJECTED
    private String verificationStatus;  // UNVERIFIED, VERIFIED
    private String onboardingStatus;  // IN_PROGRESS, COMPLETED, FAILED
    private LocalDateTime registeredAt;
    private List<String> categories;
    private Map<String, Object> businessDetails;
}
```

### Seller Approval Search Document
```java
// In seller-service
package com.handmade.ecommerce.seller.service.search;

@Data
@Builder
public class SellerApprovalSearchDocument {
    private String id;
    private String sellerId;
    private String businessName;
    private String platformId;
    private String currentState;  // IDENTITY_VERIFICATION, TAX_VERIFICATION, etc.
    private String approvalStatus;  // PENDING, APPROVED, REJECTED, MORE_INFO_REQUIRED
    private String assignedTo;  // Admin user ID
    private LocalDateTime submittedAt;
    private LocalDateTime lastUpdatedAt;
    private String lockedPolicyId;
    private String lockedPolicyVersion;
    private Map<String, Object> verificationData;
}
```

---

## 2. Elasticsearch Index Mappings

### Platform Index
```json
{
  "index": "handmade-platform",
  "mappings": {
    "properties": {
      "id": { "type": "keyword" },
      "name": { "type": "text", "analyzer": "standard" },
      "code": { "type": "keyword" },
      "country": { "type": "keyword" },
      "currency": { "type": "keyword" },
      "status": { "type": "keyword" },
      "timezone": { "type": "keyword" },
      "createdAt": { "type": "date" }
    }
  }
}
```

### Policy Index
```json
{
  "index": "handmade-policy",
  "mappings": {
    "properties": {
      "id": { "type": "keyword" },
      "policyType": { "type": "keyword" },
      "version": { "type": "keyword" },
      "platformId": { "type": "keyword" },
      "platformName": { "type": "text" },
      "status": { "type": "keyword" },
      "country": { "type": "keyword" },
      "effectiveFrom": { "type": "date" },
      "effectiveTo": { "type": "date" },
      "createdBy": { "type": "keyword" },
      "createdAt": { "type": "date" }
    }
  }
}
```

### Seller Index
```json
{
  "index": "handmade-seller",
  "mappings": {
    "properties": {
      "id": { "type": "keyword" },
      "businessName": { "type": "text", "analyzer": "standard" },
      "email": { "type": "keyword" },
      "phone": { "type": "keyword" },
      "country": { "type": "keyword" },
      "platformId": { "type": "keyword" },
      "status": { "type": "keyword" },
      "verificationStatus": { "type": "keyword" },
      "onboardingStatus": { "type": "keyword" },
      "registeredAt": { "type": "date" },
      "categories": { "type": "keyword" }
    }
  }
}
```

### Seller Approval Index
```json
{
  "index": "handmade-seller-approval",
  "mappings": {
    "properties": {
      "id": { "type": "keyword" },
      "sellerId": { "type": "keyword" },
      "businessName": { "type": "text" },
      "platformId": { "type": "keyword" },
      "currentState": { "type": "keyword" },
      "approvalStatus": { "type": "keyword" },
      "assignedTo": { "type": "keyword" },
      "submittedAt": { "type": "date" },
      "lastUpdatedAt": { "type": "date" },
      "lockedPolicyId": { "type": "keyword" },
      "lockedPolicyVersion": { "type": "keyword" }
    }
  }
}
```

---

## 3. Backend Search Indexing

### Platform Indexing
```java
// In platform-service
@Service
public class PlatformIndexer {
    
    @Autowired
    private SearchService searchService;
    
    @EventListener
    public void onPlatformCreated(PlatformCreatedEvent event) {
        Platform platform = event.getPlatform();
        
        PlatformSearchDocument doc = PlatformSearchDocument.builder()
            .id(platform.getId())
            .name(platform.getName())
            .code(platform.getCode())
            .country(platform.getCountry())
            .currency(platform.getCurrency())
            .status(platform.getStatus().name())
            .timezone(platform.getTimezone())
            .createdAt(platform.getCreatedAt())
            .build();
        
        searchService.index(IndexRequest.builder()
            .entityType("PLATFORM")
            .entityId(platform.getId())
            .document(objectMapper.convertValue(doc, Map.class))
            .build());
    }
}
```

### Policy Indexing
```java
// In onboarding-service (similar for other policies)
@Service
public class PolicyIndexer {
    
    @Autowired
    private SearchService searchService;
    
    @EventListener
    public void onPolicyCreated(PolicyCreatedEvent event) {
        OnboardingPolicy policy = event.getPolicy();
        
        PolicySearchDocument doc = PolicySearchDocument.builder()
            .id(policy.getId())
            .policyType("ONBOARDING")
            .version(policy.getVersion())
            .platformId(policy.getPlatformId())
            .status(policy.getStatus().name())
            .country(policy.getCountry())
            .effectiveFrom(policy.getEffectiveFrom())
            .createdBy(policy.getCreatedBy())
            .createdAt(policy.getCreatedAt())
            .build();
        
        searchService.index(IndexRequest.builder()
            .entityType("POLICY")
            .entityId(policy.getId())
            .document(objectMapper.convertValue(doc, Map.class))
            .build());
    }
}
```

### Seller Indexing
```java
// In seller-service
@Service
public class SellerIndexer {
    
    @Autowired
    private SearchService searchService;
    
    @EventListener
    public void onSellerRegistered(SellerRegisteredEvent event) {
        SellerAccount seller = event.getSeller();
        
        SellerSearchDocument doc = SellerSearchDocument.builder()
            .id(seller.getId())
            .businessName(seller.getBusinessName())
            .email(seller.getEmail())
            .phone(seller.getPhone())
            .country(seller.getCountry())
            .platformId(seller.getPlatformId())
            .status(seller.getStatus().name())
            .verificationStatus(seller.getVerificationStatus().name())
            .onboardingStatus(seller.getOnboardingStatus().name())
            .registeredAt(seller.getRegisteredAt())
            .build();
        
        searchService.index(IndexRequest.builder()
            .entityType("SELLER")
            .entityId(seller.getId())
            .document(objectMapper.convertValue(doc, Map.class))
            .build());
    }
}
```

### Seller Approval Indexing
```java
// In seller-service
@Service
public class SellerApprovalIndexer {
    
    @Autowired
    private SearchService searchService;
    
    @EventListener
    public void onSellerStateChanged(SellerStateChangedEvent event) {
        SellerAccount seller = event.getSeller();
        
        SellerApprovalSearchDocument doc = SellerApprovalSearchDocument.builder()
            .id(seller.getId())
            .sellerId(seller.getId())
            .businessName(seller.getBusinessName())
            .platformId(seller.getPlatformId())
            .currentState(seller.getCurrentState())
            .approvalStatus(seller.getApprovalStatus().name())
            .submittedAt(seller.getSubmittedAt())
            .lastUpdatedAt(LocalDateTime.now())
            .lockedPolicyId(seller.getLockedPolicyId())
            .lockedPolicyVersion(seller.getLockedPolicyVersion())
            .build();
        
        searchService.index(IndexRequest.builder()
            .entityType("SELLER_APPROVAL")
            .entityId(seller.getId())
            .document(objectMapper.convertValue(doc, Map.class))
            .build());
    }
}
```

---

## 4. BFF Search Endpoints

### Platform Search
```typescript
// BFF: /bff/search/platforms
POST /bff/search/platforms
{
  "query": "India",
  "filters": {
    "status": "ACTIVE",
    "country": "IN"
  },
  "pagination": { "page": 1, "size": 20 }
}

// Response
{
  "results": [
    {
      "id": "PLT-001",
      "name": "Handmade India",
      "code": "IN",
      "country": "India",
      "currency": "INR",
      "status": "ACTIVE",
      "timezone": "Asia/Kolkata"
    }
  ],
  "totalHits": 1,
  "facets": {
    "status": [
      { "value": "ACTIVE", "count": 5 },
      { "value": "INACTIVE", "count": 2 }
    ],
    "country": [
      { "value": "IN", "count": 3 },
      { "value": "US", "count": 2 }
    ]
  }
}
```

### Policy Search
```typescript
// BFF: /bff/search/policies
POST /bff/search/policies
{
  "query": "onboarding",
  "filters": {
    "policyType": "ONBOARDING",
    "platformId": "PLT-001",
    "status": "ACTIVE"
  },
  "pagination": { "page": 1, "size": 20 }
}

// Response
{
  "results": [
    {
      "id": "POL-001",
      "policyType": "ONBOARDING",
      "version": "1.0",
      "platformId": "PLT-001",
      "platformName": "Handmade India",
      "status": "ACTIVE",
      "country": "IN",
      "effectiveFrom": "2024-01-01T00:00:00Z",
      "createdBy": "admin@handmade.com"
    }
  ],
  "totalHits": 1,
  "facets": {
    "policyType": [
      { "value": "ONBOARDING", "count": 10 },
      { "value": "COMMISSION", "count": 8 }
    ],
    "status": [
      { "value": "ACTIVE", "count": 15 },
      { "value": "LOCKED", "count": 5 }
    ]
  }
}
```

### Seller Search
```typescript
// BFF: /bff/search/sellers
POST /bff/search/sellers
{
  "query": "leather goods",
  "filters": {
    "platformId": "PLT-001",
    "status": "ACTIVE",
    "verificationStatus": "VERIFIED"
  },
  "pagination": { "page": 1, "size": 20 }
}

// Response
{
  "results": [
    {
      "id": "SEL-001",
      "businessName": "Premium Leather Goods",
      "email": "seller@example.com",
      "country": "IN",
      "platformId": "PLT-001",
      "status": "ACTIVE",
      "verificationStatus": "VERIFIED",
      "onboardingStatus": "COMPLETED",
      "registeredAt": "2024-01-15T10:00:00Z"
    }
  ],
  "totalHits": 1,
  "facets": {
    "status": [
      { "value": "ACTIVE", "count": 100 },
      { "value": "PENDING", "count": 25 }
    ],
    "verificationStatus": [
      { "value": "VERIFIED", "count": 80 },
      { "value": "UNVERIFIED", "count": 45 }
    ]
  }
}
```

### Seller Approval Search
```typescript
// BFF: /bff/search/seller-approvals
POST /bff/search/seller-approvals
{
  "query": "",
  "filters": {
    "approvalStatus": "PENDING",
    "platformId": "PLT-001",
    "currentState": "IDENTITY_VERIFICATION"
  },
  "sort": { "field": "submittedAt", "order": "DESC" },
  "pagination": { "page": 1, "size": 20 }
}

// Response
{
  "results": [
    {
      "id": "SEL-002",
      "sellerId": "SEL-002",
      "businessName": "Artisan Crafts",
      "platformId": "PLT-001",
      "currentState": "IDENTITY_VERIFICATION",
      "approvalStatus": "PENDING",
      "assignedTo": null,
      "submittedAt": "2024-01-20T14:30:00Z",
      "lastUpdatedAt": "2024-01-20T14:30:00Z",
      "lockedPolicyId": "POL-001",
      "lockedPolicyVersion": "1.0"
    }
  ],
  "totalHits": 1,
  "facets": {
    "approvalStatus": [
      { "value": "PENDING", "count": 15 },
      { "value": "APPROVED", "count": 50 },
      { "value": "MORE_INFO_REQUIRED", "count": 5 }
    ],
    "currentState": [
      { "value": "IDENTITY_VERIFICATION", "count": 8 },
      { "value": "TAX_VERIFICATION", "count": 5 },
      { "value": "BANK_VERIFICATION", "count": 2 }
    ]
  }
}
```

---

## 5. Frontend React Components

### Platform Search Component
```tsx
// components/search/PlatformSearch.tsx
import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { bffClient } from '@/lib/api-client';

export function PlatformSearch() {
  const [query, setQuery] = useState('');
  const [filters, setFilters] = useState({});
  
  const { data, isLoading } = useQuery({
    queryKey: ['platforms', query, filters],
    queryFn: () => bffClient.post('/bff/search/platforms', {
      query,
      filters,
      pagination: { page: 1, size: 20 }
    })
  });
  
  return (
    <div className="space-y-4">
      <input
        type="text"
        placeholder="Search platforms..."
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        className="w-full px-4 py-2 border rounded"
      />
      
      {/* Filters */}
      <div className="flex gap-4">
        <select onChange={(e) => setFilters({...filters, status: e.target.value})}>
          <option value="">All Statuses</option>
          <option value="ACTIVE">Active</option>
          <option value="INACTIVE">Inactive</option>
        </select>
      </div>
      
      {/* Results */}
      {isLoading ? (
        <div>Loading...</div>
      ) : (
        <div className="space-y-2">
          {data?.results.map((platform) => (
            <PlatformCard key={platform.id} platform={platform} />
          ))}
        </div>
      )}
    </div>
  );
}
```

### Seller Approval Queue Component
```tsx
// components/search/SellerApprovalQueue.tsx
import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { bffClient } from '@/lib/api-client';

export function SellerApprovalQueue() {
  const [filters, setFilters] = useState({
    approvalStatus: 'PENDING'
  });
  
  const { data, isLoading } = useQuery({
    queryKey: ['seller-approvals', filters],
    queryFn: () => bffClient.post('/bff/search/seller-approvals', {
      query: '',
      filters,
      sort: { field: 'submittedAt', order: 'DESC' },
      pagination: { page: 1, size: 20 }
    })
  });
  
  return (
    <div className="space-y-4">
      <h2 className="text-2xl font-bold">Seller Approval Queue</h2>
      
      {/* Filters */}
      <div className="flex gap-4">
        <select onChange={(e) => setFilters({...filters, approvalStatus: e.target.value})}>
          <option value="PENDING">Pending</option>
          <option value="MORE_INFO_REQUIRED">More Info Required</option>
          <option value="APPROVED">Approved</option>
          <option value="REJECTED">Rejected</option>
        </select>
        
        <select onChange={(e) => setFilters({...filters, currentState: e.target.value})}>
          <option value="">All States</option>
          <option value="IDENTITY_VERIFICATION">Identity Verification</option>
          <option value="TAX_VERIFICATION">Tax Verification</option>
          <option value="BANK_VERIFICATION">Bank Verification</option>
        </select>
      </div>
      
      {/* Results */}
      {isLoading ? (
        <div>Loading...</div>
      ) : (
        <div className="space-y-2">
          {data?.results.map((approval) => (
            <SellerApprovalCard key={approval.id} approval={approval} />
          ))}
        </div>
      )}
      
      {/* Facets */}
      <div className="mt-4">
        <h3 className="font-semibold">Filters</h3>
        {data?.facets.approvalStatus.map((facet) => (
          <div key={facet.value}>
            {facet.value}: {facet.count}
          </div>
        ))}
      </div>
    </div>
  );
}
```

---

## 6. Implementation Checklist

### Backend
- [ ] Create search document DTOs for each entity
- [ ] Implement indexers with event listeners
- [ ] Create Elasticsearch index mappings
- [ ] Add search endpoints to each service
- [ ] Test indexing on entity creation/update

### BFF
- [ ] Create `/bff/search/platforms` endpoint
- [ ] Create `/bff/search/policies` endpoint
- [ ] Create `/bff/search/sellers` endpoint
- [ ] Create `/bff/search/seller-approvals` endpoint
- [ ] Add aggregation logic (if needed)

### Frontend
- [ ] Create `PlatformSearch` component
- [ ] Create `PolicySearch` component
- [ ] Create `SellerSearch` component
- [ ] Create `SellerApprovalQueue` component
- [ ] Add search to navigation/dashboard
- [ ] Implement faceted filtering
- [ ] Add pagination

---

## 7. Configuration

### Backend (application.yml)
```yaml
search:
  provider: elasticsearch
  elasticsearch:
    hosts: ${ELASTICSEARCH_HOSTS:localhost:9200}
    username: ${ELASTICSEARCH_USERNAME:}
    password: ${ELASTICSEARCH_PASSWORD:}
    index-prefix: handmade
  indexing:
    async: true
    batch-size: 100
```

### BFF (environment variables)
```env
BACKEND_SEARCH_URL=http://localhost:8080/search
ELASTICSEARCH_ENABLED=true
```

---

## 8. Next Steps

1. **Backend**: Implement indexers for each entity
2. **BFF**: Create search aggregation endpoints
3. **Frontend**: Build search UI components
4. **Testing**: Test search with real data
5. **Deploy**: Deploy to staging for UAT
