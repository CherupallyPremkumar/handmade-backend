package com.handmade.ecommerce.productorch.service;


import com.handmade.ecommerce.inventory.InventoryService;
import org.chenile.owiz.Command;

public class CreateInventoryCommandService implements Command<ProductContext> {

    InventoryService inventoryService;

    @Override
    public void execute(ProductContext context) throws Exception {
        System.out.println("Creating inventory in CreateInventoryCommandService");

    }
}
