Feature: Pricing Policy Management
  As a Platform Owner
  I want to manage pricing policies
  So that I can control marketplace pricing rules

  Scenario: Create a new pricing policy in DRAFT state
    When I POST a request to service "pricingPolicyManager" and operation "create" with payload
    """
    {
      "policyVersion": "2024.1-US-ELECTRONICS",
      "countryCode": "US",
      "category": "ELECTRONICS",
      "effectiveDate": "2024-01-01",
      "minPriceCents": 100,
      "maxPriceCents": 1000000
    }
    """
    Then the response contains key "id"
    And the response key "policyVersion" is "2024.1-US-ELECTRONICS"
    And the response key "currentState.stateId" is "DRAFT"

  Scenario: Add a pricing rule to a DRAFT policy
    When I POST a request to service "pricingPolicyManager" and operation "addRule" with payload
    """
    {
      "policyId": "PRICING_POLICY_001",
      "ruleName": "MinPriceCheck",
      "violationType": "PRICE_TOO_LOW",
      "required": true,
      "thresholdValue": 1.00
    }
    """
    Then the response contains key "rules"

  Scenario: Approve a pricing policy
    When I POST a request to service "pricingPolicyManager" and operation "approve" with payload
    """
    {
      "policyId": "PRICING_POLICY_001",
      "complianceOfficerId": "OFFICER_123"
    }
    """
    Then the response key "approvedBy" is "OFFICER_123"
    And the response key "currentState.stateId" is "ACTIVE"
