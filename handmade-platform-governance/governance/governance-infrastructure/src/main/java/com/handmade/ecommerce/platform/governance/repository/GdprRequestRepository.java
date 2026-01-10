package com.handmade.ecommerce.platform.governance.repository;

import com.handmade.ecommerce.platform.governance.entity.GdprRequest;
import com.handmade.ecommerce.platform.governance.model.GdprRequestStatus;
import com.handmade.ecommerce.platform.governance.model.GdprRequestType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for GdprRequest
 */
@Repository
public interface GdprRequestRepository extends JpaRepository<GdprRequest, String> {

    List<GdprRequest> findByUserId(String userId);

    List<GdprRequest> findByRequestType(GdprRequestType requestType);

    List<GdprRequest> findByStatus(GdprRequestStatus status);

    List<GdprRequest> findByPolicyId(String policyId);
}
