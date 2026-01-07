package com.handmade.ecommerce.inventory.service.impl;

import com.handmade.ecommerce.inventory.api.InventoryService;
import com.handmade.ecommerce.inventory.command.CreateInventoryCommand;
import com.handmade.ecommerce.inventory.domain.aggregate.Inventory;
import com.handmade.ecommerce.inventory.domain.aggregate.InventoryTransaction;
import com.handmade.ecommerce.inventory.domain.repository.InventoryRepository;
import com.handmade.ecommerce.inventory.domain.repository.InventoryTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryTransactionRepository transactionRepository;

    @Override
    @Transactional
    public void createInventory(CreateInventoryCommand command) {
        Inventory inventory = new Inventory();
        inventory.setId(command.getId() != null ? command.getId() : UUID.randomUUID().toString());
        inventory.setVariantId(command.getVariantId());
        inventory.setSellerId(command.getSellerId());
        inventory.setQuantityAvailable(command.getQuantityAvailable());
        inventory.setQuantityReserved(command.getQuantityReserved());
        inventory.setBackOrderAllowed(command.isBackOrderAllowed());
        
        inventoryRepository.save(inventory);
        
        // Log transaction
        logTransaction(inventory.getId(), "INITIAL_LOAD", command.getQuantityAvailable(), "Initial inventory load");
    }

    @Override
    public int getAvailableQuantity(String variantId, String sellerId) {
        return inventoryRepository.findByVariantIdAndSellerId(variantId, sellerId)
                .map(Inventory::getNetAvailable)
                .orElse(0);
    }

    @Override
    @Transactional
    public void updateStock(String variantId, String sellerId, int delta) {
        Inventory inventory = inventoryRepository.findByVariantIdAndSellerId(variantId, sellerId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for variant: " + variantId + " and seller: " + sellerId));
        
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + delta);
        inventoryRepository.save(inventory);
        
        logTransaction(inventory.getId(), "ADJUSTMENT", delta, "Stock update adjustment");
    }

    private void logTransaction(String inventoryId, String type, int change, String remarks) {
        InventoryTransaction tx = new InventoryTransaction();
        tx.setId(UUID.randomUUID().toString());
        tx.setInventoryId(inventoryId);
        tx.setTransactionType(type);
        tx.setQuantityChange(change);
        tx.setRemarks(remarks);
        transactionRepository.save(tx);
    }
}
