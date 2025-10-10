# Frontend-Backend Integration Guide

## Overview
Complete integration guide for connecting the React/TypeScript frontend with the Spring Boot backend.

---

## Architecture

```
React Frontend (Port 3000) ←→ Spring Boot Backend (Port 8080)
     ↓                                    ↓
  Services Layer                    REST Controllers
     ↓                                    ↓
  API Config                        Service Layer
     ↓                                    ↓
  HTTP Requests                     JPA Repositories
                                          ↓
                                      Database
```

---

## Configuration

### Backend Configuration

**File:** `src/main/resources/application.yml`

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

# CORS Configuration (Allow frontend)
cors:
  allowed-origins: http://localhost:3000
  allowed-methods: GET,POST,PUT,DELETE,OPTIONS
  allowed-headers: "*"
  allow-credentials: true
```

### Frontend Configuration

**File:** `.env.development`

```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_TENANT_ID=default
VITE_CUSTOMER_ID=1
```

**File:** `src/config/api.ts`

```typescript
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';
export const DEFAULT_TENANT_ID = import.meta.env.VITE_TENANT_ID || 'default';
```

---

## API Integration

### 1. Cart Service Integration

**Frontend:** `src/services/cartService.ts`

```typescript
import { API_ENDPOINTS, DEFAULT_TENANT_ID, buildUrl, getFetchOptions } from "@/config/api";

export const cartService = {
  getCart: async (customerId: number, tenantId = DEFAULT_TENANT_ID) => {
    const url = buildUrl(API_ENDPOINTS.user.cart, { customerId, tenantId });
    const response = await fetch(url, getFetchOptions('GET'));
    return await response.json();
  },
  
  addToCart: async (customerId: number, productId: number, quantity: number) => {
    const url = buildUrl(API_ENDPOINTS.user.cart, { customerId, tenantId: DEFAULT_TENANT_ID });
    const response = await fetch(url, getFetchOptions('POST', { productId, quantity }));
    return await response.json();
  },
};
```

**Backend:** `UserCartController.java`

```java
@RestController
@RequestMapping("/api/user/cart")
public class UserCartController {
    
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCart(
            @RequestParam Long customerId,
            @RequestParam(defaultValue = "default") String tenantId) {
        // Returns cart items
    }
    
    @PostMapping
    public ResponseEntity<CartItemDTO> addToCart(
            @RequestParam Long customerId,
            @RequestBody AddToCartRequest request,
            @RequestParam(defaultValue = "default") String tenantId) {
        // Adds item to cart
    }
}
```

### 2. Order Service Integration

**Frontend:** `src/services/orderService.ts`

```typescript
export const orderService = {
  createOrder: async (orderData: any, tenantId = DEFAULT_TENANT_ID) => {
    const url = buildUrl(API_ENDPOINTS.user.order, { tenantId });
    const response = await fetch(url, getFetchOptions('POST', orderData));
    return await response.json();
  },
  
  getAllOrders: async (customerId: number, tenantId = DEFAULT_TENANT_ID) => {
    const url = buildUrl(API_ENDPOINTS.user.order, { customerId, tenantId });
    const response = await fetch(url, getFetchOptions('GET'));
    return await response.json();
  },
};
```

**Backend:** `UserOrderController.java`

```java
@RestController
@RequestMapping("/api/user/order")
public class UserOrderController {
    
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody CreateOrderRequest request,
            @RequestParam(defaultValue = "default") String tenantId) {
        // Creates order from cart
    }
    
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(
            @RequestParam Long customerId,
            @RequestParam(defaultValue = "default") String tenantId) {
        // Returns customer orders
    }
}
```

### 3. Payment Service Integration

**Frontend:** `src/services/paymentService.ts`

```typescript
export const paymentService = {
  createPayment: async (request: CreatePaymentRequestDto, tenantId = DEFAULT_TENANT_ID) => {
    const url = buildUrl(`${API_ENDPOINTS.user.payment}/create`, { tenantId });
    const response = await fetch(url, getFetchOptions('POST', request));
    return await response.json();
  },
  
  checkPaymentStatus: async (transactionId: string, tenantId = DEFAULT_TENANT_ID) => {
    const url = buildUrl(`${API_ENDPOINTS.user.payment}/status/${transactionId}`, { tenantId });
    const response = await fetch(url, getFetchOptions('GET'));
    return await response.json();
  },
};
```

**Backend:** `UserPaymentController.java`

```java
@RestController
@RequestMapping("/api/user/payment")
public class UserPaymentController {
    
    @PostMapping("/create")
    public ResponseEntity<CreatePaymentResponse> createPayment(
            @RequestBody CreatePaymentRequest request,
            @RequestParam(defaultValue = "default") String tenantId) {
        // Initiates PhonePe payment
    }
    
    @GetMapping("/status/{transactionId}")
    public ResponseEntity<Map<String, Object>> checkPaymentStatus(
            @PathVariable String transactionId,
            @RequestParam(defaultValue = "default") String tenantId) {
        // Checks payment status
    }
}
```

---

## Complete Checkout Flow

### Step 1: Add Items to Cart

**Frontend:**
```typescript
const handleAddToCart = async (productId: number) => {
  const customerId = 1; // From auth context
  await cartService.addToCart(customerId, productId, 1);
};
```

**Backend:**
- Validates product exists
- Captures price snapshot
- Creates/updates cart item
- Returns updated cart item

### Step 2: View Cart

**Frontend:**
```typescript
const loadCart = async () => {
  const customerId = 1;
  const items = await cartService.getCart(customerId);
  setCartItems(items);
};
```

**Backend:**
- Fetches cart items for customer
- Returns items with current snapshot prices
- Calculates subtotals

### Step 3: Checkout

**Frontend:**
```typescript
const handleCheckout = async (formData: CheckoutFormData) => {
  const orderData = {
    customerId: 1,
    customerName: formData.customerName,
    customerEmail: formData.customerEmail,
    customerPhone: formData.customerPhone,
    shippingAddress: formData.shippingAddress,
    city: formData.city,
    state: formData.state,
    pincode: formData.pincode,
    paymentMethod: "PHONEPE"
  };
  
  const order = await orderService.createOrder(orderData);
  return order;
};
```

**Backend:**
- Validates cart not empty
- Checks stock availability
- Creates order with items
- Calculates tax (18%) and shipping
- Reserves stock
- Marks cart as CHECKED_OUT
- Returns order with order number

### Step 4: Initiate Payment

**Frontend:**
```typescript
const handlePayment = async (order: OrderDto) => {
  const paymentRequest = {
    amount: order.total,
    currency: "INR",
    orderId: order.orderNumber,
    customerPhone: order.customerPhone,
    customerName: order.customerName,
    redirectUrl: `${window.location.origin}/payment/success`,
    callbackUrl: `${API_BASE_URL}/user/payment/callback`
  };
  
  const response = await paymentService.createPayment(paymentRequest);
  
  if (response.success) {
    // Redirect to PhonePe
    window.location.href = response.paymentUrl;
  }
};
```

**Backend:**
- Creates payment record
- Calls PhonePe API
- Generates checksum
- Returns payment URL

### Step 5: Payment Callback

**Backend Only:**
- PhonePe calls webhook
- Verifies signature
- Updates payment status
- Updates order status
- Clears cart (if success)
- Restores stock (if failed)

### Step 6: Check Payment Status

**Frontend:**
```typescript
const checkStatus = async (transactionId: string) => {
  const status = await paymentService.checkPaymentStatus(transactionId);
  
  if (status.status === "SUCCESS") {
    // Show success page
    navigate(`/order/success?orderNumber=${status.orderId}`);
  }
};
```

---

## Error Handling

### Frontend Error Handler

```typescript
export const handleApiError = async (response: Response): Promise<never> => {
  let errorMessage = `API Error: ${response.status}`;
  
  try {
    const errorData = await response.json();
    errorMessage = errorData.message || errorMessage;
  } catch {
    // Use default message
  }
  
  throw new Error(errorMessage);
};
```

### Usage in Services

```typescript
try {
  const response = await fetch(url, options);
  if (!response.ok) {
    await handleApiError(response);
  }
  return await response.json();
} catch (error) {
  console.error('API call failed:', error);
  // Fallback to mock data in development
  if (import.meta.env.DEV) {
    return mockData;
  }
  throw error;
}
```

---

## Development vs Production

### Development Mode
- Uses `import.meta.env.DEV` to detect
- Falls back to mock data if API fails
- Logs errors to console
- Allows testing without backend

### Production Mode
- Throws errors if API fails
- No mock data fallback
- Proper error messages to user
- Requires backend to be running

---

## Testing the Integration

### 1. Start Backend
```bash
cd backend
./mvnw spring-boot:run
```

Backend runs on: http://localhost:8080

### 2. Start Frontend
```bash
cd terra-dwell-commerce
npm run dev
```

Frontend runs on: http://localhost:3000

### 3. Test Cart Flow
1. Browse products
2. Add item to cart
3. View cart
4. Update quantities
5. Remove items

### 4. Test Checkout Flow
1. Add items to cart
2. Click checkout
3. Fill shipping details
4. Submit order
5. Verify order created

### 5. Test Payment Flow
1. Complete checkout
2. Initiate payment
3. Redirect to PhonePe (sandbox)
4. Complete payment
5. Verify order confirmed

---

## API Endpoint Summary

| Frontend Service | Backend Endpoint | Method | Purpose |
|-----------------|------------------|--------|---------|
| `cartService.getCart()` | `/api/user/cart` | GET | Get cart items |
| `cartService.addToCart()` | `/api/user/cart` | POST | Add to cart |
| `cartService.updateCartItem()` | `/api/user/cart/{id}` | PUT | Update quantity |
| `cartService.removeFromCart()` | `/api/user/cart/{id}` | DELETE | Remove item |
| `orderService.createOrder()` | `/api/user/order` | POST | Create order |
| `orderService.getAllOrders()` | `/api/user/order` | GET | Get orders |
| `orderService.getOrderById()` | `/api/user/order/{number}` | GET | Get order details |
| `paymentService.createPayment()` | `/api/user/payment/create` | POST | Initiate payment |
| `paymentService.checkPaymentStatus()` | `/api/user/payment/status/{txn}` | GET | Check status |

---

## CORS Configuration

**Backend:** `SecurityConfig.java`

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

---

## Troubleshooting

### Issue: CORS Error
**Solution:** Ensure backend CORS is configured to allow `http://localhost:3000`

### Issue: 404 Not Found
**Solution:** Verify backend is running and endpoint paths match

### Issue: Network Error
**Solution:** Check `VITE_API_BASE_URL` in `.env` file

### Issue: Empty Response
**Solution:** Check backend logs for errors, verify tenant ID

---

## Summary

✅ **Frontend services updated** to call backend APIs
✅ **Centralized API configuration** in `config/api.ts`
✅ **Environment variables** for configuration
✅ **Error handling** with fallback to mock data
✅ **Complete integration** for cart, order, and payment
✅ **Development and production** modes supported

**The frontend is now fully integrated with the backend!**
