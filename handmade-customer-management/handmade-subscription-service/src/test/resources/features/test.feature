Feature: Testcase ID 
Tests the subscription Workflow Service using a REST client. Subscription service exists and is under test.
It helps to create a subscription and manages the state of the subscription as documented in states xml
Scenario: Create a new subscription
Given that "flowName" equals "SUBSCRIPTION_FLOW"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/subscription" with payload
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

Scenario: Retrieve the subscription that just got created
When I GET a REST request to URL "/subscription/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activatePaid event to the subscription with comments
Given that "comment" equals "Comment for activatePaid"
And that "event" equals "activatePaid"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the cancel event to the subscription with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
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
Tests the subscription Workflow Service using a REST client. Subscription service exists and is under test.
It helps to create a subscription and manages the state of the subscription as documented in states xml
Scenario: Create a new subscription
Given that "flowName" equals "SUBSCRIPTION_FLOW"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/subscription" with payload
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

Scenario: Retrieve the subscription that just got created
When I GET a REST request to URL "/subscription/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activatePaid event to the subscription with comments
Given that "comment" equals "Comment for activatePaid"
And that "event" equals "activatePaid"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the enterGrace event to the subscription with comments
Given that "comment" equals "Comment for enterGrace"
And that "event" equals "enterGrace"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "GRACE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the cancel event to the subscription with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
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
Tests the subscription Workflow Service using a REST client. Subscription service exists and is under test.
It helps to create a subscription and manages the state of the subscription as documented in states xml
Scenario: Create a new subscription
Given that "flowName" equals "SUBSCRIPTION_FLOW"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/subscription" with payload
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

Scenario: Retrieve the subscription that just got created
When I GET a REST request to URL "/subscription/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the activatePaid event to the subscription with comments
Given that "comment" equals "Comment for activatePaid"
And that "event" equals "activatePaid"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the enterGrace event to the subscription with comments
Given that "comment" equals "Comment for enterGrace"
And that "event" equals "enterGrace"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "GRACE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the expire event to the subscription with comments
Given that "comment" equals "Comment for expire"
And that "event" equals "expire"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
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
Tests the subscription Workflow Service using a REST client. Subscription service exists and is under test.
It helps to create a subscription and manages the state of the subscription as documented in states xml
Scenario: Create a new subscription
Given that "flowName" equals "SUBSCRIPTION_FLOW"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/subscription" with payload
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

Scenario: Retrieve the subscription that just got created
When I GET a REST request to URL "/subscription/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the startTrial event to the subscription with comments
Given that "comment" equals "Comment for startTrial"
And that "event" equals "startTrial"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "TRIAL"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the cancel event to the subscription with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
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
Tests the subscription Workflow Service using a REST client. Subscription service exists and is under test.
It helps to create a subscription and manages the state of the subscription as documented in states xml
Scenario: Create a new subscription
Given that "flowName" equals "SUBSCRIPTION_FLOW"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/subscription" with payload
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

Scenario: Retrieve the subscription that just got created
When I GET a REST request to URL "/subscription/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the startTrial event to the subscription with comments
Given that "comment" equals "Comment for startTrial"
And that "event" equals "startTrial"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "TRIAL"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the expire event to the subscription with comments
Given that "comment" equals "Comment for expire"
And that "event" equals "expire"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
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
Tests the subscription Workflow Service using a REST client. Subscription service exists and is under test.
It helps to create a subscription and manages the state of the subscription as documented in states xml
Scenario: Create a new subscription
Given that "flowName" equals "SUBSCRIPTION_FLOW"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/subscription" with payload
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

Scenario: Retrieve the subscription that just got created
When I GET a REST request to URL "/subscription/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the startTrial event to the subscription with comments
Given that "comment" equals "Comment for startTrial"
And that "event" equals "startTrial"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "TRIAL"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the activatePaid event to the subscription with comments
Given that "comment" equals "Comment for activatePaid"
And that "event" equals "activatePaid"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the cancel event to the subscription with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
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
Tests the subscription Workflow Service using a REST client. Subscription service exists and is under test.
It helps to create a subscription and manages the state of the subscription as documented in states xml
Scenario: Create a new subscription
Given that "flowName" equals "SUBSCRIPTION_FLOW"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/subscription" with payload
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

Scenario: Retrieve the subscription that just got created
When I GET a REST request to URL "/subscription/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the startTrial event to the subscription with comments
Given that "comment" equals "Comment for startTrial"
And that "event" equals "startTrial"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "TRIAL"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the activatePaid event to the subscription with comments
Given that "comment" equals "Comment for activatePaid"
And that "event" equals "activatePaid"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the enterGrace event to the subscription with comments
Given that "comment" equals "Comment for enterGrace"
And that "event" equals "enterGrace"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "GRACE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the cancel event to the subscription with comments
Given that "comment" equals "Comment for cancel"
And that "event" equals "cancel"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
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
Tests the subscription Workflow Service using a REST client. Subscription service exists and is under test.
It helps to create a subscription and manages the state of the subscription as documented in states xml
Scenario: Create a new subscription
Given that "flowName" equals "SUBSCRIPTION_FLOW"
And that "initialState" equals "CREATED"
When I POST a REST request to URL "/subscription" with payload
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

Scenario: Retrieve the subscription that just got created
When I GET a REST request to URL "/subscription/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the startTrial event to the subscription with comments
Given that "comment" equals "Comment for startTrial"
And that "event" equals "startTrial"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "TRIAL"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the activatePaid event to the subscription with comments
Given that "comment" equals "Comment for activatePaid"
And that "event" equals "activatePaid"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the enterGrace event to the subscription with comments
Given that "comment" equals "Comment for enterGrace"
And that "event" equals "enterGrace"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "GRACE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the expire event to the subscription with comments
Given that "comment" equals "Comment for expire"
And that "event" equals "expire"
When I PATCH a REST request to URL "/subscription/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "EXPIRED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
