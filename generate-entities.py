#!/usr/bin/env python3
"""
JPA Entity Generator from Liquibase Changelogs
Parses YAML changelogs and generates JPA entities with Chenile annotations
"""

import os
import yaml
import re
from pathlib import Path
from typing import Dict, List, Tuple, Optional

# Configuration
LIQUIBASE_DIR = "/Users/premkumar/Desktop/handmade/handmade-liquibase-management/liquibase-changelogs/src/main/resources/db/changelog"
PROJECT_ROOT = "/Users/premkumar/Desktop/handmade"

# Fields inherited from BaseJpaEntity (don't generate these)
BASE_JPA_FIELDS = {'id', 'created_time', 'last_modified_time', 'created_by', 'last_modified_by', 'tenant', 'version'}

# Fields inherited from AbstractJpaStateEntity (for STM entities)
STM_FIELDS = {'state_id', 'flow_id', 'state_entry_time', 'sla_yellow_date', 'sla_red_date', 'sla_tending_late', 'sla_late'}

# Liquibase type -> Java type mapping
TYPE_MAPPING = {
    'VARCHAR': 'String',
    'TEXT': 'String',
    'INTEGER': 'Integer',
    'BIGINT': 'Long',
    'DECIMAL': 'BigDecimal',
    'BOOLEAN': 'Boolean',
    'TIMESTAMP': 'Date',
    'DATE': 'Date',
    'UUID': 'String',  # Chenile uses String for IDs
    'JSONB': 'String',
    'JSON': 'String',
}

# Table -> Module mapping (COMPLETE - All ~200+ tables)
TABLE_MODULE_MAP = {
    # ========== PLATFORM GOVERNANCE ==========
    # Platform Core
    "hm_platform": ("handmade-platform-governance", "platform-core", "platform-core-domain", "com.handmade.ecommerce.platform"),
    "hm_platform_feature": ("handmade-platform-governance", "platform-core", "platform-core-domain", "com.handmade.ecommerce.platform"),
    "hm_platform_policy": ("handmade-platform-governance", "platform-core", "platform-core-domain", "com.handmade.ecommerce.platform"),
    "hm_platform_compliance": ("handmade-platform-governance", "platform-core", "platform-core-domain", "com.handmade.ecommerce.platform"),
    "hm_platform_activity_log": ("handmade-platform-governance", "platform-core", "platform-core-domain", "com.handmade.ecommerce.platform"),
    "hm_platform_audit_log": ("handmade-platform-governance", "platform-core", "platform-core-domain", "com.handmade.ecommerce.platform"),
    
    # IAM
    "hm_role": ("handmade-platform-governance", "iam", "iam-domain", "com.handmade.ecommerce.platform.iam"),
    "hm_permission": ("handmade-platform-governance", "iam", "iam-domain", "com.handmade.ecommerce.platform.iam"),
   "hm_role_permission": ("handmade-platform-governance", "iam", "iam-domain", "com.handmade.ecommerce.platform.iam"),
    "hm_user_role": ("handmade-platform-governance", "iam", "iam-domain", "com.handmade.ecommerce.platform.iam"),
    
    # Policy Engine
    "hm_policy_definition": ("handmade-platform-governance", "policy-engine", "policy-engine-domain", "com.handmade.ecommerce.platform.policy"),
    "hm_policy_rule": ("handmade-platform-governance", "policy-engine", "policy-engine-domain", "com.handmade.ecommerce.platform.policy"),
    "hm_policy_scope": ("handmade-platform-governance", "policy-engine", "policy-engine-domain", "com.handmade.ecommerce.platform.policy"),
    "hm_policy_decision": ("handmade-platform-governance", "policy-engine", "policy-engine-domain", "com.handmade.ecommerce.platform.policy"),
    "hm_policy_evaluation_log": ("handmade-platform-governance", "policy-engine", "policy-engine-domain", "com.handmade.ecommerce.platform.policy"),
    "hm_product_eligibility": ("handmade-platform-governance", "policy-engine", "policy-engine-domain", "com.handmade.ecommerce.platform.policy"),
    
    # Governance
    "hm_privacy_policy_ack": ("handmade-platform-governance", "governance", "governance-domain", "com.handmade.ecommerce.platform.governance"),
    "hm_gdpr_request": ("handmade-platform-governance", "governance", "governance-domain", "com.handmade.ecommerce.platform.governance"),
    "hm_audit_snapshot": ("handmade-platform-governance", "governance", "governance-domain", "com.handmade.ecommerce.platform.governance"),
    
    # Limits & Quotas
    "hm_limit_definition": ("handmade-platform-governance", "limits-quotas", "limits-quotas-domain", "com.handmade.ecommerce.platform.limits"),
    "hm_limit_scope": ("handmade-platform-governance", "limits-quotas", "limits-quotas-domain", "com.handmade.ecommerce.platform.limits"),
    "hm_limit_counter": ("handmade-platform-governance", "limits-quotas", "limits-quotas-domain", "com.handmade.ecommerce.platform.limits"),
    
    # Observability
    "hm_error_log": ("handmade-platform-governance", "observability", "observability-domain", "com.handmade.ecommerce.platform.observability"),
    "hm_metrics_snapshot": ("handmade-platform-governance", "observability", "observability-domain", "com.handmade.ecommerce.platform.observability"),
    "hm_activity_stream": ("handmade-platform-governance", "observability", "observability-domain", "com.handmade.ecommerce.platform.observability"),
    
    # Event Infrastructure
    "hm_event_queue": ("handmade-platform-governance", "event-infrastructure", "event-infrastructure-domain", "com.handmade.ecommerce.platform.event"),
    "hm_event_subscription": ("handmade-platform-governance", "event-infrastructure", "event-infrastructure-domain", "com.handmade.ecommerce.platform.event"),
    "hm_job_scheduler": ("handmade-platform-governance", "event-infrastructure", "event-infrastructure-domain", "com.handmade.ecommerce.platform.event"),
    "hm_workflow_task": ("handmade-platform-governance", "event-infrastructure", "event-infrastructure-domain", "com.handmade.ecommerce.platform.event"),
    
    # Notifications
    "hm_notification_template": ("handmade-platform-governance", "notifications", "notifications-domain", "com.handmade.ecommerce.platform.notification"),
    "hm_user_preference": ("handmade-platform-governance", "notifications", "notifications-domain", "com.handmade.ecommerce.platform.notification"),
    "hm_notification_log": ("handmade-platform-governance", "notifications", "notifications-domain", "com.handmade.ecommerce.platform.notification"),
    "hm_notification_queue": ("handmade-platform-governance", "notifications", "notifications-domain", "com.handmade.ecommerce.platform.notification"),
    
    # Compliance & Risk
    "hm_risk_signal": ("handmade-platform-governance", "compliance-risk", "compliance-risk-domain", "com.handmade.ecommerce.platform.risk"),
    "hm_fraud_case": ("handmade-platform-governance", "compliance-risk", "compliance-risk-domain", "com.handmade.ecommerce.platform.risk"),
    "hm_seller_trust_score": ("handmade-platform-governance", "compliance-risk", "compliance-risk-domain", "com.handmade.ecommerce.platform.risk"),
    "hm_compliance_document": ("handmade-platform-governance", "compliance-risk", "compliance-risk-domain", "com.handmade.ecommerce.platform.risk"),
    "hm_fraud_signal": ("handmade-platform-governance", "compliance-risk", "compliance-risk-domain", "com.handmade.ecommerce.platform.risk"),
    
    # ========== CATALOG & PRODUCT ==========
    # Catalog
    "hm_browse_node": ("handmade-catalog-product", "catalog", "catalog-domain", "com.handmade.ecommerce.catalog"),
    "hm_catalog_item": ("handmade-catalog-product", "catalog", "catalog-domain", "com.handmade.ecommerce.catalog"),
    "hm_product_browse_node_mapping": ("handmade-catalog-product", "catalog", "catalog-domain", "com.handmade.ecommerce.catalog"),
    "hm_attribute_definition": ("handmade-catalog-product", "catalog", "catalog-domain", "com.handmade.ecommerce.catalog"),
    "hm_catalog_item_attribute": ("handmade-catalog-product", "catalog", "catalog-domain", "com.handmade.ecommerce.catalog"),
    "hm_collection": ("handmade-catalog-product", "catalog", "catalog-domain", "com.handmade.ecommerce.catalog"),
    "hm_collection_product_mapping": ("handmade-catalog-product", "catalog", "catalog-domain", "com.handmade.ecommerce.catalog"),
    "hm_browse_node_attribute_rule": ("handmade-catalog-product", "catalog", "catalog-domain", "com.handmade.ecommerce.catalog"),
    "hm_taxonomy": ("handmade-catalog-product", "catalog", "catalog-domain", "com.handmade.ecommerce.catalog"),
    
    # Product
    "hm_product": ("handmade-catalog-product", "product", "product-domain", "com.handmade.ecommerce.catalog.product"),
    "hm_product_attribute_value": ("handmade-catalog-product", "product", "product-domain", "com.handmade.ecommerce.catalog.product"),
    "hm_product_media": ("handmade-catalog-product", "product", "product-domain", "com.handmade.ecommerce.catalog.product"),
    "hm_product_tags": ("handmade-catalog-product", "product", "product-domain", "com.handmade.ecommerce.catalog.product"),
    "hm_product_browse_node": ("handmade-catalog-product", "product", "product-domain", "com.handmade.ecommerce.catalog.product"),
    "hm_product_rich_media": ("handmade-catalog-product", "product", "product-domain", "com.handmade.ecommerce.catalog.product"),
    
    # Product Relationships
    "hm_product_relationship": ("handmade-catalog-product", "product-relationship", "product-relationship-domain", "com.handmade.ecommerce.catalog.relationship"),
    
    # Product Comparison
    "hm_product_comparison": ("handmade-catalog-product", "product-comparison", "product-comparison-domain", "com.handmade.ecommerce.catalog.comparison"),
    "hm_comparison_item": ("handmade-catalog-product", "product-comparison", "product-comparison-domain", "com.handmade.ecommerce.catalog.comparison"),
    
    # ========== SELLER / OFFER ==========
    # Seller Account
    "hm_seller_account": ("handmade-seller-offer", "seller-account", "seller-account-domain", "com.handmade.ecommerce.seller"),
    "hm_seller_kyc": ("handmade-seller-offer", "seller-account", "seller-account-domain", "com.handmade.ecommerce.seller"),
    "hm_seller_store": ("handmade-seller-offer", "seller-account", "seller-account-domain", "com.handmade.ecommerce.seller"),
    "hm_seller_tax_info": ("handmade-seller-offer", "seller-account", "seller-account-domain", "com.handmade.ecommerce.seller"),
    "hm_seller_tax_documents": ("handmade-seller-offer", "seller-account", "seller-account-domain", "com.handmade.ecommerce.seller"),
    "hm_seller_payment_info": ("handmade-seller-offer", "seller-account", "seller-account-domain", "com.handmade.ecommerce.seller"),
    "hm_seller_configuration": ("handmade-seller-offer", "seller-account", "seller-account-domain", "com.handmade.ecommerce.seller"),
    
    # Seller Performance
    "hm_seller_performance": ("handmade-seller-offer", "seller-performance", "seller-performance-domain", "com.handmade.ecommerce.seller.performance"),
    "hm_seller_feedback": ("handmade-seller-offer", "seller-performance", "seller-performance-domain", "com.handmade.ecommerce.seller.performance"),
    "hm_seller_goal": ("handmade-seller-offer", "seller-performance", "seller-performance-domain", "com.handmade.ecommerce.seller.performance"),
    "hm_seller_recommendation": ("handmade-seller-offer", "seller-performance", "seller-performance-domain", "com.handmade.ecommerce.seller.performance"),
    "hm_seller_action_item": ("handmade-seller-offer", "seller-performance", "seller-performance-domain", "com.handmade.ecommerce.seller.performance"),
    "hm_seller_badge": ("handmade-seller-offer", "seller-performance", "seller-performance-domain", "com.handmade.ecommerce.seller.performance"),
    
    # Offer
    "hm_offer": ("handmade-seller-offer", "offer", "offer-domain", "com.handmade.ecommerce.seller.offer"),
    "hm_offer_price": ("handmade-seller-offer", "offer", "offer-domain", "com.handmade.ecommerce.seller.offer"),
    "hm_offer_tier_price": ("handmade-seller-offer", "offer", "offer-domain", "com.handmade.ecommerce.seller.offer"),
    "hm_shipping_template": ("handmade-seller-offer", "offer", "offer-domain", "com.handmade.ecommerce.seller.offer"),
    "hm_shipping_rule": ("handmade-seller-offer", "offer", "offer-domain", "com.handmade.ecommerce.seller.offer"),
    "hm_buy_box_eligibility": ("handmade-seller-offer", "offer", "offer-domain", "com.handmade.ecommerce.seller.offer"),
    "hm_price_rule": ("handmade-seller-offer", "offer", "offer-domain", "com.handmade.ecommerce.seller.offer"),
    
    # Seller Onboarding
    "hm_seller_onboarding_case": ("handmade-seller-offer", "seller-onboarding", "seller-onboarding-domain", "com.handmade.ecommerce.seller.onboarding"),
    "hm_seller_onboarding_step": ("handmade-seller-offer", "seller-onboarding", "seller-onboarding-domain", "com.handmade.ecommerce.seller.onboarding"),
    "hm_seller_verification": ("handmade-seller-offer", "seller-onboarding", "seller-onboarding-domain", "com.handmade.ecommerce.seller.onboarding"),
    
    # Seller Messaging
    "hm_seller_conversation": ("handmade-seller-offer", "seller-messaging", "seller-messaging-domain", "com.handmade.ecommerce.seller.messaging"),
    "hm_seller_message": ("handmade-seller-offer", "seller-messaging", "seller-messaging-domain", "com.handmade.ecommerce.seller.messaging"),
    
    # ========== INVENTORY & LOGISTICS ==========
    # Inventory Core
    "hm_fulfillment_node": ("handmade-inventory-logistics", "inventory-core", "inventory-core-domain", "com.handmade.ecommerce.inventory"),
    "hm_inventory_ledger": ("handmade-inventory-logistics", "inventory-core", "inventory-core-domain", "com.handmade.ecommerce.inventory"),
    "hm_inventory_snapshot": ("handmade-inventory-logistics", "inventory-core", "inventory-core-domain", "com.handmade.ecommerce.inventory"),
    
    # Inbound Shipments
    "hm_inbound_shipment": ("handmade-inventory-logistics", "inbound-shipments", "inbound-shipments-domain", "com.handmade.ecommerce.inventory.inbound"),
    "hm_inbound_shipment_item": ("handmade-inventory-logistics", "inbound-shipments", "inbound-shipments-domain", "com.handmade.ecommerce.inventory.inbound"),
    
    # Vendor / PO
    "hm_vendor_po": ("handmade-inventory-logistics", "vendor-po", "vendor-po-domain", "com.handmade.ecommerce.inventory.vendor"),
    "hm_vendor_po_line": ("handmade-inventory-logistics", "vendor-po", "vendor-po-domain", "com.handmade.ecommerce.inventory.vendor"),
    
    # Inventory Operations
    "hm_inventory_health": ("handmade-inventory-logistics", "inventory-operations", "inventory-operations-domain", "com.handmade.ecommerce.inventory.operations"),
    "hm_inventory_reservation": ("handmade-inventory-logistics", "inventory-operations", "inventory-operations-domain", "com.handmade.ecommerce.inventory.operations"),
    "hm_inventory_position": ("handmade-inventory-logistics", "inventory-operations", "inventory-operations-domain", "com.handmade.ecommerce.inventory.operations"),
    "hm_pick_task": ("handmade-inventory-logistics", "inventory-operations", "inventory-operations-domain", "com.handmade.ecommerce.inventory.operations"),
    "hm_pack_task": ("handmade-inventory-logistics", "inventory-operations", "inventory-operations-domain", "com.handmade.ecommerce.inventory.operations"),
    "hm_fulfillment_bin": ("handmade-inventory-logistics", "inventory-operations", "inventory-operations-domain", "com.handmade.ecommerce.inventory.operations"),
    
    # Logistics
    "hm_carrier": ("handmade-inventory-logistics", "logistics", "logistics-domain", "com.handmade.ecommerce.inventory.logistics"),
    "hm_shipping_label": ("handmade-inventory-logistics", "logistics", "logistics-domain", "com.handmade.ecommerce.inventory.logistics"),
    "hm_delivery_attempt": ("handmade-inventory-logistics", "logistics", "logistics-domain", "com.handmade.ecommerce.inventory.logistics"),
    "hm_delivery_exception": ("handmade-inventory-logistics", "logistics", "logistics-domain", "com.handmade.ecommerce.inventory.logistics"),
    "hm_route_plan": ("handmade-inventory-logistics", "logistics", "logistics-domain", "com.handmade.ecommerce.inventory.logistics"),
    "hm_warehouse_zone": ("handmade-inventory-logistics", "logistics", "logistics-domain", "com.handmade.ecommerce.inventory.logistics"),
    
    # ========== CUSTOMER / SHOPPING ==========
    # Customer Core
    "hm_customer_profile": ("handmade-customer-shopping", "customer-core", "customer-core-domain", "com.handmade.ecommerce.customer"),
    "hm_customer_address": ("handmade-customer-shopping", "customer-core", "customer-core-domain", "com.handmade.ecommerce.customer"),
    "hm_customer_wallet": ("handmade-customer-shopping", "customer-core", "customer-core-domain", "com.handmade.ecommerce.customer"),
    "hm_subscription": ("handmade-customer-shopping", "customer-core", "customer-core-domain", "com.handmade.ecommerce.customer"),
    "hm_gift_card": ("handmade-customer-shopping", "customer-core", "customer-core-domain", "com.handmade.ecommerce.customer"),
    
    # Wishlist
    "hm_wishlist": ("handmade-customer-shopping", "wishlist", "wishlist-domain", "com.handmade.ecommerce.customer.wishlist"),
    "hm_wishlist_item": ("handmade-customer-shopping", "wishlist", "wishlist-domain", "com.handmade.ecommerce.customer.wishlist"),
    
    # Cart
    "hm_cart": ("handmade-customer-shopping", "cart", "cart-domain", "com.handmade.ecommerce.customer.cart"),
    "hm_cart_item": ("handmade-customer-shopping", "cart", "cart-domain", "com.handmade.ecommerce.customer.cart"),
    "hm_cart_saved_for_later": ("handmade-customer-shopping", "cart", "cart-domain", "com.handmade.ecommerce.customer.cart"),
    
    # Payment
    "hm_payment_transaction": ("handmade-customer-shopping", "payment", "payment-domain", "com.handmade.ecommerce.customer.payment"),
    "hm_payment_method": ("handmade-customer-shopping", "payment", "payment-domain", "com.handmade.ecommerce.customer.payment"),
    "hm_payment_authorization": ("handmade-customer-shopping", "payment", "payment-domain", "com.handmade.ecommerce.customer.payment"),
    "hm_payment_capture": ("handmade-customer-shopping", "payment", "payment-domain", "com.handmade.ecommerce.customer.payment"),
    "hm_payment_refund_transaction": ("handmade-customer-shopping", "payment", "payment-domain", "com.handmade.ecommerce.customer.payment"),
    
    # Q&A
    "hm_product_question": ("handmade-customer-shopping", "qa", "qa-domain", "com.handmade.ecommerce.customer.qa"),
    "hm_product_answer": ("handmade-customer-shopping", "qa", "qa-domain", "com.handmade.ecommerce.customer.qa"),
    "hm_qa_vote": ("handmade-customer-shopping", "qa", "qa-domain", "com.handmade.ecommerce.customer.qa"),
    
    # Reviews
    "hm_product_review": ("handmade-customer-shopping", "reviews", "reviews-domain", "com.handmade.ecommerce.customer.reviews"),
    "hm_product_review_vote": ("handmade-customer-shopping", "reviews", "reviews-domain", "com.handmade.ecommerce.customer.reviews"),
    "hm_review_moderation_log": ("handmade-customer-shopping", "reviews", "reviews-domain", "com.handmade.ecommerce.customer.reviews"),
    "hm_review_aggregation_snapshot": ("handmade-customer-shopping", "reviews", "reviews-domain", "com.handmade.ecommerce.customer.reviews"),
    
    # ========== ORDER & FINANCE ==========
    # Order Core
    "hm_order": ("handmade-order-finance", "order-core", "order-core-domain", "com.handmade.ecommerce.order"),
    "hm_order_line": ("handmade-order-finance", "order-core", "order-core-domain", "com.handmade.ecommerce.order"),
    "hm_shipment": ("handmade-order-finance", "order-core", "order-core-domain", "com.handmade.ecommerce.order"),
    "hm_shipment_item": ("handmade-order-finance", "order-core", "order-core-domain", "com.handmade.ecommerce.order"),
    "hm_return_request": ("handmade-order-finance", "order-core", "order-core-domain", "com.handmade.ecommerce.order"),
    "hm_refund": ("handmade-order-finance", "order-core", "order-core-domain", "com.handmade.ecommerce.order"),
    "hm_order_financial_event_link": ("handmade-order-finance", "order-core", "order-core-domain", "com.handmade.ecommerce.order"),
    
    # Finance
    "hm_financial_transaction": ("handmade-order-finance", "finance", "finance-domain", "com.handmade.ecommerce.order.finance"),
    "hm_payout": ("handmade-order-finance", "finance", "finance-domain", "com.handmade.ecommerce.order.finance"),
    "hm_fee_charge": ("handmade-order-finance", "finance", "finance-domain", "com.handmade.ecommerce.order.finance"),
    "hm_fee_definition": ("handmade-order-finance", "finance", "finance-domain", "com.handmade.ecommerce.order.finance"),
    "hm_fee_application": ("handmade-order-finance", "finance", "finance-domain", "com.handmade.ecommerce.order.finance"),
    "hm_tax_calculation": ("handmade-order-finance", "finance", "finance-domain", "com.handmade.ecommerce.order.finance"),
    "hm_settlement_batch": ("handmade-order-finance", "finance", "finance-domain", "com.handmade.ecommerce.order.finance"),
    
  # Tax
    "hm_tax_jurisdiction": ("handmade-order-finance", "tax", "tax-domain", "com.handmade.ecommerce.order.tax"),
    "hm_tax_rate": ("handmade-order-finance", "tax", "tax-domain", "com.handmade.ecommerce.order.tax"),
    
    # Dispute
    "hm_dispute": ("handmade-order-finance", "dispute", "dispute-domain", "com.handmade.ecommerce.order.dispute"),
    "hm_dispute_evidence": ("handmade-order-finance", "dispute", "dispute-domain", "com.handmade.ecommerce.order.dispute"),
    "hm_dispute_resolution": ("handmade-order-finance", "dispatch", "dispute-domain", "com.handmade.ecommerce.order.dispute"),
    
    # ========== PROMOTION / PRICING ==========
    # Promotion
    "hm_promotion": ("handmade-promotion-pricing", "promotion", "promotion-domain", "com.handmade.ecommerce.promotion"),
    "hm_promotion_rule": ("handmade-promotion-pricing", "promotion", "promotion-domain", "com.handmade.ecommerce.promotion"),
    "hm_coupon": ("handmade-promotion-pricing", "promotion", "promotion-domain", "com.handmade.ecommerce.promotion"),
    "hm_coupon_usage": ("handmade-promotion-pricing", "promotion", "promotion-domain", "com.handmade.ecommerce.promotion"),
    "hm_discount_application": ("handmade-promotion-pricing", "promotion", "promotion-domain", "com.handmade.ecommerce.promotion"),
    
    # Pricing Core
    "hm_price_history": ("handmade-promotion-pricing", "pricing-core", "pricing-core-domain", "com.handmade.ecommerce.promotion.pricing"),
    "hm_price_alert": ("handmade-promotion-pricing", "pricing-core", "pricing-core-domain", "com.handmade.ecommerce.promotion.pricing"),
    
    # Pricing Engine
    "hm_pricing_rule_definition": ("handmade-promotion-pricing", "pricing-engine", "pricing-engine-domain", "com.handmade.ecommerce.promotion.pricing.engine"),
    "hm_pricing_rule_condition": ("handmade-promotion-pricing", "pricing-engine", "pricing-engine-domain", "com.handmade.ecommerce.promotion.pricing.engine"),
    "hm_pricing_rule_action": ("handmade-promotion-pricing", "pricing-engine", "pricing-engine-domain", "com.handmade.ecommerce.promotion.pricing.engine"),
    "hm_pricing_priority": ("handmade-promotion-pricing", "pricing-engine", "pricing-engine-domain", "com.handmade.ecommerce.promotion.pricing.engine"),
    "hm_pricing_decision_log": ("handmade-promotion-pricing", "pricing-engine", "pricing-engine-domain", "com.handmade.ecommerce.promotion.pricing.engine"),
    
    # Loyalty
    "hm_loyalty_program": ("handmade-promotion-pricing", "loyalty", "loyalty-domain", "com.handmade.ecommerce.promotion.loyalty"),
    "hm_loyalty_points": ("handmade-promotion-pricing", "loyalty", "loyalty-domain", "com.handmade.ecommerce.promotion.loyalty"),
    "hm_referral_program": ("handmade-promotion-pricing", "loyalty", "loyalty-domain", "com.handmade.ecommerce.promotion.loyalty"),
    
    # AdTech
    "hm_ad_campaign": ("handmade-promotion-pricing", "adtech", "adtech-domain", "com.handmade.ecommerce.promotion.adtech"),
    "hm_sponsored_product": ("handmade-promotion-pricing", "adtech", "adtech-domain", "com.handmade.ecommerce.promotion.adtech"),
    "hm_ad_performance": ("handmade-promotion-pricing", "adtech", "adtech-domain", "com.handmade.ecommerce.promotion.adtech"),
    
    # Experiment Engine
    "hm_experiment_definition": ("handmade-promotion-pricing", "experiment-engine", "experiment-engine-domain", "com.handmade.ecommerce.promotion.experiment"),
    "hm_experiment_bucket": ("handmade-promotion-pricing", "experiment-engine", "experiment-engine-domain", "com.handmade.ecommerce.promotion.experiment"),
    "hm_experiment_audience": ("handmade-promotion-pricing", "experiment-engine", "experiment-engine-domain", "com.handmade.ecommerce.promotion.experiment"),
    "hm_experiment_assignment": ("handmade-promotion-pricing", "experiment-engine", "experiment-engine-domain", "com.handmade.ecommerce.promotion.experiment"),
    
    # ========== ANALYTICS & SEARCH ==========
    # Search
    "hm_search_query": ("handmade-analytics-search", "search", "search-domain", "com.handmade.ecommerce.analytics.search"),
    "hm_search_result": ("handmade-analytics-search", "search", "search-domain", "com.handmade.ecommerce.analytics.search"),
    "hm_search_filter": ("handmade-analytics-search", "search", "search-domain", "com.handmade.ecommerce.analytics.search"),
    "hm_search_synonym": ("handmade-analytics-search", "search", "search-domain", "com.handmade.ecommerce.analytics.search"),
    "hm_search_redirect": ("handmade-analytics-search", "search", "search-domain", "com.handmade.ecommerce.analytics.search"),
    
    # Ranking & Recommendations
    "hm_ranking_signal": ("handmade-analytics-search", "ranking-recommendations", "ranking-recommendations-domain", "com.handmade.ecommerce.analytics.ranking"),
    "hm_recommendation_graph": ("handmade-analytics-search", "ranking-recommendations", "ranking-recommendations-domain", "com.handmade.ecommerce.analytics.ranking"),
    
    # Behavioral Signals
    "hm_behavior_signal": ("handmade-analytics-search", "behavioral-signals", "behavioral-signals-domain", "com.handmade.ecommerce.analytics.behavior"),
    "hm_popularity_signal": ("handmade-analytics-search", "behavioral-signals", "behavioral-signals-domain", "com.handmade.ecommerce.analytics.behavior"),
    "hm_browse_history": ("handmade-analytics-search", "behavioral-signals", "behavioral-signals-domain", "com.handmade.ecommerce.analytics.behavior"),
    "hm_reco_experiment_result": ("handmade-analytics-search", "behavioral-signals", "behavioral-signals-domain", "com.handmade.ecommerce.analytics.behavior"),
    
    # Event Analytics
    "hm_analytics_event": ("handmade-analytics-search", "event-analytics", "event-analytics-domain", "com.handmade.ecommerce.analytics.event"),
    "hm_analytics_session": ("handmade-analytics-search", "event-analytics", "event-analytics-domain", "com.handmade.ecommerce.analytics.event"),
    
    # Metrics Management
    "hm_metric_definition": ("handmade-analytics-search", "metrics-management", "metrics-management-domain", "com.handmade.ecommerce.analytics.metrics"),
    "hm_metric_value": ("handmade-analytics-search", "metrics-management", "metrics-management-domain", "com.handmade.ecommerce.analytics.metrics"),
    
    # Search Index
    "hm_search_index_snapshot": ("handmade-analytics-search", "search-index", "search-index-domain", "com.handmade.ecommerce.analytics.index"),
    "hm_index_version": ("handmade-analytics-search", "search-index", "search-index-domain", "com.handmade.ecommerce.analytics.index"),
    
    # ========== CONTENT / LOCALIZATION ==========
    # CMS Core
    "hm_content_page": ("handmade-content-localization", "cms-core", "cms-core-domain", "com.handmade.ecommerce.content.cms"),
    "hm_content_slot": ("handmade-content-localization", "cms-core", "cms-core-domain", "com.handmade.ecommerce.content.cms"),
    "hm_content_asset": ("handmade-content-localization", "cms-core", "cms-core-domain", "com.handmade.ecommerce.content.cms"),
    "hm_cms_schema": ("handmade-content-localization", "cms-core", "cms-core-domain", "com.handmade.ecommerce.content.cms"),
    "hm_cms_entry": ("handmade-content-localization", "cms-core", "cms-core-domain", "com.handmade.ecommerce.content.cms"),
    "hm_cms_entry_version": ("handmade-content-localization", "cms-core", "cms-core-domain", "com.handmade.ecommerce.content.cms"),
    "hm_cms_asset_link": ("handmade-content-localization", "cms-core", "cms-core-domain", "com.handmade.ecommerce.content.cms"),
    
    # Localization
    "hm_currency": ("handmade-content-localization", "localization", "localization-domain", "com.handmade.ecommerce.content.localization"),
    "hm_region": ("handmade-content-localization", "localization", "localization-domain", "com.handmade.ecommerce.content.localization"),
    "hm_language": ("handmade-content-localization", "localization", "localization-domain", "com.handmade.ecommerce.content.localization"),
    "hm_translation": ("handmade-content-localization", "localization", "localization-domain", "com.handmade.ecommerce.content.localization"),
    "hm_platform_region_policy": ("handmade-content-localization", "localization", "localization-domain", "com.handmade.ecommerce.content.localization"),
    "hm_currency_conversion": ("handmade-content-localization", "localization", "localization-domain", "com.handmade.ecommerce.content.localization"),
    
    # SEO
    "hm_url_mapping": ("handmade-content-localization", "seo", "seo-domain", "com.handmade.ecommerce.content.seo"),
    "hm_meta_tag": ("handmade-content-localization", "seo", "seo-domain", "com.handmade.ecommerce.content.seo"),
    
    # ========== SUPPORT / OBSERVABILITY ==========
    # Case Management
    "hm_case_management": ("handmade-support-observability", "case-management", "case-management-domain", "com.handmade.ecommerce.support.casemanagement"),
    "hm_order_note": ("handmade-support-observability", "case-management", "case-management-domain", "com.handmade.ecommerce.support.casemanagement"),
    "hm_refund_resolution": ("handmade-support-observability", "case-management", "case-management-domain", "com.handmade.ecommerce.support.casemanagement"),
    
    # ========== INTEGRATION ==========
    # External Vendor
    "hm_external_vendor": ("handmade-integration", "external-vendor", "external-vendor-domain", "com.handmade.ecommerce.integration.vendor"),
    
    # Webhooks
    "hm_webhook": ("handmade-integration", "webhooks", "webhooks-domain", "com.handmade.ecommerce.integration.webhook"),
    
    # ETL
    "hm_etl_job": ("handmade-integration", "etl-pipelines", "etl-pipelines-domain", "com.handmade.ecommerce.integration.etl"),
}


def parse_liquibase_yaml(file_path: str) -> Optional[Dict]:
    """Parse a Liquibase YAML file and extract table information."""
    try:
        with open(file_path, 'r') as f:
            data = yaml.safe_load(f)
            
        if not data or 'databaseChangeLog' not in data:
            return None
            
        for change_set in data['databaseChangeLog']:
            if 'changeSet' in change_set:
                changes = change_set['changeSet'].get('changes', [])
                for change in changes:
                    if 'createTable' in change:
                        return change['createTable']
    except Exception as e:
        print(f"Error parsing {file_path}: {e}")
    return None


def is_stm_table(columns: List) -> bool:
    """Check if table is a state machine table."""
    column_names = {col.get('column', {}).get('name', '').lower() for col in columns}
    return 'state_id' in column_names and 'flow_id' in column_names


def map_liquibase_type_to_java(column_type: str) -> Tuple[str, Optional[int], Optional[int]]:
    """Map Liquibase type to Java type."""
    # Extract type and constraints
    match = re.match(r'(\w+)(?:\((\d+)(?:,\s*(\d+))?\))?', column_type.upper())
    if not match:
        return 'String', None, None
        
    base_type = match.group(1)
    length = int(match.group(2)) if match.group(2) else None
    scale = int(match.group(3)) if match.group(3) else None
    
    java_type = TYPE_MAPPING.get(base_type, 'String')
    return java_type, length, scale


def to_camel_case(snake_str: str) -> str:
    """Convert snake_case to camelCase."""
    components = snake_str.split('_')
    return components[0] + ''.join(x.title() for x in components[1:])


def to_pascal_case(snake_str: str) -> str:
    """Convert snake_case to PascalCase."""
    return ''.join(x.title() for x in snake_str.split('_'))


def generate_entity_class(table_name: str, columns: List, package: str) -> str:
    """Generate JPA entity class code."""
    # Entity name (remove hm_ prefix and convert to PascalCase)
    entity_name = to_pascal_case(table_name.replace('hm_', ''))
    
    # Check if STM entity
    is_stm = is_stm_table(columns)
    base_class = "AbstractJpaStateEntity" if is_stm else "BaseJpaEntity"
    
    # Filter out inherited fields
    excluded_fields = BASE_JPA_FIELDS | (STM_FIELDS if is_stm else set())
    
    # Generate imports
    imports = [
        "jakarta.persistence.*",
        "lombok.*",
        f"org.chenile.jpautils.entity.{base_class}",
    ]
    
    needs_decimal = False
    needs_date = False
    
    # Build field declarations
    field_declarations = []
    for col in columns:
        column_def = col.get('column', {})
        col_name = column_def.get('name', '').lower()
        
        # Skip inherited fields
        if col_name in excluded_fields:
            continue
            
        col_type = column_def.get('type', 'VARCHAR(255)')
        java_type, length, scale = map_liquibase_type_to_java(col_type)
        
        if java_type == 'BigDecimal':
            needs_decimal = True
        if java_type == 'Date':
            needs_date = True
            
        field_name = to_camel_case(col_name)
        
        # Build @Column annotation
        col_attrs = [f'name = "{col_name}"']
        
        nullable = column_def.get('constraints', {}).get('nullable', True)
        if not nullable:
            col_attrs.append('nullable = false')
            
        if length and java_type == 'String':
            col_attrs.append(f'length = {length}')
            
        if scale and java_type == 'BigDecimal':
            col_attrs.append(f'precision = {length}, scale = {scale}')
            
        unique = column_def.get('constraints', {}).get('unique', False)
        if unique:
            col_attrs.append('unique = true')
        
        column_annotation = f'@Column({", ".join(col_attrs)})'
        
        field_declarations.append(f'    {column_annotation}\n    private {java_type} {field_name};')
    
    # Add additional imports if needed
    if needs_decimal:
        imports.append("java.math.BigDecimal")
    if needs_date:
        imports.append("java.util.Date")
    
    # Generate class code
    code = f"""package {package}.entity;

{chr(10).join(f'import {imp};' for imp in sorted(set(imports)))}

/**
 * JPA Entity for {table_name}
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "{table_name}")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class {entity_name} extends {base_class} {{
    
{chr(10).join(field_declarations) if field_declarations else '    // All fields inherited from base class'}
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}}
"""
    return code


def generate_entities():
    """Main function to generate all entities."""
    print("=" * 80)
    print("JPA Entity Generator from Liquibase Changelogs")
    print("=" * 80)
    print()
    
    generated_count = 0
    skipped_count = 0
    
    # Walk through all YAML files
    for root, dirs, files in os.walk(LIQUIBASE_DIR):
        for file in files:
            if not file.endswith('.yaml'):
                continue
                
            file_path = os.path.join(root, file)
            table_def = parse_liquibase_yaml(file_path)
            
            if not table_def:
                continue
                
            table_name = table_def.get('tableName', '')
            
            # Skip if not in mapping
            if table_name not in TABLE_MODULE_MAP:
                print(f"⏭️  Skipping {table_name} (no module mapping)")
                skipped_count += 1
                continue
                
            # Get module mapping
            parent_module, sub_module, domain_module, package = TABLE_MODULE_MAP[table_name]
            
            # Get columns
            columns = table_def.get('columns', [])
            if not columns:
                print(f"⚠️  No columns found for {table_name}")
                continue
            
            # Generate entity code
            entity_code = generate_entity_class(table_name, columns, package)
            
            # Entity name
            entity_name = to_pascal_case(table_name.replace('hm_', ''))
            
            # Create directory path
            entity_dir = os.path.join(
                PROJECT_ROOT,
                parent_module,
                sub_module,
                domain_module,
                "src/main/java",
                package.replace('.', '/'),
                "entity"
            )
            
            os.makedirs(entity_dir, exist_ok=True)
            
            # Write entity file
            entity_file = os.path.join(entity_dir, f"{entity_name}.java")
            
            with open(entity_file, 'w') as f:
                f.write(entity_code)
            
            print(f"✅ Generated {entity_name} -> {parent_module}/{sub_module}")
            generated_count += 1
    
    print()
    print("=" * 80)
    print(f"✅ Generation Complete!")
    print(f"   Generated: {generated_count} entities")
    print(f"   Skipped: {skipped_count} tables")
    print("=" * 80)
    print()
    print("Next Steps:")
    print("  1. Review generated entities")
    print("  2. Add relationships (@OneToMany, @ManyToOne)")
    print("  3. Run: mvn clean install -DskipTests")
    print()


if __name__ == '__main__':
    generate_entities()
