package com.homebase.admin.controller;

import com.homebase.admin.dto.CategoryDTO;
import com.homebase.admin.dto.ProductDTO;
import com.homebase.admin.service.CategoryService;
import com.homebase.admin.service.ProductService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    public static class ProductPageData {
        private List<ProductDTO> products;
        private List<CategoryDTO> categories;
        private List<String> tags;

        public ProductPageData(List<ProductDTO> products, List<CategoryDTO> categories, List<String> tags) {
            this.products = products;
            this.categories = categories;
            this.tags = tags;
        }

        public List<ProductDTO> getProducts() {
            return products;
        }

        public void setProducts(List<ProductDTO> products) {
            this.products = products;
        }

        public List<CategoryDTO> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoryDTO> categories) {
            this.categories = categories;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }

    @GetMapping("/page-data")
    public ResponseEntity<ProductPageData> getProductPageData() {
        List<ProductDTO> products = productService.getAllProducts();
        List<CategoryDTO> categories = categoryService.getAllCategories();
        List<String> tags = categoryService.getAllTags();
        return ResponseEntity.ok(new ProductPageData(products, categories, tags));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'EDITOR')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO created = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'EDITOR')")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO) {
        ProductDTO updated = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'EDITOR')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
