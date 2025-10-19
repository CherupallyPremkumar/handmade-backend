package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Address;
import com.homebase.ecom.entity.AddressEntity;
import org.chenile.utils.entity.service.EntityStore;

public class AddressEntityStore implements EntityStore<Address> {
    @Override
    public void store(Address entity) {

    }

    @Override
    public Address retrieve(String id) {
        return null;
    }
}
