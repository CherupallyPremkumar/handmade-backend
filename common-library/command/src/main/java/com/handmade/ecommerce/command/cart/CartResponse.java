package com.handmade.ecommerce.command.cart;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    
    private Long id;
    private Long customerId;
    private List<CartItemResponse> items;
    private Integer totalItems;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal total;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItemResponse {
        private Long id;
        private Long productId;
        private String productName;
        private String productImage;
        private Long variantId;
        private String variantName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private Boolean inStock;
    }
}
