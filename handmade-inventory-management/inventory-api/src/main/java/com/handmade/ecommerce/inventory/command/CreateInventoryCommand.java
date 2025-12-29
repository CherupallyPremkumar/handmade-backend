package com.handmade.ecommerce.inventory.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInventoryCommand {
    private String id;
    private String variantId;
    private int quantityAvailable;
    private int quantityReserved;
    private boolean isBackOrderAllowed;
}
