Feature: Tests the analytics Workflow Service using a REST client. This is done only for the
  first testcase. Analytics service exists and is under test.
  It helps to create a analytics and manages the state of the analytics as documented in states xml

  Scenario: Create a new analytics
    Given that "flowName" equals "METRIC_DEFINITION_FLOW"
    And that "initialState" equals "DRAFT"
    When I POST a REST request to URL "/analytics" with payload
"""json
{
    "metricCode": "TEST_METRIC",
    "name": "Test Metric Name",
    "description": "Description"
}
"""
    Then the REST response contains key "mutatedEntity"
    And store "$.payload.mutatedEntity.id" from response to "id"
    And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
    And the REST response key "mutatedEntity.description" is "Description"

  Scenario: Retrieve the analytics that just got created
    When I GET a REST request to URL "/analytics/${id}"
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

  Scenario: Send the activateMetric event to the analytics with comments
    Given that "comment" equals "Comment for activateMetric"
    And that "event" equals "activateMetric"
    When I PATCH a REST request to URL "/analytics/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

  Scenario: Send the pauseMetric event to the analytics with comments
    Given that "comment" equals "Comment for pauseMetric"
    And that "event" equals "pauseMetric"
    When I PATCH a REST request to URL "/analytics/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
    Then the REST response contains key "mutatedEntity"
    And the REST response key "mutatedEntity.id" is "${id}"
    And the REST response key "mutatedEntity.currentState.stateId" is "PAUSED"
    And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"


  Scenario: Send an invalid event to analytics . This will err out.
    When I PATCH a REST request to URL "/analytics/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
    Then the REST response does not contain key "mutatedEntity"
    And the http status code is 422

