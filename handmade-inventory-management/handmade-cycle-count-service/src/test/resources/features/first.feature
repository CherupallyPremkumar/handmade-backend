Feature: Tests the cyclecount Workflow Service using a REST client. This is done only for the
first testcase. Cyclecount service exists and is under test.
It helps to create a cyclecount and manages the state of the cyclecount as documented in states xml
Scenario: Create a new cyclecount
Given that "flowName" equals "cycleCountFlow"
And that "initialState" equals "PLANNED"
When I POST a REST request to URL "/cyclecount" with payload
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

Scenario: Retrieve the cyclecount that just got created
When I GET a REST request to URL "/cyclecount/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the start event to the cyclecount with comments
 Given that "comment" equals "Comment for start"
 And that "event" equals "start"
When I PATCH a REST request to URL "/cyclecount/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the complete event to the cyclecount with comments
 Given that "comment" equals "Comment for complete"
 And that "event" equals "complete"
When I PATCH a REST request to URL "/cyclecount/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the detectVariance event to the cyclecount with comments
 Given that "comment" equals "Comment for detectVariance"
 And that "event" equals "detectVariance"
When I PATCH a REST request to URL "/cyclecount/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VARIANCE_FOUND"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the approveVariance event to the cyclecount with comments
 Given that "comment" equals "Comment for approveVariance"
 And that "event" equals "approveVariance"
When I PATCH a REST request to URL "/cyclecount/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the adjust event to the cyclecount with comments
 Given that "comment" equals "Comment for adjust"
 And that "event" equals "adjust"
When I PATCH a REST request to URL "/cyclecount/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ADJUSTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to cyclecount . This will err out.
When I PATCH a REST request to URL "/cyclecount/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

