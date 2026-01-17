Feature: Testcase ID 
Tests the pricingruledefinition Workflow Service using a REST client. Pricingruledefinition service exists and is under test.
It helps to create a pricingruledefinition and manages the state of the pricingruledefinition as documented in states xml
Scenario: Create a new pricingruledefinition
Given that "flowName" equals "pricingRuleDefinitionFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/pricingruledefinition" with payload
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

Scenario: Retrieve the pricingruledefinition that just got created
When I GET a REST request to URL "/pricingruledefinition/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the submit event to the pricingruledefinition with comments
Given that "comment" equals "Comment for submit"
And that "event" equals "submit"
When I PATCH a REST request to URL "/pricingruledefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the approve event to the pricingruledefinition with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/pricingruledefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the activate event to the pricingruledefinition with comments
Given that "comment" equals "Comment for activate"
And that "event" equals "activate"
When I PATCH a REST request to URL "/pricingruledefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the deactivate event to the pricingruledefinition with comments
Given that "comment" equals "Comment for deactivate"
And that "event" equals "deactivate"
When I PATCH a REST request to URL "/pricingruledefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the deprecate event to the pricingruledefinition with comments
Given that "comment" equals "Comment for deprecate"
And that "event" equals "deprecate"
When I PATCH a REST request to URL "/pricingruledefinition/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DEPRECATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
