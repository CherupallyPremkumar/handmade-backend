package com.handmade.ecommerce.product.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ACL DTO: Represents product data exported to other modules.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalProductDto {
    private String id;
    private String name;
    private BigDecimal price;
    private boolean active;
}
