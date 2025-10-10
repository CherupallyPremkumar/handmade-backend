package com.homebase.admin.controller.user;

import com.homebase.admin.dto.ProductDTO;
import com.homebase.admin.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/products")
public class UserProductController {

    private final ProductService productService;

    public UserProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET /api/user/products?tenantId={tenantId}
     * Get all products for customer-facing website
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * GET /api/user/products/{id}?tenantId={tenantId}
     * Get a specific product by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * GET /api/user/products/category/{category}?tenantId={tenantId}
     * Get products by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        List<ProductDTO> products = productService.getAllProducts().stream()
                .filter(p -> category.equals(p.getCategory()))
                .toList();
        return ResponseEntity.ok(products);
    }

    /**
     * GET /api/user/products/featured?tenantId={tenantId}
     * Get featured products
     */
    @GetMapping("/featured")
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts(
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        List<ProductDTO> products = productService.getAllProducts().stream()
                .filter(ProductDTO::getFeatured)
                .toList();
        return ResponseEntity.ok(products);
    }
}
