package com.homebase.ecom.repository;

import com.homebase.ecom.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface PriceRepository extends JpaRepository<Price, String> {
}
