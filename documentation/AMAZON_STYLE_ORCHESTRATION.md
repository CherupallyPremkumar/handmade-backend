# Amazon-Style Product Orchestration Guide

This document defines the end-to-end workflow for creating a product on the Handmade platform. It emphasizes decoupling through event-driven state machines, ensuring scalability and regional autonomy.

## 🧭 The Core Principle
Product creation is **not** a single database transaction. It is a guided workflow across multiple domain records, connected by events and state machines.

---

## 1. The 7-Step Orchestration Flow

| Step | Module | Activity | State Change | Signal/Event |
| :--- | :--- | :--- | :--- | :--- |
| **Step 1** | Catalog / Product | Define global identity (ASIN) | `DRAFT` → `CREATED` | `ProductCreated` |
| **Step 2** | Catalog / Variant | Define physical attributes (Weight, Size) | `NULL` → `SKU_CREATED` | `VariantsCreated` |
| **Step 3** | Offer | Define commercial terms per region | `NULL` → `PENDING_ACTIVATION` | `OfferCreated` |
| **Step 4** | Inventory | Initialize physical stock at location | `NULL` → `STOCK_READY` | `InventoryAvailable` |
| **Step 5** | Compliance | Tax and Safety validation (Async) | `PENDING` → `APPROVED` | `ComplianceApproved` |
| **Step 6** | Offer STM | **Final Activation Logic** | `PENDING` → `ACTIVE` | `OfferActivated` |
| **Step 7** | Search | Index activated listing for discovery | `HIDDEN` → `VISIBLE` | `ListingVisible` |

---

## 2. Domain Model Separation

| Model | Owner | Responsibility |
| :--- | :--- | :--- |
| **Product** | Catalog | Global identity and story (Seller-agnostic). |
| **Variant** | Catalog | Physical SKU reality (Weight, Size, Color). |
| **Offer** | Commercial | Commercial reality (Seller + Price + Region). |
| **Inventory** | Physical | Physical availability (Seller + Variant + Location). |
| **State Machine** | Orchestration | Decision logic for buyability (The Buy Box). |

---

## 3. Engineering Constraints (The "Amazon Rules")

- ❌ **No Cross-Module Joins**: If Search needs Price, it listens to `OfferActivated` and caches it.
- ❌ **No Price in Product**: Product is global; Price is regional (Offer).
- ❌ **No Stock in Offer**: Offer is a commercial promise; Stock is physical reality (Inventory).
- ❌ **No Single Transaction**: Every step is eventually consistent.
- ✅ **Single Source of Truth**: The `Offer` state is the **only** thing that determines "Can I sell this right now?"

> [!IMPORTANT]
> This architecture ensures that a stock-out in Mumbai doesn't disable an Offer in London, and a price update in Germany doesn't lock the Product record globally.
