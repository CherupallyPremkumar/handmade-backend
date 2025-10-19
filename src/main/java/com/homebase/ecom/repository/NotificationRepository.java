package com.homebase.ecom.repository;

import com.homebase.ecom.domain.Notification;
import com.homebase.ecom.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {
    

}
