Feature: Full Seller Onboarding Lifecycle (Amazon-Grade)
  Tests the complete onboarding flow from DRAFT to APPROVED.

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

Scenario: Complete Identity Step
    # 1. Identity Step
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/submit-identity" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "IDENTITY_VERIFICATION_INITIATED"

Scenario: Complete Identity Verification
    # Simulate Stripe Webhook for Identity
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/identity-verified" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "IDENTITY_VERIFIED"

    # 2. Tax Step
    Scenario: Complete Tax Step
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/submit-tax" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "TAX_VERIFICATION_INITIATED"

    # Simulate Tax Webhook
    Scenario: Complete Tax Verification
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/tax-verified" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "TAX_VERIFIED"

    # 3. Bank Step
    Scenario: Complete Bank Step
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/submit-bank" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "BANK_VERIFICATION_INITIATED"

    # Simulate Bank Webhook
    Scenario: Complete Bank Verification
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/bank-verified" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "BANK_VERIFIED"

    # 4. Review Step
    Scenario: Complete Review Step
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/submit-review" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "REVIEW_PENDING"


    # 5. Admin Approval
    Scenario: Complete Admin Approval
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I PATCH a REST request to URL "/seller-onboarding/${id}/approve" with payload
    """
    {}
    """
    Then success is true
    And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"

    # 6. Verify Steps Detail
    Scenario: Verify Steps Detail
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I GET a REST request to URL "/seller-onboarding/${id}/steps"
    Then success is true
    And the REST response key "[0].stepName" is "IDENTITY"
    And the REST response key "[0].status" is "VERIFIED"
    And the REST response key "[1].stepName" is "TAX"
    And the REST response key "[1].status" is "VERIFIED"
    And the REST response key "[2].stepName" is "BANK"
    And the REST response key "[2].status" is "VERIFIED"
