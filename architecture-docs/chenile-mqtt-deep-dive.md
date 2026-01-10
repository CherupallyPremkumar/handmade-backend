# Chenile MQTT: Asynchronous Communication (Official)

This document describes the **Chenile MQTT** module, which enables transport-neutral asynchronous communication by bridging MQTT topics to Chenile services.

---

## 📡 1. The Message Bridge: `MqttEntryPoint`

The `MqttEntryPoint` is the bridge between the world of MQTT topics and the governed Chenile core.

### Logic:
- **Topic Mapping**: Topics are conventionally structured as `[baseTopic]/[serviceId]/[operationName]`.
- **Exchange Creation**:
    1.  Parses the topic to resolve the `serviceId` and `operationName`.
    2.  Populates `ChenileExchange` metadata from these values.
    3.  Copies MQTT **User Properties** into the exchange headers.
- **Execution**: Delegates to the standard `chenileEntryPoint.execute(exchange)`, ensuring the **exact same** interceptors (Security, Audit, STM) are applied as HTTP requests.

---

## 🔄 2. The Listener: `MqttSubscriber`

A robust callback listener (`MqttCallback`) that manages the subscription lifecycle.

### Key Features:
- **Auto-Subscription**: Subscribes to `[subscribeTopic]/+` to listen for all operations under a specific service.
- **Message Filtering (Governance)**:
    - **Self-Ignition Protection**: Automatically ignores messages where the "source" property matches its own client ID.
    - **Targeted Delivery**: Only processes messages where the "target" property matches its client ID (or uses `!` for negations).
- **Acknowledgements**: Automatically sends MQTT ACKs after successful processing.

---

## 🛠️ 3. Automation: `MqttInitializer`

Chenile automates the wiring of MQTT services during application startup (`ApplicationReadyEvent`).

### The "@ChenileMqtt" Magic:
When a class is annotated with `@ChenileMqtt`, the initializer:
1.  **Registers the Service**: Links the service to a specific `subscribeTopic` and `publishTopic`.
2.  **Configures QoS**: Sets the "Quality of Service" (0, 1, or 2) as specified in the annotation.
3.  **Active Subscription**: Immediately subscribes the client to the broker topics on startup.

---

## 🎯 4. Handmade Implementation Rule
> [!IMPORTANT]
> **Transport Transparency**: The service layer should never know if it was called via HTTP or MQTT.
> **Convention-Based Topics**: Always use the `/domain/subdomain/service/operation` convention for topics.
> **Header Mapping**: Use User Properties for metadata (e.g., `trajectoryID`, `eventID`) instead of embedding them in the payload.

**This document completes the transport documentation for the Handmade platform.**
