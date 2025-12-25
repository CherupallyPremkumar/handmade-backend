# Platform API - REST Endpoints

## Overview
Complete REST API implementation for Platform Management following Clean Architecture principles.

---

## API Endpoints

### Base URL
```
http://localhost:8080/api/v1/platform
```

---

## 1. Activate Platform

**Endpoint:** `POST /api/v1/platform/activate`

**Description:** Activate the platform from PROVISIONING to LIVE state (Day 0 operation)

**Request Body:**
```json
{
  "operatorId": "admin-123",
  "reason": "Initial platform launch"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Platform activated successfully",
  "data": null,
  "timestamp": "2024-01-15T10:30:00Z"
}
```

**Status Codes:**
- `200 OK` - Platform activated successfully
- `400 BAD_REQUEST` - Invalid request data
- `403 FORBIDDEN` - Operator not authorized
- `409 CONFLICT` - Platform already activated or in invalid state

---

## 2. Revise Commission Structure

**Endpoint:** `POST /api/v1/platform/commission`

**Description:** Update platform commission rates and transaction fees

**Request Body:**
```json
{
  "operatorId": "admin-123",
  "newTakeRate": 15.5,
  "flatFee": 2.50,
  "currency": "USD",
  "reason": "Market adjustment Q1 2024"
}
```

**Validation:**
- `newTakeRate`: Required, must be >= 0
- `flatFee`: Required, must be >= 0
- `currency`: Required, 3-letter currency code
- `operatorId`: Required

**Response:**
```json
{
  "success": true,
  "message": "Commission structure revised successfully",
  "data": null,
  "timestamp": "2024-01-15T10:35:00Z"
}
```

**Status Codes:**
- `200 OK` - Commission revised successfully
- `400 BAD_REQUEST` - Invalid commission values
- `403 FORBIDDEN` - Operator not authorized

---

## 3. Lock Platform

**Endpoint:** `POST /api/v1/platform/lock`

**Description:** Impose sanctions and restrict platform operations (emergency use)

**Request Body:**
```json
{
  "operatorId": "admin-123",
  "reason": "Security incident - temporary lockdown"
}
```

**Validation:**
- `reason`: Required (mandatory for audit trail)

**Response:**
```json
{
  "success": true,
  "message": "Platform locked successfully",
  "data": null,
  "timestamp": "2024-01-15T10:40:00Z"
}
```

**Status Codes:**
- `200 OK` - Platform locked successfully
- `400 BAD_REQUEST` - Missing reason
- `403 FORBIDDEN` - Operator not authorized

---

## 4. Update Brand Identity

**Endpoint:** `PUT /api/v1/platform/brand`

**Description:** Update platform visual identity (logo, colors, support email)

**Request Body:**
```json
{
  "operatorId": "admin-123",
  "logoUrl": "https://cdn.handmade.com/logo.png",
  "primaryColor": "#FF5733",
  "supportEmail": "support@handmade.com"
}
```

**Validation:**
- `logoUrl`: Required, valid URL
- `primaryColor`: Valid hex color (#RRGGBB or #RGB)
- `supportEmail`: Required, valid email format

**Response:**
```json
{
  "success": true,
  "message": "Brand identity updated successfully",
  "data": null,
  "timestamp": "2024-01-15T10:45:00Z"
}
```

**Status Codes:**
- `200 OK` - Brand updated successfully
- `400 BAD_REQUEST` - Invalid URL, color, or email format
- `403 FORBIDDEN` - Operator not authorized

---

## 5. Publish Legal Terms

**Endpoint:** `POST /api/v1/platform/legal-terms`

**Description:** Publish or update terms of service and privacy policy

**Request Body:**
```json
{
  "operatorId": "admin-123",
  "termsUrl": "https://handmade.com/terms",
  "privacyUrl": "https://handmade.com/privacy"
}
```

**Validation:**
- `termsUrl`: Required, valid URL
- `privacyUrl`: Required, valid URL

**Response:**
```json
{
  "success": true,
  "message": "Legal terms published successfully",
  "data": null,
  "timestamp": "2024-01-15T10:50:00Z"
}
```

**Status Codes:**
- `200 OK` - Legal terms published successfully
- `400 BAD_REQUEST` - Invalid URLs
- `403 FORBIDDEN` - Operator not authorized

---

## Error Responses

### Validation Error (400)
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "operatorId": "Operator ID is required",
    "newTakeRate": "Take rate must be non-negative"
  },
  "timestamp": "2024-01-15T10:55:00Z"
}
```

### Authorization Error (403)
```json
{
  "success": false,
  "message": "Operator admin-123 is not authorized to perform operation: ACTIVATE_PLATFORM",
  "data": null,
  "timestamp": "2024-01-15T10:56:00Z"
}
```

### Business Rule Violation (409)
```json
{
  "success": false,
  "message": "Platform can only be activated from PROVISIONING state",
  "data": null,
  "timestamp": "2024-01-15T10:57:00Z"
}
```

### Server Error (500)
```json
{
  "success": false,
  "message": "An unexpected error occurred. Please try again later.",
  "data": null,
  "timestamp": "2024-01-15T10:58:00Z"
}
```

---

## OpenAPI/Swagger Documentation

Access interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

OpenAPI spec available at:
```
http://localhost:8080/v3/api-docs
```

---

## Architecture

### Request Flow
```
HTTP Request
    ↓
Controller (platform-api)
    ↓
Command DTO
    ↓
Application Service (platform-application)
    ↓
Domain Aggregate (platform-domain)
    ↓
Repository Adapter (platform-infrastructure)
    ↓
Database
```

### Layer Responsibilities

| Layer | Component | Responsibility |
|-------|-----------|----------------|
| **API** | Controller | HTTP handling, request/response mapping |
| **API** | DTOs | Request validation, data transfer |
| **API** | Exception Handler | Error to HTTP status mapping |
| **Application** | Service | Orchestration, authorization, transactions |
| **Domain** | Aggregate | Business rules, invariants |
| **Infrastructure** | Repository | Persistence, event publishing |

---

## Testing

### cURL Examples

**Activate Platform:**
```bash
curl -X POST http://localhost:8080/api/v1/platform/activate \
  -H "Content-Type: application/json" \
  -d '{
    "operatorId": "admin-123",
    "reason": "Initial launch"
  }'
```

**Revise Commission:**
```bash
curl -X POST http://localhost:8080/api/v1/platform/commission \
  -H "Content-Type: application/json" \
  -d '{
    "operatorId": "admin-123",
    "newTakeRate": 15.5,
    "flatFee": 2.50,
    "currency": "USD",
    "reason": "Market adjustment"
  }'
```

**Update Brand:**
```bash
curl -X PUT http://localhost:8080/api/v1/platform/brand \
  -H "Content-Type: application/json" \
  -d '{
    "operatorId": "admin-123",
    "logoUrl": "https://cdn.handmade.com/logo.png",
    "primaryColor": "#FF5733",
    "supportEmail": "support@handmade.com"
  }'
```

---

## Files Created

**Request DTOs (5):**
1. `ActivatePlatformRequest.java`
2. `ReviseCommissionRequest.java`
3. `LockPlatformRequest.java`
4. `UpdateBrandIdentityRequest.java`
5. `PublishLegalTermsRequest.java`

**Response DTOs (1):**
6. `ApiResponse.java` - Generic response wrapper

**Controllers (1):**
7. `PlatformGovernanceController.java` - All 5 endpoints

**Exception Handling (1):**
8. `GlobalExceptionHandler.java` - Centralized error handling

**Configuration (1):**
9. `OpenApiConfig.java` - Swagger/OpenAPI setup

**Total: 9 files**
