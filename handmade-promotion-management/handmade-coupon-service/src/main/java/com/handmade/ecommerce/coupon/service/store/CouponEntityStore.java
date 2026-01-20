package com.handmade.ecommerce.coupon.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.promotion.model.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.coupon.configuration.dao.CouponRepository;
import java.util.Optional;

public class CouponEntityStore implements EntityStore<Coupon>{
    @Autowired private CouponRepository couponRepository;

	@Override
	public void store(Coupon entity) {
        couponRepository.save(entity);
	}

	@Override
	public Coupon retrieve(String id) {
        Optional<Coupon> entity = couponRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Coupon with ID " + id);
	}

}
