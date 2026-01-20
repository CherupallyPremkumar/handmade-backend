Feature: Tests the inventoryadjustment Workflow Service using a REST client. This is done only for the
first testcase. Inventoryadjustment service exists and is under test.
It helps to create a inventoryadjustment and manages the state of the inventoryadjustment as documented in states xml
Scenario: Create a new inventoryadjustment
Given that "flowName" equals "inventoryAdjustmentFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/inventoryadjustment" with payload
"""json
{
    "adjustmentType": "CYCLE_COUNT",
    "sku": "SKU-123",
    "quantityChange": 10.0
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.sku" is "SKU-123"

Scenario: Retrieve the inventoryadjustment that just got created
When I GET a REST request to URL "/inventoryadjustment/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the submit event to the inventoryadjustment with comments
 Given that "comment" equals "Comment for submit"
 And that "event" equals "submit"
When I PATCH a REST request to URL "/inventoryadjustment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PENDING_APPROVAL"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the approve event to the inventoryadjustment with comments
 Given that "comment" equals "Comment for approve"
 And that "event" equals "approve"
When I PATCH a REST request to URL "/inventoryadjustment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the apply event to the inventoryadjustment with comments
 Given that "comment" equals "Comment for apply"
 And that "event" equals "apply"
When I PATCH a REST request to URL "/inventoryadjustment/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPLIED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"



Scenario: Send an invalid event to inventoryadjustment . This will err out.
When I PATCH a REST request to URL "/inventoryadjustment/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

