Feature: Full Seller Onboarding Lifecycle
  Tests the complete onboarding flow from DRAFT to COMPLETED.

  Scenario: Complete Happy Path Onboarding
    Given that "tenant" equals "tenant0"
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I construct a REST request with header "x-chenile-eid" and value "E1"
    And I POST a REST request to URL "/seller-onboarding" with payload
    """
    {
      "email": "amazon@example.com",
      "businessName": "Amazon",
      "businessType": "ECOMMERCE",
      "country": "US",
      "contactPerson": "John Doe",
      "phoneNumber": "1234567890",
      "termsAccepted": true
    }
    """
    Then success is true
    And store "$.payload.mutatedEntity.id" from response to "id"
    And the REST response key "mutatedEntity.currentState.stateId" is "DRAFT"

  Scenario: Start Onboarding Step
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/start-onboarding" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "INITIALIZED"

  Scenario: Complete Identity Verification
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/identity-verified" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "VERIFIED_PENDING_DOCS"

  Scenario: Submit Documents
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/submit-docs" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "VERIFIED_PENDING_DOCS"

  Scenario: Confirm Onboarding
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/confirm-onboarding" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
