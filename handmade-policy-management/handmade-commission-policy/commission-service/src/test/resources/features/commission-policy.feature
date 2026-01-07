Feature: Commission Policy Management
  As a Platform Owner
  I want to manage commission policies
  So that I can control marketplace fees

  Scenario: Create a new commission policy in DRAFT state
    When I POST a request to service "commissionPolicyManager" and operation "create" with payload
    """
    {
      "policyVersion": "2024.1-US-ALL",
      "countryCode": "US",
      "effectiveDate": "2024-01-01"
    }
    """
    Then the response contains key "id"
    And the response key "policyVersion" is "2024.1-US-ALL"
    And the response key "currentState.stateId" is "DRAFT"

  Scenario: Add a commission rule to a DRAFT policy
    When I POST a request to service "commissionPolicyManager" and operation "addRule" with payload
    """
    {
      "policyId": "COMM_POLICY_001",
      "ruleName": "ReferralFee",
      "feeType": "REFERRAL_FEE",
      "thresholdValue": 0.10,
      "required": true
    }
    """
    Then the response contains key "rules"
    And the response key "rules[0].ruleName" is "ReferralFee"

  Scenario: Cannot add rules to ACTIVE policy
    When I POST a request to service "commissionPolicyManager" and operation "addRule" with payload
    """
    {
      "policyId": "COMM_POLICY_ACTIVE_001",
      "ruleName": "NewFee",
      "feeType": "PROCESSING_FEE",
      "thresholdValue": 0.05,
      "required": true
    }
    """
    Then an exception is thrown with message code 1001
