#!/bin/bash

# Platform Management API Test Suite
# Tests all state transitions using correct Chenile endpoints

BASE_URL="http://localhost:8080/api"

echo "========================================="
echo "Platform Management API Test Suite"
echo "========================================="
echo ""

# 1. CREATE PLATFORM
echo "1. Creating new platform..."
CREATE_RESPONSE=$(curl -s -X POST "${BASE_URL}/platform" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Handmade Crafts Platform",
    "legalBusinessName": "Handmade Crafts Ltd",
    "taxRegistrationId": "TAX123456",
    "registeredAddress": "123 Main St, San Francisco, CA 94102",
    "supportEmail": "support@handmadecrafts.com",
    "supportPhoneNumber": "+1-555-0123",
    "primaryCurrency": "USD",
    "supportedCurrencies": ["USD", "EUR", "GBP"],
    "timezone": "America/Los_Angeles",
    "defaultLanguage": "en",
    "supportedLanguages": ["en", "es", "fr"],
    "primaryColorHex": "#FF5733",
    "logoUrl": "https://example.com/logo.png",
    "faviconUrl": "https://example.com/favicon.ico"
  }')

echo "$CREATE_RESPONSE" | jq '.'
PLATFORM_ID=$(echo $CREATE_RESPONSE | jq -r '.payload.mutatedEntity.id')
echo "Created Platform ID: $PLATFORM_ID"
echo ""

sleep 2

# 2. UPDATE BRAND IDENTITY
echo "2. Updating brand identity..."
curl -s -X PATCH "${BASE_URL}/platform/${PLATFORM_ID}/updateBrandIdentity" \
  -H "Content-Type: application/json" \
  -d '{
    "primaryColorHex": "#00AA00",
    "logoUrl": "https://example.com/new-logo.png",
    "faviconUrl": "https://example.com/new-favicon.ico"
  }' | jq '.'
echo ""

sleep 2

# 3. SUSPEND PLATFORM
echo "3. Suspending platform..."
curl -s -X PATCH "${BASE_URL}/platform/${PLATFORM_ID}/suspend" \
  -H "Content-Type: application/json" \
  -d '{
    "reason": "Maintenance required for security update",
    "suspendedBy": "admin@handmade.com"
  }' | jq '.'
echo ""

sleep 2

# 4. REACTIVATE PLATFORM  
echo "4. Reactivating platform..."
curl -s -X PATCH "${BASE_URL}/platform/${PLATFORM_ID}/reactivate" \
  -H "Content-Type: application/json" \
  -d '{
    "reactivatedBy": "admin@handmade.com",
    "notes": "Maintenance completed successfully"
  }' | jq '.'
echo ""

sleep 2

# 5. GET PLATFORM DETAILS
echo "5. Getting platform details..."
curl -s -X GET "${BASE_URL}/platform/${PLATFORM_ID}" | jq '.'
echo ""

sleep 2

# 6. SUSPEND AGAIN FOR DELETION
echo "6. Suspending platform for deletion..."
curl -s -X PATCH "${BASE_URL}/platform/${PLATFORM_ID}/suspend" \
  -H "Content-Type: application/json" \
  -d '{
    "reason": "Preparing for deletion",
    "suspendedBy": "admin@handmade.com"
  }' | jq '.'
echo ""

sleep 2

# 7. DELETE PLATFORM
echo "7. Deleting platform (soft delete)..."
curl -s -X PATCH "${BASE_URL}/platform/${PLATFORM_ID}/delete" \
  -H "Content-Type: application/json" \
  -d '{
    "reason": "Platform no longer needed",
    "deletedBy": "admin@handmade.com"
  }' | jq '.'
echo ""

sleep 2

# 8. VERIFY DELETION
echo "8. Verifying platform is deleted..."
curl -s -X GET "${BASE_URL}/platform/${PLATFORM_ID}" | jq '.payload.mutatedEntity | {id, deleted, currentState}'
echo ""

echo "========================================="
echo "Test Suite Completed!"
echo "Platform ID tested: $PLATFORM_ID"
echo "========================================="
