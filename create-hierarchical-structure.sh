#!/bin/bash
# =============================================================================
# Hierarchical Maven Project Structure Generator
# Creates 11 high-level modules with 64 sub-modules
# =============================================================================

set -e  # Exit on error

PROJECT_ROOT="/Users/premkumar/Desktop/handmade"
PARENT_VERSION="\${revision}"

echo "==========================================================================="
echo "Hierarchical Maven Project Structure Generator"
echo "==========================================================================="
echo ""
echo "This script will create:"
echo "  - 11 high-level parent modules"
echo "  - 64 sub-modules"
echo "  - All POMs with proper Maven coordinates"
echo ""
echo "Target directory: $PROJECT_ROOT"
echo ""

# Change to project root
cd "$PROJECT_ROOT"

# =============================================================================
# Function: Create Parent POM
# =============================================================================
create_parent_pom() {
    local parent_dir=$1
    local parent_artifact=$2
    local parent_group=$3
    local parent_name=$4
    local modules=$5
    
    cat > "$parent_dir/pom.xml" << EOF
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.handmade.ecommerce</groupId>
        <artifactId>handmade-parent</artifactId>
        <version>$PARENT_VERSION</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    
    <groupId>$parent_group</groupId>
    <artifactId>$parent_artifact</artifactId>
    <packaging>pom</packaging>
    
    <name>$parent_name</name>
    
    <modules>
$modules
    </modules>
</project>
EOF
}

# =============================================================================
# Function: Create Sub-module POM
# =============================================================================
create_submodule_pom() {
    local submodule_dir=$1
    local submodule_artifact=$2
    local parent_group=$3
    local parent_artifact=$4
    local submodule_name=$5
    
    mkdir -p "$submodule_dir/src/main/java"
    mkdir -p "$submodule_dir/src/main/resources"
    mkdir -p "$submodule_dir/src/test/java"
    
    cat > "$submodule_dir/pom.xml" << EOF
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>$parent_group</groupId>
        <artifactId>$parent_artifact</artifactId>
        <version>$PARENT_VERSION</version>
    </parent>
    
    <artifactId>$submodule_artifact</artifactId>
    <packaging>jar</packaging>
    
    <name>$submodule_name</name>
</project>
EOF
}

# =============================================================================
# 1. PLATFORM GOVERNANCE (10 sub-modules)
# =============================================================================
echo "Creating: handmade-platform-governance..."

PARENT_DIR="handmade-platform-governance"
mkdir -p "$PARENT_DIR"

# Sub-modules array
SUBMODULES=(
    "platform-core"
    "iam"
    "policy-engine"
    "governance"
    "limits-quotas"
    "observability"
    "event-infrastructure"
    "notifications"
    "compliance-risk"
    "stm-kernel"
)

# Generate module list
MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-platform-governance" \
    "com.handmade.ecommerce.platform" "handmade-platform-governance" \
    "$(echo -e "$MODULES")"

# Create sub-modules
for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.platform" "handmade-platform-governance" \
        "$module"
done

echo "  ✅ Created 10 sub-modules"

# =============================================================================
# 2. CATALOG PRODUCT (5 sub-modules)
# =============================================================================
echo "Creating: handmade-catalog-product..."

PARENT_DIR="handmade-catalog-product"
mkdir -p "$PARENT_DIR"

SUBMODULES=(
    "catalog"
    "product"
    "product-relationship"
    "product-comparison"
    "attribute-metadata"
)

MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-catalog-product" \
    "com.handmade.ecommerce.catalog" "handmade-catalog-product" \
    "$(echo -e "$MODULES")"

for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.catalog" "handmade-catalog-product" \
        "$module"
done

echo "  ✅ Created 5 sub-modules"

# =============================================================================
# 3. SELLER OFFER (5 sub-modules)
# =============================================================================
echo "Creating: handmade-seller-offer..."

PARENT_DIR="handmade-seller-offer"
mkdir -p "$PARENT_DIR"

SUBMODULES=(
    "seller-account"
    "seller-performance"
    "offer"
    "seller-onboarding"
    "seller-messaging"
)

MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-seller-offer" \
    "com.handmade.ecommerce.seller" "handmade-seller-offer" \
    "$(echo -e "$MODULES")"

for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.seller" "handmade-seller-offer" \
        "$module"
done

echo "  ✅ Created 5 sub-modules"

# =============================================================================
# 4. INVENTORY LOGISTICS (6 sub-modules)
# =============================================================================
echo "Creating: handmade-inventory-logistics..."

PARENT_DIR="handmade-inventory-logistics"
mkdir -p "$PARENT_DIR"

SUBMODULES=(
    "inventory-core"
    "inbound-shipments"
    "vendor-po"
    "inventory-operations"
    "logistics"
    "inventory-stm"
)

MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-inventory-logistics" \
    "com.handmade.ecommerce.inventory" "handmade-inventory-logistics" \
    "$(echo -e "$MODULES")"

for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.inventory" "handmade-inventory-logistics" \
        "$module"
done

echo "  ✅ Created 6 sub-modules"

# =============================================================================
# 5. CUSTOMER SHOPPING (6 sub-modules)
# =============================================================================
echo "Creating: handmade-customer-shopping..."

PARENT_DIR="handmade-customer-shopping"
mkdir -p "$PARENT_DIR"

SUBMODULES=(
    "customer-core"
    "wishlist"
    "cart"
    "payment"
    "qa"
    "reviews"
)

MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-customer-shopping" \
    "com.handmade.ecommerce.customer" "handmade-customer-shopping" \
    "$(echo -e "$MODULES")"

for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.customer" "handmade-customer-shopping" \
        "$module"
done

echo "  ✅ Created 6 sub-modules"

# =============================================================================
# 6. ORDER FINANCE (6 sub-modules)
# =============================================================================
echo "Creating: handmade-order-finance..."

PARENT_DIR="handmade-order-finance"
mkdir -p "$PARENT_DIR"

SUBMODULES=(
    "order-core"
    "finance"
    "tax"
    "dispute"
    "order-stm"
    "audit-logging"
)

MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-order-finance" \
    "com.handmade.ecommerce.order" "handmade-order-finance" \
    "$(echo -e "$MODULES")"

for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.order" "handmade-order-finance" \
        "$module"
done

echo "  ✅ Created 6 sub-modules"

# =============================================================================
# 7. PROMOTION PRICING (6 sub-modules)
# =============================================================================
echo "Creating: handmade-promotion-pricing..."

PARENT_DIR="handmade-promotion-pricing"
mkdir -p "$PARENT_DIR"

SUBMODULES=(
    "promotion"
    "pricing-core"
    "pricing-engine"
    "loyalty"
    "adtech"
    "experiment-engine"
)

MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-promotion-pricing" \
    "com.handmade.ecommerce.promotion" "handmade-promotion-pricing" \
    "$(echo -e "$MODULES")"

for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.promotion" "handmade-promotion-pricing" \
        "$module"
done

echo "  ✅ Created 6 sub-modules"

# =============================================================================
# 8. ANALYTICS SEARCH (6 sub-modules)
# =============================================================================
echo "Creating: handmade-analytics-search..."

PARENT_DIR="handmade-analytics-search"
mkdir -p "$PARENT_DIR"

SUBMODULES=(
    "search"
    "ranking-recommendations"
    "behavioral-signals"
    "event-analytics"
    "metrics-management"
    "search-index"
)

MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-analytics-search" \
    "com.handmade.ecommerce.analytics" "handmade-analytics-search" \
    "$(echo -e "$MODULES")"

for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.analytics" "handmade-analytics-search" \
        "$module"
done

echo "  ✅ Created 6 sub-modules"

# =============================================================================
# 9. CONTENT LOCALIZATION (6 sub-modules)
# =============================================================================
echo "Creating: handmade-content-localization..."

PARENT_DIR="handmade-content-localization"
mkdir -p "$PARENT_DIR"

SUBMODULES=(
    "cms-core"
    "localization"
    "seo"
    "media-assets"
    "content-workflow"
    "marketing-extensions"
)

MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-content-localization" \
    "com.handmade.ecommerce.content" "handmade-content-localization" \
    "$(echo -e "$MODULES")"

for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.content" "handmade-content-localization" \
        "$module"
done

echo "  ✅ Created 6 sub-modules"

# =============================================================================
# 10. SUPPORT OBSERVABILITY (5 sub-modules)
# =============================================================================
echo "Creating: handmade-support-observability..."

PARENT_DIR="handmade-support-observability"
mkdir -p "$PARENT_DIR"

SUBMODULES=(
    "case-management"
    "notification-service"
    "observability-service"
    "event-service"
    "risk-signals"
)

MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-support-observability" \
    "com.handmade.ecommerce.support" "handmade-support-observability" \
    "$(echo -e "$MODULES")"

for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.support" "handmade-support-observability" \
        "$module"
done

echo "  ✅ Created 5 sub-modules"

# =============================================================================
# 11. INTEGRATION (3 sub-modules)
# =============================================================================
echo "Creating: handmade-integration..."

PARENT_DIR="handmade-integration"
mkdir -p "$PARENT_DIR"

SUBMODULES=(
    "external-vendor"
    "webhooks"
    "etl-pipelines"
)

MODULES=""
for module in "${SUBMODULES[@]}"; do
    MODULES="${MODULES}        <module>$module</module>\n"
done

create_parent_pom "$PARENT_DIR" "handmade-integration" \
    "com.handmade.ecommerce.integration" "handmade-integration" \
    "$(echo -e "$MODULES")"

for module in "${SUBMODULES[@]}"; do
    create_submodule_pom "$PARENT_DIR/$module" "$module" \
        "com.handmade.ecommerce.integration" "handmade-integration" \
        "$module"
done

echo "  ✅ Created 3 sub-modules"

# =============================================================================
# Update Parent POM
# =============================================================================
echo ""
echo "Updating parent pom.xml..."

# Backup existing pom.xml
if [ -f "pom.xml" ]; then
    cp pom.xml pom.xml.backup-$(date +%Y%m%d-%H%M%S)
    echo "  ✅ Backed up existing pom.xml"
fi

# Note: This doesn't automatically update the parent POM
# User needs to manually add the 11 modules to the parent <modules> section
echo ""
echo "  ⚠️  MANUAL STEP REQUIRED:"
echo "  Add these modules to your parent pom.xml <modules> section:"
echo ""
cat << 'EOF'
    <module>handmade-platform-governance</module>
    <module>handmade-catalog-product</module>
    <module>handmade-seller-offer</module>
    <module>handmade-inventory-logistics</module>
    <module>handmade-customer-shopping</module>
    <module>handmade-order-finance</module>
    <module>handmade-promotion-pricing</module>
    <module>handmade-analytics-search</module>
    <module>handmade-content-localization</module>
    <module>handmade-support-observability</module>
    <module>handmade-integration</module>
EOF

# =============================================================================
# Summary
# =============================================================================
echo ""
echo "==========================================================================="
echo "✅ HIERARCHICAL STRUCTURE CREATED SUCCESSFULLY"
echo "==========================================================================="
echo ""
echo "Summary:"
echo "  - 11 high-level parent modules created"
echo "  - 64 sub-modules created"
echo "  - All POMs generated with proper Maven coordinates"
echo ""
echo "Structure:"
echo "  handmade-platform-governance (10 sub-modules)"
echo "  handmade-catalog-product (5 sub-modules)"
echo "  handmade-seller-offer (5 sub-modules)"
echo "  handmade-inventory-logistics (6 sub-modules)"
echo "  handmade-customer-shopping (6 sub-modules)"
echo "  handmade-order-finance (6 sub-modules)"
echo "  handmade-promotion-pricing (6 sub-modules)"
echo "  handmade-analytics-search (6 sub-modules)"
echo "  handmade-content-localization (6 sub-modules)"
echo "  handmade-support-observability (5 sub-modules)"
echo "  handmade-integration (3 sub-modules)"
echo ""
echo "Next Steps:"
echo "  1. Update parent pom.xml with the 11 module entries (shown above)"
echo "  2. Run: mvn clean install -DskipTests"
echo "  3. Verify all modules compile successfully"
echo ""
echo "Module Coordinates Pattern:"
echo "  Parent: com.handmade.ecommerce.<domain>"
echo "  Child: <parent-groupId> + <child-artifactId>"
echo ""
