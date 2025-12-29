-- PostgreSQL Full-Text Search Setup for Catalog Entry
-- Run this migration to enable search functionality

-- 1. Create catalog_entry table
CREATE TABLE IF NOT EXISTS catalog_entry (
    id BIGSERIAL PRIMARY KEY,
    product_id VARCHAR(50) UNIQUE NOT NULL,
    category_id VARCHAR(50),
    name VARCHAR(255),
    description TEXT,
    search_vector tsvector,
    popularity INTEGER DEFAULT 0,
    view_count BIGINT DEFAULT 0,
    sales_count BIGINT DEFAULT 0,
    indexed_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- 2. Create indexes
CREATE INDEX IF NOT EXISTS idx_catalog_product_id ON catalog_entry(product_id);
CREATE INDEX IF NOT EXISTS idx_catalog_category_id ON catalog_entry(category_id);
CREATE INDEX IF NOT EXISTS idx_catalog_popularity ON catalog_entry(popularity DESC);

-- GIN index for full-text search (critical for performance)
CREATE INDEX IF NOT EXISTS idx_catalog_search_vector ON catalog_entry USING GIN(search_vector);

-- 3. Create function to update search_vector
CREATE OR REPLACE FUNCTION catalog_entry_search_vector_update() 
RETURNS TRIGGER AS $$
BEGIN
    -- Build weighted search vector
    -- A = highest priority (name)
    -- B = medium priority (description)
    NEW.search_vector := 
        setweight(to_tsvector('english', COALESCE(NEW.name, '')), 'A') ||
        setweight(to_tsvector('english', COALESCE(NEW.description, '')), 'B');
    
    NEW.updated_at := NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 4. Create trigger to auto-update search_vector
DROP TRIGGER IF EXISTS catalog_entry_search_vector_trigger ON catalog_entry;
CREATE TRIGGER catalog_entry_search_vector_trigger
BEFORE INSERT OR UPDATE ON catalog_entry
FOR EACH ROW EXECUTE FUNCTION catalog_entry_search_vector_update();

-- 5. Comments for documentation
COMMENT ON TABLE catalog_entry IS 'Search index for products using PostgreSQL full-text search';
COMMENT ON COLUMN catalog_entry.search_vector IS 'Auto-generated tsvector for full-text search';
COMMENT ON COLUMN catalog_entry.popularity IS 'Calculated as (sales_count * 10) + (view_count / 100)';
