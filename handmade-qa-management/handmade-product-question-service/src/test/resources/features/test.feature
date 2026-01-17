Feature: Testcase ID 
Tests the productquestion Workflow Service using a REST client. Productquestion service exists and is under test.
It helps to create a productquestion and manages the state of the productquestion as documented in states xml
Scenario: Create a new productquestion
Given that "flowName" equals "productQuestionFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/productquestion" with payload
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

Scenario: Retrieve the productquestion that just got created
When I GET a REST request to URL "/productquestion/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the review event to the productquestion with comments
Given that "comment" equals "Comment for review"
And that "event" equals "review"
When I PATCH a REST request to URL "/productquestion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the approve event to the productquestion with comments
Given that "comment" equals "Comment for approve"
And that "event" equals "approve"
When I PATCH a REST request to URL "/productquestion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the unpublish event to the productquestion with comments
Given that "comment" equals "Comment for unpublish"
And that "event" equals "unpublish"
When I PATCH a REST request to URL "/productquestion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNPUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the productquestion Workflow Service using a REST client. Productquestion service exists and is under test.
It helps to create a productquestion and manages the state of the productquestion as documented in states xml
Scenario: Create a new productquestion
Given that "flowName" equals "productQuestionFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/productquestion" with payload
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

Scenario: Retrieve the productquestion that just got created
When I GET a REST request to URL "/productquestion/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the review event to the productquestion with comments
Given that "comment" equals "Comment for review"
And that "event" equals "review"
When I PATCH a REST request to URL "/productquestion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNDER_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the reject event to the productquestion with comments
Given that "comment" equals "Comment for reject"
And that "event" equals "reject"
When I PATCH a REST request to URL "/productquestion/${id}/${event}" with payload
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
Tests the productquestion Workflow Service using a REST client. Productquestion service exists and is under test.
It helps to create a productquestion and manages the state of the productquestion as documented in states xml
Scenario: Create a new productquestion
Given that "flowName" equals "productQuestionFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/productquestion" with payload
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

Scenario: Retrieve the productquestion that just got created
When I GET a REST request to URL "/productquestion/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the publish event to the productquestion with comments
Given that "comment" equals "Comment for publish"
And that "event" equals "publish"
When I PATCH a REST request to URL "/productquestion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the answer event to the productquestion with comments
Given that "comment" equals "Comment for answer"
And that "event" equals "answer"
When I PATCH a REST request to URL "/productquestion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ANSWERED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the close event to the productquestion with comments
Given that "comment" equals "Comment for close"
And that "event" equals "close"
When I PATCH a REST request to URL "/productquestion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CLOSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the productquestion Workflow Service using a REST client. Productquestion service exists and is under test.
It helps to create a productquestion and manages the state of the productquestion as documented in states xml
Scenario: Create a new productquestion
Given that "flowName" equals "productQuestionFlow"
And that "initialState" equals "SUBMITTED"
When I POST a REST request to URL "/productquestion" with payload
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

Scenario: Retrieve the productquestion that just got created
When I GET a REST request to URL "/productquestion/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the publish event to the productquestion with comments
Given that "comment" equals "Comment for publish"
And that "event" equals "publish"
When I PATCH a REST request to URL "/productquestion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "PUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the unpublish event to the productquestion with comments
Given that "comment" equals "Comment for unpublish"
And that "event" equals "unpublish"
When I PATCH a REST request to URL "/productquestion/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "UNPUBLISHED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
