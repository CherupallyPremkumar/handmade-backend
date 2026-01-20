package com.handmade.ecommerce.inventory.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ReceiveInboundShipmentItemPayload {
    private Integer receivedQuantity;
}
