# Cart Price Recalculation - Design Patterns Implementation

## Overview
This document explains the design patterns implemented to handle automatic cart price recalculation when product prices change (including sale price transitions).

## Problem Statement
When a product's price changes or goes on/off sale, all cart items containing that product need to be updated to reflect the current price. This ensures customers always see accurate pricing in their carts.

## Design Patterns Used

### 1. **Strategy Pattern** - Pricing Calculation
**Location:** `com.homebase.ecom.strategy`

**Purpose:** Encapsulates different pricing algorithms to calculate product prices.

**Components:**
- `PricingStrategy` (Interface) - Defines the contract for price calculation
- `DefaultPricingStrategy` - Calculates price using regular product price
- `SalePricingStrategy` - Calculates price considering sale status (uses sale price if on sale, otherwise regular price)
- `PriceCalculationService` - Service that uses the strategy to calculate prices

**Benefits:**
- Easy to add new pricing strategies (bulk discounts, loyalty pricing, etc.)
- Separates pricing logic from business logic
- Testable and maintainable

**Example:**
```java
@Service
public class PriceCalculationService {
    private final PricingStrategy pricingStrategy;
    
    public BigDecimal getEffectivePrice(Product product) {
        if (product.isOnSale() && product.getSalePrice() != null) {
            return product.getSalePrice();
        }
        return product.getPrice();
    }
}
```

### 2. **Observer Pattern** - Price Change Notifications
**Location:** `com.homebase.ecom.observer`

**Purpose:** Automatically notifies and updates cart items when product prices change.

**Components:**
- `ProductPriceChangedEvent` - Event object containing price change details
- `ProductPriceObserver` - Listener that responds to price change events
- `ProductService` - Publisher that fires events when prices change
- `CartService` - Updates cart items when notified

**Flow:**
1. Admin updates product price or sale status in `ProductService`
2. `ProductService` publishes `ProductPriceChangedEvent`
3. `ProductPriceObserver` listens for the event
4. Observer calls `CartService.recalculateCartItemPrices()`
5. All cart items with that product are updated

**Benefits:**
- Loose coupling between product and cart modules
- Automatic synchronization
- Easy to add more observers (notifications, analytics, etc.)

**Example:**
```java
@Component
public class ProductPriceObserver {
    @EventListener
    public void onProductPriceUpdated(ProductPriceChangedEvent event) {
        cartService.recalculateCartItemPrices(event.getProductId(), tenantId);
    }
}
```

### 3. **Snapshot Pattern** - Price History
**Location:** `CartItem` entity

**Purpose:** Captures the price at the time the item was added to the cart for audit and comparison.

**Fields in CartItem:**
- `snapshotPrice` - The effective price when item was added
- `wasOnSale` - Whether product was on sale when added
- `originalPrice` - The regular (non-sale) price for reference

**Benefits:**
- Audit trail of price changes
- Can show customers "You saved $X" messages
- Historical data for analytics

## Implementation Details

### Price Change Detection
When a product is updated, the system checks:
1. Regular price changed
2. Sale price changed
3. Sale status changed (on sale → not on sale, or vice versa)

```java
boolean priceChanged = !oldPrice.equals(updated.getPrice());
boolean salePriceChanged = (oldSalePrice == null && updated.getSalePrice() != null) ||
                           (oldSalePrice != null && !oldSalePrice.equals(updated.getSalePrice()));
boolean saleStatusChanged = !oldOnSale.equals(updated.isOnSale());

if (priceChanged || salePriceChanged || saleStatusChanged) {
    // Publish event
}
```

### Cart Item Update Process
1. Find all cart items containing the product
2. For each item:
   - Get current effective price from product
   - Update snapshot price
   - Update sale status flags
   - Save cart item
3. Recalculate cart total

```java
public void recalculateCartItemPrices(Long productId, String tenantId) {
    List<CartItem> affectedItems = cartItemRepository
        .findCartItemsContainingProduct(productId, tenantId);
    
    for (CartItem item : affectedItems) {
        Product product = item.getProduct();
        updatePriceSnapshot(item, product);
        cartItemRepository.save(item);
        recalculateCartTotal(item.getCart());
    }
}
```

## API Endpoints

### Customer-Facing Cart APIs
- `GET /api/user/cart?customerId={id}&tenantId={tenant}` - Get cart items
- `POST /api/user/cart?customerId={id}&tenantId={tenant}` - Add to cart
- `PUT /api/user/cart/{itemId}?tenantId={tenant}` - Update quantity
- `DELETE /api/user/cart/{itemId}?tenantId={tenant}` - Remove item
- `DELETE /api/user/cart?customerId={id}&tenantId={tenant}` - Clear cart

### Admin Product APIs
- `PUT /api/products/{id}` - Update product (triggers price change event)

## Example Scenarios

### Scenario 1: Product Goes On Sale
1. Admin sets `onSale=true` and `salePrice=29.99` for a product
2. `ProductPriceChangedEvent` is published
3. All carts containing this product are updated
4. Cart items now show the sale price ($29.99 instead of $39.99)

### Scenario 2: Sale Ends
1. Admin sets `onSale=false` for a product
2. Event is published
3. Cart items are updated to regular price
4. Customers see the updated price in their cart

### Scenario 3: Price Increase
1. Admin increases regular price from $50 to $60
2. Event is published
3. All cart items are updated to $60
4. Customers are notified of the price change

## Multi-Tenant Support
All cart operations are tenant-aware:
- Cart items are filtered by `tenantId`
- Price change events only affect carts in the same tenant
- Each tenant can have different pricing strategies

## Testing the Implementation

### Test Price Change:
```bash
# 1. Add product to cart
curl -X POST http://localhost:8080/api/user/cart?customerId=1&tenantId=default \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}'

# 2. Update product price (as admin)
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Authorization: Bearer {admin_token}" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Product",
    "price": 59.99,
    "onSale": true,
    "salePrice": 49.99,
    ...
  }'

# 3. Get cart again - price should be updated
curl http://localhost:8080/api/user/cart?customerId=1&tenantId=default
```

## Future Enhancements

1. **Price Lock Strategy** - Allow customers to lock prices for X minutes
2. **Price Alert Observer** - Notify customers when items in their wishlist go on sale
3. **Dynamic Pricing Strategy** - Time-based, inventory-based pricing
4. **Price History Tracking** - Complete audit trail of all price changes
5. **Bulk Discount Strategy** - Quantity-based pricing tiers

## Benefits of This Architecture

✅ **Automatic Synchronization** - Cart prices always match current product prices
✅ **Extensible** - Easy to add new pricing strategies or observers
✅ **Maintainable** - Clear separation of concerns
✅ **Testable** - Each component can be tested independently
✅ **Audit Trail** - Complete history of price changes
✅ **Multi-Tenant** - Isolated per tenant
✅ **Real-time** - Immediate updates when prices change

## Code Structure

```
src/main/java/com/homebase/admin/
├── entity/
│   ├── Cart.java                    # Cart entity with items
│   ├── CartItem.java                # Item with price snapshot
│   └── Product.java                 # Product with sale fields
├── strategy/
│   ├── PricingStrategy.java         # Strategy interface
│   └── impl/
│       ├── DefaultPricingStrategy.java
│       └── SalePricingStrategy.java
├── observer/
│   ├── ProductPriceObserver.java    # Event listener
│   └── event/
│       └── ProductPriceChangedEvent.java
├── service/
│   ├── CartService.java             # Cart operations + recalculation
│   ├── ProductService.java          # Product updates + event publishing
│   └── PriceCalculationService.java # Price calculation logic
└── controller/
    └── user/
        ├── UserCartController.java  # Customer cart APIs
        └── UserProductController.java
```

## Conclusion

This implementation uses proven design patterns to create a robust, maintainable system for handling cart price updates. The combination of Strategy and Observer patterns ensures that cart prices are always accurate while keeping the code modular and extensible.
