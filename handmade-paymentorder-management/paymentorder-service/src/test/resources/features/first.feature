Feature: Tests the paymentorder Workflow Service using a REST client. This is done only for the
first testcase. Paymentorder service exists and is under test.
It helps to create a paymentorder and manages the state of the paymentorder as documented in states xml
Scenario: Create a new paymentorder
Given that "flowName" equals "ShippingFlow"
And that "initialState" equals "OPENED"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I POST a REST request to URL "/paymentorder" with payload
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

Scenario: Retrieve the paymentorder that just got created
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I GET a REST request to URL "/paymentorder/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

 Scenario: Send the assign event to the paymentorder with comments
 Given that "comment" equals "Comment for assign"
 And that "event" equals "assign"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ASSIGNED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the resolve event to the paymentorder with comments
 Given that "comment" equals "Comment for resolve"
 And that "event" equals "resolve"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the close event to the paymentorder with comments
 Given that "comment" equals "Comment for close"
 And that "event" equals "close"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CLOSED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"

 Scenario: Send the captureObservations event to the paymentorder with comments
 Given that "comment" equals "Comment for captureObservations"
 And that "event" equals "captureObservations"
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ARCHIVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"


Scenario: Add new mandatory activities a1,a2 for the last state.
Add a new state "__TERMINAL_STATE__"
Add a completion checker activity "cc" to the last state that leads to __TERMINAL_STATE__
Send cc event on the paymentorder with comments. This should fail since the mandatory activities
have not been completed.
Given that "terminalState" equals "__TERMINAL_STATE__"
And that config strategy is "paymentorderConfigProvider" with prefix "Paymentorder"
And that a new mandatory activity "a1" is added from state "${finalState}" to state "${finalState}" in flow "${flowName}"
And that a new mandatory activity "a2" is added from state "${finalState}" to state "${finalState}" in flow "${flowName}"
And that a new state "${terminalState}" is added to flow "${flowName}"
And that a new activity completion checker "cc" is added from state "${finalState}" to state "${terminalState}" in flow "${flowName}"
And that "comment" equals "Attempting to send cc event without mandatory activities being completed."
And that "event" equals "cc"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
    {
    "comment": "${comment}"
    }
"""
Then the REST response does not contain key "mutatedEntity"
And success is false
And the http status code is 400
And the top level subErrorCode is 49000

Scenario: Retrieve the paymentorder that just got created
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I GET a REST request to URL "/paymentorder/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${finalState}"

Scenario: Perform mandatory activity (a1) on the  paymentorder with comments
Given that "comment" equals "Performed activity a1."
And that "event" equals "a1"
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
"comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${finalState}"
And the REST response key "mutatedEntity.activities" collection has an item with keys and values:
| key             | value         |
| activityName    | ${event}      |
| activityComment | ${comment}    |

Scenario: Perform mandatory activity (a2) on the  paymentorder with comments
Given that "comment" equals "Performed activity a2."
And that "event" equals "a2"
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
"comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${finalState}"
And the REST response key "mutatedEntity.activities" collection has an item with keys and values:
| key             | value         |
| activityName    | ${event}      |
| activityComment | ${comment}    |

Scenario: Perform mandatory activity (cc) on the  paymentorder with comments
Given that "comment" equals "Performed activity cc after completing all activities."
And that "event" equals "cc"
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/paymentorder/${id}/${event}" with payload
"""json
{
"comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${terminalState}"

Scenario: Send an invalid event to paymentorder . This will err out.
When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
When I PATCH a REST request to URL "/paymentorder/${id}/invalid" with payload
"""json
{
    "comment": "invalid stuff"
}
"""
Then the REST response does not contain key "mutatedEntity"
And the http status code is 422

