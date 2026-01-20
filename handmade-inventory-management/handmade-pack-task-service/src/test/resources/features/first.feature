Feature: Tests the packtask Workflow Service using a REST client. This is done only for the
first testcase. Packtask service exists and is under test.
It helps to create a packtask and manages the state of the packtask as documented in states xml

Scenario: Create a new packtask
Given that "flowName" equals "HM_PACK_TASK_FLOW"
And that "initialState" equals "PENDING"
When I POST a REST request to URL "/packtask" with payload
"""json
{
    "pickTaskId": "pick-task-first",
    "orderId": "order-first",
    "packagingType": "ENVELOPE"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.packagingType" is "ENVELOPE"

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

Scenario: Send the cancel event to the packtask with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/packtask/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send an invalid event to packtask. This will err out.
When I PATCH a REST request to URL "/packtask/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422
