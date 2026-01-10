Feature: 3-Phase Seller Onboarding Orchestration (Amazon-Grade)
  Tests the refined Init -> Resume -> Confirm flow with specialized contexts.

  Scenario: Complete Happy Path Phased Onboarding
    Given that "tenant" equals "tenant0"
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I construct a REST request with header "x-chenile-eid" and value "E1"
    # Phase 0: Create the Case
    And I POST a REST request to URL "/seller-onboarding" with payload
    """
    {
      "email": "verification-test@example.com",
      "businessName": "Specialized Context Test",
      "businessType": "ECOMMERCE",
      "country": "US",
      "contactPerson": "Verifier",
      "phoneNumber": "1234567890",
      "termsAccepted": true
    }
    """
    Then success is true
    And store "$.payload.mutatedEntity.id" from response to "id"
    And the REST response key "mutatedEntity.currentState.stateId" is "DRAFT"

    # Phase 1: Initiation (Start Onboarding)
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/start-onboarding" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "INITIALIZED"

    # Phase 2: Resumption (Identity Verified - Simulated Webhook)
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/identity-verified" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "VERIFIED_PENDING_DOCS"

    # Phase 3: Confirmation (Final Account Creation)
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/confirm-onboarding" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
