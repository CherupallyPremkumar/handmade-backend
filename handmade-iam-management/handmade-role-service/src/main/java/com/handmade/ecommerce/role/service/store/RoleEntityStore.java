package com.handmade.ecommerce.role.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.iam.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.role.configuration.dao.RoleRepository;
import java.util.Optional;

public class RoleEntityStore implements EntityStore<Role>{
    @Autowired private RoleRepository roleRepository;

	@Override
	public void store(Role entity) {
        roleRepository.save(entity);
	}

	@Override
	public Role retrieve(String id) {
        Optional<Role> entity = roleRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find Role with ID " + id);
	}

}
