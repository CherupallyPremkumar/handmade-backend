# Commission Management Module

## 🎯 Overview
The **Commission Management** module is the revenue engine of the platform. It calculates, tracks, and manages the fees charged to sellers for using the marketplace. It supports flexible commission strategies including flat percentages, fixed fees, and category-specific rates.

## 🏗️ Domain Models

### **1. CommissionRule** (Entity)
**Purpose**: Defines the logic for calculating commissions.
- **Key Fields**: `type` (PERCENTAGE, FIXED), `value`, `categoryId`, `sellerId`, `priority`.
- **Logic**:
  - **Global Rules**: Apply to everything (e.g., "10% storewide").
  - **Category Rules**: Override global rules (e.g., "15% for Jewelry").
  - **Seller Rules**: Override all others (e.g., "0% for New Seller Promo").
  - **Min/Max**: Supports floor and cap (e.g., "Min $0.50, Max $50.00").

### **2. Commission** (Entity)
**Purpose**: Immutable record of a fee charged for a specific transaction.
- **Key Fields**: `orderId`, `sellerId`, `amount`, `taxAmount`, `status`.
- **Lifecycle**:
  - `PENDING`: Calculated when order is placed.
  - `COLLECTED`: Deducted from seller's ledger when payment clears.
  - `SETTLED`: Marked as revenue for the platform.
  - `REFUNDED`: Returned to seller if order is cancelled/refunded.

## 🚀 Key Features
- **Flexible Strategies**: Support for percentage, fixed, and hybrid models.
- **Hierarchical Rules**: Category-specific and seller-specific overrides.
- **Revenue Assurance**: Exact tracking of platform earnings.
- **Audit Trail**: Immutable records linked to orders and settlements.

## 🔗 Integrations
- **Order Management**: Triggers commission calculation on order creation.
- **Financial Settlement**: Uses commission records to calculate net payout to sellers.
- **Payment Management**: Ensures platform fee is split/reserved during payment processing.
