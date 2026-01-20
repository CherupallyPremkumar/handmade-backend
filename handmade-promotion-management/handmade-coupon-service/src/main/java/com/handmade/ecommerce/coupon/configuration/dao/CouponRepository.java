package com.handmade.ecommerce.coupon.configuration.dao;

import com.handmade.ecommerce.promotion.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface CouponRepository extends JpaRepository<Coupon,String> {}
