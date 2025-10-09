package com.homebase.admin.config;

import com.homebase.admin.entity.*;
import com.homebase.admin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize data for multiple tenants with distinct data
        initializeTenantData("default", "Home Decor");
        initializeTenantData("tenant1", "Fashion Store");
        initializeTenantData("tenant2", "Tech Shop");
    }

    private void initializeTenantData(String tenantId, String businessName) {
        // Set tenant context for this initialization
        TenantContext.setCurrentTenant(tenantId);
        
        // Create tenant-specific admin users
        String adminEmail = getAdminEmail(tenantId);
        String editorEmail = getEditorEmail(tenantId);
        String viewerEmail = getViewerEmail(tenantId);
        
        // Check if data already exists
        if (adminUserRepository.existsByEmailAndTenantId(adminEmail, tenantId)) {
            return;
        }
        
        createAdminUser(adminEmail, "admin123", businessName + " Admin", 
                       AdminUser.UserRole.SUPER_ADMIN, false, tenantId);
        createAdminUser(editorEmail, "editor123", businessName + " Editor", 
                       AdminUser.UserRole.EDITOR, true, tenantId);
        createAdminUser(viewerEmail, "viewer123", businessName + " Viewer", 
                       AdminUser.UserRole.VIEWER, false, tenantId);

        // Create tenant-specific data
        if (tenantId.equals("default")) {
            // Home Decor products
            createCategory("Storage", "storage", "Storage solutions", tenantId);
            createCategory("Decorative", "decorative", "Decorative items", tenantId);
            createCategory("Wall Art", "wall-art", "Wall decorations", tenantId);
            
            createProduct("Handwoven Basket", "Beautiful handwoven basket perfect for storage",
                         new BigDecimal("45.99"), 15, "Storage", 
                         Arrays.asList("handmade", "natural"), true, tenantId);
            createProduct("Ceramic Vase", "Elegant ceramic vase with modern design",
                         new BigDecimal("32.50"), 8, "Decorative", 
                         Arrays.asList("ceramic", "modern"), false, tenantId);
            createProduct("Wooden Wall Art", "Rustic wooden wall art piece",
                         new BigDecimal("89.99"), 5, "Wall Art", 
                         Arrays.asList("wood", "rustic"), true, tenantId);
            
            createCustomer("Sarah Johnson", "sarah@homedecor.com", "+1234567890", tenantId);
            createCustomer("Michael Chen", "michael@homedecor.com", "+0987654321", tenantId);
            
        } else if (tenantId.equals("tenant1")) {
            // Fashion Store products
            createCategory("Clothing", "clothing", "Fashion clothing", tenantId);
            createCategory("Accessories", "accessories", "Fashion accessories", tenantId);
            createCategory("Footwear", "footwear", "Shoes and sandals", tenantId);
            
            createProduct("Designer T-Shirt", "Premium cotton designer t-shirt",
                         new BigDecimal("29.99"), 50, "Clothing", 
                         Arrays.asList("fashion", "cotton"), true, tenantId);
            createProduct("Leather Handbag", "Genuine leather handbag",
                         new BigDecimal("129.99"), 12, "Accessories", 
                         Arrays.asList("leather", "luxury"), true, tenantId);
            createProduct("Running Shoes", "Comfortable running shoes",
                         new BigDecimal("79.99"), 25, "Footwear", 
                         Arrays.asList("sports", "comfort"), false, tenantId);
            
            createCustomer("Emma Davis", "emma@fashion.com", "+1555123456", tenantId);
            createCustomer("James Wilson", "james@fashion.com", "+1555654321", tenantId);
            
        } else if (tenantId.equals("tenant2")) {
            // Tech Shop products
            createCategory("Electronics", "electronics", "Electronic devices", tenantId);
            createCategory("Tech Accessories", "tech-accessories", "Tech accessories", tenantId);
            createCategory("Gaming", "gaming", "Gaming equipment", tenantId);
            
            createProduct("Wireless Mouse", "Ergonomic wireless mouse",
                         new BigDecimal("24.99"), 100, "Electronics", 
                         Arrays.asList("tech", "wireless"), true, tenantId);
            createProduct("USB-C Cable", "Premium USB-C charging cable",
                         new BigDecimal("12.99"), 200, "Tech Accessories", 
                         Arrays.asList("cable", "usb"), false, tenantId);
            createProduct("Gaming Keyboard", "RGB mechanical gaming keyboard",
                         new BigDecimal("89.99"), 30, "Gaming", 
                         Arrays.asList("gaming", "rgb"), true, tenantId);
            
            createCustomer("Alex Tech", "alex@techshop.com", "+1777888999", tenantId);
            createCustomer("Lisa Chen", "lisa@techshop.com", "+1777999888", tenantId);
        }

        System.out.println("Sample data initialized for " + businessName + " (tenant: " + tenantId + ")");
        
        // Clear tenant context after initialization
        TenantContext.clear();
    }

    private void createAdminUser(String email, String password, String name, 
                                 AdminUser.UserRole role, boolean requires2FA, String tenantId) {
        AdminUser user = new AdminUser();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setRole(role);
        user.setRequiresTwoFactor(requires2FA);
        user.setEnabled(true);
        user.setAvatarUrl("https://api.dicebear.com/7.x/avataaars/svg?seed=" + email);
        adminUserRepository.save(user);
    }

    private Category createCategory(String name, String slug, String description, String tenantId) {
        Category category = new Category();
        category.setName(name);
        category.setSlug(slug);
        category.setDescription(description);
        category.setProductCount(0);
        return categoryRepository.save(category);
    }

    private void createProduct(String name, String description, BigDecimal price, 
                              Integer stock, String category, List<String> tags, 
                              Boolean featured, String tenantId) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        product.setTags(tags);
        product.setFeatured(featured);
        product.setRating(4.5);
        product.setImageUrl("https://images.unsplash.com/photo-1595428774223-ef52624120d2?w=400");
        productRepository.save(product);
    }

    private void createCustomer(String name, String email, String phone, String tenantId) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setOrderCount(0);
        customer.setTotalSpent(BigDecimal.ZERO);
        customerRepository.save(customer);
    }

    // Helper methods to generate tenant-specific emails
    private String getAdminEmail(String tenantId) {
        if (tenantId.equals("default")) {
            return "admin@homedecor.com";
        } else if (tenantId.equals("tenant1")) {
            return "admin@fashion.com";
        } else if (tenantId.equals("tenant2")) {
            return "admin@techshop.com";
        }
        return "admin@" + tenantId + ".com";
    }

    private String getEditorEmail(String tenantId) {
        if (tenantId.equals("default")) {
            return "editor@homedecor.com";
        } else if (tenantId.equals("tenant1")) {
            return "editor@fashion.com";
        } else if (tenantId.equals("tenant2")) {
            return "editor@techshop.com";
        }
        return "editor@" + tenantId + ".com";
    }

    private String getViewerEmail(String tenantId) {
        if (tenantId.equals("default")) {
            return "viewer@homedecor.com";
        } else if (tenantId.equals("tenant1")) {
            return "viewer@fashion.com";
        } else if (tenantId.equals("tenant2")) {
            return "viewer@techshop.com";
        }
        return "viewer@" + tenantId + ".com";
    }
}
