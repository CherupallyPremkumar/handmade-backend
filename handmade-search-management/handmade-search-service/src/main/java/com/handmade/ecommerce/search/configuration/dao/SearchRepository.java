package com.handmade.ecommerce.search.configuration.dao;

import com.handmade.ecommerce.search.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  public interface SearchRepository extends JpaRepository<Search,String> {}
