package com.handmade.ecommerce.userrole.service.store;

import org.chenile.utils.entity.service.EntityStore;
import com.handmade.ecommerce.iam.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.chenile.base.exception.NotFoundException;
import com.handmade.ecommerce.userrole.configuration.dao.UserRoleRepository;
import java.util.Optional;

public class UserRoleEntityStore implements EntityStore<UserRole>{
    @Autowired private UserRoleRepository userroleRepository;

	@Override
	public void store(UserRole entity) {
        userroleRepository.save(entity);
	}

	@Override
	public UserRole retrieve(String id) {
        Optional<UserRole> entity = userroleRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find UserRole with ID " + id);
	}

}
