Feature: Payment Processing API Tests

  Scenario: Create a payment for order
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/payments" with payload
    """
    {
      "orderId": "order-001",
      "amount": 225.98,
      "currency": "INR",
      "transactionId": "TXN-TEST-001",
      "merchantTransactionId": "MERCH-TXN-001"
    }
    """
    Then the http status code is 201
    And success is true
    And the REST response key "orderId" is "order-001"
    And the REST response key "amount" is "225.98"
    And the REST response key "currency" is "INR"

  Scenario: Get payment by ID
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/payments/payment-001"
    Then the http status code is 200
    And success is true
    And the REST response key "id" is "payment-001"
    And the REST response key "transactionId" exists

  Scenario: Get payments for an order
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I GET a REST request to URL "/api/payments/order/order-001"
    Then the http status code is 200
    And success is true
    And the REST response is an array

  Scenario: Update payment status to COMPLETED
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I PUT a REST request to URL "/api/payments/payment-001/status" with payload
    """
    {
      "status": "COMPLETED"
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Process refund
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/payments/payment-001/refund" with payload
    """
    {
      "amount": 225.98,
      "reason": "Customer requested refund"
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "status" is "REFUNDED"

  Scenario: Query payments by transaction ID
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/q/payment" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "default",
        "transaction_id": "TXN-001-2024"
      }
    }
    """
    Then the http status code is 200
    And success is true
    And the REST response key "numRowsReturned" is "1"

  Scenario: Query payments by date range
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/q/payment" with payload
    """
    {
      "numRowsInPage": 10,
      "pageNum": 1,
      "filters": {
        "tenant_id": "default",
        "created_time_from": "2024-01-01T00:00:00",
        "created_time_to": "2024-12-31T23:59:59"
      }
    }
    """
    Then the http status code is 200
    And success is true

  Scenario: Verify payment webhook
    When I construct a REST request with authorization header in realm "tenant0" for user "t0-premium" and password "t0-premium"
    And I construct a REST request with header "x-chenile-tenant-id" and value "tenant0"
    When I POST a REST request to URL "/api/payments/webhook" with payload
    """
    {
      "transactionId": "TXN-001-2024",
      "status": "SUCCESS",
      "amount": 225.98,
      "signature": "test-signature"
    }
    """
    Then the http status code is 200
    And success is true
