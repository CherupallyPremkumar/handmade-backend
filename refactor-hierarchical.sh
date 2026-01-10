#!/bin/bash
# Big Bang Hierarchical Refactoring Script
# This script reorganizes ~57 flat modules into 11 high-level hierarchical modules

set -e  # Exit on error

HANDMADE_ROOT="/Users/premkumar/Desktop/handmade"
cd "$HANDMADE_ROOT"

echo "========================================="
echo "Big Bang Hierarchical Refactoring"
echo "========================================="
echo ""
echo "This will reorganize all modules into:"
echo "  - 11 high-level modules"
echo "  - 64 sub-modules"
echo ""
echo "WARNING: This is a BREAKING change!"
echo "  - Maven coordinates will change"
echo "  - Directory structure will change"
echo ""
read -p "Continue? (yes/no): " confirm
if [ "$confirm" != "yes" ]; then
    echo "Aborted."
    exit 1
fi

echo ""
echo "Step 1: Creating backup branch..."
git checkout -b refactor/hierarchical-structure-backup-$(date +%Y%m%d)
git add -A
git commit -m "Backup before hierarchical refactoring" || true

echo ""
echo "Step 2: Creating new feature branch..."
git checkout -b feature/hierarchical-refactoring

echo ""
echo "Step 3: Creating 11 high-level module directories..."

# Create all high-level module parent directories
mkdir -p handmade-platform-governance
mkdir -p handmade-catalog-product
mkdir -p handmade-seller-offer
mkdir -p handmade-inventory-logistics
mkdir -p handmade-customer-shopping
mkdir -p handmade-order-finance
mkdir -p handmade-promotion-pricing
mkdir -p handmade-analytics-search
mkdir -p handmade-content-localization
mkdir -p handmade-support-observability
mkdir -p handmade-integration

echo "✅ Created 11 high-level module directories"

echo ""
echo "Step 4: Moving modules into hierarchical structure..."

# ========================================
# 1. PLATFORM GOVERNANCE
# ========================================
echo "  → Platform Governance (10 sub-modules)"

mv handmade-platform-management handmade-platform-governance/platform-core
mv handmade-security-management handmade-platform-governance/iam
mv handmade-user-management handmade-platform-governance/iam/user-management
mv handmade-policy-management handmade-platform-governance/policy-engine
# governance - will create new
mv handmade-event-management handmade-platform-governance/event-infrastructure
mv handmade-notification-management handmade-platform-governance/notifications
mv handmade-risk-management handmade-platform-governance/compliance-risk
mv handmade-app-core handmade-platform-governance/stm-kernel
# observability - extract later
# limits-quotas - create new

# ========================================
# 2. CATALOG PRODUCT
# ========================================
echo "  → Catalog Product (5 sub-modules)"

mv handmade-catalog-management handmade-catalog-product/catalog
mv handmade-product-management handmade-catalog-product/product
# product-relationship - extract from catalog
# product-comparison - already exists, move it
# attribute-metadata - shared, extract later

# ========================================
# 3. SELLER OFFER
# ========================================
echo "  → Seller Offer (5 sub-modules)"

mv handmade-seller-account-management handmade-seller-offer/seller-account
mv handmade-seller-management handmade-seller-offer/seller-performance
mv handmade-offer-management handmade-seller-offer/offer
mv handmade-onboarding-management handmade-seller-offer/seller-onboarding 2>/dev/null || true
mv handmade-messaging-management handmade-seller-offer/seller-messaging

# ========================================
# 4. INVENTORY LOGISTICS
# ========================================
echo "  → Inventory Logistics (6 sub-modules)"

mv handmade-inventory-management handmade-inventory-logistics/inventory-core
mv handmade-fulfillment-management handmade-inventory-logistics/logistics
mv handmade-shipping-management handmade-inventory-logistics/logistics/shipping
# inbound-shipments - extract from inventory-core
# vendor-po - extract from inventory-core
# inventory-operations - extract from inventory-core

# ========================================
# 5. CUSTOMER SHOPPING
# ========================================
echo "  → Customer Shopping (6 sub-modules)"

mv handmade-customer-management handmade-customer-shopping/customer-core
mv handmade-cart-management handmade-customer-shopping/cart
mv handmade-payment-management handmade-customer-shopping/payment
mv handmade-payment-executor handmade-customer-shopping/payment/payment-executor
mv handmade-payment-orchestrator handmade-customer-shopping/payment/payment-orchestrator
mv handmade-paymentorder-management handmade-customer-shopping/payment/paymentorder
mv handmade-qa-management handmade-customer-shopping/qa
mv handmade-review-management handmade-customer-shopping/reviews
# wishlist - extract from customer-core

# ========================================
# 6. ORDER FINANCE
# ========================================
echo "  → Order Finance (6 sub-modules)"

mv handmade-order-management handmade-order-finance/order-core
mv handmade-finance-management handmade-order-finance/finance
mv handmade-ledger-management handmade-order-finance/finance/ledger
mv handmade-cash-management handmade-order-finance/finance/cash
mv handmade-wallet-management handmade-order-finance/finance/wallet
mv handmade-reconciliation-management handmade-order-finance/finance/reconciliation
mv handmade-tax-management handmade-order-finance/tax
mv handmade-dispute-management handmade-order-finance/dispute
# order-stm - embedded in order-core
# audit-logging - create new

# ========================================
# 7. PROMOTION PRICING
# ========================================
echo "  → Promotion Pricing (6 sub-modules)"

mv handmade-promotion-management handmade-promotion-pricing/promotion
mv handmade-pricing-management handmade-promotion-pricing/pricing-core
# pricing-engine - extract from pricing-management
# loyalty - create new
# adtech - create new
# experiment-engine - create new

# ========================================
# 8. ANALYTICS SEARCH
# ========================================
echo "  → Analytics Search (6 sub-modules)"

mv handmade-search-management handmade-analytics-search/search
# ranking-recommendations - create new
# behavioral-signals - create new
# event-analytics - create new
# metrics-management - create new
# search-index - create new

# ========================================
# 9. CONTENT LOCALIZATION
# ========================================
echo "  → Content Localization (6 sub-modules)"

# All sub-modules need to be created from tables
# cms-core - create new
# localization - create new
# seo - create new
# media-assets - create new
# content-workflow - create new
# marketing-extensions - create new

# ========================================
# 10. SUPPORT OBSERVABILITY
# ========================================
echo "  → Support Observability (5 sub-modules)"

# case-management - create new
# notifications - link to platform-governance
# observability - link to platform-governance
# event-infra - link to platform-governance
# risk-signals - link to platform-governance

# ========================================
# 11. INTEGRATION
# ========================================
echo "  → Integration (3 sub-modules)"

mv handmade-stripe-integration handmade-integration/external-vendor/stripe
mv handmade-razorpay-integration handmade-integration/external-vendor/razorpay
# webhooks - create new
# etl-pipelines - create new

# ========================================
# SPECIAL CASES / POLICY MODULES
# ========================================
echo "  → Moving policy modules..."

# Policy sub-modules under policy-engine
mv handmade-onboarding-policy handmade-platform-governance/policy-engine/onboarding-policy 2>/dev/null || true
mv handmade-payout-policy handmade-platform-governance/policy-engine/payout-policy 2>/dev/null || true
mv handmade-promotion-policy handmade-platform-governance/policy-engine/promotion-policy 2>/dev/null || true
mv handmade-compliance-policy handmade-platform-governance/policy-engine/compliance-policy 2>/dev/null || true
mv handmade-commission-policy handmade-platform-governance/policy-engine/commission-policy 2>/dev/null || true

# ========================================
# INFRASTRUCTURE / VERIFICATION
# ========================================
echo "  → Moving infrastructure modules..."

# KYC/Tax management (India-specific)
mv handmade-kyc-verification handmade-seller-offer/seller-account/kyc-verification
mv handmade-ein-management handmade-seller-offer/seller-account/ein-management
mv handmade-gstin-management handmade-seller-offer/seller-account/gstin-management

# Artisan (seller sub-type)
mv handmade-artisan-management handmade-seller-offer/seller-account/artisan

# Workflow orchestrator
mv handmade-workflow-orchestrator handmade-platform-governance/event-infrastructure/workflow-orchestrator

echo "✅ Module reorganization complete!"

echo ""
echo "Step 5: Creating parent POMs for high-level modules..."

# This would be 11 separate POM creation operations
# For brevity, showing the template for one:

cat > handmade-seller-offer/pom.xml << 'EOF'
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.handmade.ecommerce</groupId>
        <artifactId>handmade-parent</artifactId>
        <version>${revision}</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    
    <groupId>com.handmade.ecommerce.seller</groupId>
    <artifactId>handmade-seller-offer</artifactId>
    <packaging>pom</packaging>
    
    <name>handmade-seller-offer</name>
    <description>Seller and Offer Management</description>
    
    <modules>
        <module>seller-account</module>
        <module>seller-performance</module>
        <module>offer</module>
        <module>seller-onboarding</module>
        <module>seller-messaging</module>
    </modules>
</project>
EOF

echo "  ✅ Created parent POM for handmade-seller-offer"
echo "  ⚠️  Need to create 10 more parent POMs (manual step)"

echo ""
echo "Step 6: Updating parent POM module list..."

# Backup current pom.xml
cp pom.xml pom.xml.backup

# This would update the main pom.xml to reference the 11 high-level modules
echo "  ⚠️  Manual step required: Update pom.xml <modules> section"

echo ""
echo "========================================="
echo "REFACTORING COMPLETE (Structure Only)"
echo "========================================="
echo ""
echo "Next Steps (Manual):"
echo ""
echo "1. Create parent POMs for remaining 10 high-level modules"
echo "2. Update each sub-module's parent reference"
echo "3. Update groupId/artifactId in all sub-module POMs"
echo "4. Fix cross-module dependencies"
echo "5. Update parent POM module list"
echo "6. Run: mvn clean install -DskipTests"
echo "7. Fix any build errors"
echo "8. Run tests: mvn test"
echo "9. Commit: git add -A && git commit -m 'Hierarchical refactoring'"
echo ""
echo "Backup branch: refactor/hierarchical-structure-backup-$(date +%Y%m%d)"
echo "Working branch: feature/hierarchical-refactoring"
echo ""
