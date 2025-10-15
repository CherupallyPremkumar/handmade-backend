Feature: Customer Management API Tests

  Scenario: Register a new customer
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/customers/register" with payload
    """
    {
      "name": "John Doe",
      "email": "john.doe@example.com",
      "password": "SecurePassword123!",
      "phone": "+1-555-0100",
      "address": "123 Test Street",
      "city": "New York",
      "state": "NY",
      "pincode": "10001",
      "country": "USA"
    }
    """
    Then the http status code is 201
    And success is true
    And the REST response key "name" is "John Doe"
    And the REST response key "email" is "john.doe@example.com"
    And the REST response key "active" is "true"

  Scenario: Get customer by ID
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/customers/customer-001"
    Then the http status code is 200
    And success is true
    And the REST response key "id" is "customer-001"
    And the REST response key "name" exists
    And the REST response key "email" exists

  Scenario: Update customer profile
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/customers/customer-001" with payload
    """
    {
      "name": "Alice Johnson Updated",
      "phone": "+1-555-9999",
      "address": "456 New Address",
      "city": "Brooklyn",
      "state": "NY",
      "pincode": "11201"
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "name" is "Alice Johnson Updated"
    And the REST response key "phone" is "+1-555-9999"

  Scenario: Search customers by email
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/q/customer" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "tenant0",
        "email": "alice.johnson@example.com"
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "numRowsReturned" is "1"
    And the REST response key "list[0].row.email" is "alice.johnson@example.com"

  Scenario: Get customer order history
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/customers/customer-001/orders"
    Then the http status code is 200
    And success is true
    And the REST response is an array

  Scenario: Deactivate customer account
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/customers/customer-001/deactivate"
    Then the http status code is 200
    And success is true
    And the REST response key "active" is "false"

  Scenario: Customer login with valid credentials
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/customers/login" with payload
    """
    {
      "email": "alice.johnson@example.com",
      "password": "password123"
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "token" exists

  Scenario: Customer login with invalid credentials
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/customers/login" with payload
    """
    {
      "email": "alice.johnson@example.com",
      "password": "wrongpassword"
    }
    """
    Then the http status code is 401
    And success is false
