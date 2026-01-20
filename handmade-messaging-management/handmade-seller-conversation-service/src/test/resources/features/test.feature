Feature: Testcase ID 
Tests the sellerconversation Workflow Service using a REST client. Sellerconversation service exists and is under test.
It helps to create a sellerconversation and manages the state of the sellerconversation as documented in states xml

Scenario: Create a new sellerconversation
Given that "flowName" equals "sellerConversationFlow"
And that "initialState" equals "OPEN"
When I POST a REST request to URL "/sellerconversation" with payload
"""json
{
    "customerId": "cust-001",
    "sellerId": "seller-001",
    "subject": "Missing items in my order",
    "productId": "prod-123"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.subject" is "Missing items in my order"

Scenario: Retrieve the sellerconversation that just got created
When I GET a REST request to URL "/sellerconversation/${id}"
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the assign event to the sellerconversation
Given that "event" equals "assign"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "assignedTo": "support-agent-01",
    "comment": "Assigning to support agent"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"

Scenario: Send the respond event to the sellerconversation
Given that "event" equals "respond"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "message": "We are looking into it.",
    "comment": "Responding to customer"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "AWAITING_CUSTOMER"

Scenario: Send the customerReply event to the sellerconversation
Given that "event" equals "customerReply"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "message": "Thank you, please hurry.",
    "comment": "Customer replied"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"

Scenario: Send the escalate event to the sellerconversation
Given that "event" equals "escalate"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "Escalating to manager"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "ESCALATED"

Scenario: Send the resolve event to the sellerconversation
Given that "event" equals "resolve"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "Issue resolved"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"

Scenario: Send the close event to the sellerconversation
Given that "event" equals "close"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "Closing conversation"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "CLOSED"

Scenario: Send the reopen event to the sellerconversation
Given that "event" equals "reopen"
When I PATCH a REST request to URL "/sellerconversation/${id}/${event}" with payload
"""json
{
    "comment": "Reopening at customer request"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "OPEN"
