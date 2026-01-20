Feature: Tests the loyalty Service using a REST client.
 
  Scenario: Save the loyalty first.
    Given that "tenant" equals "tenant0"
    And that "employee" equals "E1"
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I construct a REST request with header "x-chenile-eid" and value "${employee}"
    And I POST a REST request to URL "/loyalty" with payload
    """
    {
      "programName": "Gold Rewards",
      "tierLevel": 2
	}
	"""
	Then success is true
    And store "$.payload.id" from response to "id"
    And the REST response key "programName" is "Gold Rewards"

  Scenario: Retrieve the saved loyalty
    Given that "entity" equals "loyalty"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "id" is "${id}"

  Scenario: Save a loyalty using an ID that already is determined
  Given that "id" equals "123"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I construct a REST request with header "x-chenile-eid" and value "E1"
    And I POST a REST request to URL "/loyalty" with payload
  """
  {
    "id": "${id}",
    "programName": "Platinum Rewards"
  }
  """
    Then success is true
    And the REST response key "id" is "${id}"
    And the REST response key "programName" is "Platinum Rewards"

  Scenario: Retrieve the saved loyalty
  Given that "entity" equals "loyalty"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "id" is "${id}"


    