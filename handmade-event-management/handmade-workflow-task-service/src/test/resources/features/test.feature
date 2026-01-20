Feature: Testcase ID 
Tests the workflowtask Workflow Service using a REST client. Workflowtask service exists and is under test.
It helps to create a workflowtask and manages the state of the workflowtask as documented in states xml

Scenario: Create a new workflowtask
Given that "flowName" equals "workflowTaskFlow"
And that "initialState" equals "STARTED"
When I POST a REST request to URL "/workflowtask" with payload
"""json
{
    "workflowType": "PAYMENT_CAPTURE",
    "instanceId": "wf-inst-002",
    "stepName": "AUTHORIZE_PAYMENT"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.workflowType" is "PAYMENT_CAPTURE"

Scenario: Retrieve the workflowtask that just got created
When I GET a REST request to URL "/workflowtask/${id}"
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the complete event to the workflowtask with comments
Given that "comment" equals "Payment authorized successfully"
And that "event" equals "complete"
When I PATCH a REST request to URL "/workflowtask/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
