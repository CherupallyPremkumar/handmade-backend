package com.homebase.admin.controller.user;

import com.homebase.admin.dto.AddToCartRequest;
import com.homebase.admin.dto.CartItemDTO;
import com.homebase.admin.dto.UpdateCartItemRequest;
import com.homebase.admin.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/cart")
public class UserCartController {

    private final CartService cartService;

    public UserCartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * GET /api/user/cart
     * Get all items in the authenticated customer's cart
     * Reads customer email from JWT token and tenantId from X-Tenant-ID header
     */
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCart() {
        String customerEmail = getAuthenticatedUserId();
        List<CartItemDTO> items = cartService.getCartItems(customerEmail);
        return ResponseEntity.ok(items);
    }

    /**
     * POST /api/user/cart
     * Add a product to the cart
     * Reads customer email from JWT token and tenantId from X-Tenant-ID header
     */
    @PostMapping
    public ResponseEntity<CartItemDTO> addToCart(@RequestBody AddToCartRequest request) {
        String customerEmail = getAuthenticatedUserId();
        CartItemDTO item = cartService.addToCart(
                customerEmail, 
                request.getProductId(), 
                request.getQuantity()
        );
        return ResponseEntity.ok(item);
    }

    /**
     * PUT /api/user/cart/{itemId}
     * Update quantity of a cart item
     * Reads tenantId from X-Tenant-ID header
     */
    @PutMapping("/{itemId}")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @PathVariable String itemId,
            @RequestBody UpdateCartItemRequest request) {
        CartItemDTO item = cartService.updateCartItem(itemId, request.getQuantity());
        return ResponseEntity.ok(item);
    }

    /**
     * DELETE /api/user/cart/{itemId}
     * Remove an item from the cart
     * Reads tenantId from X-Tenant-ID header
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable String itemId) {
        cartService.removeFromCart(itemId);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/user/cart
     * Clear all items from the cart
     * Reads customer email from JWT token and tenantId from X-Tenant-ID header
     */
    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        String customerEmail = getAuthenticatedUserId();
        cartService.clearCart(customerEmail);
        return ResponseEntity.noContent().build();
    }

    /**
     * Helper method to extract authenticated user email from JWT token
     */
    private String getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User not authenticated");
        }
        return authentication.getName(); // Returns email (username) from JWT
    }
}
