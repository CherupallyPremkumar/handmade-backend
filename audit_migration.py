import os
import re

REPO_ROOT = "/Users/premkumar/homebase-backend.git"
ENTITY_MGMT_DIR = os.path.join(REPO_ROOT, "handmade-entity-managemet/src/main/java/com/handmade/ecommerce/domain")
STM_XML_DIR = os.path.join(REPO_ROOT, "handmade-stm-management/src/main/resources/stm/stm")

def get_all_domains():
    if not os.path.exists(ENTITY_MGMT_DIR):
        return []
    return [d for d in os.listdir(ENTITY_MGMT_DIR) if os.path.isdir(os.path.join(ENTITY_MGMT_DIR, d))]

def get_entities_in_source(domain):
    path = os.path.join(ENTITY_MGMT_DIR, domain)
    if not os.path.exists(path):
        return []
    return [f for f in os.listdir(path) if f.endswith(".java")]

def get_entities_in_target(domain):
    # Target path: handmade-{domain}-management/handmade-{domain}-api/src/main/java/com/handmade/ecommerce/{domain}/model
    # Note: Some domains might use 'model' package, others might have used 'domain' if I didn't standardize it perfectly, 
    # but my scripts used 'model'.
    
    # Special case handling if package naming varied, but let's assume standard first.
    target_path = os.path.join(REPO_ROOT, f"handmade-{domain}-management", f"handmade-{domain}-api", 
                               "src/main/java/com/handmade/ecommerce", domain, "model")
    
    if not os.path.exists(target_path):
        # Try finding it if package is slightly different
        base_api = os.path.join(REPO_ROOT, f"handmade-{domain}-management", f"handmade-{domain}-api", "src/main/java")
        if os.path.exists(base_api):
            for root, dirs, files in os.walk(base_api):
                if "model" in root.split(os.sep):
                    return [f for f in files if f.endswith(".java")]
        return []
        
    return [f for f in os.listdir(target_path) if f.endswith(".java")]

def check_stm_xml(domain):
    # Look for {domain}-states.xml or similar
    if not os.path.exists(STM_XML_DIR):
        return None
        
    for f in os.listdir(STM_XML_DIR):
        if f.endswith(".xml"):
            if f == f"{domain}-states.xml":
                return f
            if f.startswith(domain) and "states" in f:
                return f
            # Check for entity names if domain doesn't match
            # (This is harder without knowing all entity names, but we can do a fuzzy check)
    return None

def is_stm_module(domain):
    # Check service module for Configuration class with STM
    service_dir = os.path.join(REPO_ROOT, f"handmade-{domain}-management", f"handmade-{domain}-service", 
                               "src/main/java/com/handmade/ecommerce", domain, "configuration")
    
    if not os.path.exists(service_dir):
        return False
        
    for f in os.listdir(service_dir):
        if f.endswith("Configuration.java"):
            try:
                with open(os.path.join(service_dir, f), 'r') as file:
                    content = file.read()
                    if "STMFlowStore" in content or "STMImpl" in content:
                        return True
            except:
                pass
    return False

def audit():
    domains = get_all_domains()
    print(f"{'DOMAIN':<20} | {'ENTITIES (Src/Tgt)':<20} | {'STM XML':<30} | {'IS STM MODULE':<15} | {'STATUS'}")
    print("-" * 110)
    
    for domain in sorted(domains):
        src_entities = get_entities_in_source(domain)
        tgt_entities = get_entities_in_target(domain)
        stm_xml = check_stm_xml(domain)
        is_stm = is_stm_module(domain)
        
        status = "OK"
        if len(src_entities) > 0 and len(tgt_entities) == 0:
            status = "MISSING IN TARGET"
        elif len(src_entities) > len(tgt_entities):
             # This might happen if we deleted some default ones or if some were missed
             # But usually we copied all.
             status = "PARTIAL MOVE?"
        
        # Check if it SHOULD be STM
        # If it has STM XML but is_stm is False -> Problem
        if stm_xml and not is_stm:
            status = "STM MISMATCH"
            
        xml_display = stm_xml if stm_xml else "None"
        entity_count = f"{len(src_entities)} / {len(tgt_entities)}"
        
        print(f"{domain:<20} | {entity_count:<20} | {xml_display:<30} | {str(is_stm):<15} | {status}")

if __name__ == "__main__":
    audit()
