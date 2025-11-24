package com.handmade.ecommerce.variant;

import com.handmade.ecommerce.command.CreateVariantCommand;

public interface VaraiantService {
    void createVariant(CreateVariantCommand variant);
}
