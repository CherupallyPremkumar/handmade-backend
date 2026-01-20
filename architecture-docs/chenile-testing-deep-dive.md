# Chenile Testing: BDD & Service Contracts (Official)

This document describes the **Chenile Testing** framework, which empowers developers to verify service behavior using Cucumber-based BDD (Behavior Driven Development).

---

## 🏗️ 1. The Core: `cucumber-utils`

Chenile provides a suite of pre-built Gherkin steps that allow testing without writing boilerplate Java code.

### 🌐 REST Testing (`RestCukesSteps`)
Tests services via the full HTTP stack using Spring's `MockMvc`.
- **Steps**: `I POST a REST request to URL "{url}" with payload`, `the http status code is {int}`.
- **Variable Substitution**: Use `store "{jsonPath}" from response to "{varName}"` to capture IDs and reuse them in subsequent steps (e.g., `/{varName}`).
- **Assertions**: Rich support for JSON Path assertions (`the REST response key "{key}" is "{value}"`).

### 📦 Local Testing (`CukesSteps`)
Tests services directly via the `ChenileEntryPoint`, bypassing the HTTP layer for ultra-fast execution.
- **Steps**: `I POST a request to service "{service}" and operation "{op}" with payload`.
- **Deep Assertions**: Uses **SpEL** (Spring Expression Language) to verify nested object properties in the response.

---

## 🛡️ 2. Security Testing: `cucumber-sec-utils`

Verification of secured endpoints is handled by `RestCukesSecSteps`.

- **Authorized Requests**: 
  `I construct a REST request with authorization header in realm "{realm}" for user "{user}" and password "{pass}"`
- **Mechanism**: Automatically interfaces with Keycloak (or a test instance) to fetch a JWT token and inject it into the `Authorization` header of the `MockMvc` request.

---

## 📡 3. Async Testing: `cucumber-mqtt-utils`

For event-driven services, Chenile provides `MqttBaseTest`.

- **Testcontainers Integration**: Automatically spins up a **HiveMQ** Docker container on a random port.
- **Dynamic Connection**: Injects the broker's URI into the Spring environment (`mqtt.connection.ServerURIs`), ensuring services automatically connect to the test broker.

---

## 🛠️ 4. Shared State: `CukesContext`

All steps share a singleton `CukesContext` (Thread-Local) which stores:
- The current `ChenileExchange` or `MockMvc` result.
- Captured variables (`varMap`).
- Error codes and exception messages for verification.

---

## 🎯 5. Handmade Implementation Rule
> [!IMPORTANT]
> **Contract-First Verification**: Always include a `.feature` file for every new service operation.
> **Prefer MockMvc for Integration**: Use `RestCukesSteps` for end-to-end API verification as it tests interceptors and filters.
> **Avoid Hardcoding IDs**: Use the `store` step to capture dynamic IDs (e.g., from a 'Create' operation) and use them in 'Get' or 'Update' steps.

**This document defines the quality and verification standard for the Handmade platform.**
