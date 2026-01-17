Feature: Testcase ID 
Tests the sellerverification Workflow Service using a REST client. Sellerverification service exists and is under test.
It helps to create a sellerverification and manages the state of the sellerverification as documented in states xml
Scenario: Create a new sellerverification
Given that "flowName" equals "sellerVerificationFlow"
And that "initialState" equals "INITIATED"
When I POST a REST request to URL "/sellerverification" with payload
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

Scenario: Retrieve the sellerverification that just got created
When I GET a REST request to URL "/sellerverification/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the submit event to the sellerverification with comments
Given that "comment" equals "Comment for submit"
And that "event" equals "submit"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PENDING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the verify event to the sellerverification with comments
Given that "comment" equals "Comment for verify"
And that "event" equals "verify"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFYING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the requiresReview event to the sellerverification with comments
Given that "comment" equals "Comment for requiresReview"
And that "event" equals "requiresReview"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "MANUAL_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the approve event to the sellerverification with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFIED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the sellerverification Workflow Service using a REST client. Sellerverification service exists and is under test.
It helps to create a sellerverification and manages the state of the sellerverification as documented in states xml
Scenario: Create a new sellerverification
Given that "flowName" equals "sellerVerificationFlow"
And that "initialState" equals "INITIATED"
When I POST a REST request to URL "/sellerverification" with payload
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

Scenario: Retrieve the sellerverification that just got created
When I GET a REST request to URL "/sellerverification/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the submit event to the sellerverification with comments
Given that "comment" equals "Comment for submit"
And that "event" equals "submit"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PENDING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the verify event to the sellerverification with comments
Given that "comment" equals "Comment for verify"
And that "event" equals "verify"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFYING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the requiresReview event to the sellerverification with comments
Given that "comment" equals "Comment for requiresReview"
And that "event" equals "requiresReview"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "MANUAL_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the reject event to the sellerverification with comments
Given that "comment" equals "Comment for reject"
And that "event" equals "reject"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the sellerverification Workflow Service using a REST client. Sellerverification service exists and is under test.
It helps to create a sellerverification and manages the state of the sellerverification as documented in states xml
Scenario: Create a new sellerverification
Given that "flowName" equals "sellerVerificationFlow"
And that "initialState" equals "INITIATED"
When I POST a REST request to URL "/sellerverification" with payload
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

Scenario: Retrieve the sellerverification that just got created
When I GET a REST request to URL "/sellerverification/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the submit event to the sellerverification with comments
Given that "comment" equals "Comment for submit"
And that "event" equals "submit"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PENDING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the verify event to the sellerverification with comments
Given that "comment" equals "Comment for verify"
And that "event" equals "verify"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFYING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the approve event to the sellerverification with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFIED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the sellerverification Workflow Service using a REST client. Sellerverification service exists and is under test.
It helps to create a sellerverification and manages the state of the sellerverification as documented in states xml
Scenario: Create a new sellerverification
Given that "flowName" equals "sellerVerificationFlow"
And that "initialState" equals "INITIATED"
When I POST a REST request to URL "/sellerverification" with payload
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

Scenario: Retrieve the sellerverification that just got created
When I GET a REST request to URL "/sellerverification/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the submit event to the sellerverification with comments
Given that "comment" equals "Comment for submit"
And that "event" equals "submit"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PENDING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the verify event to the sellerverification with comments
Given that "comment" equals "Comment for verify"
And that "event" equals "verify"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "VERIFYING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the reject event to the sellerverification with comments
Given that "comment" equals "Comment for reject"
And that "event" equals "reject"
When I PATCH a REST request to URL "/sellerverification/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "REJECTED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
