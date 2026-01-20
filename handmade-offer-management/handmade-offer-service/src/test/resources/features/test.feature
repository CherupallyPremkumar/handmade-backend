Feature: Testcase ID 
Tests the offer Workflow Service using a REST client. Offer service exists and is under test.
It helps to create a offer and manages the state of the offer as documented in states xml

# --- Cancel Path (from scheduled) ---

Scenario: Create a new offer for cancel path
Given that "flowName" equals "offerFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/offer" with payload
"""json
{
    "platformId": "platform-001",
    "productId": "product-001",
    "sellerId": "seller-001",
    "sellerSku": "SKU-001",
    "conditionType": "New",
    "fulfillmentChannel": "Merchant",
    "availableQuantity": 100
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.sellerSku" is "SKU-001"

Scenario: Retrieve the offer that just got created
When I GET a REST request to URL "/offer/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the submit event to the offer
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

Scenario: Send the approve event to the offer
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

Scenario: Send the schedule event to the offer
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

Scenario: Send the cancel event to cancel scheduled offer
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

# --- Expire Path ---

Scenario: Create a new offer for expire path
Given that "flowName" equals "offerFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/offer" with payload
"""json
{
    "platformId": "platform-002",
    "productId": "product-002",
    "sellerId": "seller-002",
    "sellerSku": "SKU-002",
    "conditionType": "New",
    "fulfillmentChannel": "FBA",
    "availableQuantity": 50
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"

Scenario: Submit and approve offer for expire path
Given that "event" equals "submit"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{ "comment": "Submit for expire path" }
"""
Then the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
Given that "event" equals "approve"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{ "comment": "Approve for expire path" }
"""
Then the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"

Scenario: Activate and expire offer
Given that "event" equals "activate"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{ "comment": "Activate offer" }
"""
Then the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
Given that "event" equals "expire"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{ "comment": "Expire offer" }
"""
Then the REST response key "mutatedEntity.currentState.stateId" is "EXPIRED"

# --- Pause/Resume Path ---

Scenario: Create a new offer for pause path
Given that "flowName" equals "offerFlow"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/offer" with payload
"""json
{
    "platformId": "platform-003",
    "productId": "product-003",
    "sellerId": "seller-003",
    "sellerSku": "SKU-003",
    "conditionType": "Used",
    "fulfillmentChannel": "Merchant",
    "availableQuantity": 25
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"

Scenario: Submit approve and activate for pause path
Given that "event" equals "submit"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{ "comment": "Submit for pause" }
"""
Then the REST response key "mutatedEntity.currentState.stateId" is "REVIEW"
Given that "event" equals "approve"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{ "comment": "Approve for pause" }
"""
Then the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
Given that "event" equals "activate"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{ "comment": "Activate for pause" }
"""
Then the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"

Scenario: Pause and resume offer
Given that "event" equals "pause"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{ "comment": "Pause the offer" }
"""
Then the REST response key "mutatedEntity.currentState.stateId" is "PAUSED"
Given that "event" equals "resume"
When I PATCH a REST request to URL "/offer/${id}/${event}" with payload
"""json
{ "comment": "Resume the offer" }
"""
Then the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
