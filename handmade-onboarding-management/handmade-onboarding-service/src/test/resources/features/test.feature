Feature: Testcase ID 
Tests the onboarding Workflow Service using a REST client. Onboarding service exists and is under test.
It helps to create a onboarding and manages the state of the onboarding as documented in states xml
Scenario: Create a new onboarding
Given that "flowName" equals "SELLER_ONBOARDING_FLOW"
And that "initialState" equals "APPLIED"
When I POST a REST request to URL "/onboarding" with payload
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

Scenario: Retrieve the onboarding that just got created
When I GET a REST request to URL "/onboarding/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the kycStarted event to the onboarding with comments
Given that "comment" equals "Comment for kycStarted"
And that "event" equals "kycStarted"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "KYC_IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the kycRejected event to the onboarding with comments
Given that "comment" equals "Comment for kycRejected"
And that "event" equals "kycRejected"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
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
Tests the onboarding Workflow Service using a REST client. Onboarding service exists and is under test.
It helps to create a onboarding and manages the state of the onboarding as documented in states xml
Scenario: Create a new onboarding
Given that "flowName" equals "SELLER_ONBOARDING_FLOW"
And that "initialState" equals "APPLIED"
When I POST a REST request to URL "/onboarding" with payload
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

Scenario: Retrieve the onboarding that just got created
When I GET a REST request to URL "/onboarding/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the kycStarted event to the onboarding with comments
Given that "comment" equals "Comment for kycStarted"
And that "event" equals "kycStarted"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "KYC_IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the kycApproved event to the onboarding with comments
Given that "comment" equals "Comment for kycApproved"
And that "event" equals "kycApproved"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "POLICY_EVALUATION_PENDING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the startPolicyEval event to the onboarding with comments
Given that "comment" equals "Comment for startPolicyEval"
And that "event" equals "startPolicyEval"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "POLICY_EVALUATION"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the reject event to the onboarding with comments
Given that "comment" equals "Comment for reject"
And that "event" equals "reject"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
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
Tests the onboarding Workflow Service using a REST client. Onboarding service exists and is under test.
It helps to create a onboarding and manages the state of the onboarding as documented in states xml
Scenario: Create a new onboarding
Given that "flowName" equals "SELLER_ONBOARDING_FLOW"
And that "initialState" equals "APPLIED"
When I POST a REST request to URL "/onboarding" with payload
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

Scenario: Retrieve the onboarding that just got created
When I GET a REST request to URL "/onboarding/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the kycStarted event to the onboarding with comments
Given that "comment" equals "Comment for kycStarted"
And that "event" equals "kycStarted"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "KYC_IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the kycApproved event to the onboarding with comments
Given that "comment" equals "Comment for kycApproved"
And that "event" equals "kycApproved"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "POLICY_EVALUATION_PENDING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the startPolicyEval event to the onboarding with comments
Given that "comment" equals "Comment for startPolicyEval"
And that "event" equals "startPolicyEval"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "POLICY_EVALUATION"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the policyFlagged event to the onboarding with comments
Given that "comment" equals "Comment for policyFlagged"
And that "event" equals "policyFlagged"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "POLICY_MANUAL_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the reject event to the onboarding with comments
Given that "comment" equals "Comment for reject"
And that "event" equals "reject"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
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
Tests the onboarding Workflow Service using a REST client. Onboarding service exists and is under test.
It helps to create a onboarding and manages the state of the onboarding as documented in states xml
Scenario: Create a new onboarding
Given that "flowName" equals "SELLER_ONBOARDING_FLOW"
And that "initialState" equals "APPLIED"
When I POST a REST request to URL "/onboarding" with payload
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

Scenario: Retrieve the onboarding that just got created
When I GET a REST request to URL "/onboarding/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the kycStarted event to the onboarding with comments
Given that "comment" equals "Comment for kycStarted"
And that "event" equals "kycStarted"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "KYC_IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the kycApproved event to the onboarding with comments
Given that "comment" equals "Comment for kycApproved"
And that "event" equals "kycApproved"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "POLICY_EVALUATION_PENDING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the startPolicyEval event to the onboarding with comments
Given that "comment" equals "Comment for startPolicyEval"
And that "event" equals "startPolicyEval"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "POLICY_EVALUATION"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the policyFlagged event to the onboarding with comments
Given that "comment" equals "Comment for policyFlagged"
And that "event" equals "policyFlagged"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "POLICY_MANUAL_REVIEW"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the manualApprove event to the onboarding with comments
Given that "comment" equals "Comment for manualApprove"
And that "event" equals "manualApprove"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FINANCE_SETUP"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the financeProfileCreated event to the onboarding with comments
Given that "comment" equals "Comment for financeProfileCreated"
And that "event" equals "financeProfileCreated"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "READY_FOR_GO_LIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the goLiveConfirmed event to the onboarding with comments
Given that "comment" equals "Comment for goLiveConfirmed"
And that "event" equals "goLiveConfirmed"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Feature: Testcase ID 
Tests the onboarding Workflow Service using a REST client. Onboarding service exists and is under test.
It helps to create a onboarding and manages the state of the onboarding as documented in states xml
Scenario: Create a new onboarding
Given that "flowName" equals "SELLER_ONBOARDING_FLOW"
And that "initialState" equals "APPLIED"
When I POST a REST request to URL "/onboarding" with payload
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

Scenario: Retrieve the onboarding that just got created
When I GET a REST request to URL "/onboarding/${id}"
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "${currentState}"

Scenario: Send the kycStarted event to the onboarding with comments
Given that "comment" equals "Comment for kycStarted"
And that "event" equals "kycStarted"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "KYC_IN_PROGRESS"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the kycApproved event to the onboarding with comments
Given that "comment" equals "Comment for kycApproved"
And that "event" equals "kycApproved"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "POLICY_EVALUATION_PENDING"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the startPolicyEval event to the onboarding with comments
Given that "comment" equals "Comment for startPolicyEval"
And that "event" equals "startPolicyEval"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "POLICY_EVALUATION"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the policyApproved event to the onboarding with comments
Given that "comment" equals "Comment for policyApproved"
And that "event" equals "policyApproved"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "FINANCE_SETUP"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the financeProfileCreated event to the onboarding with comments
Given that "comment" equals "Comment for financeProfileCreated"
And that "event" equals "financeProfileCreated"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "READY_FOR_GO_LIVE"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
Scenario: Send the goLiveConfirmed event to the onboarding with comments
Given that "comment" equals "Comment for goLiveConfirmed"
And that "event" equals "goLiveConfirmed"
When I PATCH a REST request to URL "/onboarding/${id}/${event}" with payload
"""json
{
    "comment": "${comment}"
}
"""
Then the REST response contains key "mutatedEntity"
And the REST response key "mutatedEntity.id" is "${id}"
And the REST response key "mutatedEntity.currentState.stateId" is "COMPLETED"
And store "$.payload.mutatedEntity.currentState.stateId" from response to "finalState"
