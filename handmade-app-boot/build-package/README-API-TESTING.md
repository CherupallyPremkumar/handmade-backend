# Platform Management API Testing Guide

## Quick Start

Run the full test suite:
```bash
./test-platform-api.sh
```

This will test the complete platform lifecycle with all state transitions.

---

## Individual API Tests

### 1. Create Platform
```bash
curl -X POST "http://localhost:8080/api/platform" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "My Handmade Platform",
    "legalBusinessName": "My Business Ltd",
    "taxRegistrationId": "TAX123456",
    "registeredAddress": "123 Main St, City, State 12345",
    "supportEmail": "support@example.com",
    "supportPhoneNumber": "+1-555-0100",
    "primaryCurrency": "USD",
    "supportedCurrencies": ["USD", "EUR"],
    "timezone": "America/New_York",
    "defaultLanguage": "en",
    "supportedLanguages": ["en", "es"],
    "primaryColorHex": "#FF5733"
  }'
```

**Expected State:** `DRAFT`

---

### 2. Activate Platform
```bash
curl -X POST "http://localhost:8080/api/platform/{platformId}/activate" \
  -H "Content-Type: application/json" \
  -d '{
    "activatedBy": "admin@example.com",
    "activationNotes": "Platform approved"
  }'
```

**State Transition:** `DRAFT` → `ACTIVE`

---

### 3. Update Brand Identity
```bash
curl -X PUT "http://localhost:8080/api/platform/{platformId}/brand" \
  -H "Content-Type: application/json" \
  -d '{
    "primaryColorHex": "#00AA00",
    "logoUrl": "https://example.com/logo.png",
    "faviconUrl": "https://example.com/favicon.ico"
  }'
```

**Valid States:** `DRAFT`, `ACTIVE`, `SUSPENDED`

---

### 4. Update Corporate Identity
```bash
curl -X PUT "http://localhost:8080/api/platform/{platformId}/corporate" \
  -H "Content-Type: application/json" \
  -d '{
    "legalBusinessName": "Updated Business Name",
    "taxRegistrationId": "TAX789012",
    "registeredAddress": "456 New St, City, State 67890"
  }'
```

**Valid States:** `DRAFT`, `ACTIVE`, `SUSPENDED`

---

### 5. Revise Commission Rates
```bash
curl -X POST "http://localhost:8080/api/platform/{platformId}/commission/revise" \
  -H "Content-Type: application/json" \
  -d '{
    "newPolicyId": "premium-policy",
    "effectiveFrom": "2025-01-01T00:00:00Z",
    "reason": "Updated pricing model"
  }'
```

**Valid States:** `ACTIVE`, `SUSPENDED`

---

### 6. Configure Features
```bash
curl -X POST "http://localhost:8080/api/platform/{platformId}/features" \
  -H "Content-Type: application/json" \
  -d '{
    "isSellerRegistrationOpen": true,
    "isCheckoutEnabled": true,
    "isGuestCheckoutAllowed": false
  }'
```

**Valid States:** `ACTIVE`, `SUSPENDED`

---

### 7. Suspend Platform
```bash
curl -X POST "http://localhost:8080/api/platform/{platformId}/suspend" \
  -H "Content-Type: application/json" \
  -d '{
    "reason": "Maintenance required",
    "suspendedBy": "admin@example.com"
  }'
```

**State Transition:** `ACTIVE` → `SUSPENDED`

---

### 8. Reactivate Platform
```bash
curl -X POST "http://localhost:8080/api/platform/{platformId}/reactivate" \
  -H "Content-Type: application/json" \
  -d '{
    "reactivatedBy": "admin@example.com",
    "notes": "Maintenance completed"
  }'
```

**State Transition:** `SUSPENDED` → `ACTIVE`

---

### 9. Delete Platform (Soft Delete)
```bash
curl -X DELETE "http://localhost:8080/api/platform/{platformId}" \
  -H "Content-Type: application/json" \
  -d '{
    "reason": "Platform no longer needed",
    "deletedBy": "admin@example.com"
  }'
```

**State Transition:** `SUSPENDED` → `DELETED`  
**Note:** Platform must be `SUSPENDED` before deletion

---

### 10. Get Platform Details
```bash
curl -X GET "http://localhost:8080/api/platform/{platformId}"
```

---

## State Machine Flow

```
DRAFT → ACTIVATE → ACTIVE
                    ↓ ↑
                SUSPEND REACTIVATE
                    ↓
                SUSPENDED → DELETE → DELETED
```

## Valid Transitions

| Current State | Allowed Actions |
|--------------|----------------|
| `DRAFT` | Activate, Update Brand, Update Corporate |
| `ACTIVE` | Suspend, Update Brand, Update Corporate, Revise Commission, Configure Features |
| `SUSPENDED` | Reactivate, Delete, Update Brand, Update Corporate, Revise Commission, Configure Features |
| `DELETED` | *(No actions allowed - terminal state)* |

## Events Published

All state transitions publish events via the InVMEventPublisher:
- `PLATFORM_CREATED`
- `PLATFORM_ACTIVATED`
- `PLATFORM_SUSPENDED`
- `PLATFORM_REACTIVATED`
- `PLATFORM_DELETED`
- `COMMISSION_REVISED`
- `BRAND_UPDATED`
- `CORPORATE_IDENTITY_UPDATED`
- `FEATURES_CONFIGURED`

## Testing Tips

1. **Sequential Testing**: Run operations in logical order (Create → Activate → Suspend → etc.)
2. **State Validation**: Check the response to verify state transitions
3. **Audit Logs**: All actions are logged in `platform_audit_log` table
4. **H2 Console**: Access at `http://localhost:8080/api/h2-console` to inspect data

## Error Cases to Test

### Invalid State Transitions
```bash
# Try to delete an ACTIVE platform (should fail)
curl -X DELETE "http://localhost:8080/api/platform/{platformId}" \
  -H "Content-Type: application/json" \
  -d '{"reason": "Test", "deletedBy": "admin@example.com"}'
```

### Missing Required Fields
```bash
# Create platform without required fields (should fail)
curl -X POST "http://localhost:8080/api/platform" \
  -H "Content-Type: application/json" \
  -d '{"name": "Incomplete Platform"}'
```

### Invalid Platform ID
```bash
# Get non-existent platform (should return 404)
curl -X GET "http://localhost:8080/api/platform/invalid-id"
```

---

## Sample Full Lifecycle Test

```bash
# 1. Create
PLATFORM_ID=$(curl -s -X POST "http://localhost:8080/api/platform" \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Platform","legalBusinessName":"Test Ltd","supportEmail":"test@example.com"}' \
  | jq -r '.id')

# 2. Activate
curl -X POST "http://localhost:8080/api/platform/${PLATFORM_ID}/activate" \
  -H "Content-Type: application/json" \
  -d '{"activatedBy":"admin@example.com"}'

# 3. Suspend
curl -X POST "http://localhost:8080/api/platform/${PLATFORM_ID}/suspend" \
  -H "Content-Type: application/json" \
  -d '{"reason":"Maintenance","suspendedBy":"admin@example.com"}'

# 4. Delete
curl -X DELETE "http://localhost:8080/api/platform/${PLATFORM_ID}" \
  -H "Content-Type: application/json" \
  -d '{"reason":"Cleanup","deletedBy":"admin@example.com"}'
```

---

**All 6 Cucumber tests validated these flows! 🎉**
