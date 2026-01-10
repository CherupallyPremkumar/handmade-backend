package com.handmade.ecommerce.platform.governance.repository;

import com.handmade.ecommerce.platform.governance.entity.PrivacyPolicyAck;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for PrivacyPolicyAck
 */
@Repository
public interface PrivacyPolicyAckRepository extends JpaRepository<PrivacyPolicyAck, String> {

    List<PrivacyPolicyAck> findByUserId(String userId);

    List<PrivacyPolicyAck> findByPolicyId(String policyId);

    List<PrivacyPolicyAck> findByTermsType(String termsType);
}
