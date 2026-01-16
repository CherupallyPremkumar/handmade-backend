import os

REPO_ROOT = "/Users/premkumar/homebase-backend.git"
ENTITY_MGMT_DIR = os.path.join(REPO_ROOT, "handmade-entity-managemet")
STM_DIR = os.path.join(REPO_ROOT, "handmade-stm-management/src/main/resources/stm/stm")

# List of domains we generated as standard
STANDARD_DOMAINS = [
    "logistics", "adtech", "analytics", "comparison", "customer", 
    "reviews", "qa", "catalog", "settlement", "shipping", "iam" # Added iam as user mentioned it
]

def check_entities():
    candidates = []
    
    for domain in STANDARD_DOMAINS:
        # Check in the generated module first (since we moved them)
        # But wait, we moved them to handmade-{domain}-api.
        # Let's check there.
        
        api_dir = os.path.join(REPO_ROOT, f"handmade-{domain}-management", f"handmade-{domain}-api", "src/main/java/com/handmade/ecommerce", domain, "model")
        
        if not os.path.exists(api_dir):
            # Fallback to entity management if not found (e.g. if we haven't moved them yet or something)
            api_dir = os.path.join(ENTITY_MGMT_DIR, "src/main/java/com/handmade/ecommerce/domain", domain)
            
        if not os.path.exists(api_dir):
            print(f"Warning: Could not find entity dir for {domain}")
            continue
            
        for f in os.listdir(api_dir):
            if f.endswith(".java"):
                with open(os.path.join(api_dir, f), 'r') as file:
                    content = file.read()
                    if "extends AbstractJpaStateEntity" in content:
                        print(f"Found STM Entity: {domain} -> {f}")
                        candidates.append((domain, f[:-5]))
                        
    return candidates

def find_xml(domain, entity_name):
    # Try to find a matching XML
    # 1. {domain}-states.xml
    # 2. {entity}-states.xml (kebab-case)
    # 3. Any xml in stm dir containing domain or entity name
    
    # Helper to camel to kebab
    kebab_entity = ''.join(['-'+c.lower() if c.isupper() else c for c in entity_name]).lstrip('-')
    
    patterns = [
        f"{domain}-states.xml",
        f"{kebab_entity}-states.xml",
        f"{domain}.xml"
    ]
    
    for p in patterns:
        if os.path.exists(os.path.join(STM_DIR, p)):
            return p
            
    # Fuzzy search
    for x in os.listdir(STM_DIR):
        if domain in x or kebab_entity in x:
            if x.endswith(".xml"):
                return x
                
    return None

if __name__ == "__main__":
    candidates = check_entities()
    print("\n--- Matching XMLs ---")
    for domain, entity in candidates:
        xml = find_xml(domain, entity)
        print(f"Domain: {domain}, Entity: {entity}, XML: {xml}")
