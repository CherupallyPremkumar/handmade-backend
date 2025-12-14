-- Discount Rules - Sample Data
-- These can be managed via API - no code changes needed!

-- Bulk Discount: Buy 10+ items, get 10% off
INSERT INTO discount_rules 
(rule_name, rule_type, condition_field, condition_operator, condition_value, 
 discount_type, discount_value, priority, scope, stackable, description, is_active, created_at, updated_at)
VALUES 
('Bulk Discount 10%', 'BULK_DISCOUNT', 'quantity', '>=', '10', 
 'PERCENTAGE', 10.00, 10, 'GLOBAL', true, 'Buy 10 or more items and get 10% off', true, NOW(), NOW());

-- VIP Customer Discount
INSERT INTO discount_rules 
(rule_name, rule_type, condition_field, condition_operator, condition_value, 
 discount_type, discount_value, priority, scope, stackable, description, is_active, created_at, updated_at)
VALUES 
('VIP Customer Discount', 'CUSTOMER_SEGMENT', 'customerSegment', '==', 'VIP', 
 'PERCENTAGE', 5.00, 20, 'GLOBAL', true, 'VIP customers get 5% off', true, NOW(), NOW());

-- First Order Discount
INSERT INTO discount_rules 
(rule_name, rule_type, condition_field, condition_operator, condition_value, 
 discount_type, discount_value, priority, scope, stackable, description, is_active, created_at, updated_at)
VALUES 
('First Order Discount', 'FIRST_ORDER', 'isFirstOrder', '==', 'true', 
 'PERCENTAGE', 10.00, 30, 'GLOBAL', true, 'First order gets 10% off', true, NOW(), NOW());

-- Bulk Discount Tier 2
INSERT INTO discount_rules 
(rule_name, rule_type, condition_field, condition_operator, condition_value, 
 discount_type, discount_value, priority, scope, stackable, max_discount_amount, description, is_active, created_at, updated_at)
VALUES 
('Bulk Discount 15%', 'BULK_DISCOUNT', 'quantity', '>=', '50', 
 'PERCENTAGE', 15.00, 5, 'GLOBAL', false, 5000.00, 'Buy 50+ items and get 15% off (max 5000)', true, NOW(), NOW());


-- Tax Rules - Sample Data

-- India GST 18%
INSERT INTO tax_rules 
(region_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('IN', 'GST', 18.00, 'EXCLUSIVE', 100, 'India Goods and Services Tax', true, NOW(), NOW());

-- USA Federal + Average State Tax 7.25%
INSERT INTO tax_rules 
(region_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('US', 'Sales Tax', 7.25, 'EXCLUSIVE', 100, 'USA Average Sales Tax', true, NOW(), NOW());

-- UK VAT 20%
INSERT INTO tax_rules 
(region_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('GB', 'VAT', 20.00, 'INCLUSIVE', 100, 'UK Value Added Tax', true, NOW(), NOW());

-- Germany VAT 19%
INSERT INTO tax_rules 
(region_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('DE', 'MwSt', 19.00, 'INCLUSIVE', 100, 'Germany Value Added Tax', true, NOW(), NOW());

-- Canada GST 5%
INSERT INTO tax_rules 
(region_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('CA', 'GST', 5.00, 'EXCLUSIVE', 100, 'Canada Goods and Services Tax', true, NOW(), NOW());

-- Singapore GST 8%
INSERT INTO tax_rules 
(region_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('SG', 'GST', 8.00, 'EXCLUSIVE', 100, 'Singapore Goods and Services Tax', true, NOW(), NOW());

-- UAE VAT 5%
INSERT INTO tax_rules 
(region_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('AE', 'VAT', 5.00, 'EXCLUSIVE', 100, 'UAE Value Added Tax', true, NOW(), NOW());
