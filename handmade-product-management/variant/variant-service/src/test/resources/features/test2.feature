Feature: Testcase ID 2
Tests the variant Workflow Service using a REST client. Variant service exists and is under test.
It helps to create a variant and manages the state of the variant as documented in states xml

Scenario: Create a new variant
Given that "flowName" equals "productVariantFlow"
And that "initialState" equals "DRAFT"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I POST a REST request to URL "/variant" with payload
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

Scenario: Retrieve the variant that just got created
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I GET a REST request to URL "/variant/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"


Scenario: Send the activate event to the variant with comments
Given that "comment" equals "Comment for activate"
And that "event" equals "activate"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/variant/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the deactivate event to the variant with comments
Given that "comment" equals "Comment for deactivate"
And that "event" equals "deactivate"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/variant/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "INACTIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

Scenario: Send the discontinue event to the variant with comments
Given that "comment" equals "Comment for discontinue"
And that "event" equals "discontinue"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/variant/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "DISCONTINUED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
