Feature: Testcase ID 
Tests the compliancedocument Workflow Service using a REST client. Compliancedocument service exists and is under test.
It helps to create a compliancedocument and manages the state of the compliancedocument as documented in states xml

Scenario: Create a new compliancedocument
Given that "flowName" equals "complianceDocumentFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/compliancedocument" with payload
"""json
{
    "entityId": "entity-test-002",
    "entityType": "SELLER",
    "docType": "VAT_CERTIFICATE",
    "fileUri": "https://example.com/docs/vat.pdf"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.entityId" is "entity-test-002"

Scenario: Retrieve the compliancedocument that just got created
When I GET a REST request to URL "/compliancedocument/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the review event to the compliancedocument with comments
Given that "comment" equals "Comment for review"
And that "event" equals "review"
When I PATCH a REST request to URL "/compliancedocument/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the approve event to the compliancedocument with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/compliancedocument/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "APPROVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the revoke event to the compliancedocument with comments
Given that "comment" equals "Comment for revoke"
And that "event" equals "revoke"
When I PATCH a REST request to URL "/compliancedocument/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REVOKED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Create another compliancedocument for rejection test
Given that "flowName" equals "complianceDocumentFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/compliancedocument" with payload
"""json
{
    "entityId": "entity-test-003",
    "entityType": "PRODUCT",
    "docType": "PRODUCT_SAFETY_DOC",
    "fileUri": "https://example.com/docs/safety.pdf"
}
"""
Then the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id2"
And the REST response key "mutatedEntity.currentState.stateId" is "SUBMITTED"

Scenario: Move the second document to UNDER_REVIEW
Given that "event" equals "review"
When I PATCH a REST request to URL "/compliancedocument/${id2}/${event}" with payload
"""json
{
    "comment": "Moving to review"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"

Scenario: Reject the second document
Given that "event" equals "reject"
When I PATCH a REST request to URL "/compliancedocument/${id2}/${event}" with payload
"""json
{
    "comment": "Rejecting for invalid data"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
