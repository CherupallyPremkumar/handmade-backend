package com.homebase.admin.service;

import com.homebase.admin.dto.WishlistItemDTO;
import com.homebase.admin.entity.*;
import com.homebase.admin.repository.CustomerRepository;
import com.homebase.admin.repository.ProductRepository;
import com.homebase.admin.repository.WishlistItemRepository;
import com.homebase.admin.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public WishlistService(WishlistRepository wishlistRepository, WishlistItemRepository wishlistItemRepository,
                          ProductRepository productRepository, CustomerRepository customerRepository) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<WishlistItemDTO> getWishlistItems(Long customerId, String tenantId) {
        Wishlist wishlist = getOrCreateWishlist(customerId, tenantId);
        return wishlist.getItems().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public WishlistItemDTO addToWishlist(Long customerId, Long productId, String tenantId) {
        Wishlist wishlist = getOrCreateWishlist(customerId, tenantId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Check if item already exists in wishlist
        boolean exists = wishlist.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(productId));

        if (exists) {
            throw new IllegalArgumentException("Product already in wishlist");
        }

        WishlistItem newItem = new WishlistItem();
        newItem.setWishlist(wishlist);
        newItem.setProduct(product);
        newItem.setDesiredQuantity(1);
        newItem.setTenantId(tenantId);
        
        wishlist.getItems().add(newItem);
        wishlistItemRepository.save(newItem);
        
        return convertToDTO(newItem);
    }

    @Transactional
    public void removeFromWishlist(Long itemId, String tenantId) {
        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist item not found"));
        
        if (!item.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Unauthorized access to wishlist item");
        }

        Wishlist wishlist = item.getWishlist();
        wishlist.getItems().remove(item);
        wishlistItemRepository.delete(item);
    }

    @Transactional
    public void clearWishlist(Long customerId, String tenantId) {
        Wishlist wishlist = wishlistRepository.findByCustomerIdAndTenantId(customerId, tenantId)
                .orElse(null);

        if (wishlist != null) {
            wishlistItemRepository.deleteAll(wishlist.getItems());
            wishlist.getItems().clear();
            wishlistRepository.save(wishlist);
        }
    }

    @Transactional(readOnly = true)
    public boolean isInWishlist(Long customerId, Long productId, String tenantId) {
        Wishlist wishlist = wishlistRepository.findByCustomerIdAndTenantId(customerId, tenantId)
                .orElse(null);
        
        if (wishlist == null) {
            return false;
        }

        return wishlist.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(productId));
    }

    private Wishlist getOrCreateWishlist(Long customerId, String tenantId) {
        return wishlistRepository.findByCustomerIdAndTenantId(customerId, tenantId)
                .orElseGet(() -> {
                    Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
                    
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setCustomer(customer);
                    newWishlist.setTenantId(tenantId);
                    return wishlistRepository.save(newWishlist);
                });
    }

    private WishlistItemDTO convertToDTO(WishlistItem item) {
        WishlistItemDTO dto = new WishlistItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setProductImage(item.getProduct().getImageUrl());
        dto.setPrice(item.getProduct().getPrice());
        dto.setDesiredQuantity(item.getDesiredQuantity());
        dto.setInStock(item.getProduct().getStock() > 0);
        return dto;
    }
}
