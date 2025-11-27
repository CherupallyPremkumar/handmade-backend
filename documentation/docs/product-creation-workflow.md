# Product Creation Workflow Documentation

## Overview

This document describes the complete seller product creation workflow in the Handmade E-commerce Platform, following Amazon-style multi-step validation and approval process.

## Workflow Stages

### Stage 1: Product Information Entry

**Seller Actions:**
- Enter basic product details (title, brand, category)
- Add product description
- Upload product images (main + additional)
- Set pricing (MRP, selling price, discounts)
- Add product specifications/attributes
- Set product dimensions & weight

**Backend Operations:**
1. Validate seller account status (active, verified)
2. Validate product data (required fields, format)
3. Check for duplicate SKUs
4. Validate images (size, format, quality)
5. Save product with status: `DRAFT`

**Commands Executed:**
- `ValidateSellerCommand`
- `ValidateProductDataCommand`
- `CreateProductCommand`

---

### Stage 2: Variants & Inventory

**Seller Actions:**
- Create product variants (size, color, etc.)
- Set variant-specific pricing
- Add variant images
- Set initial inventory/stock for each variant
- Set warehouse/location

**Backend Operations:**
1. Validate variant combinations
2. Create inventory records
3. Reserve SKUs for variants
4. Link variants to parent product
5. Update status to: `PENDING_REVIEW`

**Commands Executed:**
- `CreateVariantsCommand`
- `CreateInventoryCommand`

---

### Stage 3: Compliance & Categorization

**Seller Actions:**
- Select product category & subcategory
- Add keywords/search terms
- Set product attributes (brand, material, etc.)
- Upload compliance documents (if required)
- Add tax/HSN codes

**Backend Operations:**
1. Validate category selection
2. Check compliance requirements
3. Verify tax codes
4. Auto-generate search keywords
5. Create catalog entry

**Commands Executed:**
- `ValidateComplianceCommand`
- `CreateCatalogEntryCommand`

---

### Stage 4: Shipping & Fulfillment

**Seller Actions:**
- Set shipping dimensions
- Choose fulfillment method (FBA/FBM)
- Set shipping charges
- Define delivery timelines
- Set return policy

**Backend Operations:**
1. Calculate shipping costs
2. Validate fulfillment settings
3. Create shipping profiles
4. Link to logistics partners

**Commands Executed:**
- `CreateShippingProfileCommand`

---

### Stage 5: Review & Approval

**Platform Operations:**
1. Content moderation (check for prohibited items)
2. Image quality check
3. Price validation (not too low/high)
4. Duplicate product detection
5. Brand verification (if applicable)
6. Compliance check

**Possible Outcomes:**
- ✅ `APPROVED` → Product goes LIVE
- ⚠️ `NEEDS_REVISION` → Sent back to seller with feedback
- ❌ `REJECTED` → Product rejected with reason

**Commands Executed:**
- `SubmitForReviewCommand`
- `ReviewProductCommand` (Admin)

---

### Stage 6: Go Live

**When Approved:**
1. Status changed to `ACTIVE`/`PUBLISHED`
2. Product indexed in search
3. Added to category listings
4. Inventory made available
5. Seller notified
6. Product appears on storefront

**Commands Executed:**
- `PublishProductCommand`

---

## Product States

| State | Description | Visible to Customers | Editable by Seller |
|-------|-------------|---------------------|-------------------|
| `DRAFT` | Product being created | ❌ No | ✅ Yes |
| `PENDING_REVIEW` | Submitted, awaiting approval | ❌ No | ❌ No |
| `NEEDS_REVISION` | Sent back for changes | ❌ No | ✅ Yes |
| `APPROVED` | Approved, ready to publish | ❌ No | ❌ No |
| `ACTIVE` | Live and visible | ✅ Yes | ⚠️ Limited |
| `INACTIVE` | Temporarily disabled | ❌ No | ✅ Yes |
| `OUT_OF_STOCK` | No inventory available | ⚠️ Visible but not purchasable | ✅ Yes |
| `REJECTED` | Not approved | ❌ No | ❌ No |

---

## State Transitions

```
[*] → DRAFT
DRAFT → PENDING_REVIEW (Submit for review)
PENDING_REVIEW → APPROVED (Admin approves)
PENDING_REVIEW → NEEDS_REVISION (Admin requests changes)
PENDING_REVIEW → REJECTED (Admin rejects)
NEEDS_REVISION → DRAFT (Seller makes changes)
APPROVED → ACTIVE (System publishes)
ACTIVE → INACTIVE (Seller/Admin deactivates)
ACTIVE → OUT_OF_STOCK (Inventory depleted)
INACTIVE → ACTIVE (Seller/Admin reactivates)
OUT_OF_STOCK → ACTIVE (Inventory restocked)
REJECTED → [*] (Deleted)
```

---

## Notifications to Seller

| Event | Notification | Channel |
|-------|-------------|---------|
| Product Draft Saved | "Your product draft has been saved" | Email + Dashboard |
| Product Submitted | "Product submitted for review" | Email + Dashboard |
| Needs Revision | "Product needs revision: [feedback]" | Email + Dashboard + SMS |
| Product Approved | "Congratulations! Product approved" | Email + Dashboard |
| Product Published | "Your product is now LIVE!" | Email + Dashboard + SMS |
| Product Rejected | "Product rejected: [reason]" | Email + Dashboard + SMS |
| Low Stock Alert | "Low stock for [product]" | Email + Dashboard |
| Out of Stock | "[Product] is out of stock" | Email + Dashboard |

---

## Orchestration Commands

### 1. ValidateSellerCommand
**Purpose:** Validate seller eligibility to create products

**Validations:**
- Seller account is verified
- Seller account is active
- Seller has not exceeded product limits
- Seller has no pending violations

**Context Updates:**
- Sets `sellerValid` flag
- Stores seller ID in context

---

### 2. ValidateProductDataCommand
**Purpose:** Validate product information

**Validations:**
- Required fields present (name, SKU, description, price)
- SKU is unique
- Price is positive
- Images meet quality standards
- Description meets minimum length

**Context Updates:**
- Sets `productDataValid` flag
- Stores validation errors (if any)

---

### 3. CreateProductCommand
**Purpose:** Create product entity in database

**Operations:**
- Create product with `DRAFT` status
- Generate product ID
- Set seller ID from context
- Store product images
- Create audit log entry

**Context Updates:**
- Stores product ID
- Stores product entity

---

### 4. CreateVariantsCommand
**Purpose:** Create product variants

**Operations:**
- Validate variant data
- Generate variant SKUs
- Create variant entities
- Link to parent product
- Store variant images

**Context Updates:**
- Stores variant IDs
- Stores variant entities

---

### 5. CreateInventoryCommand
**Purpose:** Create inventory records for variants

**Operations:**
- Validate stock levels
- Create inventory entries
- Link to warehouse/location
- Set reorder points
- Create inventory audit log

**Context Updates:**
- Stores inventory IDs
- Updates product availability

---

### 6. ValidateComplianceCommand
**Purpose:** Validate compliance and categorization

**Validations:**
- Category is valid
- Tax codes are correct
- No restricted items
- Required documents uploaded (if applicable)
- Brand verification (if applicable)

**Context Updates:**
- Sets `complianceValid` flag
- Stores compliance documents

---

### 7. CreateCatalogEntryCommand
**Purpose:** Create catalog entry for search and discovery

**Operations:**
- Generate search keywords
- Create category mappings
- Index product attributes
- Create search index entry

**Context Updates:**
- Stores catalog entry ID

---

### 8. SubmitForReviewCommand
**Purpose:** Submit product for admin review

**Operations:**
- Update status to `PENDING_REVIEW`
- Create review task
- Notify review team
- Lock product for editing

**Context Updates:**
- Stores review task ID
- Sets `submittedForReview` flag

---

### 9. ReviewProductCommand (Admin)
**Purpose:** Admin review of product

**Operations:**
- Content moderation check
- Image quality verification
- Price reasonability check
- Duplicate detection
- Compliance verification

**Outcomes:**
- `APPROVED` - Product approved
- `NEEDS_REVISION` - Sent back with feedback
- `REJECTED` - Product rejected with reason

**Context Updates:**
- Stores review decision
- Stores review feedback

---

### 10. PublishProductCommand
**Purpose:** Publish approved product

**Operations:**
- Update status to `ACTIVE`
- Index in search engine
- Add to category listings
- Make inventory available
- Notify seller
- Create product live event

**Context Updates:**
- Sets `published` flag
- Stores publish timestamp

---

## API Endpoints

### Seller APIs

```
POST   /api/products                    - Create product draft
PUT    /api/products/{id}               - Update product draft
POST   /api/products/{id}/variants      - Add variants
POST   /api/products/{id}/inventory     - Set inventory
POST   /api/products/{id}/submit        - Submit for review
GET    /api/products/{id}/status        - Get product status
PUT    /api/products/{id}/activate      - Activate product
PUT    /api/products/{id}/deactivate    - Deactivate product
```

### Admin APIs

```
GET    /api/admin/products/pending      - Get pending reviews
POST   /api/admin/products/{id}/approve - Approve product
POST   /api/admin/products/{id}/reject  - Reject product
POST   /api/admin/products/{id}/revise  - Request revision
```

---

## Error Handling

### Validation Errors
- Return HTTP 400 with detailed error messages
- Include field-level validation errors
- Provide suggestions for correction

### Business Rule Violations
- Return HTTP 422 with business rule violation details
- Include specific rule that was violated
- Provide guidance on resolution

### System Errors
- Return HTTP 500 with generic error message
- Log detailed error for debugging
- Notify support team for critical errors

---

## Performance Considerations

1. **Asynchronous Processing**
   - Image processing done asynchronously
   - Search indexing done in background
   - Notifications sent asynchronously

2. **Caching**
   - Cache category hierarchies
   - Cache compliance rules
   - Cache seller information

3. **Database Optimization**
   - Index on product status
   - Index on seller ID
   - Index on SKU for uniqueness check

---

## Security Considerations

1. **Authorization**
   - Sellers can only edit their own products
   - Admins have full access
   - Role-based access control

2. **Data Validation**
   - Sanitize all user inputs
   - Validate file uploads
   - Check for SQL injection

3. **Audit Trail**
   - Log all product changes
   - Track who made changes
   - Store change history

---

## Monitoring & Metrics

### Key Metrics
- Average time from draft to live
- Approval rate (approved/total submissions)
- Revision rate (needs revision/total submissions)
- Rejection rate (rejected/total submissions)
- Average review time

### Alerts
- High rejection rate
- Long review queue
- Failed image uploads
- Inventory sync failures

---

## Related Documentation

- [Product Data Model](./product-data-model.md)
- [Inventory Management](./inventory-management.md)
- [Search & Catalog](./search-catalog.md)
- [Seller Onboarding](./seller-onboarding.md)
