import os
import re

domain_dir = "/Users/premkumar/homebase-backend.git/handmade-entity-managemet/src/main/java/com/handmade/ecommerce/domain"
stm_dir = "/Users/premkumar/homebase-backend.git/handmade-stm-management/src/main/resources/stm/stm"

mappings = []

for domain in os.listdir(domain_dir):
    d_path = os.path.join(domain_dir, domain)
    if not os.path.isdir(d_path):
        continue
        
    # Find entity extending AbstractJpaStateEntity
    state_entity = None
    for f in os.listdir(d_path):
        if f.endswith(".java"):
            with open(os.path.join(d_path, f), 'r') as file:
                content = file.read()
                if "extends AbstractJpaStateEntity" in content:
                    state_entity = f
                    break
    
    if not state_entity:
        continue
        
    # Find corresponding STM XML
    # Try {domain}-states.xml
    xml_file = f"{domain}-states.xml"
    if not os.path.exists(os.path.join(stm_dir, xml_file)):
        # Try finding any XML that looks like it belongs to this domain
        xml_file = None
        for x in os.listdir(stm_dir):
            if x.startswith(domain) and x.endswith("-states.xml"):
                xml_file = x
                break
    
    if xml_file:
        mappings.append({
            "domain": domain,
            "entity": os.path.join(d_path, state_entity),
            "xml": os.path.join(stm_dir, xml_file)
        })
    else:
        print(f"DEBUG: No XML found for domain {domain}")

for m in mappings:
    print(f"MATCH: {m['domain']}|{m['entity']}|{m['xml']}")
