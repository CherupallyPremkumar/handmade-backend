Feature: Testcase ID 
Tests the promotion Workflow Service using a REST client. Promotion service exists and is under test.
It helps to create a promotion and manages the state of the promotion as documented in states xml
Scenario: Create a new promotion
Given that "flowName" equals "PROMOTION_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/promotion" with payload
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

Scenario: Retrieve the promotion that just got created
When I GET a REST request to URL "/promotion/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the schedulePromotion event to the promotion with comments
Given that "comment" equals "Comment for schedulePromotion"
And that "event" equals "schedulePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SCHEDULED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the cancelPromotion event to the promotion with comments
Given that "comment" equals "Comment for cancelPromotion"
And that "event" equals "cancelPromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the promotion Workflow Service using a REST client. Promotion service exists and is under test.
It helps to create a promotion and manages the state of the promotion as documented in states xml
Scenario: Create a new promotion
Given that "flowName" equals "PROMOTION_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/promotion" with payload
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

Scenario: Retrieve the promotion that just got created
When I GET a REST request to URL "/promotion/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the schedulePromotion event to the promotion with comments
Given that "comment" equals "Comment for schedulePromotion"
And that "event" equals "schedulePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SCHEDULED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the activatePromotion event to the promotion with comments
Given that "comment" equals "Comment for activatePromotion"
And that "event" equals "activatePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the cancelPromotion event to the promotion with comments
Given that "comment" equals "Comment for cancelPromotion"
And that "event" equals "cancelPromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the promotion Workflow Service using a REST client. Promotion service exists and is under test.
It helps to create a promotion and manages the state of the promotion as documented in states xml
Scenario: Create a new promotion
Given that "flowName" equals "PROMOTION_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/promotion" with payload
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

Scenario: Retrieve the promotion that just got created
When I GET a REST request to URL "/promotion/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the schedulePromotion event to the promotion with comments
Given that "comment" equals "Comment for schedulePromotion"
And that "event" equals "schedulePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SCHEDULED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the activatePromotion event to the promotion with comments
Given that "comment" equals "Comment for activatePromotion"
And that "event" equals "activatePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the expirePromotion event to the promotion with comments
Given that "comment" equals "Comment for expirePromotion"
And that "event" equals "expirePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "EXPIRED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the promotion Workflow Service using a REST client. Promotion service exists and is under test.
It helps to create a promotion and manages the state of the promotion as documented in states xml
Scenario: Create a new promotion
Given that "flowName" equals "PROMOTION_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/promotion" with payload
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

Scenario: Retrieve the promotion that just got created
When I GET a REST request to URL "/promotion/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the schedulePromotion event to the promotion with comments
Given that "comment" equals "Comment for schedulePromotion"
And that "event" equals "schedulePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SCHEDULED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the activatePromotion event to the promotion with comments
Given that "comment" equals "Comment for activatePromotion"
And that "event" equals "activatePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pausePromotion event to the promotion with comments
Given that "comment" equals "Comment for pausePromotion"
And that "event" equals "pausePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PAUSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the cancelPromotion event to the promotion with comments
Given that "comment" equals "Comment for cancelPromotion"
And that "event" equals "cancelPromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CANCELLED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the promotion Workflow Service using a REST client. Promotion service exists and is under test.
It helps to create a promotion and manages the state of the promotion as documented in states xml
Scenario: Create a new promotion
Given that "flowName" equals "PROMOTION_FLOW"
And that "initialState" equals "DRAFT"
When I POST a REST request to URL "/promotion" with payload
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

Scenario: Retrieve the promotion that just got created
When I GET a REST request to URL "/promotion/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the schedulePromotion event to the promotion with comments
Given that "comment" equals "Comment for schedulePromotion"
And that "event" equals "schedulePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "SCHEDULED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the activatePromotion event to the promotion with comments
Given that "comment" equals "Comment for activatePromotion"
And that "event" equals "activatePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the pausePromotion event to the promotion with comments
Given that "comment" equals "Comment for pausePromotion"
And that "event" equals "pausePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PAUSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the expirePromotion event to the promotion with comments
Given that "comment" equals "Comment for expirePromotion"
And that "event" equals "expirePromotion"
When I PATCH a REST request to URL "/promotion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "EXPIRED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
