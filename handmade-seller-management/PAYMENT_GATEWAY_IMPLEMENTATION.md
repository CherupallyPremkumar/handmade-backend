# Payment Gateway Integration - Implementation Guide

## Overview
This implementation follows Amazon's seller onboarding architecture with multi-gateway support (Stripe, Razorpay, PayPal).

## Architecture

### Flow Diagram
```
┌──────────┐         ┌──────────┐         ┌──────────────┐         ┌──────────┐
│ Frontend │────────▶│ Backend  │────────▶│ Stripe/      │────────▶│ Webhook  │
│ (React)  │         │ (Spring) │         │ Razorpay     │         │ Handler  │
└──────────┘         └──────────┘         └──────────────┘         └──────────┘
     │                     │                      │                      │
     │ 1. Submit form      │                      │                      │
     │────────────────────▶│                      │                      │
     │                     │ 2. Create account    │                      │
     │                     │─────────────────────▶│                      │
     │                     │                      │                      │
     │                     │ 3. Return URL        │                      │
     │                     │◀─────────────────────│                      │
     │ 4. Redirect URL     │                      │                      │
     │◀────────────────────│                      │                      │
     │                     │                      │                      │
     │ 5. Complete KYC     │                      │                      │
     │─────────────────────────────────────────────▶                     │
     │                     │                      │                      │
     │                     │                      │ 6. Account verified  │
     │                     │                      │─────────────────────▶│
     │                     │                      │                      │
     │                     │ 7. Update seller     │                      │
     │                     │◀──────────────────────────────────────────────
     │                     │                      │                      │
     │ 8. Admin approves   │                      │                      │
     │────────────────────▶│                      │                      │
     │                     │                      │                      │
     │ 9. Seller ACTIVE    │                      │                      │
     │◀────────────────────│                      │                      │
```

## Files Created

### 1. DTOs (Data Transfer Objects)
- `PaymentOnboardingRequest.java` - Request to initiate onboarding
- `PaymentOnboardingResponse.java` - Response with account ID and URL
- `PaymentAccountStatus.java` - Payment account verification status

### 2. Service Layer
- `PaymentGatewayService.java` - Interface
- `PaymentGatewayServiceImpl.java` - Implementation with Stripe/Razorpay

### 3. Controller
- `SellerPaymentController.java` - REST endpoints

## API Endpoints

### 1. Initiate Payment Onboarding
```http
POST /api/sellers/payment/onboarding
Content-Type: application/json

{
  "sellerId": "SELLER-001",
  "gateway": "STRIPE",
  "email": "seller@example.com",
  "phone": "+919876543210",
  "businessName": "John's Crafts",
  "businessType": "individual",
  "country": "IN",
  "returnUrl": "http://localhost:3000/sellers/onboarding/complete",
  "refreshUrl": "http://localhost:3000/sellers/onboarding/refresh"
}
```

**Response:**
```json
{
  "accountId": "acct_1234567890",
  "onboardingUrl": "https://connect.stripe.com/setup/...",
  "gateway": "STRIPE",
  "isComplete": false,
  "message": "Stripe Connect account created..."
}
```

### 2. Get Payment Status
```http
GET /api/sellers/{sellerId}/payment/status?gateway=STRIPE
```

### 3. Refresh Onboarding Link
```http
POST /api/sellers/{sellerId}/payment/refresh?gateway=STRIPE
```

### 4. Webhooks
```http
POST /api/webhooks/stripe
POST /api/webhooks/razorpay
POST /api/webhooks/paypal
```

## Database Changes Required

### Add field to SellerPaymentInfo
```sql
ALTER TABLE hm_seller_payment_info 
ADD COLUMN gateway_account_id VARCHAR(255);

ALTER TABLE hm_seller_payment_info 
ADD COLUMN gateway_type VARCHAR(50);

ALTER TABLE hm_seller_payment_info 
ADD COLUMN verification_status VARCHAR(50) DEFAULT 'PENDING';

ALTER TABLE hm_seller_payment_info 
ADD COLUMN payouts_enabled BOOLEAN DEFAULT FALSE;
```

## Configuration (application.yml)

```yaml
payment:
  stripe:
    secret:
      key: ${STRIPE_SECRET_KEY:sk_test_xxx}
    webhook:
      secret: ${STRIPE_WEBHOOK_SECRET:whsec_xxx}
  
  razorpay:
    key:
      id: ${RAZORPAY_KEY_ID:rzp_test_xxx}
      secret: ${RAZORPAY_KEY_SECRET:xxx}
    webhook:
      secret: ${RAZORPAY_WEBHOOK_SECRET:xxx}

app:
  base:
    url: ${APP_BASE_URL:http://localhost:3000}
```

## Dependencies Required (pom.xml)

```xml
<!-- Stripe Java SDK -->
<dependency>
    <groupId>com.stripe</groupId>
    <artifactId>stripe-java</artifactId>
    <version>24.0.0</version>
</dependency>

<!-- Razorpay Java SDK -->
<dependency>
    <groupId>com.razorpay</groupId>
    <artifactId>razorpay-java</artifactId>
    <version>1.4.3</version>
</dependency>

<!-- Lombok (if not already present) -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
```

## Testing

### 1. Test with Stripe
```bash
# Use Stripe test mode keys
STRIPE_SECRET_KEY=sk_test_xxx

# Test webhook locally with Stripe CLI
stripe listen --forward-to localhost:8080/api/webhooks/stripe
```

### 2. Test with Razorpay
```bash
# Use Razorpay test mode keys
RAZORPAY_KEY_ID=rzp_test_xxx
RAZORPAY_KEY_SECRET=xxx
```

## Webhook Setup

### Stripe
1. Go to Stripe Dashboard → Developers → Webhooks
2. Add endpoint: `https://yourdomain.com/api/webhooks/stripe`
3. Select events:
   - `account.updated`
   - `account.external_account.created`
   - `account.external_account.updated`

### Razorpay
1. Go to Razorpay Dashboard → Settings → Webhooks
2. Add URL: `https://yourdomain.com/api/webhooks/razorpay`
3. Select events:
   - `account.activated`
   - `account.suspended`

## Security Considerations

1. **Never store raw bank details** - Only store gateway account IDs
2. **Verify webhook signatures** - Prevent fake webhooks
3. **Use HTTPS in production** - Protect sensitive data
4. **Encrypt gateway keys** - Use environment variables
5. **Rate limit endpoints** - Prevent abuse

## Next Steps

1. Add Stripe/Razorpay dependencies to `pom.xml`
2. Run database migrations
3. Configure environment variables
4. Test with test mode keys
5. Set up webhooks in gateway dashboards
6. Deploy and test end-to-end flow

## Support

For issues:
- Stripe: https://stripe.com/docs/connect
- Razorpay: https://razorpay.com/docs/route/
