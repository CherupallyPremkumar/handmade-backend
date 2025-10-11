package com.homebase.admin.controller.user;

import com.homebase.admin.dto.AddToCartRequest;
import com.homebase.admin.dto.CartItemDTO;
import com.homebase.admin.dto.UpdateCartItemRequest;
import com.homebase.admin.service.CartService;
import org.springframework.http.ResponseEntity;
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
     * GET /api/user/cart?customerId={customerId}&tenantId={tenantId}
     * Get all items in the customer's cart
     */
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCart(
            @RequestParam String customerId,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        List<CartItemDTO> items = cartService.getCartItems(customerId, tenantId);
        return ResponseEntity.ok(items);
    }

    /**
     * POST /api/user/cart?customerId={customerId}&tenantId={tenantId}
     * Add a product to the cart
     */
    @PostMapping
    public ResponseEntity<CartItemDTO> addToCart(
            @RequestParam String customerId,
            @RequestParam(required = false, defaultValue = "default") String tenantId,
            @RequestBody AddToCartRequest request) {
        CartItemDTO item = cartService.addToCart(
                customerId, 
                request.getProductId(), 
                request.getQuantity(), 
                tenantId
        );
        return ResponseEntity.ok(item);
    }

    /**
     * PUT /api/user/cart/{itemId}?tenantId={tenantId}
     * Update quantity of a cart item
     */
    @PutMapping("/{itemId}")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @PathVariable String itemId,
            @RequestParam(required = false, defaultValue = "default") String tenantId,
            @RequestBody UpdateCartItemRequest request) {
        CartItemDTO item = cartService.updateCartItem(itemId, request.getQuantity(), tenantId);
        return ResponseEntity.ok(item);
    }

    /**
     * DELETE /api/user/cart/{itemId}?tenantId={tenantId}
     * Remove an item from the cart
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable String itemId,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        cartService.removeFromCart(itemId, tenantId);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/user/cart?customerId={customerId}&tenantId={tenantId}
     * Clear all items from the cart
     */
    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            @RequestParam String customerId,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        cartService.clearCart(customerId, tenantId);
        return ResponseEntity.noContent().build();
    }
}
