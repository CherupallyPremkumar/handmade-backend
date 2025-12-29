package com.handmade.ecommerce.product.delegate;

import com.handmade.ecommerce.product.dto.CreateVariantCommand;

import java.util.List;

/**
 * Client interface for interacting with the Variant Service.
 */
public interface VariantManagerClient {
    void createVariant(CreateVariantCommand variant);

    void validate(List<CreateVariantCommand> variants);
}
