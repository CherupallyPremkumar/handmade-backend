import os
import shutil
import subprocess
import re

# Configuration
REPO_ROOT = "/Users/premkumar/homebase-backend.git"
GEN_SCRIPT = "/Users/premkumar/chenile-gen/app-gen/scripts/gen-workflow-service-custom.sh"
TEMP_DIR = "/tmp/chenile_gen_cms_refactor_entry"
STM_XML = os.path.join(REPO_ROOT, "handmade-stm-management/src/main/resources/stm/stm/cms-entry-states.xml")
GEN_JOB_NAME = "cmsentry" # Java friendly name
PARENT_MODULE = "handmade-cms-management"
API_MODULE = "handmade-cms-api"
SERVICE_MODULE_NAME = "handmade-cms-service" # Existing module to replace

def run_command(command, cwd=None):
    print(f"Running: {command}")
    result = subprocess.run(command, shell=True, cwd=cwd, capture_output=True, text=True)
    if result.returncode != 0:
        print(f"Error: {result.stderr}")
        raise Exception(f"Command failed: {command}")
    return result.stdout

def generate_service():
    print(f"Generating service for {GEN_JOB_NAME}")
    
    if os.path.exists(TEMP_DIR):
        shutil.rmtree(TEMP_DIR)
    os.makedirs(TEMP_DIR)
    
    # Generate code
    cmd = f"{GEN_SCRIPT} -d {TEMP_DIR} -x {STM_XML} -j {GEN_JOB_NAME}"
    run_command(cmd, cwd=REPO_ROOT)
    
    gen_base = os.path.join(TEMP_DIR, GEN_JOB_NAME)
    gen_svc_dir = os.path.join(gen_base, f"{GEN_JOB_NAME}-service")
    
    target_base = os.path.join(REPO_ROOT, PARENT_MODULE)
    target_svc_dir = os.path.join(target_base, SERVICE_MODULE_NAME)
    
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
    content = content.replace(f"<artifactId>{GEN_JOB_NAME}-service</artifactId>", f"<artifactId>{SERVICE_MODULE_NAME}</artifactId>")
    content = content.replace(f"<name>{GEN_JOB_NAME}-service</name>", f"<name>CMS Service Implementations</name>")
    
    # Fix Dependencies
    dep_regex = r'<dependency>\s*<groupId>.*?</groupId>\s*<artifactId>' + GEN_JOB_NAME + r'-api</artifactId>\s*</dependency>'
    new_dep = f"""<dependency>
            <groupId>com.handmade.ecommerce</groupId>
            <artifactId>{API_MODULE}</artifactId>
            <version>${{revision}}</version>
        </dependency>"""
    content = re.sub(dep_regex, new_dep, content, flags=re.DOTALL)
    
    if "<artifactId>stm-generate-puml</artifactId>" in content:
         content = content.replace("<artifactId>stm-generate-puml</artifactId>", "<artifactId>stm-generate-puml</artifactId>\n                <version>${revision}</version>")

    content = re.sub(r'<groupId>com.handmade.ecommerce\.' + GEN_JOB_NAME + r'</groupId>', '', content, count=1)
    
    with open(svc_pom, 'w') as f:
        f.write(content)
        
    # Register in Parent POM (Ensure it's there)
    mgmt_pom = os.path.join(target_base, "pom.xml")
    with open(mgmt_pom, 'r') as f:
        content = f.read()
    
    if f"<module>{SERVICE_MODULE_NAME}</module>" not in content:
        modules_block = "<modules>"
        new_modules_block = f"<modules>\n        <module>{SERVICE_MODULE_NAME}</module>"
        content = content.replace(modules_block, new_modules_block)
        with open(mgmt_pom, 'w') as f:
            f.write(content)

    # Copy generated Payloads to handmade-cms-api
    gen_api_dir = os.path.join(gen_base, f"{GEN_JOB_NAME}-api")
    gen_payloads_dir = os.path.join(gen_api_dir, "src/main/java/com/handmade/ecommerce/cmsentry/model")
    
    cms_api_dir = os.path.join(REPO_ROOT, "handmade-cms-management/handmade-cms-api")
    target_payloads_dir = os.path.join(cms_api_dir, "src/main/java/com/handmade/ecommerce/cms/model")
    
    if os.path.exists(gen_payloads_dir):
        for file in os.listdir(gen_payloads_dir):
            if file.endswith("Payload.java"):
                src_file = os.path.join(gen_payloads_dir, file)
                
                # Rename Cmsentry -> CmsEntry
                new_filename = file
                if "Cmsentry" in file:
                    new_filename = file.replace("Cmsentry", "CmsEntry")
                
                dest_file = os.path.join(target_payloads_dir, new_filename)
                
                with open(src_file, 'r') as f:
                    content = f.read()
                
                content = content.replace("package com.handmade.ecommerce.cmsentry.model;", "package com.handmade.ecommerce.cms.model;")
                
                if "Cmsentry" in content:
                    content = content.replace("Cmsentry", "CmsEntry")
                    
                with open(dest_file, 'w') as f:
                    f.write(content)
                print(f"Copied and updated payload {new_filename} to CMS API")

    # Fix Imports and Renaming in Service
    java_root = os.path.join(target_svc_dir, "src/main/java")
    for root, dirs, files in os.walk(java_root):
        for file in files:
            file_path = os.path.join(root, file)
            
            # Rename file if it contains Cmsentry
            if "Cmsentry" in file:
                new_file = file.replace("Cmsentry", "CMSEntry")
                new_file_path = os.path.join(root, new_file)
                os.rename(file_path, new_file_path)
                file_path = new_file_path
                print(f"Renamed {file} to {new_file}")
            
            if file_path.endswith(".java"):
                with open(file_path, 'r') as f:
                    content = f.read()
                
                updated = False
                
                # Replace import com.handmade.ecommerce.cmsentry.model.Cmsentry with com.handmade.ecommerce.cms.model.CMSEntry
                if "com.handmade.ecommerce.cmsentry.model.Cmsentry" in content:
                    content = content.replace("com.handmade.ecommerce.cmsentry.model.Cmsentry", "com.handmade.ecommerce.cms.model.CMSEntry")
                    updated = True
                elif "import com.handmade.ecommerce.cmsentry.model.*;" in content:
                     content = content.replace("import com.handmade.ecommerce.cmsentry.model.*;", "import com.handmade.ecommerce.cms.model.*;")
                     updated = True

                # Replace Cmsentry with CMSEntry globally (Entity and Classes)
                if "Cmsentry" in content:
                    content = content.replace("Cmsentry", "CMSEntry")
                    updated = True
                    
                if updated:
                    with open(file_path, 'w') as f:
                        f.write(content)
                    print(f"Updated content in {file_path}")

    print(f"Generated {SERVICE_MODULE_NAME} and updated configuration.")

if __name__ == "__main__":
    generate_service()
