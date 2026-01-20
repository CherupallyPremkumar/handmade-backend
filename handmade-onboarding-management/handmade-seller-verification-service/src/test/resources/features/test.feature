Feature: Testcase ID 
Tests the sellerverification Workflow Service using a REST client. Sellerverification service exists and is under test.
It helps to create a sellerverification and manages the state of the sellerverification as documented in states xml

Scenario: Create a new sellerverification
Given that "flowName" equals "HM_SELLER_VERIFICATION_FLOW"
And that "initialState" equals "INITIATED"
When I POST a REST request to URL "/sellerverification" with payload
"""json
{
    "sellerId": "SEL-FLOW-001",
    "verificationType": "ADDRESS",
    "initiatedAt": "2024-01-01T00:00:00.000+00:00"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And store "$.payload.mutatedEntity.id" from response to "id"
And the REST response key "mutatedEntity.currentState.stateId" is "${initialState}"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "currentState"
And the REST response key "mutatedEntity.sellerId" is "SEL-FLOW-001"

Scenario: Retrieve the sellerverification that just got created
When I GET a REST request to URL "/sellerverification/${id}"
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Submit the verification
Given that "event" equals "submit"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "Submitting address proof"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "PENDING"

Scenario: Verify the submission
Given that "event" equals "verify"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "Starting automated verification"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFYING"

Scenario: Approve the verification
Given that "event" equals "approve"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "verifiedBy": "system",
    "comment": "Verified successfully"
}
"""
Then success is true
And the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFIED"

Scenario: Create for manual review test
Given that "flowName" equals "HM_SELLER_VERIFICATION_FLOW"
And that "initialState" equals "INITIATED"
When I POST a REST request to URL "/sellerverification" with payload
"""json
{
    "sellerId": "SEL-FLOW-002",
    "verificationType": "BUSINESS_LICENSE",
    "initiatedAt": "2024-01-01T00:00:00.000+00:00"
}
"""
Then success is true
And store "$.payload.mutatedEntity.id" from response to "id2"

Scenario: Transition to manual review
When I PATCH a REST request to URL "/sellerverification/${id2}/submit" with payload
"""json
{}
"""
And I PATCH a REST request to URL "/sellerverification/${id2}/verify" with payload
"""json
{}
"""
And I PATCH a REST request to URL "/sellerverification/${id2}/requiresReview" with payload
"""json
{
    "comment": "Needs human eye"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "MANUAL_REVIEW"

Scenario: Approve from manual review
When I PATCH a REST request to URL "/sellerverification/${id2}/approve" with payload
"""json
{
    "comment": "Manual approval"
}
"""
Then success is true
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFIED"
