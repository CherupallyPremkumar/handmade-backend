Feature: Testcase ID 
Tests the packtask Workflow Service using a REST client. Packtask service exists and is under test.
It helps to create a packtask and manages the state of the packtask as documented in states xml

Scenario: Create a new packtask
Given that "flowName" equals "HM_PACK_TASK_FLOW"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/packtask" with payload
"""json
{
    "pickTaskId": "pick-task-001",
    "orderId": "order-001",
    "packagingType": "BOX"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.packagingType" is "BOX"

Scenario: Retrieve the packtask that just got created
When I GET a REST request to URL "/packtask/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the start event to the packtask with comments
Given that "comment" equals "Comment for start"
And that "event" equals "start"
When I PATCH a REST request to URL "/packtask/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the complete event to the packtask with comments
Given that "comment" equals "Comment for complete"
And that "event" equals "complete"
When I PATCH a REST request to URL "/packtask/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
