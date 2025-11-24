package com.handmade.ecommerce.product.dto;

import com.handmade.ecommerce.event.model.DomainEvent;
import org.chenile.workflow.param.MinimalPayload;

/**
    Customized Payload for the addVariant event.
*/
public class AddVariantProductPayload extends MinimalPayload implements DomainEvent {

}
