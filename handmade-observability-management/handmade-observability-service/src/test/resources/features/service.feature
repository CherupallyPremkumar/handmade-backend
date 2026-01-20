Feature: Tests the observability Service using a REST client.

  Scenario: Save the observability first.
    Given that "tenant" equals "tenant0"
    And that "employee" equals "E1"
    When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
    And I construct a REST request with header "x-chenile-eid" and value "${employee}"
    And I POST a REST request to URL "/observability" with payload
    """
    {
      "metricName": "CPU_USAGE",
      "metricValue": 45.5,
      "unit": "PERCENT"
    }
    """
    Then success is true
    And store "$.payload.id" from response to "id"

  Scenario: Retrieve the saved observability
    Given that "entity" equals "observability"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "id" is "${id}"
    And the REST response key "metricName" is "CPU_USAGE"

  Scenario: Save a observability using an ID that already is determined
    Given that "id" equals "123-obs-id"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I construct a REST request with header "x-chenile-eid" and value "E1"
    And I POST a REST request to URL "/observability" with payload
    """
    {
      "id": "${id}",
      "metricName": "MEMORY_USAGE",
      "metricValue": 512,
      "unit": "MB"
    }
    """
    Then success is true
    And the REST response key "id" is "${id}"

  Scenario: Retrieve the saved observability
    Given that "entity" equals "observability"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    And I GET a REST request to URL "/${entity}/${id}"
    Then success is true
    And the REST response key "id" is "${id}"
    And the REST response key "metricName" is "MEMORY_USAGE"