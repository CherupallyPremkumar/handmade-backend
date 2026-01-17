Feature: Testcase ID 
Tests the deliveryattempt Workflow Service using a REST client. Deliveryattempt service exists and is under test.
It helps to create a deliveryattempt and manages the state of the deliveryattempt as documented in states xml
Scenario: Create a new deliveryattempt
Given that "flowName" equals "deliveryAttemptFlow"
And that "initialState" equals "SCHEDULED"
When I POST a REST request to URL "/deliveryattempt" with payload
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

Scenario: Retrieve the deliveryattempt that just got created
When I GET a REST request to URL "/deliveryattempt/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the start event to the deliveryattempt with comments
Given that "comment" equals "Comment for start"
And that "event" equals "start"
When I PATCH a REST request to URL "/deliveryattempt/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the fail event to the deliveryattempt with comments
Given that "comment" equals "Comment for fail"
And that "event" equals "fail"
When I PATCH a REST request to URL "/deliveryattempt/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FAILED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the deliveryattempt Workflow Service using a REST client. Deliveryattempt service exists and is under test.
It helps to create a deliveryattempt and manages the state of the deliveryattempt as documented in states xml
Scenario: Create a new deliveryattempt
Given that "flowName" equals "deliveryAttemptFlow"
And that "initialState" equals "SCHEDULED"
When I POST a REST request to URL "/deliveryattempt" with payload
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

Scenario: Retrieve the deliveryattempt that just got created
When I GET a REST request to URL "/deliveryattempt/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the start event to the deliveryattempt with comments
Given that "comment" equals "Comment for start"
And that "event" equals "start"
When I PATCH a REST request to URL "/deliveryattempt/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the succeed event to the deliveryattempt with comments
Given that "comment" equals "Comment for succeed"
And that "event" equals "succeed"
When I PATCH a REST request to URL "/deliveryattempt/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SUCCESSFUL"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
