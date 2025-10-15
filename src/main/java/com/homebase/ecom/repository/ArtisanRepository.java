package com.homebase.ecom.repository;

import com.homebase.ecom.entity.ArtisanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtisanRepository extends JpaRepository<ArtisanEntity,String> {
}
