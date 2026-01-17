Feature: Testcase ID 
Tests the shippinglabel Workflow Service using a REST client. Shippinglabel service exists and is under test.
It helps to create a shippinglabel and manages the state of the shippinglabel as documented in states xml
Scenario: Create a new shippinglabel
Given that "flowName" equals "shippingLabelFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/shippinglabel" with payload
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

Scenario: Retrieve the shippinglabel that just got created
When I GET a REST request to URL "/shippinglabel/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the print event to the shippinglabel with comments
Given that "comment" equals "Comment for print"
And that "event" equals "print"
When I PATCH a REST request to URL "/shippinglabel/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PRINTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the use event to the shippinglabel with comments
Given that "comment" equals "Comment for use"
And that "event" equals "use"
When I PATCH a REST request to URL "/shippinglabel/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "USED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the shippinglabel Workflow Service using a REST client. Shippinglabel service exists and is under test.
It helps to create a shippinglabel and manages the state of the shippinglabel as documented in states xml
Scenario: Create a new shippinglabel
Given that "flowName" equals "shippingLabelFlow"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/shippinglabel" with payload
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

Scenario: Retrieve the shippinglabel that just got created
When I GET a REST request to URL "/shippinglabel/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the void event to the shippinglabel with comments
Given that "comment" equals "Comment for void"
And that "event" equals "void"
When I PATCH a REST request to URL "/shippinglabel/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VOIDED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
