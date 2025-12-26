package com.handmade.ecommerce.variant;

import com.handmade.ecommerce.command.CreateVariantCommand;

import java.util.List;

public interface VaraiantService {
    void createVariant(CreateVariantCommand variant);

    void validate(List<CreateVariantCommand> variants);
}
