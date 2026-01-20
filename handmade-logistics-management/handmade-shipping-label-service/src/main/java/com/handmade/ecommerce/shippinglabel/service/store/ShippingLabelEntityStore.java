package com.handmade.ecommerce.shippinglabel.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.logistics.model.ShippingLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.shippinglabel.configuration.dao.ShippingLabelRepository;
import java.util.Optional;

public class ShippingLabelEntityStore implements EntityStore<ShippingLabel>{
    @Autowired private ShippingLabelRepository shippinglabelRepository;

	@Override
	public void store(ShippingLabel entity) {
        shippinglabelRepository.save(entity);
	}

	@Override
	public ShippingLabel retrieve(String id) {
        Optional<ShippingLabel> entity = shippinglabelRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find ShippingLabel with ID " + id);
	}

}
