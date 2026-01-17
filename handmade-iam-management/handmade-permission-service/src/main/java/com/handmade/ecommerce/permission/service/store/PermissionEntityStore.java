package com.handmade.ecommerce.permission.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.iam.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.permission.configuration.dao.PermissionRepository;
import java.util.Optional;

public class PermissionEntityStore implements EntityStore<Permission>{
    @Autowired private PermissionRepository permissionRepository;

	@Override
	public void store(Permission entity) {
        permissionRepository.save(entity);
	}

	@Override
	public Permission retrieve(String id) {
        Optional<Permission> entity = permissionRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Permission with ID " + id);
	}

}
