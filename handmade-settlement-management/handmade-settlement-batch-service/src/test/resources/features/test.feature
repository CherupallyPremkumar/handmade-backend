Feature: Testcase ID 
Tests the settlementbatch Workflow Service using a REST client. Settlementbatch service exists and is under test.
It helps to create a settlementbatch and manages the state of the settlementbatch as documented in states xml
Scenario: Create a new settlementbatch
Given that "flowName" equals "settlementBatchFlow"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/settlementbatch" with payload
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
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PROCESSING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the complete event to the settlementbatch with comments
Given that "comment" equals "Comment for complete"
And that "event" equals "complete"
When I PATCH a REST request to URL "/settlementbatch/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the reconcile event to the settlementbatch with comments
Given that "comment" equals "Comment for reconcile"
And that "event" equals "reconcile"
When I PATCH a REST request to URL "/settlementbatch/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RECONCILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
