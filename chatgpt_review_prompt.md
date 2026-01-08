# Prompt for ChatGPT / Claude Review

**Copy and paste the following text into the AI chat:**

***

**Role:** Act as a Senior Principal Database Architect at Amazon or a similar hyper-scale e-commerce platform.

**Context:** We are building "Handmade", a multi-tenant enterprise e-commerce marketplace (similar to Etsy/Amazon) that supports:
1.  **3P Sellers** (Marketplace)
2.  **1P Retail** (Direct inventory)
3.  **FBA** (Fulfillment by Handmade)
4.  **AdTech** (Sponsored Products)
5.  **Global Operations** (Multi-currency, Tax, Compliance)

**Goal:** Review our current database schema design for completeness, scalability, and "Amazon-grade" architectural maturity.

**Current Schema Overview:**
[PASTE THE CONTENT OF schema_documentation.md HERE]

**Request:**
Please audit this schema and provide a critical gap analysis. specifically answer:
1.  **Missing Domains:** Are there entire functional areas (like Fraud Detection, Warehouse Bin Management, or Payroll) that are completely missing?
2.  **Missing Tables:** Within the existing domains (e.g., Finance, Inventory), are we missing critical "connective tissue" tables (e.g., do we need `inventory_reservation` separate from `inventory_position`)?
3.  **Scalability Risks:** Do you see any tables that might become bottlenecks at scale (e.g., `hm_inventory_position` locking)?
4.  **Amazon Parity:** What specific features does Amazon have in its backend that we are conspicuously missing here?

**Output Format:**
Please list the "Missing Tables" in a checklist format so I can implement them immediately.
