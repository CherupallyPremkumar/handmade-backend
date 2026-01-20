Feature: Tests the limitcounter Workflow Service using a REST client. This is done only for the
first testcase. Limitcounter service exists and is under test.
It helps to create a limitcounter and manages the state of the limitcounter as documented in states xml
Scenario: Create a new limitcounter
Given that "flowName" equals "limitCounterFlow"
And that "initialState" equals "ACTIVE"
When I POST a REST request to URL "/limitcounter" with payload
"""json
{
    "entityId": "ENT-123",
    "limitKey": "DAILY_ORDER_LIMIT"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.entityId" is "ENT-123"

Scenario: Retrieve the limitcounter that just got created
When I GET a REST request to URL "/limitcounter/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the breach event to the limitcounter with comments
 Given that "comment" equals "Comment for breach"
 And that "event" equals "breach"
When I PATCH a REST request to URL "/limitcounter/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "BREACHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the reset event to the limitcounter with comments
 Given that "comment" equals "Comment for reset"
 And that "event" equals "reset"
When I PATCH a REST request to URL "/limitcounter/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RESET"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to limitcounter . This will err out.
When I PATCH a REST request to URL "/limitcounter/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

