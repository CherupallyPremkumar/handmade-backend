Feature: Testcase ID 
Tests the risk Workflow Service using a REST client. Risk service exists and is under test.
It helps to create a risk and manages the state of the risk as documented in states xml
Scenario: Create a new risk
Given that "flowName" equals "RISK_SIGNAL_FLOW"
And that "initialState" equals "ANALYZING"
When I POST a REST request to URL "/risk" with payload
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

Scenario: Retrieve the risk that just got created
When I GET a REST request to URL "/risk/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the escalateSignal event to the risk with comments
Given that "comment" equals "Comment for escalateSignal"
And that "event" equals "escalateSignal"
When I PATCH a REST request to URL "/risk/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ESCALATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the resolveSignal event to the risk with comments
Given that "comment" equals "Comment for resolveSignal"
And that "event" equals "resolveSignal"
When I PATCH a REST request to URL "/risk/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the risk Workflow Service using a REST client. Risk service exists and is under test.
It helps to create a risk and manages the state of the risk as documented in states xml
Scenario: Create a new risk
Given that "flowName" equals "RISK_SIGNAL_FLOW"
And that "initialState" equals "ANALYZING"
When I POST a REST request to URL "/risk" with payload
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

Scenario: Retrieve the risk that just got created
When I GET a REST request to URL "/risk/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the markFalsePositive event to the risk with comments
Given that "comment" equals "Comment for markFalsePositive"
And that "event" equals "markFalsePositive"
When I PATCH a REST request to URL "/risk/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FALSE_POSITIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the risk Workflow Service using a REST client. Risk service exists and is under test.
It helps to create a risk and manages the state of the risk as documented in states xml
Scenario: Create a new risk
Given that "flowName" equals "RISK_SIGNAL_FLOW"
And that "initialState" equals "ANALYZING"
When I POST a REST request to URL "/risk" with payload
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

Scenario: Retrieve the risk that just got created
When I GET a REST request to URL "/risk/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the confirmRisk event to the risk with comments
Given that "comment" equals "Comment for confirmRisk"
And that "event" equals "confirmRisk"
When I PATCH a REST request to URL "/risk/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CONFIRMED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the escalateSignal event to the risk with comments
Given that "comment" equals "Comment for escalateSignal"
And that "event" equals "escalateSignal"
When I PATCH a REST request to URL "/risk/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ESCALATED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the resolveSignal event to the risk with comments
Given that "comment" equals "Comment for resolveSignal"
And that "event" equals "resolveSignal"
When I PATCH a REST request to URL "/risk/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the risk Workflow Service using a REST client. Risk service exists and is under test.
It helps to create a risk and manages the state of the risk as documented in states xml
Scenario: Create a new risk
Given that "flowName" equals "RISK_SIGNAL_FLOW"
And that "initialState" equals "ANALYZING"
When I POST a REST request to URL "/risk" with payload
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

Scenario: Retrieve the risk that just got created
When I GET a REST request to URL "/risk/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the confirmRisk event to the risk with comments
Given that "comment" equals "Comment for confirmRisk"
And that "event" equals "confirmRisk"
When I PATCH a REST request to URL "/risk/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "CONFIRMED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the resolveSignal event to the risk with comments
Given that "comment" equals "Comment for resolveSignal"
And that "event" equals "resolveSignal"
When I PATCH a REST request to URL "/risk/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "RESOLVED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the risk Workflow Service using a REST client. Risk service exists and is under test.
It helps to create a risk and manages the state of the risk as documented in states xml
Scenario: Create a new risk
Given that "flowName" equals "RISK_SIGNAL_FLOW"
And that "initialState" equals "SUPPRESSED"
When I POST a REST request to URL "/risk" with payload
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

Scenario: Retrieve the risk that just got created
When I GET a REST request to URL "/risk/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the analyzeSignal event to the risk with comments
Given that "comment" equals "Comment for analyzeSignal"
And that "event" equals "analyzeSignal"
When I PATCH a REST request to URL "/risk/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "ANALYZING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
