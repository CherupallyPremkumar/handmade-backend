Feature: Testcase ID 
Tests the pricealert Workflow Service using a REST client. Pricealert service exists and is under test.
It helps to create a pricealert and manages the state of the pricealert as documented in states xml
Scenario: Create a new pricealert
Given that "flowName" equals "priceAlertFlow"
And that "initialState" equals "ACTIVE"
When I POST a REST request to URL "/pricealert" with payload
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

Scenario: Retrieve the pricealert that just got created
When I GET a REST request to URL "/pricealert/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the trigger event to the pricealert with comments
Given that "comment" equals "Comment for trigger"
And that "event" equals "trigger"
When I PATCH a REST request to URL "/pricealert/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "TRIGGERED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the notify event to the pricealert with comments
Given that "comment" equals "Comment for notify"
And that "event" equals "notify"
When I PATCH a REST request to URL "/pricealert/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "NOTIFIED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the acknowledge event to the pricealert with comments
Given that "comment" equals "Comment for acknowledge"
And that "event" equals "acknowledge"
When I PATCH a REST request to URL "/pricealert/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACKNOWLEDGED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the pricealert Workflow Service using a REST client. Pricealert service exists and is under test.
It helps to create a pricealert and manages the state of the pricealert as documented in states xml
Scenario: Create a new pricealert
Given that "flowName" equals "priceAlertFlow"
And that "initialState" equals "ACTIVE"
When I POST a REST request to URL "/pricealert" with payload
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

Scenario: Retrieve the pricealert that just got created
When I GET a REST request to URL "/pricealert/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the pause event to the pricealert with comments
Given that "comment" equals "Comment for pause"
And that "event" equals "pause"
When I PATCH a REST request to URL "/pricealert/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PAUSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
