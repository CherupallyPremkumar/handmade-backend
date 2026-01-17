Feature: Testcase ID 
Tests the supportcase Workflow Service using a REST client. Supportcase service exists and is under test.
It helps to create a supportcase and manages the state of the supportcase as documented in states xml
Scenario: Create a new supportcase
Given that "flowName" equals "supportCaseFlow"
And that "initialState" equals "OPEN"
When I POST a REST request to URL "/supportcase" with payload
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

Scenario: Retrieve the supportcase that just got created
When I GET a REST request to URL "/supportcase/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the assign event to the supportcase with comments
Given that "comment" equals "Comment for assign"
And that "event" equals "assign"
When I PATCH a REST request to URL "/supportcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the investigate event to the supportcase with comments
Given that "comment" equals "Comment for investigate"
And that "event" equals "investigate"
When I PATCH a REST request to URL "/supportcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the awaitCustomer event to the supportcase with comments
Given that "comment" equals "Comment for awaitCustomer"
And that "event" equals "awaitCustomer"
When I PATCH a REST request to URL "/supportcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "AWAITING_CUSTOMER"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the supportcase Workflow Service using a REST client. Supportcase service exists and is under test.
It helps to create a supportcase and manages the state of the supportcase as documented in states xml
Scenario: Create a new supportcase
Given that "flowName" equals "supportCaseFlow"
And that "initialState" equals "OPEN"
When I POST a REST request to URL "/supportcase" with payload
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

Scenario: Retrieve the supportcase that just got created
When I GET a REST request to URL "/supportcase/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the assign event to the supportcase with comments
Given that "comment" equals "Comment for assign"
And that "event" equals "assign"
When I PATCH a REST request to URL "/supportcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the investigate event to the supportcase with comments
Given that "comment" equals "Comment for investigate"
And that "event" equals "investigate"
When I PATCH a REST request to URL "/supportcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the escalate event to the supportcase with comments
Given that "comment" equals "Comment for escalate"
And that "event" equals "escalate"
When I PATCH a REST request to URL "/supportcase/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ESCALATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
