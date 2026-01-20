Feature: Tests the workflowtask Workflow Service using a REST client. This is done only for the
first testcase. Workflowtask service exists and is under test.
It helps to create a workflowtask and manages the state of the workflowtask as documented in states xml

Scenario: Create a new workflowtask
Given that "flowName" equals "workflowTaskFlow"
And that "initialState" equals "STARTED"
When I POST a REST request to URL "/workflowtask" with payload
"""json
{
    "workflowType": "ORDER_PROCESSING",
    "instanceId": "wf-inst-001",
    "stepName": "VALIDATE_INVENTORY",
    "inputData": "{\"orderId\": \"ORD-123\"}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.workflowType" is "ORDER_PROCESSING"

Scenario: Retrieve the workflowtask that just got created
When I GET a REST request to URL "/workflowtask/${id}"
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the fail event to the workflowtask with comments
Given that "comment" equals "Inventory validation failed"
And that "event" equals "fail"
When I PATCH a REST request to URL "/workflowtask/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"

Scenario: Send an invalid event to workflowtask. This will err out.
When I PATCH a REST request to URL "/workflowtask/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422
