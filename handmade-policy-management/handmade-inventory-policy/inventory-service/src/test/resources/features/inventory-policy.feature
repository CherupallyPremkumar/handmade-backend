Feature: Inventory Policy Management
  As a Platform Owner
  I want to manage inventory policies
  So that I can control stock management rules

  Scenario: Create a new inventory policy
    When I POST a request to service "inventoryPolicyManager" and operation "create" with payload
    """
    {
      "policyVersion": "2024.1-US",
      "countryCode": "US",
      "minStockLevel": 5,
      "maxStockLevel": 10000
    }
    """
    Then the response contains key "id"
    And the response key "currentState.stateId" is "DRAFT"

  Scenario: Add inventory rule
    When I POST a request to service "inventoryPolicyManager" and operation "addRule" with payload
    """
    {
      "policyId": "INVENTORY_POLICY_001",
      "ruleName": "LowStockAlert",
      "violationType": "STOCK_TOO_LOW",
      "thresholdValue": 10,
      "required": true
    }
    """
    Then the response contains key "rules"
