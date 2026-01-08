# Handmade Platform: Detailed Data Dictionary

This document provides a detailed breakdown of every table managed by the `handmade-liquibase-management` module, organized by domain.

---

## 1. Platform Domain
Core governance and global platform entities.

### `hm_platform`
*   **Purpose**: Stores global platform settings and organizational identity.
*   **Key Usage**: Used to identify different platform instances (e.g., US, India) and their core operational flags.
*   **Relationship**: Parent of almost all other entities via `platform_id`.

### `hm_platform_feature`
*   **Purpose**: Feature toggle management at the platform level.
*   **Key Usage**: Controls availability of features like "One-Click Checkout" or "Global Shipping" for specific platform instances.

### `hm_audit_snapshot`
*   **Purpose**: Immutable snapshots of sensitive configurations for point-in-time auditing.
*   **Key Usage**: Records state of policies or financial rules before major changes.

---

## 2. Seller Domain
Management of seller identity, lifecycle, and performance.

### `hm_seller_account`
*   **Purpose**: The root of the Seller aggregate.
*   **Key Usage**: Stores authentication mapping (email), verification status, and state machine fields for onboarding.
*   **Relationship**: Parent of all seller-specific data (Store, Tax, KYC).

### `hm_seller_store`
*   **Purpose**: Public-facing seller presence.
*   **Key Usage**: Stores URLs, logos, display names, and ratings.
*   **Relationship**: Linked to `hm_seller_account` (1:1 per marketplace).

### `hm_seller_kyc`
*   **Purpose**: Identity verification documents.
*   **Key Usage**: Tracks document types (Passport, Tax ID), status (Verified/Rejected), and storage paths (S3 keys).

### `hm_seller_goal`
*   **Purpose**: Performance targets for sellers.
*   **Key Usage**: Tracks monthly GMV targets, cancellation rate limits, and achievement status.

### `hm_seller_recommendation`
*   **Purpose**: System or AI-generated actionable insights.
*   **Key Usage**: Suggests price changes, inventory restocks, or ad campaigns to improve seller performance.

### `hm_seller_action_item`
*   **Purpose**: Mandatory tasks and compliance warnings.
*   **Key Usage**: Tracks critical items like "Tax ID Required" or "Policy Violation" that block seller operations.

### `hm_seller_badge`
*   **Purpose**: Gamification and reputation markers.
*   **Key Usage**: Awards badges like "Top Seller" or "Fast Shipper" based on performance metrics.

---

## 3. Platform Policies (Centralized Governance)
Specialized rulesets that govern platform behavior.

### `hm_onboarding_policy`
*   **Purpose**: Defines required steps and compliance for new sellers.
*   **Key Usage**: Linked to a seller during registration to enforce country-specific onboarding flows.

### `hm_promotion_policy`
*   **Purpose**: Global rules for platform-funded discounts.
*   **Key Usage**: Defines max discount percentages and funding split between platform and seller.

### `hm_commission_policy`
*   **Purpose**: Revenue model for the platform.
*   **Key Usage**: Defines category-based percentages and fixed fees charged to sellers.

### `hm_error_log`
*   **Purpose**: Centralized system error tracking.
*   **Key Usage**: Records exceptions, stack traces, and service-level health.
*   **Relationship**: Independent, metadata-driven observability.

### `hm_metrics_snapshot`
*   **Purpose**: Point-in-time capture of operational metrics.
*   **Key Usage**: Powering infrastructure dashboards (Latency, Throughput).

### `hm_activity_stream`
*   **Purpose**: High-volume, unified stream of all system/user actions.
*   **Key Usage**: Real-time audit trails and behavioral analysis.

### `hm_payout_policy` / `hm_payout_policy_rule`
*   **Purpose**: Controls Disbursement frequency and conditions.
*   **Key Usage**: Stores rules for "Express Payouts" vs "Standard Payouts" and minimum settlement thresholds.

### `hm_compliance_policy`
*   **Purpose**: Global regulatory and safety rules.
*   **Key Usage**: Tracks prohibited items, restricted categories, and mandatory certifications per region.

### `hm_tax_policy`
*   **Purpose**: Tax calculation logic governance.
*   **Key Usage**: Maps nexus rules and product tax codes to specific calculation engines.

### `hm_language`
*   **Purpose**: Supported languages per region.
*   **Key Usage**: Used for multi-lingual UI and product content selection.

### `hm_translation`
*   **Purpose**: Dictionary for UI and content.
*   **Key Usage**: Stores localized strings for products, policies, and labels.

### `hm_currency_conversion`
*   **Purpose**: Historical exchange rates.
*   **Key Usage**: Powers multi-currency price calculations and financial reporting.

### `hm_fulfillment_policy`
*   **Purpose**: Logistics and delivery standards.
*   **Key Usage**: Defines mandatory SLA (Service Level Agreements) for delivery times and packaging standards.

### `hm_product_eligibility_policy`
*   **Purpose**: Controls which products can be sold on specific marketplaces.
*   **Key Usage**: Checks for copyright, hazardous materials, or category gatedness.

### `hm_policy_experiment_flags`
*   **Purpose**: A/B testing for platform policies.
*   **Key Usage**: Allows testing different commission rates or tax rules for a subset of sellers.

---

## 4. Catalog Domain
Global product definitions.

### `hm_category`
*   **Purpose**: Hierarchical taxonomy (Browse Nodes).
*   **Key Usage**: Used for product categorization and search filtering.

### `hm_product`
*   **Purpose**: Global product identifiers (ASIN-equivalent).
*   **Key Usage**: Core attributes (Name, Brand) that are seller-agnostic.

### `hm_product_rich_media`
*   **Purpose**: Advanced product assets (360 views, AR models).
*   **Key Usage**: Enhances product storytelling and VR/AR browsing.

---

## 5. Offer Domain
Seller-specific commercial conditions for a product.

### `hm_offer`
*   **Purpose**: Represents a seller's intent to sell a specific product.
*   **Key Usage**: Stores SKU, condition (New/Used), and link to the global `hm_product`.
*   **Relationship**: Child of `hm_product` and `hm_seller_account`.

### `hm_offer_price`
*   **Purpose**: Pricing for an offer.
*   **Key Usage**: Tracks current price, MSRP, and currency.
*   **Relationship**: 1:1 or 1:N with `hm_offer` depending on regional pricing.

### `hm_shipping_template`
*   **Purpose**: Reusable shipping configurations.
*   **Key Usage**: Defines shipping speed, costs, and availability regions.

---

## 6. Inventory Domain
Physical stock management.

### `hm_inventory_item`
*   **Purpose**: Tracks stock levels for a specific seller's product.
*   **Key Usage**: Stores quantities (available, reserved, damaged).
*   **Relationship**: Linked to `hm_offer` via SKU/SellerId.

### `hm_inventory_position`
*   **Purpose**: Tracks inventory across multiple warehouses/locations.
*   **Key Usage**: Used for multi-node fulfillment logic.

---

## 7. Order Domain
Transactional processing of customer purchases.

### `hm_order`
*   **Purpose**: Customer order header.
*   **Key Usage**: Tracks order status (Pending, Shipped, Cancelled), totals, and buyer ID.

### `hm_order_line`
*   **Purpose**: Individual items within an order.
*   **Key Usage**: Links to `hm_offer` and calculates taxes/discounts per unit.

---

## 8. Finance Domain
Money movement, payouts, and taxes.

### `hm_financial_transaction`
*   **Purpose**: Ledger of all financial movements.
*   **Key Usage**: Tracks debits/credits for orders, refunds, and fees.

### `hm_payout`
*   **Purpose**: Periodic disbursements to sellers.
*   **Key Usage**: Summarizes transactions for a settlement period and tracks bank transfer status.

---

## 9. Analytics & Search
Data signals and business intelligence.

### `hm_ranking_signal`
*   **Purpose**: Search relevance factors.
*   **Key Usage**: Stores popularity, conversion rates, and relevance weights for products.

### `hm_recommendation_graph`
*   **Purpose**: Product relationships for "Customers also bought".
*   **Key Usage**: Stores directed edges between related product IDs.

### `hm_search_index_version`
*   **Purpose**: Version control for search indices (ElasticSearch/OpenSearch).
*   **Key Usage**: Manages aliases and reindexing status.

### `hm_order_summary`
*   **Purpose**: Pre-aggregated sales data for BI.
*   **Key Usage**: Hourly/Daily rollups of revenue and order counts.

### `hm_product_popularity_signal`
*   **Purpose**: Tracks aggregated popularity metrics (views, wishlist adds).
*   **Key Usage**: Used for "Trending" and "Most Gifted" product carousels.

### `hm_user_behavior_signal`
*   **Purpose**: Captures granular interaction data (dwell time, clicks).
*   **Key Usage**: Powers machine learning models for personalized recommendations.

### `hm_recommendation_experiment_result`
*   **Purpose**: Stores A/B test outcomes for recommendation algorithms.
*   **Key Usage**: Used to track conversion lift and revenue impact of different reco strategies.

---

## 10. AdTech Domain
Sponsored visibility and marketing.

### `hm_ad_campaign`
*   **Purpose**: Seller-funded marketing campaigns.
*   **Key Usage**: Tracks budgets, dates, and campaign types (PPC, Display).

### `hm_sponsored_product`
*   **Purpose**: Products targeted in campaigns.
*   **Key Usage**: Links specific `hm_offer` IDs to active ad groups.

---

## 11. IAM & Security
Access control and identity management.

### `hm_role` / `hm_permission`
*   **Purpose**: RBAC (Role-Based Access Control) definitions.
*   **Key Usage**: Maps hierarchical permissions (e.g., `CATALOG_EDIT`, `ORDER_VIEW`) to roles (e.g., `SELLER_ADMIN`).

---

## 12. Support & Trust
Customer service, fraud prevention, and compliance.

### `hm_case_management`
*   **Purpose**: Unified support ticketing.
*   **Key Usage**: Tracks disputes, returns, and general inquiries.

### `hm_fraud_case`
*   **Purpose**: Risk-engine flagged events.
*   **Key Usage**: Records suspicious IP activity, high-value velocity alerts, and verification failures.

### `hm_fraud_signal`
*   **Purpose**: Raw signal ingestion for risk engines.
*   **Key Usage**: High-velocity capture of IP/Device/User fingerprints.

---

## 13. Notifications
Async communication and user preferences.

### `hm_notification_queue`
*   **Purpose**: Decouples notification trigger from delivery lifecycle.
*   **Key Usage**: Stores pending notifications (Email, SMS) for background sending.

### `hm_notification_log`
*   **Purpose**: Audit trail for all sent communications.
*   **Key Usage**: Records delivery status and engagement metrics.

---

## 14. Content & CMS Domain
Management of marketing content and UI assets.

### `hm_content_page`
*   **Purpose**: Managed CMS pages (Help, FAQ).
*   **Key Usage**: Stores raw HTML/Markdown content and SEO meta for static pages.

### `hm_content_asset`
*   **Purpose**: Multi-purpose media library.
*   **Key Usage**: Stores banner images, promotional videos, and UI icons.

### `hm_content_slot`
*   **Purpose**: Dynamic UI placeholders.
*   **Key Usage**: Controls which assets appear in "Hero" or "Sidebar" slots.

---

## 15. Loyalty & Engagement Domain
Customer retention and viral growth.

### `hm_loyalty_program`
*   **Purpose**: Membership management (Prime-style levels).
*   **Key Usage**: Defines benefits like "Free Shipping" per tier.

### `hm_loyalty_points`
*   **Purpose**: Points ledger for rewards.
*   **Key Usage**: Tracks balance, earnings, and redemptions.

### `hm_referral_program`
*   **Purpose**: Viral referral tracking.
*   **Key Usage**: Manages referral codes and associated growth metrics.

---

## 16. Governance & Compliance
Regulatory tracking and privacy.

### `hm_privacy_policy_ack`
*   **Purpose**: Legal compliance tracking.
*   **Key Usage**: Records user acceptance of TOC and Privacy policies versioned over time.

### `hm_gdpr_request`
*   **Purpose**: Privacy workflow management.
*   **Key Usage**: Tracks status of "Right to be Forgotten" or "Data Export" requests.

---

## 17. SEO & Contextual Discovery
Search engine optimization and URL management.

### `hm_url_mapping`
*   **Purpose**: SE-friendly URL management.
*   **Key Usage**: Maps vanity URLs to internal paths and handles 301/302 redirects.

### `hm_meta_tag`
*   **Purpose**: Page-specific SEO metadata.
*   **Key Usage**: Stores titles, descriptions, and keywords for better discovery.

---

## 18. Integration & Ecosystem
External system connections.

### `hm_external_vendor`
*   **Purpose**: Partner management (3PL, Tax, Payment).
*   **Key Usage**: Tracks integration protocols and active status of external providers.

### `hm_webhook`
*   **Purpose**: Real-time outbound notifications.
*   **Key Usage**: Delivers events like `ORDER_SHIPPED` to third-party endpoints.

### `hm_etl_job`
*   **Purpose**: Data pipeline monitoring.
*   **Key Usage**: Tracks status and throughput of BI and integration jobs.

---

## 27. Marketplace Trust & Reviews
Customer feedback and content moderation.

### `hm_product_review`
*   **Purpose**: Customer text reviews and ratings.
*   **Key Usage**: Stores 1-5 star ratings, review text, images, and verification status.

### `hm_product_review_vote`
*   **Purpose**: Community feedback on reviews.
*   **Key Usage**: Tracks "Helpful" / "Not Helpful" votes to rank top reviews.

### `hm_review_moderation_log`
*   **Purpose**: Audit trail for safety moderation.
*   **Key Usage**: Records who approved/rejected a review and why (e.g., "Inappropriate Content").

### `hm_review_aggregation_snapshot`
*   **Purpose**: Read-optimized rating cache.
*   **Key Usage**: Stores calculated average ratings and distribution counts (e.g., 80% 5-star) to prevent expensive real-time queries.

---

## 28. Fulfillment & Logistics
FBA-style inventory management and last-mile delivery tracking.

### `hm_carrier`
*   **Purpose**: Shipping service providers.
*   **Key Usage**: Manages integrations with FedEx, UPS, DHCP, etc., including API credentials and tracking URL templates.

### `hm_shipping_label`
*   **Purpose**: Generated postage and tracking.
*   **Key Usage**: Links an order shipment to a carrier's tracking number and stores the printable label URL.

### `hm_delivery_attempt`
*   **Purpose**: Granular tracking history.
*   **Key Usage**: Records every delivery attempt, including timestamps, outcomes (Success/Failed), and coordinates.

### `hm_delivery_exception`
*   **Purpose**: Shipping problem management.
*   **Key Usage**: Tracks delays, damages, or lost packages requiring intervention.

### `hm_warehouse_zone`
*   **Purpose**: Physical inventory location management.
*   **Key Usage**: Defines zones (Picking, Receiving) within a warehouse for optimized inventory placement.

### `hm_route_plan`
*   **Purpose**: Last-mile delivery optimization.
*   **Key Usage**: Groups shipments into efficient driver routes for local delivery services.

---

## 29. Advanced Policy Engine
Centralized, rule-based governance system.

### `hm_policy_definition`
*   **Purpose**: Global registry of policies.
*   **Key Usage**: Defines high-level intent (e.g., "Global Refund Policy") and default outcomes.

### `hm_policy_rule`
*   **Purpose**: JSON-based logic blocks.
*   **Key Usage**: Stores versioned conditions (`{"region": "US"}`) and effects (`{"result": "ALLOW"}`).

### `hm_policy_scope`
*   **Purpose**: Context binding.
*   **Key Usage**: Links rules to specific dimensions like Region, Tenant, or Environment.

### `hm_policy_decision`
*   **Purpose**: Runtime audit log.
*   **Key Usage**: Records the final decision ("ALLOW"/"DENY") for high-level auditing.

### `hm_policy_evaluation_log`
*   **Purpose**: Deep-dive explainability.
*   **Key Usage**: Traces exactly which rules matched and their contribution to the final decision.

---

## 30. Advanced Experiment Engine
Dynamic A/B testing and Feature Flagging system.

### `hm_experiment_definition`
*   **Purpose**: Master registry of experiments.
*   **Key Usage**: Defines the experiment (e.g. "Checkout Button Color"), its type (AB_TEST/FEATURE_FLAG), and status.

### `hm_experiment_bucket`
*   **Purpose**: Experiment Variants.
*   **Key Usage**: Defines the buckets (e.g. "Control", "Treatment") and their configuration payloads (`{"color": "blue"}`).

### `hm_experiment_audience`
*   **Purpose**: Targeting Rules.
*   **Key Usage**: JSON-based rules defining who is eligible for the experiment (e.g. `{"device": "mobile"}`).

### `hm_experiment_assignment`
*   **Purpose**: Runtime assignment logs.
*   **Key Usage**: Durable record of which entity was assigned to which variant for consistent experience.

---

## 40. Unified Pricing Engine
Centralized system for monetary impact rules (Price, Promotion, Fees).

### `hm_pricing_rule_definition`
*   **Purpose**: Master rule registry.
*   **Key Usage**: Defines the rule (e.g. "Holiday Sale"), type (DISCOUNT/SURCHARGE), and status.

### `hm_pricing_rule_condition`
*   **Purpose**: JSON Matching Logic.
*   **Key Usage**: Defines where the rule applies (e.g. `{"category": "ELECTRONICS"}`).

### `hm_pricing_rule_action`
*   **Purpose**: Financial Impact.
*   **Key Usage**: Defines the output (e.g. `PERCENTAGE_OFF`, value `10.00`).

### `hm_pricing_priority`
*   **Purpose**: Conflict Resolution.
*   **Key Usage**: Determines stacking order and exclusivity (e.g. Priority 100).

### `hm_pricing_decision_log`
*   **Purpose**: Financial Audit Trail.
*   **Key Usage**: Immutable record of price calculations for every transaction.

---

## 45. Limits & Quotas Engine
Dynamic tracking of resource usage and enforcement of rate limits (API, Products, Storage).

### `hm_limit_definition`
*   **Purpose**: Limit Registry.
*   **Key Usage**: Defines the limit (e.g. "Max Products"), resource type, and default global value.

### `hm_limit_scope`
*   **Purpose**: Targeting Rules.
*   **Key Usage**: JSON logic defining override values for specific groups (e.g. `{"seller_tier": "GOLD"`} = 1M products).

### `hm_limit_counter`
*   **Purpose**: Usage State.
*   **Key Usage**: Tracks runtime usage for an entity (e.g. `seller-123` has 50 products).
