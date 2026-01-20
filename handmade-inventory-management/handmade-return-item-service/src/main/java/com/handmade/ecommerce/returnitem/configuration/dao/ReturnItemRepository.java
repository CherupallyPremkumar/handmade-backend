package com.handmade.ecommerce.returnitem.configuration.dao;

import com.handmade.ecommerce.inventory.model.ReturnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface ReturnItemRepository extends JpaRepository<ReturnItem,String> {}
