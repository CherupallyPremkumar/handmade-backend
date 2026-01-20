Feature: Tests the tax Service using a REST client.
 
  Scenario: Save the tax first.
    Given that "tenant" equals "tenant0"
    And that "employee" equals "E1"
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I construct a REST request with header "x-chenile-eid" and value "${employee}"
    And I POST a REST request to URL "/taxRate" with payload
    """
    {
      "jurisdictionId": "JUR-001",
      "taxType": "SALES_TAX",
      "taxRate": 0.0825,
      "effectiveFrom": "2024-01-01"
	}
	"""
	Then success is true
    And store "$.payload.id" from response to "id"
    # And the REST response key "tenant" is "${tenant}"
    # And the REST response key "createdBy" is "${employee}"

  Scenario: Retrieve the saved tax
    Given that "entity" equals "taxRate"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/taxRate/${id}"
    Then success is true
    And the REST response key "id" is "${id}"

  Scenario: Save a tax using an ID that already is determined
  Given that "id" equals "123"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I construct a REST request with header "x-chenile-eid" and value "E1"
    And I POST a REST request to URL "/taxRate" with payload
  """
  {
    "id": "${id}",
    "jurisdictionId": "JUR-123",
    "taxType": "SALES_TAX",
    "taxRate": 0.0825,
    "effectiveFrom": "2024-01-01"
  }
  """
    Then success is true
    And the REST response key "id" is "${id}"

  Scenario: Retrieve the saved tax
  Given that "entity" equals "taxRate"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/taxRate/${id}"
    Then success is true
    And the REST response key "id" is "${id}"


    