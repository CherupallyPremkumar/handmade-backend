package com.handmade.ecommerce.product.delegate;

import com.handmade.ecommerce.product.dto.CreateVariantCommand;
import com.handmade.ecommerce.product.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VariantManagerClientImpl implements VariantManagerClient {

    @Autowired
    private VariantService variantService;

    @Override
    public void createVariant(CreateVariantCommand variant) {
        variantService.createVariant(variant);
    }

    @Override
    public void validate(List<CreateVariantCommand> variants) {
        variantService.validate(variants);
    }
}
