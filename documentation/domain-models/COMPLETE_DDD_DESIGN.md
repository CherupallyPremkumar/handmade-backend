# Complete DDD Design for E-Commerce Platform

## 🎯 Overview

This document defines **all Domain-Driven Design (DDD) models** needed for a complete handmade e-commerce marketplace platform.

---

## 📦 Bounded Contexts & Domain Models

### **1. 🛒 Order Management**

#### **Aggregates**:
- **Order** (Aggregate Root)
  - OrderId (Identity)
  - CustomerId
  - OrderStatus (Enum: PENDING, CONFIRMED, PAID, SHIPPED, DELIVERED, CANCELLED)
  - OrderItems (List of OrderItem entities)
  - ShippingAddress (Value Object)
  - BillingAddress (Value Object)
  - TotalAmount (Money Value Object)
  - CreatedAt, UpdatedAt

#### **Entities**:
- **OrderItem**
  - OrderItemId
  - ProductId
  - Quantity
  - UnitPrice (Money)
  - Subtotal (Money)
  - SellerId

#### **Value Objects**:
- **ShippingAddress**
  - FullName, AddressLine1, AddressLine2, City, State, PostalCode, Country, Phone
- **BillingAddress**
  - Same as ShippingAddress
- **Money**
  - Amount (BigDecimal), Currency (String)

#### **Domain Events**:
- OrderCreatedEvent
- OrderConfirmedEvent
- OrderPaidEvent
- OrderShippedEvent
- OrderDeliveredEvent
- OrderCancelledEvent

---

### **2. 📦 Inventory Management**

#### **Aggregates**:
- **InventoryItem** (Aggregate Root)
  - InventoryItemId
  - ProductId
  - SellerId
  - AvailableQuantity
  - ReservedQuantity
  - TotalQuantity
  - WarehouseLocation
  - ReorderLevel
  - StockReservations (List)

#### **Entities**:
- **StockReservation**
  - ReservationId
  - OrderId
  - Quantity
  - ReservationStatus (PENDING, CONFIRMED, RELEASED, EXPIRED)
  - ExpiresAt

#### **Value Objects**:
- **WarehouseLocation**
  - WarehouseId, Aisle, Shelf, Bin

#### **Domain Events**:
- StockReservedEvent
- StockReleasedEvent
- StockReplenishedEvent
- LowStockAlertEvent

---

### **3. 💳 Payment Management**

#### **Aggregates**:
- **PaymentTransaction** (Aggregate Root)
  - PaymentTransactionId
  - OrderId
  - Amount (Money)
  - PaymentMethod (CREDIT_CARD, DEBIT_CARD, UPI, WALLET, COD)
  - PaymentGateway (STRIPE, RAZORPAY, PAYPAL)
  - PaymentStatus (PENDING, AUTHORIZED, CAPTURED, FAILED, REFUNDED)
  - GatewayTransactionId
  - CreatedAt, UpdatedAt

- **Refund** (Aggregate Root)
  - RefundId
  - PaymentTransactionId
  - OrderId
  - Amount (Money)
  - Reason
  - RefundStatus (PENDING, PROCESSED, FAILED)
  - ProcessedAt

#### **Domain Events**:
- PaymentInitiatedEvent
- PaymentAuthorizedEvent
- PaymentCapturedEvent
- PaymentFailedEvent
- RefundInitiatedEvent
- RefundProcessedEvent

---

### **4. 🚚 Shipping Management**

#### **Aggregates**:
- **Shipment** (Aggregate Root)
  - ShipmentId
  - OrderId
  - Carrier (FEDEX, UPS, DHL, etc.)
  - TrackingNumber
  - ShipmentStatus (PENDING, LABEL_CREATED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, FAILED)
  - ShippingAddress (Value Object)
  - Weight
  - Dimensions (Value Object)
  - ShippingCost (Money)
  - EstimatedDeliveryDate
  - ActualDeliveryDate

#### **Value Objects**:
- **TrackingInfo**
  - TrackingUrl, CurrentLocation, LastUpdateMessage, UpdatedAt
- **Dimensions**
  - Length, Width, Height, Unit

#### **Domain Events**:
- ShipmentCreatedEvent
- ShipmentPickedUpEvent
- ShipmentInTransitEvent
- ShipmentDeliveredEvent
- ShipmentFailedEvent

---

### **5. 🎨 Product Management**

#### **Aggregates**:
- **Product** (Aggregate Root)
  - ProductId
  - SellerId
  - Name
  - Description
  - Category
  - Subcategory
  - Price (Money)
  - SKU
  - ProductStatus (DRAFT, ACTIVE, INACTIVE, OUT_OF_STOCK)
  - Images (List of ProductImage)
  - Attributes (Map<String, String>)
  - Tags (List<String>)
  - CreatedAt, UpdatedAt

#### **Entities**:
- **ProductImage**
  - ImageId, Url, AltText, IsPrimary, DisplayOrder

- **ProductVariant**
  - VariantId, ProductId, SKU, Attributes (color, size), Price, Stock

#### **Value Objects**:
- **ProductDimensions**
  - Length, Width, Height, Weight

#### **Domain Events**:
- ProductCreatedEvent
- ProductUpdatedEvent
- ProductActivatedEvent
- ProductDeactivatedEvent
- PriceChangedEvent

---

### **6. 📂 Catalog Management**

#### **Aggregates**:
- **Category** (Aggregate Root)
  - CategoryId
  - Name
  - Description
  - ParentCategoryId
  - Level
  - DisplayOrder
  - IsActive
  - ImageUrl
  - Metadata (SEO, filters)

- **Collection** (Aggregate Root)
  - CollectionId
  - Name
  - Description
  - ProductIds (List)
  - IsActive
  - StartDate, EndDate

#### **Value Objects**:
- **CategoryMetadata**
  - MetaTitle, MetaDescription, Keywords

#### **Domain Events**:
- CategoryCreatedEvent
- CategoryUpdatedEvent
- CollectionCreatedEvent
- ProductAddedToCollectionEvent

---

### **7. 👤 Customer Management**

#### **Aggregates**:
- **Customer** (Aggregate Root)
  - CustomerId
  - Email
  - FirstName, LastName
  - Phone
  - CustomerStatus (ACTIVE, SUSPENDED, DELETED)
  - Addresses (List of Address)
  - DefaultShippingAddressId
  - DefaultBillingAddressId
  - CreatedAt, UpdatedAt

#### **Entities**:
- **Address**
  - AddressId, Type (SHIPPING, BILLING), FullName, AddressLine1, City, State, etc.

- **CustomerPreferences**
  - PreferredLanguage, Currency, NotificationSettings

#### **Domain Events**:
- CustomerRegisteredEvent
- CustomerUpdatedEvent
- AddressAddedEvent
- CustomerSuspendedEvent

---

### **8. 🏪 Seller Management**

#### **Aggregates**:
- **Seller** (Aggregate Root)
  - SellerId
  - BusinessName
  - Email
  - Phone
  - SellerStatus (PENDING_APPROVAL, ACTIVE, SUSPENDED, TERMINATED)
  - KYCStatus (NOT_SUBMITTED, PENDING, VERIFIED, REJECTED)
  - BankAccount (Value Object)
  - BusinessAddress (Value Object)
  - TaxInformation (Value Object)
  - CommissionRate
  - CreatedAt, UpdatedAt

#### **Value Objects**:
- **BankAccount**
  - AccountHolderName, AccountNumber, BankName, IFSCCode, AccountType
- **TaxInformation**
  - TaxId, GSTNumber, PANNumber

#### **Domain Events**:
- SellerRegisteredEvent
- SellerApprovedEvent
- SellerSuspendedEvent
- KYCSubmittedEvent
- KYCVerifiedEvent

---

### **9. ⭐ Review Management**

#### **Aggregates**:
- **ProductReview** (Aggregate Root)
  - ReviewId
  - ProductId
  - CustomerId
  - OrderId
  - Rating (1-5)
  - Title
  - Comment
  - ReviewStatus (PENDING, APPROVED, REJECTED, FLAGGED)
  - Images (List)
  - HelpfulCount
  - CreatedAt, UpdatedAt

- **SellerReview** (Aggregate Root)
  - ReviewId
  - SellerId
  - CustomerId
  - OrderId
  - Rating (1-5)
  - Comment
  - ReviewStatus

#### **Domain Events**:
- ReviewSubmittedEvent
- ReviewApprovedEvent
- ReviewFlaggedEvent

---

### **10. 🎁 Promotion Management**

#### **Aggregates**:
- **Promotion** (Aggregate Root)
  - PromotionId
  - Name
  - Description
  - PromotionType (PERCENTAGE_OFF, FIXED_AMOUNT_OFF, BUY_X_GET_Y, FREE_SHIPPING)
  - DiscountValue
  - ApplicableProducts (List)
  - ApplicableCategories (List)
  - MinimumOrderValue (Money)
  - MaximumDiscount (Money)
  - StartDate, EndDate
  - UsageLimit
  - UsageCount
  - IsActive

- **Coupon** (Aggregate Root)
  - CouponId
  - Code
  - PromotionId
  - DiscountType
  - DiscountValue
  - MinimumOrderValue
  - MaxUsagePerCustomer
  - TotalUsageLimit
  - ValidFrom, ValidUntil
  - IsActive

#### **Domain Events**:
- PromotionCreatedEvent
- CouponAppliedEvent
- PromotionExpiredEvent

---

### **11. 💰 Pricing Engine**

#### **Aggregates**:
- **PriceRule** (Aggregate Root)
  - PriceRuleId
  - ProductId
  - RuleType (BASE_PRICE, BULK_DISCOUNT, SEASONAL, DYNAMIC)
  - Conditions (List)
  - PriceAdjustment
  - Priority
  - ValidFrom, ValidUntil

#### **Value Objects**:
- **PriceCalculationResult**
  - BasePrice, Discount, Tax, FinalPrice, AppliedRules

#### **Domain Events**:
- PriceCalculatedEvent
- PriceRuleAppliedEvent

---

### **12. 🛒 Cart Management**

#### **Aggregates**:
- **ShoppingCart** (Aggregate Root)
  - CartId
  - CustomerId
  - CartItems (List of CartItem)
  - Subtotal (Money)
  - AppliedCoupons (List)
  - TotalDiscount (Money)
  - TotalAmount (Money)
  - ExpiresAt
  - CreatedAt, UpdatedAt

#### **Entities**:
- **CartItem**
  - CartItemId
  - ProductId
  - Quantity
  - UnitPrice (Money)
  - Subtotal (Money)

#### **Domain Events**:
- ItemAddedToCartEvent
- ItemRemovedFromCartEvent
- CartCheckedOutEvent
- CartAbandonedEvent

---

### **13. 📊 Analytics Management**

#### **Aggregates**:
- **ProductAnalytics** (Aggregate Root)
  - ProductId
  - ViewCount
  - AddToCartCount
  - PurchaseCount
  - ConversionRate
  - AverageRating
  - TotalRevenue (Money)
  - Period (DAILY, WEEKLY, MONTHLY)

- **SellerAnalytics** (Aggregate Root)
  - SellerId
  - TotalOrders
  - TotalRevenue (Money)
  - AverageOrderValue (Money)
  - CustomerCount
  - AverageRating

#### **Value Objects**:
- **Trend**
  - Metric, Value, ChangePercentage, Period

---

### **14. 💬 Messaging Management**

#### **Aggregates**:
- **Conversation** (Aggregate Root)
  - ConversationId
  - Participants (List of ParticipantId)
  - Messages (List of Message)
  - Status (ACTIVE, CLOSED)
  - CreatedAt, UpdatedAt

#### **Entities**:
- **Message**
  - MessageId
  - SenderId
  - Content
  - Attachments (List)
  - SentAt
  - ReadAt

#### **Domain Events**:
- MessageSentEvent
- MessageReadEvent
- ConversationClosedEvent

---

### **15. 🔔 Notification Management**

#### **Aggregates**:
- **Notification** (Aggregate Root)
  - NotificationId
  - RecipientId
  - NotificationType (ORDER_UPDATE, PAYMENT_CONFIRMATION, SHIPMENT_UPDATE, PROMOTION)
  - Channel (EMAIL, SMS, PUSH, IN_APP)
  - Subject
  - Message
  - Status (PENDING, SENT, FAILED, READ)
  - SentAt
  - ReadAt

#### **Domain Events**:
- NotificationCreatedEvent
- NotificationSentEvent
- NotificationReadEvent

---

### **16. ⚖️ Dispute Management**

#### **Aggregates**:
- **Dispute** (Aggregate Root)
  - DisputeId
  - OrderId
  - CustomerId
  - SellerId
  - DisputeType (PRODUCT_NOT_RECEIVED, PRODUCT_DAMAGED, WRONG_ITEM, REFUND_REQUEST)
  - DisputeStatus (OPEN, UNDER_REVIEW, RESOLVED, CLOSED)
  - Description
  - Evidence (List of Attachment)
  - Resolution
  - CreatedAt, ResolvedAt

#### **Domain Events**:
- DisputeOpenedEvent
- DisputeResolvedEvent
- DisputeEscalatedEvent

---

### **17. 💸 Financial Settlement**

#### **Aggregates**:
- **Settlement** (Aggregate Root)
  - SettlementId
  - SellerId
  - Period (StartDate, EndDate)
  - TotalSales (Money)
  - PlatformCommission (Money)
  - Refunds (Money)
  - NetAmount (Money)
  - SettlementStatus (PENDING, PROCESSED, FAILED)
  - ProcessedAt

- **Commission** (Aggregate Root)
  - CommissionId
  - OrderId
  - SellerId
  - OrderAmount (Money)
  - CommissionRate
  - CommissionAmount (Money)

#### **Domain Events**:
- SettlementCalculatedEvent
- SettlementProcessedEvent
- CommissionCalculatedEvent

---

### **18. 🔍 Search Management**

#### **Aggregates**:
- **SearchQuery** (Aggregate Root)
  - QueryId
  - Query
  - Filters (Map)
  - Facets (List)
  - SortBy
  - Page, Size

- **SearchAnalytics** (Aggregate Root)
  - SearchTerm
  - SearchCount
  - ResultCount
  - ClickThroughRate
  - Period

#### **Domain Events**:
- SearchPerformedEvent
- SearchResultClickedEvent

---

### **19. 👥 User Management**

#### **Aggregates**:
- **User** (Aggregate Root)
  - UserId
  - Username
  - Email
  - PasswordHash
  - UserType (CUSTOMER, SELLER, ADMIN)
  - UserStatus (ACTIVE, SUSPENDED, DELETED)
  - Roles (List)
  - Permissions (List)
  - LastLoginAt
  - CreatedAt

#### **Value Objects**:
- **UserProfile**
  - FirstName, LastName, Phone, Avatar

#### **Domain Events**:
- UserRegisteredEvent
- UserLoggedInEvent
- PasswordChangedEvent
- UserSuspendedEvent

---

### **20. 🏢 Tenant Management**

#### **Aggregates**:
- **Tenant** (Aggregate Root)
  - TenantId
  - Name
  - Domain
  - TenantStatus (ACTIVE, SUSPENDED, DELETED)
  - Configuration (Map)
  - SubscriptionPlan
  - CreatedAt

#### **Domain Events**:
- TenantCreatedEvent
- TenantActivatedEvent
- TenantSuspendedEvent

---

## 📊 DDD Summary by Module

| Module | Aggregates | Entities | Value Objects | Events |
|--------|-----------|----------|---------------|--------|
| Order Management | 1 | 1 | 3 | 6 |
| Inventory Management | 1 | 1 | 1 | 4 |
| Payment Management | 2 | 0 | 0 | 6 |
| Shipping Management | 1 | 0 | 2 | 5 |
| Product Management | 1 | 2 | 1 | 5 |
| Catalog Management | 2 | 0 | 1 | 4 |
| Customer Management | 1 | 2 | 0 | 4 |
| Seller Management | 1 | 0 | 2 | 5 |
| Review Management | 2 | 0 | 0 | 3 |
| Promotion Management | 2 | 0 | 0 | 3 |
| Pricing Engine | 1 | 0 | 1 | 2 |
| Cart Management | 1 | 1 | 0 | 4 |
| Analytics Management | 2 | 0 | 1 | 0 |
| Messaging Management | 1 | 1 | 0 | 3 |
| Notification Management | 1 | 0 | 0 | 3 |
| Dispute Management | 1 | 0 | 0 | 3 |
| Financial Settlement | 2 | 0 | 0 | 3 |
| Search Management | 2 | 0 | 0 | 2 |
| User Management | 1 | 0 | 1 | 4 |
| Tenant Management | 1 | 0 | 0 | 3 |

**Total**: 26 Aggregates, 8 Entities, 13 Value Objects, 72 Domain Events

---

This is a **complete DDD blueprint** for a production-ready e-commerce marketplace! 🚀
