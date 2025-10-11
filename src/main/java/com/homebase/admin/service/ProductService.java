package com.homebase.admin.service;

import com.homebase.admin.dto.ProductDTO;
import com.homebase.admin.entity.Product;
import com.homebase.admin.config.TenantContext;
import com.homebase.admin.observer.event.ProductPriceChangedEvent;
import com.homebase.admin.repository.ProductRepository;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(ProductRepository productRepository, ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<ProductDTO> getAllProducts() {
        String tenantId = TenantContext.getCurrentTenant();
        System.out.println("ProductService.getAllProducts() - TenantId from context: " + tenantId);
        List<Product> products = productRepository.findByTenantId(tenantId);
        System.out.println(
                "ProductService.getAllProducts() - Found " + products.size() + " products for tenant: " + tenantId);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getLowStockProducts(int threshold) {
        String tenantId = TenantContext.getCurrentTenant();
        return productRepository.findByTenantId(tenantId).stream()
                .filter(p -> p.getStock() < threshold)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        String tenantId = TenantContext.getCurrentTenant();
        Product product = productRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product saved = productRepository.save(product);
        return convertToDTO(saved);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        String tenantId = TenantContext.getCurrentTenant();
        Product product = productRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Capture old values for price change detection
        BigDecimal oldPrice = product.getPrice();
        BigDecimal oldSalePrice = product.getSalePrice();
        Boolean oldOnSale = product.isOnSale();

        // Update product fields
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(productDTO.getCategory());
        product.setTags(productDTO.getTags());
        product.setRating(productDTO.getRating());
        product.setFeatured(productDTO.getFeatured());
        
        // Update sale fields if present in DTO
        if (productDTO.getOnSale() != null) {
            product.setOnSale(productDTO.getOnSale());
        }
        if (productDTO.getSalePrice() != null) {
            product.setSalePrice(productDTO.getSalePrice());
        }

        Product updated = productRepository.save(product);
        
        // Check if price or sale status changed and publish event
        boolean priceChanged = !oldPrice.equals(updated.getPrice());
        boolean salePriceChanged = (oldSalePrice == null && updated.getSalePrice() != null) ||
                                   (oldSalePrice != null && !oldSalePrice.equals(updated.getSalePrice()));
        boolean saleStatusChanged = !oldOnSale.equals(updated.isOnSale());
        
        if (priceChanged || salePriceChanged || saleStatusChanged) {
            ProductPriceChangedEvent event = new ProductPriceChangedEvent(
                updated.getId(),
                oldPrice,
                updated.getPrice(),
                oldOnSale,
                updated.isOnSale(),
                oldSalePrice,
                updated.getSalePrice()
            );
            eventPublisher.publishEvent(event);
        }
        
        return convertToDTO(updated);
    }

    @Transactional
    public void deleteProduct(Long id) {
        String tenantId = TenantContext.getCurrentTenant();
        Product product = productRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(String.valueOf(product.getId()));
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategory(product.getCategory());
        dto.setTags(product.getTags());
        dto.setRating(product.getRating());
        dto.setFeatured(product.getFeatured());
        dto.setOnSale(product.isOnSale());
        dto.setSalePrice(product.getSalePrice());
        dto.setCreatedAt(product.getCreatedAt() != null ? product.getCreatedAt().toString() : null);
        dto.setUpdatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt().toString() : null);
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(dto.getCategory());
        product.setTags(dto.getTags());
        product.setRating(dto.getRating());
        product.setFeatured(dto.getFeatured());
        return product;
    }
}
