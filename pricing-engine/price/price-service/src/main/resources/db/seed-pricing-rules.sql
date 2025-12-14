-- System Discount Rules - Sample Data
-- Insert these into your database to enable dynamic rules

-- Bulk Discount: Buy 10+ items, get 10% off
INSERT INTO system_discount_rules 
(rule_name, rule_type, condition_field, condition_operator, condition_value, 
 discount_type, discount_value, priority, scope, stackable, description, is_active, created_at, updated_at)
VALUES 
('Bulk Discount 10%', 'BULK_DISCOUNT', 'quantity', '>=', '10', 
 'PERCENTAGE', 10.00, 10, 'GLOBAL', true, 'Buy 10 or more items and get 10% off', true, NOW(), NOW());

-- VIP Customer Discount: 5% off for VIP customers
INSERT INTO system_discount_rules 
(rule_name, rule_type, condition_field, condition_operator, condition_value, 
 discount_type, discount_value, priority, scope, stackable, description, is_active, created_at, updated_at)
VALUES 
('VIP Customer Discount', 'CUSTOMER_SEGMENT', 'customerSegment', '==', 'VIP', 
 'PERCENTAGE', 5.00, 20, 'GLOBAL', true, 'VIP customers get 5% off', true, NOW(), NOW());

-- First Order Discount: 10% off for first-time customers
INSERT INTO system_discount_rules 
(rule_name, rule_type, condition_field, condition_operator, condition_value, 
 discount_type, discount_value, priority, scope, stackable, description, is_active, created_at, updated_at)
VALUES 
('First Order Discount', 'FIRST_ORDER', 'isFirstOrder', '==', 'true', 
 'PERCENTAGE', 10.00, 30, 'GLOBAL', true, 'First order gets 10% off', true, NOW(), NOW());

-- Premium Customer Discount: 7% off for PREMIUM customers
INSERT INTO system_discount_rules 
(rule_name, rule_type, condition_field, condition_operator, condition_value, 
 discount_type, discount_value, priority, scope, stackable, description, is_active, created_at, updated_at)
VALUES 
('Premium Customer Discount', 'CUSTOMER_SEGMENT', 'customerSegment', '==', 'PREMIUM', 
 'PERCENTAGE', 7.00, 25, 'GLOBAL', true, 'Premium customers get 7% off', true, NOW(), NOW());

-- Bulk Discount Tier 2: Buy 50+ items, get 15% off
INSERT INTO system_discount_rules 
(rule_name, rule_type, condition_field, condition_operator, condition_value, 
 discount_type, discount_value, priority, scope, stackable, max_discount_amount, description, is_active, created_at, updated_at)
VALUES 
('Bulk Discount 15%', 'BULK_DISCOUNT', 'quantity', '>=', '50', 
 'PERCENTAGE', 15.00, 5, 'GLOBAL', false, 5000.00, 'Buy 50+ items and get 15% off (max ₹5000)', true, NOW(), NOW());


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

-- USA California 7.25%
INSERT INTO tax_rules 
(region_code, state_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('US', 'CA', 'CA Sales Tax', 7.25, 'EXCLUSIVE', 50, 'California Sales Tax', true, NOW(), NOW());

-- USA New York 8.0%
INSERT INTO tax_rules 
(region_code, state_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('US', 'NY', 'NY Sales Tax', 8.00, 'EXCLUSIVE', 50, 'New York Sales Tax', true, NOW(), NOW());

-- UK VAT 20%
INSERT INTO tax_rules 
(region_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('GB', 'VAT', 20.00, 'INCLUSIVE', 100, 'UK Value Added Tax', true, NOW(), NOW());

-- Germany VAT 19%
INSERT INTO tax_rules 
(region_code, tax_name, tax_rate, tax_type, priority, description, is_active, created_at, updated_at)
VALUES 
('DE', 'MwSt', 19.00, 'INCLUSIVE', 100, 'Germany Value Added Tax (Mehrwertsteuer)', true, NOW(), NOW());

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
