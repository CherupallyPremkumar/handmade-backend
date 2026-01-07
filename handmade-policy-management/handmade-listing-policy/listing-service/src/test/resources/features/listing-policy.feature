Feature: Listing Policy Management
  As a Platform Owner
  I want to manage listing policies
  So that I can control product listing standards

  Scenario: Create a new listing policy
    When I POST a request to service "listingPolicyManager" and operation "create" with payload
    """
    {
      "policyVersion": "2024.1-US",
      "countryCode": "US",
      "maxImagesPerListing": 10,
      "minImagesPerListing": 1,
      "maxDescriptionLength": 5000
    }
    """
    Then the response contains key "id"
    And the response key "currentState.stateId" is "DRAFT"

  Scenario: Add listing rule to policy
    When I POST a request to service "listingPolicyManager" and operation "addRule" with payload
    """
    {
      "policyId": "LISTING_POLICY_001",
      "ruleName": "ImageCountCheck",
      "violationType": "INSUFFICIENT_IMAGES",
      "required": true
    }
    """
    Then the response contains key "rules"
