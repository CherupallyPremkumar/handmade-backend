# Handmade Platform: Enterprise Schema Documentation

## 1. Platform Core & Infrastructure
Foundation for multi-tenancy, security, and operations.
*   **Identity & Access (IAM)**
    *   `hm_role`: RBAC roles (e.g., Seller Admin, Finance Manager).
    *   `hm_permission`: Granular system capabilities.
    *   `hm_user_role`: Assignment of roles to users.
    *   `hm_platform_owner`: Global tenant/platform configuration.
*   **Communications**
    *   `hm_notification_template`: SMS/Email templates.
    *   `hm_user_preference`: User opt-in/opt-out settings.
    *   `hm_notification_log`: Delivery audit trail.
*   **AdTech (Sponsored Products)**
    *   `hm_ad_campaign`: Seller advertising budgets and schedules.
    *   `hm_sponsored_product`: Bids on specific products.
    *   `hm_ad_performance`: Daily metrics (Clicks, Impressions, Spend).

## 2. Catalog & Taxonomy
Hierarchical organization of products (Amazon-style).
*   `hm_browse_node`: The category tree (e.g., Home > Kitchen > Cutlery).
*   `hm_collection`: Curated groups (e.g., "Summer Sale", "Gifts for Him").
*   `hm_catalog_item`: Lightweight reference for search indexing.
*   `hm_catalog_relationship`: Links between items (e.g., Variation, Accessory).
*   `hm_attribute_definition`: Global registry of attributes (Material, Size).

## 3. Product Management
The "Truth" about what a product is.
*   `hm_product`: Core product data (Brand, Manufacturer, Description).
*   `hm_product_media`: Images, Videos, Documents.
*   `hm_product_tags`: SEO keywords and internal tags.
*   `hm_product_attribute_value`: Actual values for attributes (e.g., Color=Red).

## 4. Offer & Pricing
The "Selling" layer. Separates "Product" from "Price".
*   `hm_offer`: A seller's intent to sell a product (Price, Condition).
*   `hm_offer_tier_price`: Quantity-based discounts (B2B).
*   `hm_shipping_template`: Shipping rules and rates.
*   `hm_shipping_price_rule`: Specific cost logic per region/weight.

## 5. Inventory & Supply Chain
Physical goods execution.
*   `hm_fulfillment_node`: Warehouses, Stores, or Drop-ship locations.
*   `hm_inventory_ledger`: Double-entry accounting for stock movements.
*   `hm_inventory_position`: Current aggregate stock levels.
*   **Fulfillment by Handmade (FBA)**
    *   `hm_inbound_shipment`: Sellers sending stock to our warehouses.
    *   `hm_inbound_shipment_item`: Contents of inbound shipments.
*   **1P Retail Procurement**
    *   `hm_vendor_purchase_order`: POs issued to manufacturers.
    *   `hm_vendor_po_line`: Line items for POs.

## 6. Order Management
The checkout and delivery lifecycle.
*   `hm_order`: The customer's purchase record.
*   `hm_order_line`: Individual items purchased.
*   `hm_shipment`: A physical package being sent.
*   `hm_shipment_item`: Contents of a package.

## 7. Customer Ecosystem
Buyer identity and engagement.
*   `hm_customer_profile`: Identity, Demographics.
*   `hm_customer_address`: Address book.
*   `hm_customer_wallet`: Store credit, Gift card balances.
*   `hm_wishlist`: Saved items.
*   `hm_subscription`: Recurring deliveries (Subscribe & Save).

## 8. Seller Ecosystem
Managing the merchants.
*   `hm_seller_kyc`: "Know Your Customer" documents and verification.
*   `hm_seller_store`: Public storefront profile.
*   `hm_seller_feedback`: Buyer ratings and reviews.
*   `hm_seller_performance`: ODR (Order Defect Rate) and health metrics.
*   `hm_seller_financial_event`: Ledger of fees, sales, and withheld amounts.

## 9. Finance & Payments
The flow of money.
*   `hm_transaction`: Raw payment gateway events (Charge, Refund).
*   `hm_payout`: Transfers to seller bank accounts.
*   `hm_tax_code`: Tax categories for products.

## 10. Customer Service (CS) & Support
Post-order operations.
*   `hm_case_management`: Ticketing system for inquiries.
*   `hm_order_note`: Internal CS agent logs.
*   `hm_refund_resolution`: Dispute arbitration (A-to-Z Claims).

## 11. Marketing & Analytics
Intelligence and optimization.
*   `hm_browse_history`: Recently viewed items.
*   `hm_recommendation`: "Also Bought" / "Similar Items" graph.
*   `hm_search_term_report`: Aggregated search query analytics.

---
**Technical Note**: All tables include standard `BaseJpaEntity` audit fields: `created_time`, `last_modified_time`, `created_by`, `last_modified_by`, `tenant`, `version`.
