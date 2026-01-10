#!/bin/bash
# =============================================================================
# Apply Chenile Pattern to All 64 Sub-Modules
# Transforms simple sub-modules into full Chenile structure with 6 layers
# =============================================================================

set -e

PROJECT_ROOT="/Users/premkumar/Desktop/handmade"
# Default to snapshot version if ${revision} not set
PARENT_VERSION="${revision:-2.0.35}"

cd "$PROJECT_ROOT"

echo "==========================================================================="
echo "Applying Chenile Pattern to 64 Sub-Modules"
echo "==========================================================================="
echo ""
echo "Using version: $PARENT_VERSION"
echo ""
echo "Target Structure:"
echo "  {sub-module}/"
echo "    ├── {sub-module}-domain/           → Entities, Aggregates"
echo "    ├── {sub-module}-api/              → Interfaces, DTOs"
echo "    ├── {sub-module}-service/          → Business Logic"
echo "    ├── {sub-module}-infrastructure/   → Repositories, Adapters"
echo "    ├── {sub-module}-delegator/        → Orchestration"
echo "    └── {sub-module}-async/            → Event Handlers"
echo ""

# =============================================================================
# Function: Apply Chenile Pattern to Sub-Module
# =============================================================================
apply_chenile_pattern() {
    local parent_dir=$1
    local sub_module=$2
    local parent_group=$3
    
    echo "  → Applying pattern to: $sub_module"
    
    local module_path="$parent_dir/$sub_module"
    
    # Backup original simple module if it has src/ (not already restructured)
    if [ -d "$module_path/src" ] && [ ! -d "$module_path/${sub_module}-domain" ]; then
        echo "    Backing up original module..."
        mv "$module_path" "${module_path}.backup.$(date +%Y%m%d-%H%M%S)"
        mkdir -p "$module_path"
    elif [ -d "$module_path/${sub_module}-domain" ]; then
        echo "    Already restructured, skipping..."
        return
    fi
    
    # Create parent POM for sub-module
    mkdir -p "$module_path"
    
    cat > "$module_path/pom.xml" << EOF
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>$parent_group</groupId>
        <artifactId>$(basename $parent_dir)</artifactId>
        <version>$PARENT_VERSION</version>
    </parent>
    
    <artifactId>$sub_module</artifactId>
    <packaging>pom</packaging>
    
    <name>$sub_module</name>
    
    <modules>
        <module>${sub_module}-domain</module>
        <module>${sub_module}-api</module>
        <module>${sub_module}-service</module>
        <module>${sub_module}-infrastructure</module>
        <module>${sub_module}-delegator</module>
        <module>${sub_module}-async</module>
    </modules>
</project>
EOF
    
    # Create 6 sub-layers
    create_domain_module "$module_path" "$sub_module" "$parent_group"
    create_api_module "$module_path" "$sub_module" "$parent_group"
    create_service_module "$module_path" "$sub_module" "$parent_group"
    create_infrastructure_module "$module_path" "$sub_module" "$parent_group"
    create_delegator_module "$module_path" "$sub_module" "$parent_group"
    create_async_module "$module_path" "$sub_module" "$parent_group"
}

# =============================================================================
# Function: Create Domain Module
# =============================================================================
create_domain_module() {
    local parent_path=$1
    local sub_module=$2
    local parent_group=$3
    
    local module_name="${sub_module}-domain"
    local module_path="$parent_path/$module_name"
    
    mkdir -p "$module_path/src/main/java"
    mkdir -p "$module_path/src/main/resources"
    mkdir -p "$module_path/src/test/java"
    
    cat > "$module_path/pom.xml" << EOF
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>$parent_group</groupId>
        <artifactId>$sub_module</artifactId>
        <version>$PARENT_VERSION</version>
    </parent>
    
    <artifactId>$module_name</artifactId>
    <packaging>jar</packaging>
    
    <name>$module_name</name>
    
    <dependencies>
        <dependency>
            <groupId>jakarta.persistence</groupId>
            <artifactId>jakarta.persistence-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.chenile</groupId>
            <artifactId>jpa-utils</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
EOF
}

# =============================================================================
# Function: Create API Module
# =============================================================================
create_api_module() {
    local parent_path=$1
    local sub_module=$2
    local parent_group=$3
    
    local module_name="${sub_module}-api"
    local module_path="$parent_path/$module_name"
    
    mkdir -p "$module_path/src/main/java"
    mkdir -p "$module_path/src/main/resources"
    mkdir -p "$module_path/src/test/java"
    
    cat > "$module_path/pom.xml" << EOF
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>$parent_group</groupId>
        <artifactId>$sub_module</artifactId>
        <version>$PARENT_VERSION</version>
    </parent>
    
    <artifactId>$module_name</artifactId>
    <packaging>jar</packaging>
    
    <name>$module_name</name>
    
    <dependencies>
        <dependency>
            <groupId>$parent_group</groupId>
            <artifactId>${sub_module}-domain</artifactId>
            <version>$PARENT_VERSION</version>
        </dependency>
        <dependency>
            <groupId>jakarta.validation</groupId>
            <artifactId>jakarta.validation-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
EOF
}

# =============================================================================
# Function: Create Service Module
# =============================================================================
create_service_module() {
    local parent_path=$1
    local sub_module=$2
    local parent_group=$3
    
    local module_name="${sub_module}-service"
    local module_path="$parent_path/$module_name"
    
    mkdir -p "$module_path/src/main/java"
    mkdir -p "$module_path/src/main/resources"
    mkdir -p "$module_path/src/test/java"
    
    cat > "$module_path/pom.xml" << EOF
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>$parent_group</groupId>
        <artifactId>$sub_module</artifactId>
        <version>$PARENT_VERSION</version>
    </parent>
    
    <artifactId>$module_name</artifactId>
    <packaging>jar</packaging>
    
    <name>$module_name</name>
    
    <dependencies>
        <dependency>
            <groupId>$parent_group</groupId>
            <artifactId>${sub_module}-api</artifactId>
            <version>$PARENT_VERSION</version>
        </dependency>
        <dependency>
            <groupId>$parent_group</groupId>
            <artifactId>${sub_module}-infrastructure</artifactId>
            <version>$PARENT_VERSION</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </dependency>
    </dependencies>
</project>
EOF
}

# =============================================================================
# Function: Create Infrastructure Module
# =============================================================================
create_infrastructure_module() {
    local parent_path=$1
    local sub_module=$2
    local parent_group=$3
    
    local module_name="${sub_module}-infrastructure"
    local module_path="$parent_path/$module_name"
    
    mkdir -p "$module_path/src/main/java"
    mkdir -p "$module_path/src/main/resources"
    mkdir -p "$module_path/src/test/java"
    
    cat > "$module_path/pom.xml" << EOF
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>$parent_group</groupId>
        <artifactId>$sub_module</artifactId>
        <version>$PARENT_VERSION</version>
    </parent>
    
    <artifactId>$module_name</artifactId>
    <packaging>jar</packaging>
    
    <name>$module_name</name>
    
    <dependencies>
        <dependency>
            <groupId>$parent_group</groupId>
            <artifactId>${sub_module}-domain</artifactId>
            <version>$PARENT_VERSION</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
EOF
}

# =============================================================================
# Function: Create Delegator Module
# =============================================================================
create_delegator_module() {
    local parent_path=$1
    local sub_module=$2
    local parent_group=$3
    
    local module_name="${sub_module}-delegator"
    local module_path="$parent_path/$module_name"
    
    mkdir -p "$module_path/src/main/java"
    mkdir -p "$module_path/src/main/resources"
    mkdir -p "$module_path/src/test/java"
    
    cat > "$module_path/pom.xml" << EOF
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>$parent_group</groupId>
        <artifactId>$sub_module</artifactId>
        <version>$PARENT_VERSION</version>
    </parent>
    
    <artifactId>$module_name</artifactId>
    <packaging>jar</packaging>
    
    <name>$module_name</name>
    
    <dependencies>
        <dependency>
            <groupId>$parent_group</groupId>
            <artifactId>${sub_module}-service</artifactId>
            <version>$PARENT_VERSION</version>
        </dependency>
        <dependency>
            <groupId>org.chenile</groupId>
            <artifactId>chenile-core</artifactId>
        </dependency>
    </dependencies>
</project>
EOF
}

# =============================================================================
# Function: Create Async Module
# =============================================================================
create_async_module() {
    local parent_path=$1
    local sub_module=$2
    local parent_group=$3
    
    local module_name="${sub_module}-async"
    local module_path="$parent_path/$module_name"
    
    mkdir -p "$module_path/src/main/java"
    mkdir -p "$module_path/src/main/resources"
    mkdir -p "$module_path/src/test/java"
    
    cat > "$module_path/pom.xml" << EOF
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>$parent_group</groupId>
        <artifactId>$sub_module</artifactId>
        <version>$PARENT_VERSION</version>
    </parent>
    
    <artifactId>$module_name</artifactId>
    <packaging>jar</packaging>
    
    <name>$module_name</name>
    
    <dependencies>
        <dependency>
            <groupId>$parent_group</groupId>
            <artifactId>${sub_module}-service</artifactId>
            <version>$PARENT_VERSION</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
    </dependencies>
</project>
EOF
}

# =============================================================================
# Apply Pattern to All 11 High-Level Modules
# =============================================================================

# 1. PLATFORM GOVERNANCE
echo "1. Platform Governance..."
for module in platform-core iam policy-engine governance limits-quotas observability event-infrastructure notifications compliance-risk stm-kernel; do
    apply_chenile_pattern "handmade-platform-governance" "$module" "com.handmade.ecommerce.platform"
done

# 2. CATALOG PRODUCT
echo "2. Catalog Product..."
for module in catalog product product-relationship product-comparison attribute-metadata; do
    apply_chenile_pattern "handmade-catalog-product" "$module" "com.handmade.ecommerce.catalog"
done

# 3. SELLER OFFER
echo "3. Seller Offer..."
for module in seller-account seller-performance offer seller-onboarding seller-messaging; do
    apply_chenile_pattern "handmade-seller-offer" "$module" "com.handmade.ecommerce.seller"
done

# 4. INVENTORY LOGISTICS
echo "4. Inventory Logistics..."
for module in inventory-core inbound-shipments vendor-po inventory-operations logistics inventory-stm; do
    apply_chenile_pattern "handmade-inventory-logistics" "$module" "com.handmade.ecommerce.inventory"
done

# 5. CUSTOMER SHOPPING
echo "5. Customer Shopping..."
for module in customer-core wishlist cart payment qa reviews; do
    apply_chenile_pattern "handmade-customer-shopping" "$module" "com.handmade.ecommerce.customer"
done

# 6. ORDER FINANCE
echo "6. Order Finance..."
for module in order-core finance tax dispute order-stm audit-logging; do
    apply_chenile_pattern "handmade-order-finance" "$module" "com.handmade.ecommerce.order"
done

# 7. PROMOTION PRICING
echo "7. Promotion Pricing..."
for module in promotion pricing-core pricing-engine loyalty adtech experiment-engine; do
    apply_chenile_pattern "handmade-promotion-pricing" "$module" "com.handmade.ecommerce.promotion"
done

# 8. ANALYTICS SEARCH
echo "8. Analytics Search..."
for module in search ranking-recommendations behavioral-signals event-analytics metrics-management search-index; do
    apply_chenile_pattern "handmade-analytics-search" "$module" "com.handmade.ecommerce.analytics"
done

# 9. CONTENT LOCALIZATION
echo "9. Content Localization..."
for module in cms-core localization seo media-assets content-workflow marketing-extensions; do
    apply_chenile_pattern "handmade-content-localization" "$module" "com.handmade.ecommerce.content"
done

# 10. SUPPORT OBSERVABILITY
echo "10. Support Observability..."
for module in case-management notification-service observability-service event-service risk-signals; do
    apply_chenile_pattern "handmade-support-observability" "$module" "com.handmade.ecommerce.support"
done

# 11. INTEGRATION
echo "11. Integration..."
for module in external-vendor webhooks etl-pipelines; do
    apply_chenile_pattern "handmade-integration" "$module" "com.handmade.ecommerce.integration"
done

echo ""
echo "==========================================================================="
echo "✅ CHENILE PATTERN APPLIED TO ALL 64 SUB-MODULES"
echo "==========================================================================="
echo ""
echo "Summary:"
echo "  - 64 sub-modules restructured"
echo "  - 384 layer modules created (64 × 6 layers)"
echo "  - All POMs generated with proper dependencies"
echo ""
echo "Structure per sub-module:"
echo "  {sub-module}/"
echo "    ├── {sub-module}-domain/           ✅"
echo "    ├── {sub-module}-api/              ✅"
echo "    ├── {sub-module}-service/          ✅"
echo "    ├── {sub-module}-infrastructure/   ✅"
echo "    ├── {sub-module}-delegator/        ✅"
echo "    └── {sub-module}-async/            ✅"
echo ""
echo "Next Steps:"
echo "  1. Run: mvn clean install -DskipTests"
echo "  2. Verify all 384 layer modules compile"
echo "  3. Start implementing entities in *-domain modules"
echo "  4. Implement services in *-service modules"
echo ""
