Feature: Tests the offer Workflow Service using a REST client. This is done only for the
first testcase. Offer service exists and is under test.
It helps to create a offer and manages the state of the offer as documented in states xml

Scenario: Create a new offer
Given that "flowName" equals "offerFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/offer" with payload
"""json
{
    "platformId": "platform-first",
    "productId": "product-first",
    "sellerId": "seller-first",
    "sellerSku": "SKU-FIRST",
    "conditionType": "New",
    "fulfillmentChannel": "Merchant",
    "availableQuantity": 10
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.sellerSku" is "SKU-FIRST"

Scenario: Retrieve the offer that just got created
When I GET a REST request to URL "/offer/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the submit event to the offer with comments
Given that "comment" equals "Comment for submit"
And that "event" equals "submit"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the approve event to the offer with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the schedule event to the offer with comments
Given that "comment" equals "Comment for schedule"
And that "event" equals "schedule"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SCHEDULED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the cancel event to the offer with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send an invalid event to offer. This will err out.
When I PATCH a REST request to URL "/offer/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422
