package com.handmade.ecommerce.platform.configuration.dao;

import com.handmade.ecommerce.platform.domain.aggregate.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
}
