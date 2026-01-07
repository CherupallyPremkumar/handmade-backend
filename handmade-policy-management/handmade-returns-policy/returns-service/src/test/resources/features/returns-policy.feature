Feature: Returns Policy Management
  As a Platform Owner
  I want to manage returns policies
  So that I can control return and refund rules

  Scenario: Create a new returns policy
    When I POST a request to service "returnPolicyManager" and operation "create" with payload
    """
    {
      "policyVersion": "2024.1-US-ALL",
      "countryCode": "US",
      "returnWindowDays": 30,
      "restockingFeePercentage": 10
    }
    """
    Then the response contains key "id"
    And the response key "returnWindowDays" is "30"

  Scenario: Add return rule
    When I POST a request to service "returnPolicyManager" and operation "addRule" with payload
    """
    {
      "policyId": "RETURN_POLICY_001",
      "ruleName": "DamagedItemReturn",
      "violationType": "ITEM_DAMAGED",
      "decision": "APPROVE",
      "required": true
    }
    """
    Then the response contains key "rules"
