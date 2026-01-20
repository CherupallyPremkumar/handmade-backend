Feature: Tests the settlementbatch Workflow Service using a REST client. This is done only for the
first testcase. Settlementbatch service exists and is under test.
It helps to create a settlementbatch and manages the state of the settlementbatch as documented in states xml
Scenario: Create a new settlementbatch
Given that "flowName" equals "settlementBatchFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/settlementbatch" with payload
"""json
{
    "batchReference": "BATCH-001",
    "startTime": "2024-01-01T10:00:00.000+00:00",
    "totalAmount": 1500.50
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.batchReference" is "BATCH-001"

Scenario: Retrieve the settlementbatch that just got created
When I GET a REST request to URL "/settlementbatch/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the process event to the settlementbatch with comments
 Given that "comment" equals "Comment for process"
 And that "event" equals "process"
When I PATCH a REST request to URL "/settlementbatch/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the fail event to the settlementbatch with comments
 Given that "comment" equals "Comment for fail"
 And that "event" equals "fail"
When I PATCH a REST request to URL "/settlementbatch/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the cancel event to the settlementbatch with comments
 Given that "comment" equals "Comment for cancel"
 And that "event" equals "cancel"
When I PATCH a REST request to URL "/settlementbatch/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to settlementbatch . This will err out.
When I PATCH a REST request to URL "/settlementbatch/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

