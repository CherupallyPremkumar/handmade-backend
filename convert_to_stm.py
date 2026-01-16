import os
import shutil
import subprocess
import fileinput
import sys
import re

# Configuration
REPO_ROOT = "/Users/premkumar/homebase-backend.git"
GEN_SCRIPT = "/Users/premkumar/chenile-gen/app-gen/scripts/gen-workflow-service-custom.sh"
TEMP_DIR = "/tmp/chenile_gen_stm_convert_temp"
ENTITY_MGMT_DIR = os.path.join(REPO_ROOT, "handmade-entity-managemet")
STM_DIR = os.path.join(REPO_ROOT, "handmade-stm-management/src/main/resources/stm/stm")

# Mapping of domain to primary STM XML
# Based on previous analysis
CONVERSION_MAP = {
    "logistics": "delivery-attempt-states.xml",
    "adtech": "ad-campaign-states.xml",
    "analytics": "metric-definition-states.xml",
    "customer": "subscription-states.xml",
    "reviews": "product-review-states.xml",
    "qa": "product-question-states.xml",
    "settlement": "settlement-batch-states.xml",
    "iam": "role-states.xml"
}

def run_command(command, cwd=None):
    print(f"Running: {command}")
    result = subprocess.run(command, shell=True, cwd=cwd, capture_output=True, text=True)
    if result.returncode != 0:
        print(f"Error: {result.stderr}")
        raise Exception(f"Command failed: {command}")
    return result.stdout

def process_domain(domain, xml_file):
    print(f"Converting domain to STM: {domain}")
    
    xml_path = os.path.join(STM_DIR, xml_file)
    if not os.path.exists(xml_path):
        print(f"Error: XML not found: {xml_path}")
        return

    # 0. Delete existing standard module
    target_base = os.path.join(REPO_ROOT, f"handmade-{domain}-management")
    target_api_dir = os.path.join(target_base, f"handmade-{domain}-api")
    target_svc_dir = os.path.join(target_base, f"handmade-{domain}-service")
    
    # We need to preserve entities if they are already there?
    # Actually, we should re-copy them from entity-management to be safe and clean.
    # But wait, we already moved them to handmade-{domain}-api in previous steps.
    # So the source of truth is now handmade-{domain}-api.
    # We should BACKUP the entities from the current API module before deleting it.
    
    backup_dir = os.path.join(TEMP_DIR, f"{domain}_backup")
    if os.path.exists(backup_dir):
        shutil.rmtree(backup_dir)
    os.makedirs(backup_dir)
    
    current_entity_dir = os.path.join(target_api_dir, f"src/main/java/com/handmade/ecommerce/{domain}/model")
    if os.path.exists(current_entity_dir):
        shutil.copytree(current_entity_dir, os.path.join(backup_dir, "model"))
        print(f"Backed up entities to {backup_dir}")
    else:
        print(f"Warning: No entities found in {current_entity_dir}")

    # Delete modules
    if os.path.exists(target_api_dir):
        shutil.rmtree(target_api_dir)
    if os.path.exists(target_svc_dir):
        shutil.rmtree(target_svc_dir)
        
    # 1. Run Generator
    output_dir = os.path.join(TEMP_DIR, domain)
    if os.path.exists(output_dir):
        shutil.rmtree(output_dir)
    os.makedirs(output_dir)
    
    cmd = f"{GEN_SCRIPT} -d {output_dir} -x {xml_path} -j {domain}"
    run_command(cmd, cwd=REPO_ROOT)
    
    # Paths
    gen_base = os.path.join(output_dir, domain)
    
    # 2. Process API Module
    gen_api_dir = os.path.join(gen_base, f"{domain}-api")
    shutil.copytree(gen_api_dir, target_api_dir)
    
    # 2a. Delete generated Entity (it will be restored from backup)
    # The generator creates the entity based on XML. We want our backup.
    # But wait, the backup has the entity too.
    # We should overwrite the generated entity with our backup.
    
    # Restore entities
    target_entity_dir = os.path.join(target_api_dir, f"src/main/java/com/handmade/ecommerce/{domain}/model")
    if not os.path.exists(target_entity_dir):
        os.makedirs(target_entity_dir)
        
    if os.path.exists(os.path.join(backup_dir, "model")):
        for f in os.listdir(os.path.join(backup_dir, "model")):
            shutil.copy(os.path.join(backup_dir, "model", f), target_entity_dir)
            print(f"Restored entity {f}")

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
        
    # 5. Update Management POM (Should be already correct, but good to check)
    mgmt_pom = os.path.join(target_base, "pom.xml")
    with open(mgmt_pom, 'r') as f:
        content = f.read()
    
    if f"<module>handmade-{domain}-api</module>" not in content:
        modules_block = "<modules>"
        new_modules_block = f"<modules>\n        <module>handmade-{domain}-api</module>\n        <module>handmade-{domain}-service</module>"
        content = content.replace(modules_block, new_modules_block)
        
    with open(mgmt_pom, 'w') as f:
        f.write(content)

    print(f"Completed conversion for {domain}")

if __name__ == "__main__":
    for domain, xml in CONVERSION_MAP.items():
        try:
            process_domain(domain, xml)
        except Exception as e:
            print(f"Failed to process {domain}: {e}")
