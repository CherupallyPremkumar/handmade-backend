# Handmade Architecture Documentation

This directory contains the **authoritative technical standards** for the Handmade platform, strictly aligned with the Chenile framework source code as of Jan 2026.

## 🗝️ Core Reference
- [**Official Chenile Pattern**](chenile-pattern.md): The 6-layer mapping and core class usage.
- [**Module Templates**](module-templates.md): Production-ready boilerplate for new domains.
- [**Hierarchical Module Structure**](module-structure.md): Mapping of ~200 tables to 11 top-level domains.

## 🔄 Workflow & State Management
- [**STM Catalog**](stm-catalog.md): Complete list of business workflows and states.
- [**Seller Account Model**](seller-account-model.md): A textbook example of the full-stack implementation.

## 🕵️ Framework Deep Dives
- [**Chenile Core Deep Dive**](chenile-core-deep-dive.md): Engine internals, OWIZ, and Trajectories.
- [**Chenile HTTP Deep Dive**](chenile-http-deep-dive.md): REST boundary and Controller delegation.
- [**Chenile MQTT Deep Dive**](chenile-mqtt-deep-dive.md): Asynchronous message bridging and topic mapping.
- [**Chenile Proxy Deep Dive**](chenile-proxy-deep-dive.md): Location transparency and remote service invocation.
- [**Chenile Query Deep Dive**](chenile-query-deep-dive.md): Standardized retrieval, pagination, and MyBatis integration.
- [**Chenile Query API Deep Dive**](chenile-query-api-deep-dive.md): Retrieval contracts, Metadata, and reporting.
- [**Chenile Testing Deep Dive**](chenile-testing-deep-dive.md): BDD, Cucumber utils, and Service Contract verification.
- [**Chenile JPA Deep Dive**](chenile-jpa-deep-dive.md): Persistence governance, multi-tenancy, and SLA tracking.
- [**Owiz Deep Dive**](owiz-deep-dive.md): The core orchestration engine, chains, and routers.
- [**Chenile Base Deep Dive**](chenile-base-deep-dive.md): Communication contract and Exceptions.
- [**STM Deep Dive**](stm-deep-dive.md): State Machine signatures and lifecycle.
- [**Workflow Service Deep Dive**](workflow-service-deep-dive.md): Service governance, ACLs, and HATEOAS.

---

> [!IMPORTANT]
> **Always refer to these documents before implementation.** They represent the "Locked-In" architecture that ensures Amazon-grade governance and long-term scalability.
