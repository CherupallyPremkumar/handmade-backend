package com.homebase.admin.service;

import com.homebase.admin.dto.ProductDTO;
import com.homebase.admin.dto.ProductFilterRequest;
import com.homebase.admin.dto.ProductFilterResponse;
import com.homebase.admin.entity.Product;
import com.homebase.admin.config.TenantContext;
import com.homebase.admin.mapper.ProductMapper;
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
    private final ProductMapper productMapper;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper,
            ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.eventPublisher = eventPublisher;
    }

    public List<ProductDTO> getAllProducts() {
        String tenantId = TenantContext.getCurrentTenant();
        System.out.println("ProductService.getAllProducts() - TenantId from context: " + tenantId);
        List<Product> products = productMapper.findAllByTenantId(tenantId); // MyBatis
        System.out.println(
                "ProductService.getAllProducts() - Found " + products.size() + " products for tenant: " + tenantId);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getLowStockProducts(int threshold) {
        String tenantId = TenantContext.getCurrentTenant();
        return productMapper.findAllByTenantId(tenantId).stream() // MyBatis
                .filter(p -> p.getStock() < threshold)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(String id) {
        String tenantId = TenantContext.getCurrentTenant();
        Product product = productMapper.findByIdAndTenantId(id, tenantId); // MyBatis
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return convertToDTO(product);
    }

    public ProductFilterResponse filterProducts(ProductFilterRequest filter) {
        // Get filtered products
        List<Product> products = productMapper.findProductsWithFilters(filter);
        long totalProducts = productMapper.countProductsWithFilters(filter);

        // Get metadata
        List<String> categories = productMapper.findAllCategoriesByTenantId(filter.getTenantId());
        BigDecimal minPrice = productMapper.findMinPriceByTenantId(filter.getTenantId());
        BigDecimal maxPrice = productMapper.findMaxPriceByTenantId(filter.getTenantId());

        // Build response
        ProductFilterResponse response = new ProductFilterResponse();
        response.setProducts(products.stream().map(this::convertToDTO).collect(Collectors.toList()));
        response.setCategories(new java.util.HashSet<>(categories));
        response.setMinPrice(minPrice != null ? minPrice : BigDecimal.ZERO);
        response.setMaxPrice(maxPrice != null ? maxPrice : BigDecimal.ZERO);
        response.setTotalProducts(totalProducts);
        response.setCurrentPage(filter.getPage());
        response.setTotalPages((int) Math.ceil((double) totalProducts / filter.getSize()));

        return response;
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product saved = productRepository.save(product);
        return convertToDTO(saved);
    }

    @Transactional
    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
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
                    updated.getSalePrice());
            eventPublisher.publishEvent(event);
        }

        return convertToDTO(updated);
    }

    @Transactional
    public void deleteProduct(String id) {
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
