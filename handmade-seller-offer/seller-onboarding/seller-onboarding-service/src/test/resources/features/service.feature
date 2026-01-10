Feature: Tests the common Service using a REST client.
 
  Scenario: Save the seller onboarding first.
    Given that "tenant" equals "tenant0"
    And that "employee" equals "E1"
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I construct a REST request with header "x-chenile-eid" and value "${employee}"
    And I POST a REST request to URL "/seller-onboarding" with payload
    """
    {
      "email": "test@example.com",
      "sellerId": "seller-123",
      "onboardingStatus": "DRAFT",
      "startedAt": "2023-10-26T10:00:00.000+00:00"
    }
    """
    Then success is true
    And store "$.payload.mutatedEntity.id" from response to "id"
    # And the REST response key "tenant" is "${tenant}"
    # And the REST response key "createdBy" is "${employee}"

  Scenario: Retrieve the saved seller onboarding
    Given that "entity" equals "seller-onboarding"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "mutatedEntity.id" is "${id}"

  Scenario: Save a seller onboarding using an ID that already is determined
  Given that "id" equals "123"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I construct a REST request with header "x-chenile-eid" and value "E1"
    And I POST a REST request to URL "/seller-onboarding" with payload
  """
  {
    "id": "${id}",
    "email": "test2@example.com",
    "sellerId": "seller-456",
    "onboardingStatus": "DRAFT",
    "startedAt": "2023-10-26T10:00:00.000+00:00"
  }
  """
    Then success is true
    And the REST response key "mutatedEntity.id" is "${id}"

  Scenario: Retrieve the saved seller onboarding (Duplicate Scenario Fixed)
  Given that "entity" equals "seller-onboarding"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "mutatedEntity.id" is "${id}"