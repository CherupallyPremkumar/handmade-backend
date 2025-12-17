# Catalog Management Module

## 🏢 Concept: "The Showroom vs. The Warehouse"
To understand this module, think of an IKEA:
*   **Product Management** is the **Warehouse**. It cares about pallets, SKUs, suppliers, and exact inventory counts. It is technical and messy.
*   **Catalog Management** (This Module) is the **Showroom**. It cares about beautiful displays, "Summer Sales", and featured items. It hides the messy details from the customer.

## 🌟 Key Features

### 1. Separation of Concerns
We don't mix "Marketing Data" (Showroom) with "Inventory Data" (Warehouse).
*   **`CatalogItem`**: A "Display Card" for a product. It has its own `featured` flag, `displayOrder`, and `tags`.
*   **Data Sync**: When the Warehouse updates the Price, we copy it here (via `ProductEventListener`) so the customer sees the current price, but we keep our own tags.

### 2. Smart Collections (The "Robot")
Instead of manually adding 100 items to a "Under $50" category, we define a **Rule**.
*   **Rule**: `price < 50.00`
*   **Engine**: `DynamicRuleEvaluator` (uses SpEL) checks every product against this rule.
*   **Scheduler**: Every hour, the `catalog-scheduler` runs through all products. If they match the rule, they get added to the collection automatically.

## 🏗️ Architecture

### Modules
*   **`catalog-api`**: The "Contract". Entities (`CatalogItem`, `Collection`) and Events.
*   **`catalog-service`**: The "Business Logic".
    *   **ACL**: `ProductServiceClient` fetches data from the Warehouse.
    *   **Listeners**: Listens for `ProductApprovedEvent`.
*   **`catalog-scheduler`**: The "Worker".
    *   **Spring Batch**: Reads products in chunks.
    *   **Quartz**: Triggers the job every hour.

## 🚀 How to Run
1.  Start the application.
2.  **`CatalogDataSeeder`** will automatically create 3 demo collections:
    *   "Under $50"
    *   "Summer Vibes"
    *   "Premium Selection"
3.  The **Scheduler** will eventually run (or trigger via API) and populate them based on product data.
