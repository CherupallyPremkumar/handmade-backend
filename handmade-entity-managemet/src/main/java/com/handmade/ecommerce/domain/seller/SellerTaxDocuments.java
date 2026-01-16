package com.handmade.ecommerce.domain.seller;

import jakarta.persistence.*;
import lombok.*;

/**
 * SellerTaxDocuments - Tax-related documents for sellers (Embeddable)
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerTaxDocuments {
    @Column(name = "document_url", length = 500)
    private String documentUrl;
}
