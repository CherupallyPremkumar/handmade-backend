Feature: Tests the risksignal Workflow Service using a REST client. This is done only for the
first testcase. Risksignal service exists and is under test.
It helps to create a risksignal and manages the state of the risksignal as documented in states xml
Scenario: Create a new risksignal
Given that "flowName" equals "riskSignalFlow"
And that "initialState" equals "DETECTED"
When I POST a REST request to URL "/risksignal" with payload
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

Scenario: Retrieve the risksignal that just got created
When I GET a REST request to URL "/risksignal/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the review event to the risksignal with comments
 Given that "comment" equals "Comment for review"
 And that "event" equals "review"
When I PATCH a REST request to URL "/risksignal/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the confirm event to the risksignal with comments
 Given that "comment" equals "Comment for confirm"
 And that "event" equals "confirm"
When I PATCH a REST request to URL "/risksignal/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CONFIRMED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the mitigate event to the risksignal with comments
 Given that "comment" equals "Comment for mitigate"
 And that "event" equals "mitigate"
When I PATCH a REST request to URL "/risksignal/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "MITIGATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to risksignal . This will err out.
When I PATCH a REST request to URL "/risksignal/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

