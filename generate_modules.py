import os
import shutil
import subprocess
import fileinput
import sys

# Configuration
REPO_ROOT = "/Users/premkumar/homebase-backend.git"
GEN_SCRIPT = "/Users/premkumar/chenile-gen/app-gen/scripts/gen-workflow-service-custom.sh"
TEMP_DIR = "/tmp/chenile_gen_temp"

# Mappings (We will populate this dynamically or use a specific one for testing)
# Format: {'domain': 'product', 'entity_path': '...', 'xml_path': '...'}
TARGET_DOMAINS = [
    {
        'domain': 'product',
        'entity_name': 'Product',
        'xml_path': '/Users/premkumar/homebase-backend.git/handmade-stm-management/src/main/resources/stm/stm/product-states.xml'
    }
]

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
    
    # 2b. Update API POM
    api_pom = os.path.join(target_api_dir, "pom.xml")
    replace_in_file(api_pom, f"<artifactId>{domain}-api</artifactId>", f"<artifactId>handmade-{domain}-api</artifactId>")
    # Replace parent
    parent_block = f"""<parent>
		<groupId>com.handmade.ecommerce.{domain}</groupId>
		<artifactId>{domain}-parent</artifactId>
		<version>${{revision}}</version>
		<relativePath>../pom.xml</relativePath>
	</parent>"""
    new_parent_block = f"""<parent>
        <groupId>com.handmade.ecommerce</groupId>
        <artifactId>handmade-{domain}-management</artifactId>
        <version>${{revision}}</version>
        <relativePath>../pom.xml</relativePath>
    </parent>"""
    # Simple string replace might fail due to whitespace, so we do a more robust read/write if needed.
    # For now, let's try reading the whole file and replacing.
    with open(api_pom, 'r') as f:
        content = f.read()
    
    # Regex replacement for parent might be safer
    import re
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
            <groupId>com.handmade.ecommerce</groupId>
            <artifactId>handmade-entity-managemet</artifactId>
            <version>${revision}</version>
        </dependency>
	</dependencies>"""
    content = content.replace(dep_block, new_dep_block)
    
    # Remove explicit groupId
    content = re.sub(r'<groupId>com.handmade.ecommerce\.' + domain + r'</groupId>', '', content, count=1)
    
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
    # First rename the artifactId in the dependency
    content = content.replace(f"<artifactId>{domain}-api</artifactId>", f"<artifactId>handmade-{domain}-api</artifactId>")
    
    # Now replace the dependency block to add version and fix groupId
    # We look for the dependency with the new artifactId
    dep_regex = r'<dependency>\s*<groupId>.*?</groupId>\s*<artifactId>handmade-' + domain + r'-api</artifactId>\s*</dependency>'
    new_dep = f"""<dependency>
            <groupId>com.handmade.ecommerce</groupId>
            <artifactId>handmade-{domain}-api</artifactId>
            <version>${{revision}}</version>
        </dependency>"""
    
    content = re.sub(dep_regex, new_dep, content, flags=re.DOTALL)

    # Fix plugin version
    content = content.replace("<artifactId>stm-generate-puml</artifactId>", "<artifactId>stm-generate-puml</artifactId>\n                <version>${revision}</version>")
    
    # Remove explicit groupId from the project (not dependencies)
    # We assume the project groupId is near the top, before dependencies
    # This is a bit hacky but safer than global replace.
    # Actually, let's just remove com.handmade.ecommerce.{domain} groupId if it appears as a project coordinate
    # The generator puts it right after parent or modelVersion.
    content = re.sub(r'<groupId>com.handmade.ecommerce\.' + domain + r'</groupId>', '', content, count=1)
    
    with open(svc_pom, 'w') as f:
        f.write(content)
        
    # 4. Refactor Imports in ALL files
    # Replace com.handmade.ecommerce.{domain}.model.{Entity} -> com.handmade.ecommerce.domain.{domain}.{Entity}
    old_import = f"com.handmade.ecommerce.{domain}.model.{entity_name}"
    new_import = f"com.handmade.ecommerce.domain.{domain}.{entity_name}"
    
    for root, dirs, files in os.walk(target_base):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                replace_in_file(file_path, old_import, new_import)

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
    for d in TARGET_DOMAINS:
        process_domain(d)
