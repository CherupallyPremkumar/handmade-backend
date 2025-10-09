package com.homebase.admin.service;

import com.homebase.admin.dto.CartItemDTO;
import com.homebase.admin.entity.Cart;
import com.homebase.admin.entity.Product;
import com.homebase.admin.entity.TenantContext;
import com.homebase.admin.repository.CartRepository;
import com.homebase.admin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public List<CartItemDTO> getCart(Long userId) {
        String tenantId = TenantContext.getCurrentTenant();
        return cartRepository.findByUserIdAndTenantId(userId, tenantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartItemDTO addToCart(Long userId, Long productId, Integer quantity) {
        String tenantId = TenantContext.getCurrentTenant();
        
        // Check if item already exists in cart
        var existingCart = cartRepository.findByUserIdAndProductIdAndTenantId(userId, productId, tenantId);
        
        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            return convertToDTO(cartRepository.save(cart));
        }
        
        // Get product details
        Product product = productRepository.findByIdAndTenantId(productId, tenantId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setProductName(product.getName());
        cart.setProductImage(product.getImageUrl());
        cart.setPrice(product.getPrice().intValue());
        cart.setQuantity(quantity);
        cart.setTenantId(tenantId);
        
        return convertToDTO(cartRepository.save(cart));
    }

    @Transactional
    public CartItemDTO updateCartItem(Long cartId, Long userId, Integer quantity) {
        String tenantId = TenantContext.getCurrentTenant();
        Cart cart = cartRepository.findByIdAndUserIdAndTenantId(cartId, userId, tenantId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        cart.setQuantity(quantity);
        return convertToDTO(cartRepository.save(cart));
    }

    @Transactional
    public void removeFromCart(Long cartId, Long userId) {
        String tenantId = TenantContext.getCurrentTenant();
        Cart cart = cartRepository.findByIdAndUserIdAndTenantId(cartId, userId, tenantId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartRepository.delete(cart);
    }

    @Transactional
    public void clearCart(Long userId) {
        String tenantId = TenantContext.getCurrentTenant();
        cartRepository.deleteByUserIdAndTenantId(userId, tenantId);
    }

    private CartItemDTO convertToDTO(Cart cart) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cart.getId());
        dto.setProductId(cart.getProductId());
        dto.setProductName(cart.getProductName());
        dto.setProductImage(cart.getProductImage());
        dto.setPrice(cart.getPrice());
        dto.setQuantity(cart.getQuantity());
        dto.setSubtotal(cart.getPrice() * cart.getQuantity());
        return dto;
    }
}
