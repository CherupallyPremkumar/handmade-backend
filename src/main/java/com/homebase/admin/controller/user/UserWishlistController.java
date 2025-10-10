package com.homebase.admin.controller.user;

import com.homebase.admin.dto.WishlistItemDTO;
import com.homebase.admin.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/wishlist")
public class UserWishlistController {

    private final WishlistService wishlistService;

    public UserWishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    /**
     * GET /api/user/wishlist?customerId={customerId}&tenantId={tenantId}
     * Get all items in the customer's wishlist
     */
    @GetMapping
    public ResponseEntity<List<WishlistItemDTO>> getWishlist(
            @RequestParam Long customerId,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        List<WishlistItemDTO> items = wishlistService.getWishlistItems(customerId, tenantId);
        return ResponseEntity.ok(items);
    }

    /**
     * POST /api/user/wishlist?customerId={customerId}&tenantId={tenantId}
     * Add a product to the wishlist
     */
    @PostMapping
    public ResponseEntity<WishlistItemDTO> addToWishlist(
            @RequestParam Long customerId,
            @RequestParam(required = false, defaultValue = "default") String tenantId,
            @RequestBody Map<String, Long> request) {
        Long productId = request.get("productId");
        WishlistItemDTO item = wishlistService.addToWishlist(customerId, productId, tenantId);
        return ResponseEntity.ok(item);
    }

    /**
     * DELETE /api/user/wishlist/{itemId}?tenantId={tenantId}
     * Remove an item from the wishlist
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeFromWishlist(
            @PathVariable Long itemId,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        wishlistService.removeFromWishlist(itemId, tenantId);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/user/wishlist?customerId={customerId}&tenantId={tenantId}
     * Clear all items from the wishlist
     */
    @DeleteMapping
    public ResponseEntity<Void> clearWishlist(
            @RequestParam Long customerId,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        wishlistService.clearWishlist(customerId, tenantId);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/user/wishlist/check?customerId={customerId}&productId={productId}&tenantId={tenantId}
     * Check if a product is in the wishlist
     */
    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkWishlist(
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam(required = false, defaultValue = "default") String tenantId) {
        boolean inWishlist = wishlistService.isInWishlist(customerId, productId, tenantId);
        return ResponseEntity.ok(Map.of("inWishlist", inWishlist));
    }
}
