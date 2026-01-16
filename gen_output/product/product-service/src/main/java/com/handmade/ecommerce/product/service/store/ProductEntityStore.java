package com.handmade.ecommerce.product.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.product.model.Product;

public class ProductEntityStore implements EntityStore<Product>{
    private Map<String, Product> theStore = new HashMap<>();
    public static int counter = 1; // use a counter to generate the ID. (not prod quality code)

	@Override
	public void store(Product entity) {
		if (entity.getId() == null) {
			entity.setId(counter++ + "");
		}
		theStore.put(entity.getId(), entity);
	}

	@Override
	public Product retrieve(String id) {
		return theStore.get(id);
	}

}
