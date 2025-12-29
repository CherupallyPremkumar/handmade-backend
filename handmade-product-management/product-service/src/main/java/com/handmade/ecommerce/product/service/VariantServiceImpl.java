package com.handmade.ecommerce.product.service;

import com.handmade.ecommerce.product.dto.CreateVariantCommand;
import com.handmade.ecommerce.product.dto.CreateShippingProfileCommand;
import com.handmade.ecommerce.product.dto.VariantAttributeDTO;
import com.handmade.ecommerce.core.model.VariantAttribute;
import com.handmade.ecommerce.product.service.VariantService;
import com.handmade.ecommerce.product.domain.model.Variant;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class VariantServiceImpl extends StateEntityServiceImpl<Variant> implements VariantService {

    public VariantServiceImpl(STM<Variant> stm, STMActionsInfoProvider stmActionsInfoProvider,
            EntityStore<Variant> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public void createVariant(CreateVariantCommand variantCommand) {
        Variant variant = new Variant();
        makeObject(variant, variantCommand);
        super.create(variant);
    }

    private void makeObject(Variant variant, CreateVariantCommand command) {
        variant.setProductId(command.getProductId());
        variant.setSku(command.getSku());
        variant.setDisplayName(command.getTitle());
        variant.setDescription(command.getDescription());
        variant.setFeatureDescription(command.getFeatureDescription());
        variant.setArtisanId(command.getArtisanId());

        // Map shipping profiles
        if (command.getShippingProfiles() != null && !command.getShippingProfiles().isEmpty()) {
            List<com.handmade.ecommerce.product.domain.model.ShippingProfile> entityProfiles = new ArrayList<>();
            for (CreateShippingProfileCommand cmdProfile : command.getShippingProfiles()) {
                com.handmade.ecommerce.product.domain.model.ShippingProfile entityProfile = new com.handmade.ecommerce.product.domain.model.ShippingProfile();

                // Map profile type
                if (cmdProfile.getProfileType() != null) {
                    entityProfile.setProfileType(
                            com.handmade.ecommerce.product.domain.model.ShippingProfile.ProfileType.valueOf(
                                    cmdProfile.getProfileType().toUpperCase()));
                }

                // Map dimensions
                if (cmdProfile.getShippingDimensions() != null) {
                    com.handmade.ecommerce.core.model.ShippingDimensions entityDimensions = new com.handmade.ecommerce.core.model.ShippingDimensions();
                    entityDimensions.setWeight(cmdProfile.getShippingDimensions().getWeight());
                    entityDimensions.setLength(cmdProfile.getShippingDimensions().getLength());
                    entityDimensions.setWidth(cmdProfile.getShippingDimensions().getWidth());
                    entityDimensions.setHeight(cmdProfile.getShippingDimensions().getHeight());
                    entityProfile.setDimensions(entityDimensions);
                }

                entityProfile.setPackagingType(cmdProfile.getPackagingType());
                entityProfile.setIsDefault(cmdProfile.getIsDefault() != null ? cmdProfile.getIsDefault() : false);
                entityProfile.setIsActive(true);
                entityProfile.setNotes(cmdProfile.getNotes());

                entityProfiles.add(entityProfile);
            }
            variant.setShippingProfiles(entityProfiles);
        }

        // Map command VariantAttributeDTO to entity VariantAttribute
        if (command.getVariantAttributes() != null) {
            List<com.handmade.ecommerce.core.model.VariantAttribute> entityAttributes = new ArrayList<>();
            for (VariantAttributeDTO cmdAttr : command.getVariantAttributes()) {
                com.handmade.ecommerce.core.model.VariantAttribute entityAttr = new com.handmade.ecommerce.core.model.VariantAttribute();
                entityAttr.setName(cmdAttr.getName());
                entityAttr.setValue(cmdAttr.getValue());
                entityAttributes.add(entityAttr);
            }
            variant.setAttributes(entityAttributes);
        }
    }

    private static List<VariantAttribute> getVariantAttributes(CreateVariantCommand command) {
        List<VariantAttribute> entityAttributes = new ArrayList<>();
        for (VariantAttributeDTO cmdAttr : command.getVariantAttributes()) {
            VariantAttribute entityAttr = new VariantAttribute();
            entityAttr.setName(cmdAttr.getName());
            entityAttr.setValue(cmdAttr.getValue());
            entityAttributes.add(entityAttr);
        }
        return entityAttributes;
    }

    @Override
    public void validate(List<CreateVariantCommand> variants) {
        if (variants == null || variants.isEmpty()) {
            throw new IllegalArgumentException("At least one variant is required");
        }
        for (CreateVariantCommand variant : variants) {
            if (variant.getProductId() == null || variant.getProductId().trim().isEmpty()) {
                throw new IllegalArgumentException("Product ID is required for variant");
            }
            if (variant.getSku() == null || variant.getSku().trim().isEmpty()) {
                throw new IllegalArgumentException("SKU is required for variant");
            }
            if (variant.getTitle() == null || variant.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("Title is required for variant");
            }
            if (variant.getVariantAttributes() == null || variant.getVariantAttributes().isEmpty()) {
                throw new IllegalArgumentException("Variant attributes are required");
            }
        }
    }
}
