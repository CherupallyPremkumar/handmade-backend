import os
import shutil
import subprocess
import re

# Configuration
REPO_ROOT = "/Users/premkumar/homebase-backend.git"
GEN_SCRIPT = "/Users/premkumar/chenile-gen/app-gen/scripts/gen-workflow-service-custom.sh"
PARENT_MODULE = "handmade-customer-management"
API_MODULE = "handmade-customer-api"

SERVICES = [
    {
        "job_name": "giftcard",
        "stm_xml": "handmade-stm-management/src/main/resources/stm/stm/gift-card-states.xml",
        "service_module": "handmade-gift-card-service",
        "entity_name": "GiftCard", # Entity class name in API
        "gen_entity_name": "Giftcard" # Generated class name prefix
    },
    {
        "job_name": "subscription",
        "stm_xml": "handmade-stm-management/src/main/resources/stm/stm/subscription-states.xml",
        "service_module": "handmade-subscription-service",
        "entity_name": "Subscription",
        "gen_entity_name": "Subscription"
    }
]

def run_command(command, cwd=None):
    print(f"Running: {command}")
    result = subprocess.run(command, shell=True, cwd=cwd, capture_output=True, text=True)
    if result.returncode != 0:
        print(f"Error: {result.stderr}")
        raise Exception(f"Command failed: {command}")
    return result.stdout

def generate_service(config):
    job_name = config["job_name"]
    stm_xml = os.path.join(REPO_ROOT, config["stm_xml"])
    service_module = config["service_module"]
    entity_name = config["entity_name"]
    gen_entity_name = config["gen_entity_name"]
    
    print(f"Generating service for {job_name}")
    
    temp_dir = f"/tmp/chenile_gen_{job_name}"
    if os.path.exists(temp_dir):
        shutil.rmtree(temp_dir)
    os.makedirs(temp_dir)
    
    # Generate code
    cmd = f"{GEN_SCRIPT} -d {temp_dir} -x {stm_xml} -j {job_name}"
    run_command(cmd, cwd=REPO_ROOT)
    
    gen_base = os.path.join(temp_dir, job_name)
    gen_svc_dir = os.path.join(gen_base, f"{job_name}-service")
    
    target_base = os.path.join(REPO_ROOT, PARENT_MODULE)
    target_svc_dir = os.path.join(target_base, service_module)
    
    if os.path.exists(target_svc_dir):
        print(f"Removing existing {target_svc_dir}")
        shutil.rmtree(target_svc_dir)
        
    shutil.copytree(gen_svc_dir, target_svc_dir)
    
    # Configure POM
    svc_pom = os.path.join(target_svc_dir, "pom.xml")
    with open(svc_pom, 'r') as f:
        content = f.read()
        
    # Fix Parent
    new_parent_block = f"""<parent>
        <groupId>com.handmade.ecommerce</groupId>
        <artifactId>{PARENT_MODULE}</artifactId>
        <version>${{revision}}</version>
        <relativePath>../pom.xml</relativePath>
    </parent>"""
    content = re.sub(r'<parent>.*?</parent>', new_parent_block, content, flags=re.DOTALL)
    
    # Fix ArtifactId and Name
    content = content.replace(f"<artifactId>{job_name}-service</artifactId>", f"<artifactId>{service_module}</artifactId>")
    content = content.replace(f"<name>{job_name}-service</name>", f"<name>{entity_name} Service Implementations</name>")
    
    # Fix Dependencies
    dep_regex = r'<dependency>\s*<groupId>.*?</groupId>\s*<artifactId>' + job_name + r'-api</artifactId>\s*</dependency>'
    new_dep = f"""<dependency>
            <groupId>com.handmade.ecommerce</groupId>
            <artifactId>{API_MODULE}</artifactId>
            <version>${{revision}}</version>
        </dependency>"""
    content = re.sub(dep_regex, new_dep, content, flags=re.DOTALL)
    
    if "<artifactId>stm-generate-puml</artifactId>" in content:
         content = content.replace("<artifactId>stm-generate-puml</artifactId>", "<artifactId>stm-generate-puml</artifactId>\n                <version>${revision}</version>")

    content = re.sub(r'<groupId>com.handmade.ecommerce\.' + job_name + r'</groupId>', '', content, count=1)
    
    with open(svc_pom, 'w') as f:
        f.write(content)
        
    # Register in Parent POM
    mgmt_pom = os.path.join(target_base, "pom.xml")
    with open(mgmt_pom, 'r') as f:
        content = f.read()
    
    if f"<module>{service_module}</module>" not in content:
        modules_block = "<modules>"
        new_modules_block = f"<modules>\n        <module>{service_module}</module>"
        content = content.replace(modules_block, new_modules_block)
        with open(mgmt_pom, 'w') as f:
            f.write(content)

    # Copy generated Payloads to handmade-customer-api
    gen_api_dir = os.path.join(gen_base, f"{job_name}-api")
    gen_payloads_dir = os.path.join(gen_api_dir, f"src/main/java/com/handmade/ecommerce/{job_name}/model")
    
    customer_api_dir = os.path.join(REPO_ROOT, "handmade-customer-management/handmade-customer-api")
    target_payloads_dir = os.path.join(customer_api_dir, "src/main/java/com/handmade/ecommerce/customer/model")
    
    if os.path.exists(gen_payloads_dir):
        for file in os.listdir(gen_payloads_dir):
            if file.endswith("Payload.java"):
                src_file = os.path.join(gen_payloads_dir, file)
                
                # Rename if needed
                new_filename = file
                if gen_entity_name in file and gen_entity_name != entity_name:
                    new_filename = file.replace(gen_entity_name, entity_name)
                
                dest_file = os.path.join(target_payloads_dir, new_filename)
                
                with open(src_file, 'r') as f:
                    content = f.read()
                
                content = content.replace(f"package com.handmade.ecommerce.{job_name}.model;", "package com.handmade.ecommerce.customer.model;")
                
                if gen_entity_name in content and gen_entity_name != entity_name:
                    content = content.replace(gen_entity_name, entity_name)
                    
                with open(dest_file, 'w') as f:
                    f.write(content)
                print(f"Copied and updated payload {new_filename} to Customer API")

    # Fix Imports and Renaming in Service
    java_root = os.path.join(target_svc_dir, "src/main/java")
    for root, dirs, files in os.walk(java_root):
        for file in files:
            file_path = os.path.join(root, file)
            
            # Rename file if it contains generated entity name and needs correction
            if gen_entity_name in file and gen_entity_name != entity_name:
                new_file = file.replace(gen_entity_name, entity_name)
                new_file_path = os.path.join(root, new_file)
                os.rename(file_path, new_file_path)
                file_path = new_file_path
                print(f"Renamed {file} to {new_file}")
            
            if file_path.endswith(".java"):
                with open(file_path, 'r') as f:
                    content = f.read()
                
                updated = False
                
                # Replace import com.handmade.ecommerce.{job_name}.model.{gen_entity_name} with com.handmade.ecommerce.customer.model.{entity_name}
                old_import = f"com.handmade.ecommerce.{job_name}.model.{gen_entity_name}"
                new_import = f"com.handmade.ecommerce.customer.model.{entity_name}"
                
                if old_import in content:
                    content = content.replace(old_import, new_import)
                    updated = True
                elif f"import com.handmade.ecommerce.{job_name}.model.*;" in content:
                     content = content.replace(f"import com.handmade.ecommerce.{job_name}.model.*;", "import com.handmade.ecommerce.customer.model.*;")
                     updated = True

                # Replace generated entity name with correct entity name globally
                if gen_entity_name in content and gen_entity_name != entity_name:
                    content = content.replace(gen_entity_name, entity_name)
                    updated = True
                    
                if updated:
                    with open(file_path, 'w') as f:
                        f.write(content)
                    print(f"Updated content in {file_path}")

    print(f"Generated {service_module} and updated configuration.")

if __name__ == "__main__":
    for config in SERVICES:
        generate_service(config)
