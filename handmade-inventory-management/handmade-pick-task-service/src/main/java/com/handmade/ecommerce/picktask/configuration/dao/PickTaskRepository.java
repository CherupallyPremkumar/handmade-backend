package com.handmade.ecommerce.picktask.configuration.dao;

import com.handmade.ecommerce.inventory.model.PickTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface PickTaskRepository extends JpaRepository<PickTask,String> {}
