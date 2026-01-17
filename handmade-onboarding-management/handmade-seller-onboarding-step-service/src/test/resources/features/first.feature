Feature: Tests the selleronboardingstep Workflow Service using a REST client. This is done only for the
first testcase. Selleronboardingstep service exists and is under test.
It helps to create a selleronboardingstep and manages the state of the selleronboardingstep as documented in states xml
Scenario: Create a new selleronboardingstep
Given that "flowName" equals "sellerOnboardingStepFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/selleronboardingstep" with payload
"""json
{
    "description": "Description"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.description" is "Description"

Scenario: Retrieve the selleronboardingstep that just got created
When I GET a REST request to URL "/selleronboardingstep/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the start event to the selleronboardingstep with comments
 Given that "comment" equals "Comment for start"
 And that "event" equals "start"
When I PATCH a REST request to URL "/selleronboardingstep/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the skip event to the selleronboardingstep with comments
 Given that "comment" equals "Comment for skip"
 And that "event" equals "skip"
When I PATCH a REST request to URL "/selleronboardingstep/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SKIPPED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to selleronboardingstep . This will err out.
When I PATCH a REST request to URL "/selleronboardingstep/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

