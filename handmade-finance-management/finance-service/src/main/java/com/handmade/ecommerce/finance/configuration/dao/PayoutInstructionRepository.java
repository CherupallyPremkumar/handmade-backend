package com.handmade.ecommerce.finance.configuration.dao;

import com.handmade.ecommerce.finance.domain.aggregate.PayoutInstruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayoutInstructionRepository extends JpaRepository<PayoutInstruction, String> {
    List<PayoutInstruction> findBySellerId(String sellerId);
}
