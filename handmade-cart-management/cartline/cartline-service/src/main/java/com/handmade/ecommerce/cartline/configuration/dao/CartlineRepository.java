package com.handmade.ecommerce.cartline.configuration.dao;

import com.handmade.ecommerce.cartline.model.Cartline;
import org.chenile.stm.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository  public interface CartlineRepository extends JpaRepository<Cartline,String> {
    List<Cartline> findByCartId(String cartId);

    Optional<Cartline> findByCartIdAndVariantIdAndState(
            String cartId,
            String variantId,
            State state
    );}
