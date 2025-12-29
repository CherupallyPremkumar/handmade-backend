package com.handmade.ecommerce.product.service;

import com.handmade.ecommerce.product.dto.CreateVariantCommand;
import java.util.List;

/**
 * Variant Service Interface
 */
public interface VariantService {
    void createVariant(CreateVariantCommand variant);

    void validate(List<CreateVariantCommand> variants);
}
