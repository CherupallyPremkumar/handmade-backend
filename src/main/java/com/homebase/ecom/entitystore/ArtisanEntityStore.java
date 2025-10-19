package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Artisan;
import com.homebase.ecom.repository.ArtisanRepository;
import org.chenile.utils.entity.service.EntityStore;

public class ArtisanEntityStore implements EntityStore<Artisan> {

    final ArtisanRepository artisanRepository;

    public ArtisanEntityStore(ArtisanRepository artisanRepository) {
        this.artisanRepository = artisanRepository;
    }

    @Override
    public void store(Artisan entity) {

    }

    @Override
    public Artisan retrieve(String id) {
        return null;
    }
}
