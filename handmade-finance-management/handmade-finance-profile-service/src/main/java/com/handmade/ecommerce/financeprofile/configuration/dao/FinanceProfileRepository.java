package com.handmade.ecommerce.financeprofile.configuration.dao;

import com.handmade.ecommerce.finance.model.FinanceProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface FinanceProfileRepository extends JpaRepository<FinanceProfile,String> {}
