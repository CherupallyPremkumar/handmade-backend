Feature: Tests the risk Workflow Service using a REST client. This is done only for the
first testcase. Risk service exists and is under test.
It helps to create a risk and manages the state of the risk as documented in states xml
Scenario: Create a new risk
Given that "flowName" equals "RISK_SIGNAL_FLOW"
And that "initialState" equals "ANALYZING"
When I POST a REST request to URL "/risk" with payload
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

Scenario: Retrieve the risk that just got created
When I GET a REST request to URL "/risk/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the suppressSignal event to the risk with comments
 Given that "comment" equals "Comment for suppressSignal"
 And that "event" equals "suppressSignal"
When I PATCH a REST request to URL "/risk/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SUPPRESSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to risk . This will err out.
When I PATCH a REST request to URL "/risk/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

