package com.homebase.admin.config;

import com.homebase.admin.entity.*;
import com.homebase.admin.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;

    public DataInitializer(AdminUserRepository adminUserRepository, ProductRepository productRepository, CategoryRepository categoryRepository, OrderRepository orderRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder, TenantRepository tenantRepository) {
        this.adminUserRepository = adminUserRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize data for multiple tenants with distinct data
        initializeTenantData("havenhome", "Home Decor","havenhome","USD");
        initializeTenantData("stylehub", "Fashion Store","stylehub","EUR");
        initializeTenantData("techmart", "Tech Shop","techmart","GBP");
    }

    private void initializeTenantData(String tenantCode, String businessName, String urlPath, String currency) {
        createTenant(tenantCode, businessName, urlPath, currency);
        // Set tenant context for this initialization
        TenantContext.setCurrentTenant(tenantCode);
        
        // Create tenant-specific admin users
        String adminEmail = getAdminEmail(tenantCode);
        String editorEmail = getEditorEmail(tenantCode);
        String viewerEmail = getViewerEmail(tenantCode);
        
        // Check if data already exists
        if (adminUserRepository.existsByEmailAndTenantId(adminEmail, tenantCode)) {
            return;
        }
        
        createAdminUser(adminEmail, "admin123", businessName + " Admin", 
                       List.of(AdminUser.UserRole.SUPER_ADMIN.toString()), false, tenantCode);
        createAdminUser(editorEmail, "editor123", businessName + " Editor",
                List.of(AdminUser.UserRole.EDITOR.toString()), true, tenantCode);
        createAdminUser(viewerEmail, "viewer123", businessName + " Viewer",
                List.of(AdminUser.UserRole.VIEWER.toString()), false, tenantCode);

        // Create tenant-specific data based on business name
        if (businessName.equals("Home Decor")) {
            // Home Decor products
            createCategory("Storage", "storage", "Storage solutions", tenantCode);
            createCategory("Decorative", "decorative", "Decorative items", tenantCode);
            createCategory("Wall Art", "wall-art", "Wall decorations", tenantCode);
            createCategory("Furniture", "furniture", "Home furniture", tenantCode);
            createCategory("Lighting", "lighting", "Lighting solutions", tenantCode);
            
            // Storage products
            createProduct("Handwoven Basket", "Beautiful handwoven basket perfect for storage",
                         new BigDecimal("45.99"), 15, "Storage", 
                         Arrays.asList("handmade", "natural"), true, 4.5, false, null, tenantCode);
            createProduct("Storage Ottoman", "Multi-purpose storage ottoman with cushioned top",
                         new BigDecimal("89.99"), 12, "Storage", 
                         Arrays.asList("furniture", "storage"), false, 4.2, true, new BigDecimal("69.99"), tenantCode);
            createProduct("Wooden Crate Set", "Set of 3 rustic wooden storage crates",
                         new BigDecimal("54.99"), 20, "Storage", 
                         Arrays.asList("wood", "rustic"), true, 4.7, false, null, tenantCode);
            
            // Decorative products
            createProduct("Ceramic Vase", "Elegant ceramic vase with modern design",
                         new BigDecimal("32.50"), 8, "Decorative", 
                         Arrays.asList("ceramic", "modern"), false, 4.3, false, null, tenantCode);
            createProduct("Decorative Throw Pillows", "Set of 2 bohemian style throw pillows",
                         new BigDecimal("39.99"), 25, "Decorative", 
                         Arrays.asList("textile", "bohemian"), true, 4.6, true, new BigDecimal("29.99"), tenantCode);
            createProduct("Table Runner", "Handwoven cotton table runner",
                         new BigDecimal("24.99"), 18, "Decorative", 
                         Arrays.asList("textile", "handmade"), false, 4.4, false, null, tenantCode);
            
            // Wall Art products
            createProduct("Wooden Wall Art", "Rustic wooden wall art piece",
                         new BigDecimal("89.99"), 5, "Wall Art", 
                         Arrays.asList("wood", "rustic"), true, 4.8, false, null, tenantCode);
            createProduct("Abstract Canvas Print", "Modern abstract canvas wall art",
                         new BigDecimal("79.99"), 10, "Wall Art", 
                         Arrays.asList("canvas", "modern"), false, 4.5, true, new BigDecimal("59.99"), tenantCode);
            createProduct("Metal Wall Sculpture", "Contemporary metal wall sculpture",
                         new BigDecimal("129.99"), 3, "Wall Art", 
                         Arrays.asList("metal", "contemporary"), true, 4.9, false, null, tenantCode);
            
            // Furniture products
            createProduct("Accent Chair", "Mid-century modern accent chair",
                         new BigDecimal("299.99"), 8, "Furniture", 
                         Arrays.asList("furniture", "modern"), true, 4.7, false, null, tenantCode);
            createProduct("Coffee Table", "Solid wood coffee table with storage",
                         new BigDecimal("249.99"), 6, "Furniture", 
                         Arrays.asList("wood", "furniture"), false, 4.6, true, new BigDecimal("199.99"), tenantCode);
            createProduct("Bookshelf", "5-tier industrial bookshelf",
                         new BigDecimal("179.99"), 0, "Furniture", 
                         Arrays.asList("industrial", "storage"), false, 4.4, false, null, tenantCode);
            
            // Lighting products
            createProduct("Table Lamp", "Modern ceramic table lamp",
                         new BigDecimal("69.99"), 15, "Lighting", 
                         Arrays.asList("lamp", "modern"), true, 4.5, false, null, tenantCode);
            createProduct("Floor Lamp", "Arc floor lamp with marble base",
                         new BigDecimal("159.99"), 7, "Lighting", 
                         Arrays.asList("lamp", "contemporary"), false, 4.7, true, new BigDecimal("129.99"), tenantCode);
            createProduct("String Lights", "Warm white LED string lights",
                         new BigDecimal("19.99"), 50, "Lighting", 
                         Arrays.asList("led", "decorative"), true, 4.8, false, null, tenantCode);
            
            createCustomer("Sarah Johnson", "sarah@homedecor.com", "+1234567890", tenantCode);
            createCustomer("Michael Chen", "customer@example.com", "+0987654321", tenantCode);
            
        } else if (businessName.equals("Fashion Store")) {
            // Fashion Store products
            createCategory("Clothing", "clothing", "Fashion clothing", tenantCode);
            createCategory("Accessories", "accessories", "Fashion accessories", tenantCode);
            createCategory("Footwear", "footwear", "Shoes and sandals", tenantCode);
            
            createProduct("Designer T-Shirt", "Premium cotton designer t-shirt",
                         new BigDecimal("29.99"), 50, "Clothing", 
                         Arrays.asList("fashion", "cotton"), true, 4.6, false, null, tenantCode);
            createProduct("Leather Handbag", "Genuine leather handbag",
                         new BigDecimal("129.99"), 12, "Accessories", 
                         Arrays.asList("leather", "luxury"), true, 4.8, true, new BigDecimal("99.99"), tenantCode);
            createProduct("Running Shoes", "Comfortable running shoes",
                         new BigDecimal("79.99"), 25, "Footwear", 
                         Arrays.asList("sports", "comfort"), false, 4.5, false, null, tenantCode);
            
            createCustomer("Emma Wilson", "emma@fashion.com", "+1122334455", tenantCode);
            
        } else if (businessName.equals("Tech Shop")) {
            // Tech Shop products
            createCategory("Electronics", "electronics", "Electronic devices", tenantCode);
            createCategory("Accessories", "accessories", "Tech accessories", tenantCode);
            createCategory("Gaming", "gaming", "Gaming products", tenantCode);
            
            createProduct("Wireless Mouse", "Ergonomic wireless mouse",
                         new BigDecimal("39.99"), 30, "Accessories", 
                         Arrays.asList("wireless", "ergonomic"), true, 4.7, false, null, tenantCode);
            createProduct("USB-C Hub", "7-in-1 USB-C hub adapter",
                         new BigDecimal("49.99"), 20, "Accessories", 
                         Arrays.asList("usb-c", "adapter"), false, 4.4, true, new BigDecimal("39.99"), tenantCode);
            createProduct("Gaming Headset", "RGB gaming headset with mic",
                         new BigDecimal("89.99"), 15, "Gaming", 
                         Arrays.asList("gaming", "rgb"), true, 4.9, false, null, tenantCode);
            
            createCustomer("David Lee", "david@techshop.com", "+9988776655", tenantCode);
            createCustomer("Lisa Chen", "lisa@techshop.com", "+1777999888", tenantCode);
        }

        System.out.println("Sample data initialized for " + businessName + " (tenant: " + tenantCode + ")");
        
        // Clear tenant context after initialization
        TenantContext.clear();
    }

    private Tenant createTenant(String tenantCode, String businessName, String urlPath, String currency) {
        Tenant tenant = new Tenant();

        // Basic tenant info
        tenant.setTenantCode(tenantCode);
        tenant.setTenantName(businessName);
        tenant.setDisplayName(businessName); // fallback displayName
        tenant.setUrlPath(urlPath);
        tenant.setCurrency(currency);
        tenant.setLocale("en-US");
        tenant.setTimezone("UTC");

        // Branding / UI
        tenant.setLogoUrl("https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=200");

        // Support info
        tenant.setSupportEmail("support@" + urlPath + ".com");
        tenant.setSupportPhone("+10000000000");

        // Theme (non-null)
        TenantTheme theme = new TenantTheme();
        theme.setPrimaryColor("#4A90E2");
        theme.setSecondaryColor("#50E3C2");
        theme.setBackground("#FFFFFF");
        theme.setText("#333333");
        tenant.setTheme(theme);

        // Configuration / feature flags
        TenantConfiguration config = new TenantConfiguration();
        config.setTenantId(tenantCode);
        config.setPrimaryColor("#4A90E2");
        config.setSecondaryColor("#50E3C2");
        config.setEnablePromotions(true);
        config.setEnableReviews(true);
        tenant.setConfiguration(config);

        // Save tenant (cascade will save config)
        tenantRepository.save(tenant);

        System.out.println("Tenant created: " + businessName + " (" + tenantCode + ")");
        return tenant;
    }

    private void createAdminUser(String email, String password, String name, 
                                 List<String> roles, boolean requires2FA, String tenantId) {
        AdminUser user = new AdminUser();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setRoles(roles);
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
                              Boolean featured, Double rating, Boolean onSale, 
                              BigDecimal salePrice, String tenantId) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        product.setTags(tags);
        product.setFeatured(featured);
        product.setRating(rating);
        product.setOnSale(onSale);
        product.setSalePrice(salePrice);
        product.setImageUrl("https://images.unsplash.com/photo-1595428774223-ef52624120d2?w=400");
        productRepository.save(product);
    }

    private void createCustomer(String name, String email, String phone, String tenantId) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPassword(passwordEncoder.encode("customer123"));
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setOrderCount(0);
        customer.setTotalSpent(BigDecimal.ZERO);
        customerRepository.save(customer);
    }

    // Helper methods to generate tenant-specific emails
    private String getAdminEmail(String tenantId) {
        if (tenantId.equals("havenhome")) {
            return "admin@homedecor.com";
        } else if (tenantId.equals("stylehub")) {
            return "admin@fashion.com";
        } else if (tenantId.equals("techmart")) {
            return "admin@techshop.com";
        }
        return "admin@" + tenantId + ".com";
    }

    private String getEditorEmail(String tenantId) {
        if (tenantId.equals("havenhome")) {
            return "editor@homedecor.com";
        } else if (tenantId.equals("stylehub")) {
            return "editor@fashion.com";
        } else if (tenantId.equals("techmart")) {
            return "editor@techshop.com";
        }
        return "editor@" + tenantId + ".com";
    }

    private String getViewerEmail(String tenantId) {
        if (tenantId.equals("havenhome")) {
            return "viewer@homedecor.com";
        } else if (tenantId.equals("stylehub")) {
            return "viewer@fashion.com";
        } else if (tenantId.equals("techmart")) {
            return "viewer@techshop.com";
        }
        return "viewer@" + tenantId + ".com";
    }
}
