package com.handmade.ecommerce.packtask.configuration.dao;

import com.handmade.ecommerce.inventory.model.PackTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PackTaskRepository extends JpaRepository<PackTask,String> {}
