package com.handmade.ecommerce.observability.configuration.dao;

import com.handmade.ecommerce.observability.model.MetricsSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface MetricsSnapshotRepository extends JpaRepository<MetricsSnapshot,String> {}
