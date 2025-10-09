package com.homebase.admin.controller;

import com.homebase.admin.dto.CartItemDTO;
import com.homebase.admin.service.CartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Data
    public static class AddToCartRequest {
        private Long productId;
        private Integer quantity;
    }

    @Data
    public static class UpdateCartRequest {
        private Integer quantity;
    }

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCart(@RequestParam Long userId) {
        List<CartItemDTO> cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping
    public ResponseEntity<CartItemDTO> addToCart(
            @RequestParam Long userId,
            @RequestBody AddToCartRequest request) {
        CartItemDTO item = cartService.addToCart(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @PathVariable Long itemId,
            @RequestParam Long userId,
            @RequestBody UpdateCartRequest request) {
        CartItemDTO updated = cartService.updateCartItem(itemId, userId, request.getQuantity());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable Long itemId,
            @RequestParam Long userId) {
        cartService.removeFromCart(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
