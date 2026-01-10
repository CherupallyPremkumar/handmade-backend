Feature: Seller Onboarding Lifecycle
  As a prospective seller
  I want to go through the onboarding process
  So that I can eventually list products on the Handmade platform

  Scenario: Successful onboarding from Draft to Active
    Given a new seller registration for "artisan@example.com" in "US"
    When I lock the policy version "v1" for the case
    Then the case state should be "POLICY_LOCKED"
    
    When I start the application
    Then the case state should be "APPLICATION_IN_PROGRESS"
    
    When I update the following identity details:
      | firstName | John |
      | lastName  | Doe  |
    And I submit the application
    Then the case should transition to "IDENTITY_VERIFICATION" stage
    
    When identity is verified for the case
    And I submit the application
    Then the case state should be "PENDING_APPROVAL"
    
    When the admin approves the seller
    Then the case state should be "APPROVED"
    
    When the seller is provisioned
    Then the case state should be "ACTIVE"
