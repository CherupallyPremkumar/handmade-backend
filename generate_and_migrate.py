import os
import shutil
import subprocess
import fileinput
import sys
import re

# Configuration
REPO_ROOT = "/Users/premkumar/homebase-backend.git"
GEN_SCRIPT = "/Users/premkumar/chenile-gen/app-gen/scripts/gen-workflow-service-custom.sh"
TEMP_DIR = "/tmp/chenile_gen_temp"
ENTITY_MGMT_DIR = os.path.join(REPO_ROOT, "handmade-entity-managemet")
STM_MGMT_DIR = os.path.join(REPO_ROOT, "handmade-stm-management")

# Helper to find domains, entities, and XMLs
def find_domain_mappings():
    domain_dir = os.path.join(ENTITY_MGMT_DIR, "src/main/java/com/handmade/ecommerce/domain")
    stm_dir = os.path.join(STM_MGMT_DIR, "src/main/resources/stm/stm")
    mappings = []

    if not os.path.exists(domain_dir):
        print(f"Error: Domain directory not found at {domain_dir}")
        return mappings

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
                        state_entity = f[:-5] # Remove .java
                        break
        
        if not state_entity:
            continue
            
        # Find corresponding STM XML
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
                "entity_name": state_entity,
                "xml_path": os.path.join(stm_dir, xml_file)
            })
            
    return mappings

def run_command(command, cwd=None):
    print(f"Running: {command}")
    result = subprocess.run(command, shell=True, cwd=cwd, capture_output=True, text=True)
    if result.returncode != 0:
        print(f"Error: {result.stderr}")
        raise Exception(f"Command failed: {command}")
    return result.stdout

def replace_in_file(file_path, search, replace):
    with fileinput.FileInput(file_path, inplace=True) as file:
        for line in file:
            print(line.replace(search, replace), end='')

def process_domain(domain_info):
    domain = domain_info['domain']
    entity_name = domain_info['entity_name']
    xml_path = domain_info['xml_path']
    
    print(f"Processing domain: {domain}")
    
    # 1. Run Generator
    output_dir = os.path.join(TEMP_DIR, domain)
    if os.path.exists(output_dir):
        shutil.rmtree(output_dir)
    os.makedirs(output_dir)
    
    cmd = f"{GEN_SCRIPT} -d {output_dir} -x {xml_path} -j {domain}"
    run_command(cmd, cwd=REPO_ROOT)
    
    # Paths
    gen_base = os.path.join(output_dir, domain)
    target_base = os.path.join(REPO_ROOT, f"handmade-{domain}-management")
    
    # 2. Process API Module
    gen_api_dir = os.path.join(gen_base, f"{domain}-api")
    target_api_dir = os.path.join(target_base, f"handmade-{domain}-api")
    
    if os.path.exists(target_api_dir):
        shutil.rmtree(target_api_dir)
    shutil.copytree(gen_api_dir, target_api_dir)
    
    # 2a. Delete generated Entity
    entity_package_path = f"com/handmade/ecommerce/{domain}/model"
    gen_entity_file = os.path.join(target_api_dir, "src/main/java", entity_package_path, f"{entity_name}.java")
    if os.path.exists(gen_entity_file):
        os.remove(gen_entity_file)
        print(f"Deleted generated entity: {gen_entity_file}")

    # 2b. Copy Entities from Entity Management
    source_entity_dir = os.path.join(ENTITY_MGMT_DIR, f"src/main/java/com/handmade/ecommerce/domain/{domain}")
    target_entity_dir = os.path.join(target_api_dir, "src/main/java", entity_package_path)
    
    # Ensure target directory exists (it should, but just in case)
    if not os.path.exists(target_entity_dir):
        os.makedirs(target_entity_dir)

    if os.path.exists(source_entity_dir):
        for f in os.listdir(source_entity_dir):
            if f.endswith(".java"):
                shutil.copy(os.path.join(source_entity_dir, f), target_entity_dir)
                print(f"Copied entity {f} to {target_entity_dir}")
                
                # Update package declaration in copied file
                copied_file = os.path.join(target_entity_dir, f)
                with fileinput.FileInput(copied_file, inplace=True) as file:
                    for line in file:
                        if line.startswith("package "):
                            print(f"package com.handmade.ecommerce.{domain}.model;")
                        else:
                            print(line, end='')

    
    # 2c. Update API POM
    api_pom = os.path.join(target_api_dir, "pom.xml")
    
    with open(api_pom, 'r') as f:
        content = f.read()

    content = content.replace(f"<artifactId>{domain}-api</artifactId>", f"<artifactId>handmade-{domain}-api</artifactId>")
    
    # Replace parent
    new_parent_block = f"""<parent>
        <groupId>com.handmade.ecommerce</groupId>
        <artifactId>handmade-{domain}-management</artifactId>
        <version>${{revision}}</version>
        <relativePath>../pom.xml</relativePath>
    </parent>"""
    content = re.sub(r'<parent>.*?</parent>', new_parent_block, content, flags=re.DOTALL)
    
    # Add dependency
    dep_block = """<dependencies>
		<dependency>
			<groupId>org.chenile</groupId>
			<artifactId>workflow-api</artifactId>
		</dependency>
	</dependencies>"""
    new_dep_block = """<dependencies>
		<dependency>
			<groupId>org.chenile</groupId>
			<artifactId>workflow-api</artifactId>
		</dependency>
        <dependency>
            <groupId>org.chenile</groupId>
            <artifactId>jpa-utils</artifactId>
        </dependency>
        <dependency>
            <groupId>jakarta.persistence</groupId>
            <artifactId>jakarta.persistence-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
	</dependencies>"""
    content = content.replace(dep_block, new_dep_block)
    
    # Remove explicit groupId
    content = re.sub(r'<groupId>com.handmade.ecommerce\..*?</groupId>', '', content)
    
    with open(api_pom, 'w') as f:
        f.write(content)

    # 3. Process Service Module
    gen_svc_dir = os.path.join(gen_base, f"{domain}-service")
    target_svc_dir = os.path.join(target_base, f"handmade-{domain}-service")
    
    if os.path.exists(target_svc_dir):
        shutil.rmtree(target_svc_dir)
    shutil.copytree(gen_svc_dir, target_svc_dir)
    
    # 3a. Update Service POM
    svc_pom = os.path.join(target_svc_dir, "pom.xml")
    with open(svc_pom, 'r') as f:
        content = f.read()
        
    content = re.sub(r'<parent>.*?</parent>', new_parent_block, content, flags=re.DOTALL)
    content = content.replace(f"<artifactId>{domain}-service</artifactId>", f"<artifactId>handmade-{domain}-service</artifactId>")
    
    # Fix dependency on API
    content = content.replace(f"<artifactId>{domain}-api</artifactId>", f"<artifactId>handmade-{domain}-api</artifactId>")
    
    dep_regex = r'<dependency>\s*<groupId>.*?</groupId>\s*<artifactId>handmade-' + domain + r'-api</artifactId>\s*</dependency>'
    new_dep = f"""<dependency>
            <groupId>com.handmade.ecommerce</groupId>
            <artifactId>handmade-{domain}-api</artifactId>
            <version>${{revision}}</version>
        </dependency>"""
    
    content = re.sub(dep_regex, new_dep, content, flags=re.DOTALL)

    # Fix plugin version
    content = content.replace("<artifactId>stm-generate-puml</artifactId>", "<artifactId>stm-generate-puml</artifactId>\n                <version>${revision}</version>")
    
    # Remove explicit groupId
    content = re.sub(r'<groupId>com.handmade.ecommerce\.' + domain + r'</groupId>', '', content, count=1)
    
    with open(svc_pom, 'w') as f:
        f.write(content)
        
    # 4. Refactor Imports in ALL files
    # Replace com.handmade.ecommerce.{domain}.model.{Entity} -> com.handmade.ecommerce.domain.{domain}.{Entity}
    # Wait, we moved the entities to com.handmade.ecommerce.{domain}.model, so the generated code should be correct!
    # But we need to check if there are other imports that need fixing.
    # The generated code uses com.handmade.ecommerce.{domain}.model.{Entity}
    # And we moved the entities to that package.
    # So we don't need to change imports for the main entity.
    # However, if the entities refer to other entities in different domains, those imports might be broken.
    # For now, let's assume cross-domain references are handled via IDs or fully qualified names, or we'll fix them later.

    # 5. Update Management POM
    mgmt_pom = os.path.join(target_base, "pom.xml")
    with open(mgmt_pom, 'r') as f:
        content = f.read()
    
    if f"<module>handmade-{domain}-api</module>" not in content:
        modules_block = "<modules>"
        new_modules_block = f"<modules>\n        <module>handmade-{domain}-api</module>\n        <module>handmade-{domain}-service</module>"
        content = content.replace(modules_block, new_modules_block)
        
    with open(mgmt_pom, 'w') as f:
        f.write(content)

    print(f"Completed generation for {domain}")

if __name__ == "__main__":
    mappings = find_domain_mappings()
    for m in mappings:
        try:
            process_domain(m)
        except Exception as e:
            print(f"Failed to process {m['domain']}: {e}")
