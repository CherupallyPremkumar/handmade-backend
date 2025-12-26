package com.handmade.ecommerce.catalog.controller;

import com.handmade.ecommerce.catalog.model.Collection;
import com.handmade.ecommerce.catalog.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "*")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * Create a new collection
     * POST /api/collections
     */
    @PostMapping
    public ResponseEntity<Collection> createCollection(@RequestBody Collection collection) {
        Collection created = collectionService.createCollection(collection);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update a collection
     * PUT /api/collections/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(
            @PathVariable String id,
            @RequestBody Collection collection) {
        Collection updated = collectionService.updateCollection(id, collection);
        return ResponseEntity.ok(updated);
    }

    /**
     * Get collection by ID
     * GET /api/collections/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable String id) {
        return collectionService.getCollectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all active collections
     * GET /api/collections
     */
    @GetMapping
    public ResponseEntity<List<Collection>> getActiveCollections() {
        return ResponseEntity.ok(collectionService.getActiveCollections());
    }

    /**
     * Get all dynamic collections
     * GET /api/collections/dynamic
     */
    @GetMapping("/dynamic")
    public ResponseEntity<List<Collection>> getDynamicCollections() {
        return ResponseEntity.ok(collectionService.getDynamicCollections());
    }

    /**
     * Delete a collection (soft delete)
     * DELETE /api/collections/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable String id) {
        collectionService.deleteCollection(id);
        return ResponseEntity.noContent().build();
    }
}
