# Handmade Platform - DDD Entity Architecture

This diagram visualizes the core entity relationships after the Phase 10 refactoring. The architecture is **Offer-Centric** and supports **Multi-Region** pricing and **Multi-Location** inventory.

```mermaid
classDiagram
    direction LR
    
    class Product {
        +String productId
        +String name
        +String story
        +String category
        +List materials
        +createdAt
    }

    class Variant {
        +String variantId
        +String productId
        +String sku
        +String color
        +String size
        +Double weightKg
    }

    class Seller {
        +String sellerId
        +String businessName
        +String email
        +String kycStatus
        +String regionCode
    }

    class Offer {
        +String offerCode
        +String variantId
        +String sellerId
        +String regionId
        +BigDecimal price
        +String currency
        +String fulfillmentType
        +Boolean active
    }

    class Inventory {
        +String variantId
        +String sellerId
        +String locationId
        +Integer quantityAvailable
        +Integer quantityReserved
        +Boolean backOrderAllowed
    }

    class Region {
        +String regionCode
        +String name
        +String defaultCurrency
    }

    class Location {
        +String locationId
        +String name
        +String type
        +String address
    }

    Product "1" --> "*" Variant : contains
    Offer "*" -- "1" Variant : of
    Offer "*" -- "1" Seller : by
    Offer "*" -- "1" Region : for
    
    Inventory "*" -- "1" Variant : tracks
    Inventory "*" -- "1" Seller : owned_by
    Inventory "*" -- "1" Location : at
    
    %% Conceptual relationship
    Offer ..> Inventory : derived status
```

## Key Architectural Concepts

### 1. The Global Product (ASIN Layer)
`Product` and `Variant` represent the **Single Point of Truth** for what an item *is*. This data is global and does not change based on where it is sold.

### 2. The Commercial Offer (SKU Layer)
The `Offer` is the most important commercial entity. It defines a **Seller's Proposition** for a `Variant` in a specific `Region`. This allows:
- **Regional Pricing**: Selling the same item for \$50 in the US and ₹4000 in India.
- **Multi-Sourcing**: Multiple artisans selling the same design (Variant) at different price points.

### 3. The Physical Inventory
`Inventory` is decoupled from the `Offer` to support complex logistics. Stock is tracked per `Location`, meaning a seller can have inventory in their own studio AND in a regional fulfillment center.

### 4. Bounded Context Separation
- **Product Context**: Owns `Product` and `Variant`.
- **Offer Context**: Owns `Offer` and `Region`.
- **Inventory Context**: Owns `Inventory` and `Location`.

These contexts communicate via events (e.g., `StockUpdatedEvent`) to ensure loose coupling and horizontal scalability.
