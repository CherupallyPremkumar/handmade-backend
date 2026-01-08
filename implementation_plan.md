# Implementation Plan: BaseJpaEntity Standardization

## Goal
Ensure every table in the Handmade platform aligns with the Java `BaseJpaEntity` class, enabling automatic auditing, multi-tenancy, and optimistic locking.

## Proposed Changes

### Standard Column Set
Every table will be ensured to have the following columns:
- **`created_time`**: `timestamp`
- **`last_modified_time`**: `timestamp`
- **`last_modified_by`**: `varchar(255)`
- **`created_by`**: `varchar(255)`
- **`tenant`**: `varchar(255)`
- **`version`**: `bigint` (Default: 0)

### Audit & Cleanup
- Relocate columns to the end of the `createTable` block for consistency.
- Replace legacy variants (e.g., `created_at`, `updated_at`, `updated_time`) with the standard set.
- Ensure `id` remains the primary key.

## Affected Domains
- All 26 domains (Catalog, Order, Seller, Customer, etc.)
- ~150 individual Liquibase changelog files.

### Phase 2b: Seller Growth Tooling (New)
- `hm_seller_goal`: Targets for sellers (gmv, quality).
- `hm_seller_recommendation`: AI/System driven advice.
- `hm_seller_action_item`: Mandatory tasks.
- `hm_seller_badge`: Gamification/Reputation.

### Phase 2c: Marketplace Trust & Reviews (B2C)
- **Domain**: `reviews`
- **Tables**:
    - `hm_product_review`: Text and star rating.
    - `hm_product_review_vote`: Helpful/Not Helpful counters.
    - `hm_review_moderation_log`: Audit trail for content moderation.
    - `hm_review_aggregation_snapshot`: Performance optimization for read-heavy review summaries.

### Phase 2d: Fulfillment & Logistics (FBA-Style)
- **Domain**: `logistics`
- **Tables**:
    - `hm_carrier`: Shipping providers (FedEx, UPS).
    - `hm_shipping_label`: Generated labels and tracking numbers.
    - `hm_delivery_attempt`: Tracking of failed/successful delivery attempts.
    - `hm_delivery_exception`: Delays, damages, or lost packages.
    - `hm_warehouse_zone`: Physical zones within a fulfillment center.
    - `hm_route_plan`: Optimised routing for last-mile delivery.

### Phase 3: Advanced Policy Engine (Centralized Governance)
**Goal**: Replace hardcoded/column-based policies with a dynamic, prioritized, JSON-rule engine.

- **Domain**: `policy-engine`
- **Tables**:
    1.  **`hm_policy_definition`** (Intent)
        -   **Purpose**: Global registry of policies (e.g., "Refund Policy", "Seller Eligibility").
        -   **Cols**: `policy_key` (Unique Enum), `default_effect` (ALLOW/DENY), `owner_service`.
    2.  **`hm_policy_rule`** (Rules)
        -   **Purpose**: logic blocks.
        -   **Cols**: `condition_json` (logic), `effect_json` (result), `priority`, `active_dates`.
    3.  **`hm_policy_scope`** (Scope)
        -   **Purpose**: Binds rules to specific contexts (Regions, Tenants).
        -   **Cols**: `rule_id`, `scope_dimension` (e.g., REGION), `scope_value` (e.g., US).
    4.  **`hm_policy_decision`** (Decision Audit)
        -   **Purpose**: High-level log of "What happened?".
        -   **Cols**: `trace_id`, `input_context_hash`, `final_decision`.
    5.  **`hm_policy_evaluation_log`** (Explainability)
        -   **Purpose**: Deep-dive trace of *why* it happened.
        -   **Cols**: `decision_id`, `rule_id`, `matched_boolean`, `contribution`.

### Phase 4: Policy Migration (Legacy -> Dynamic)
**Goal**: Migrate hardcoded legacy policy tables into the new Policy Engine.

- **Source**: `hm_platform_policy_product_eligibility` (Legacy)
- **Target**: `policy-engine` tables
- **Migration Data**:
    - **Policy**: `PRODUCT_ELIGIBILITY`
    - **Rule Definition**: Convert `prohibited_keywords` column into JSON condition `{"not_contains_keywords": ["bad", "words"]}`.
    - **Scope**: Map `seller_tier` column to `hm_policy_scope`.

### Phase 5: Advanced Experiment Engine
**Goal**: Create a dynamic, runtime-configurable A/B testing and feature flag system, replacing static schema-based approaches.

#### New Domain: `experiment-engine`
- `hm_experiment_definition`:
    - Registry of all experiments/feature flags.
    - Cols: `experiment_key` (unique), `status` (ACTIVE/PAUSED), `type` (AB_TEST/FEATURE_FLAG).
- `hm_experiment_variant`:
    - Defines the buckets (Control vs Treatment).
    - Cols: `variant_key`, `payload_json` (config), `allocation_weight`.
- `hm_experiment_audience`:
    - Targeting rules (Who sees this?).
    - Cols: `rule_json` (e.g., `{"region": "US", "beta_user": true}`).
- `hm_experiment_assignment`:
    - Durable record of "Who got what".
    - Cols: `entity_id`, `variant_id`, `assigned_at`.

#### Deprecation
- **Target**: `hm_platform_policy_experiment`, `hm_experiment` (Legacy).
- **Action**: Mark as deprecated in documentation.

### Phase 6: Unified Pricing & Promotion Engine
**Goal**: Unify all monetary impact rules (Price, Promotion, Fee, Commission) under a single, auditable engine.

#### New Domain: `pricing-engine`
- `hm_pricing_rule_definition`:
    - Master registry of pricing rules.
    - Cols: `rule_key`, `rule_type` (DISCOUNT, SURCHARGE, FEE, COMMISSION), `status`.
- `hm_pricing_rule_condition`:
    - JSON-based matching logic (Where does this apply?).
    - Cols: `condition_json` (e.g., `{"category": "ELECTRONICS", "cart_total": {">": 100}}`).
- `hm_pricing_rule_action`:
    - The financial effect.
    - Cols: `action_type` (PERCENTAGE_OFF, FIXED_AMOUNT, OVERRIDE), `value`, `currency`.
- `hm_pricing_priority`:
    - Conflict resolution and stacking rules.
    - Cols: `priority_score`, `stacking_group` (can this combine with other coupons?).
- `hm_pricing_decision_log`:
    - Financial audit trail.
    - Cols: `transaction_id`, `original_price`, `final_price`, `applied_rules_json`.

#### Deprecation target
- **Target**: `hm_price_rule`, `hm_order_adjustment` (Legacy logic), `hm_commission_rule`.
- **Action**: Migrate to Pricing Engine rules.

### Phase 7: Limits & Quotas Engine
**Goal**: Replace rigid tenant-based quotas with a flexible, scope-based limit system (Seller, API, Region, Tier).

#### New Domain: `limit-engine`
- `hm_limit_definition`:
    - Registry of limits (e.g. "Max Products per Seller", "API Rate Limit").
    - Cols: `limit_key`, `resource_type`, `default_value`.
- `hm_limit_scope`:
    - Targeting logic (Who gets what limit?).
    - Cols: `scope_json` (e.g., `{"seller_tier": "GOLD"}`), `override_value`.
- `hm_limit_counter`:
    - Runtime usage tracking (e.g. Redis-backed persistence or DB sync).
    - Cols: `entity_id`, `limit_key`, `current_usage`, `reset_time`.

#### Deprecation target
- **Target**: `hm_tenant_quota`, `hm_api_rate_limit`.
- **Action**: Deprecate hardcoded tenant columns in favor of scope rules.

### Phase 8: Codebase Hygiene (Refactoring)
**Goal**: Enforce "One File One Table" across all legacy changelogs.

#### Refactoring Targets
1.  **Catalog**:
    - Split `004-create-catalog-mappings.yaml` -> `hm_product_browse_node_mapping`, `hm_collection_product_mapping`.
    - Split `005-create-attribute-definition.yaml` -> `hm_attribute_definition`, `hm_browse_node_attribute_rule`, `hm_catalog_item_attribute`.
2.  **Finance**:
    - Split `004-create-fee-structure.yaml` -> `hm_fee_definition`, `hm_fee_application`.
3.  **Risk**:
    - Split `001-create-fraud-case.yaml` -> `hm_risk_signal`, `hm_fraud_case`.
4.  **Seller**:
    - Split `004-create-seller-tax-info.yaml` -> `hm_seller_tax_info`, `hm_seller_tax_documents`.

### Phase 9: Unified Analytics Engine
**Goal**: Centralize scattered domain stats into a unified event stream and scalable aggregation layer.
**Schema**:
- `hm_analytics_event`: Immutable raw event log.
    - **Optimizations**: Indexed columns for `event_type`, `session_id`, `user_id`, `entity_id`, `entity_type` to allow efficient filtering. flexible `payload_json` for details.
- `hm_analytics_session`: User session tracking.
    - **Optimizations**: `expires_at` and `status` columns for lifecycle management.
- `hm_analytics_metric_def`: Definition of aggregable metrics (e.g. "Page Views").
- `hm_analytics_metric_value`: **[NEW]** Precomputed aggregates store.
    - Columns: `metric_def_id`, `dimension` (e.g. region/category), `time_bucket` (hour/day), `value`.
    - Enables fast reporting without scanning millions of raw event rows.

### Phase 10: Headless CMS Engine
**Goal**: Decouple content from code using a flexible schema-driven model with full version control.
**Schema**:
- `hm_cms_schema`: Defines content types (e.g. "Banner", "Blog Post") with JSON validation rules.
- `hm_cms_entry`: Master record for a content piece.
    - **Optimizations**: `slug`, `title`, `status` (DRAFT/PUBLISHED), `publish_start`, `publish_end` for easy lookup and scheduling.
- `hm_cms_entry_version`: **[NEW]** Immutable history of content changes.
    - Stores `content_json` per version to allow rollbacks and audit trails.
- `hm_cms_asset_link`: Many-to-many linkage between entries and media assets.

### Phase 11: Schema Cleanup (Legacy Removal)
**Goal**: Remove redundant tables replaced by dynamic engines to prevent technical debt.
**Targeted Deletions**:
1.  **Platform Policies**: `platform-policies/*` (Superseded by Policy Engine).
2.  **Legacy Promotions**: `promotion/*` (Superseded by Pricing Engine).
3.  **Legacy Limits**: `limits/*` (Superseded by Limit Engine).
4.  **Legacy Experiment**: `hm_experiment_variant` (Superseded by Experiment Engine).

## Verification Plan
1. **Build Verification**: `mvn clean install` must pass.
2. **Schema Audit**: Verify that all `createTable` statements contain the full set of 6 metadata columns.
