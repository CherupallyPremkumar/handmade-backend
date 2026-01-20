Feature: Tests the routeplan Workflow Service using a REST client. This is done only for the
first testcase. Routeplan service exists and is under test.
It helps to create a routeplan and manages the state of the routeplan as documented in states xml

Scenario: Create a new routeplan
Given that "flowName" equals "routePlanFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/routeplan" with payload
"""json
{
    "driverId": "driver-first-001",
    "routeDate": "2026-01-21",
    "vehicleId": "vehicle-first-001"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.driverId" is "driver-first-001"

Scenario: Retrieve the routeplan that just got created
When I GET a REST request to URL "/routeplan/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the cancel event to the routeplan with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/routeplan/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send an invalid event to routeplan. This will err out.
When I PATCH a REST request to URL "/routeplan/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422
