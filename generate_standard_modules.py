import os
import shutil
import subprocess
import fileinput
import sys
import re

# Configuration
REPO_ROOT = "/Users/premkumar/homebase-backend.git"
GEN_SCRIPT = "/Users/premkumar/chenile-gen/app-gen/scripts/gen-service.sh"
TEMP_DIR = "/tmp/chenile_gen_std_temp"
ENTITY_MGMT_DIR = os.path.join(REPO_ROOT, "handmade-entity-managemet")
STM_MGMT_DIR = os.path.join(REPO_ROOT, "handmade-stm-management")

# Domains that were already processed by STM generation
# We can either hardcode this or detect it. Let's detect it by checking for existing handmade-{domain}-service folders
# that have STM configuration. Or simpler: check if we already generated them.
# But better: check if they have STM XMLs. If NOT, they are candidates for standard service.

def find_standard_domains():
    domain_dir = os.path.join(ENTITY_MGMT_DIR, "src/main/java/com/handmade/ecommerce/domain")
    stm_dir = os.path.join(STM_MGMT_DIR, "src/main/resources/stm/stm")
    candidates = []

    if not os.path.exists(domain_dir):
        print(f"Error: Domain directory not found at {domain_dir}")
        return candidates

    for domain in os.listdir(domain_dir):
        d_path = os.path.join(domain_dir, domain)
        if not os.path.isdir(d_path):
            continue
            
        # Check if this domain has an STM XML
        # Try {domain}-states.xml
        xml_file = f"{domain}-states.xml"
        has_stm = False
        if os.path.exists(os.path.join(stm_dir, xml_file)):
            has_stm = True
        else:
            # Try finding any XML that looks like it belongs to this domain
            for x in os.listdir(stm_dir):
                if x.startswith(domain) and x.endswith("-states.xml"):
                    has_stm = True
                    break
        
        if not has_stm:
            # Check if it has entities
            has_entities = False
            for f in os.listdir(d_path):
                if f.endswith(".java"):
                    has_entities = True
                    break
            
            if has_entities:
                candidates.append(domain)
            
    return candidates

def run_command(command, cwd=None):
    print(f"Running: {command}")
    result = subprocess.run(command, shell=True, cwd=cwd, capture_output=True, text=True)
    if result.returncode != 0:
        print(f"Error: {result.stderr}")
        raise Exception(f"Command failed: {command}")
    return result.stdout

def process_domain(domain):
    print(f"Processing standard domain: {domain}")
    
    # 1. Run Generator
    output_dir = os.path.join(TEMP_DIR, domain)
    if os.path.exists(output_dir):
        shutil.rmtree(output_dir)
    os.makedirs(output_dir)
    
    # -j for JPA support
    cmd = f"{GEN_SCRIPT} -d {output_dir} -j {domain}"
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
    
    # 2b. Copy Entities from Entity Management
    entity_package_path = f"com/handmade/ecommerce/{domain}/model"
    source_entity_dir = os.path.join(ENTITY_MGMT_DIR, f"src/main/java/com/handmade/ecommerce/domain/{domain}")
    target_entity_dir = os.path.join(target_api_dir, "src/main/java", entity_package_path)
    
    # Ensure target directory exists
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
			<artifactId>chenile-core</artifactId>
		</dependency>
	</dependencies>"""
    new_dep_block = """<dependencies>
		<dependency>
			<groupId>org.chenile</groupId>
			<artifactId>chenile-core</artifactId>
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

    # Remove explicit groupId
    content = re.sub(r'<groupId>com.handmade.ecommerce\.' + domain + r'</groupId>', '', content, count=1)
    
    with open(svc_pom, 'w') as f:
        f.write(content)
        
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
    domains = find_standard_domains()
    print(f"Found standard domains: {domains}")
    for d in domains:
        try:
            process_domain(d)
        except Exception as e:
            print(f"Failed to process {d}: {e}")
