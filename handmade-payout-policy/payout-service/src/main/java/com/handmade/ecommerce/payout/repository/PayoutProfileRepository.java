package com.handmade.ecommerce.payout.repository;

import com.handmade.ecommerce.payout.domain.entity.PayoutProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayoutProfileRepository extends JpaRepository<PayoutProfile, String> {
}
