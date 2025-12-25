Feature: Platform Management - State Transitions
  
  Scenario: Create a new platform
    Given that "flowName" equals "PLATFORM_FLOW"
    And that "initialState" equals "BOOTSTRAPPING"
    When I POST a REST request to URL "/platform" with payload
    """json
    {
        "name": "Handmade Platform",
        "description": "Main e-commerce platform"
    }
    """
    Then the REST response contains key "mutatedEntity"
    And store "$.payload.mutatedEntity.id" from response to "platformId"
    And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
    And the REST response key "mutatedEntity.name" is "Handmade Platform"
  
  Scenario: Retrieve the platform that just got created
    When I GET a REST request to URL "/platform/${platformId}"
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${platformId}"
    And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"
  
  Scenario: Activate the platform
    Given that "expectedState" equals "ACTIVE"
    When I PATCH a REST request to URL "/platform/${platformId}/activate" with payload
    """json
    {
        "activatedBy": "admin@handmade.com"
    }
    """
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.currentState.stateId" is "${expectedState}"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
  
  Scenario: Verify platform is active
    When I GET a REST request to URL "/platform/${platformId}"
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
    And the REST response key "mutatedEntity.suspended" is "false"
  
  Scenario: Suspend the platform
    Given that "expectedState" equals "SUSPENDED"
    When I PATCH a REST request to URL "/platform/${platformId}/suspend" with payload
    """json
    {
        "suspensionReason": "Maintenance required",
        "suspendedBy": "admin@handmade.com"
    }
    """
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.currentState.stateId" is "${expectedState}"
    And the REST response key "mutatedEntity.suspensionReason" is "Maintenance required"
  
  Scenario: Reactivate the platform
    Given that "expectedState" equals "ACTIVE"
    When I PATCH a REST request to URL "/platform/${platformId}/reactivate" with payload
    """json
    {
        "reactivatedBy": "admin@handmade.com"
    }
    """
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.currentState.stateId" is "${expectedState}"
    And the REST response key "mutatedEntity.suspended" is "false"
  
  Scenario: Delete the platform
    Given that "expectedState" equals "DELETED"
    When I PATCH a REST request to URL "/platform/${platformId}/delete" with payload
    """json
    {
        "deletionReason": "Test cleanup",
        "deletedBy": "admin@handmade.com"
    }
    """
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.currentState.stateId" is "${expectedState}"
    And the REST response key "mutatedEntity.deleted" is "true"
