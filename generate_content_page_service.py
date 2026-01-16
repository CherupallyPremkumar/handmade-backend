import os
import shutil
import subprocess
import re

# Configuration
REPO_ROOT = "/Users/premkumar/homebase-backend.git"
GEN_SCRIPT = "/Users/premkumar/chenile-gen/app-gen/scripts/gen-workflow-service-custom.sh"
TEMP_DIR = "/tmp/chenile_gen_cms_refactor"
STM_XML = os.path.join(REPO_ROOT, "handmade-stm-management/src/main/resources/stm/stm/content-page-states.xml")
GEN_JOB_NAME = "contentpage" # Java friendly name (no hyphens)
PARENT_MODULE = "handmade-cms-management"
API_MODULE = "handmade-cms-api"
SERVICE_MODULE_NAME = "handmade-content-page-service" # Maven friendly name

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
    
    # Generate code using Java-friendly job name
    cmd = f"{GEN_SCRIPT} -d {TEMP_DIR} -x {STM_XML} -j {GEN_JOB_NAME}"
    run_command(cmd, cwd=REPO_ROOT)
    
    gen_base = os.path.join(TEMP_DIR, GEN_JOB_NAME)
    # The generator creates {job_name}-service
    gen_svc_dir = os.path.join(gen_base, f"{GEN_JOB_NAME}-service")
    
    target_base = os.path.join(REPO_ROOT, PARENT_MODULE)
    target_svc_dir = os.path.join(target_base, SERVICE_MODULE_NAME)
    
    if os.path.exists(target_svc_dir):
        print(f"Warning: {target_svc_dir} already exists. Overwriting.")
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
    content = content.replace(f"<name>{GEN_JOB_NAME}-service</name>", f"<name>Content Page Service Implementations</name>")
    
    # Fix Dependencies
    # Remove dependency on generated api, add dependency on handmade-cms-api
    dep_regex = r'<dependency>\s*<groupId>.*?</groupId>\s*<artifactId>' + GEN_JOB_NAME + r'-api</artifactId>\s*</dependency>'
    new_dep = f"""<dependency>
            <groupId>com.handmade.ecommerce</groupId>
            <artifactId>{API_MODULE}</artifactId>
            <version>${{revision}}</version>
        </dependency>"""
    content = re.sub(dep_regex, new_dep, content, flags=re.DOTALL)
    
    # Ensure other dependencies are correct
    if "<artifactId>stm-generate-puml</artifactId>" in content:
         content = content.replace("<artifactId>stm-generate-puml</artifactId>", "<artifactId>stm-generate-puml</artifactId>\n                <version>${revision}</version>")

    # Remove self groupId if present
    content = re.sub(r'<groupId>com.handmade.ecommerce\.' + GEN_JOB_NAME + r'</groupId>', '', content, count=1)
    
    with open(svc_pom, 'w') as f:
        f.write(content)
        
    # Register in Parent POM
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
    # The generator creates payloads in the API module
    gen_api_dir = os.path.join(gen_base, f"{GEN_JOB_NAME}-api")
    gen_payloads_dir = os.path.join(gen_api_dir, "src/main/java/com/handmade/ecommerce/contentpage/model")
    
    cms_api_dir = os.path.join(REPO_ROOT, "handmade-cms-management/handmade-cms-api")
    target_payloads_dir = os.path.join(cms_api_dir, "src/main/java/com/handmade/ecommerce/cms/model")
    
    if os.path.exists(gen_payloads_dir):
        for file in os.listdir(gen_payloads_dir):
            if file.endswith("Payload.java"): # Only copy payloads
                src_file = os.path.join(gen_payloads_dir, file)
                
                # Rename if needed (Contentpage -> ContentPage)
                new_filename = file
                if "Contentpage" in file:
                    new_filename = file.replace("Contentpage", "ContentPage")
                
                dest_file = os.path.join(target_payloads_dir, new_filename)
                
                with open(src_file, 'r') as f:
                    content = f.read()
                
                # Update package to com.handmade.ecommerce.cms.model
                content = content.replace("package com.handmade.ecommerce.contentpage.model;", "package com.handmade.ecommerce.cms.model;")
                
                # Update class name in content if renamed
                if "Contentpage" in content:
                    content = content.replace("Contentpage", "ContentPage")
                    
                with open(dest_file, 'w') as f:
                    f.write(content)
                print(f"Copied and updated payload {new_filename} to CMS API")

    # Fix Entity Import and Payload Import in Service
    # We want to use com.handmade.ecommerce.cms.model.ContentPage
    # And com.handmade.ecommerce.cms.model.*Payload
    
    java_root = os.path.join(target_svc_dir, "src/main/java")
    for root, dirs, files in os.walk(java_root):
        for file in files:
            file_path = os.path.join(root, file)
            
            # Rename file if it contains Contentpage
            if "Contentpage" in file:
                new_file = file.replace("Contentpage", "ContentPage")
                new_file_path = os.path.join(root, new_file)
                os.rename(file_path, new_file_path)
                file_path = new_file_path
                print(f"Renamed {file} to {new_file}")
            
            if file_path.endswith(".java"):
                with open(file_path, 'r') as f:
                    content = f.read()
                
                updated = False
                
                # Replace import com.handmade.ecommerce.contentpage.model.ContentPage with com.handmade.ecommerce.cms.model.ContentPage
                if "com.handmade.ecommerce.contentpage.model.ContentPage" in content:
                    content = content.replace("com.handmade.ecommerce.contentpage.model.ContentPage", "com.handmade.ecommerce.cms.model.ContentPage")
                    updated = True
                # Also check if it just imports model.*
                elif "import com.handmade.ecommerce.contentpage.model.*;" in content:
                     content = content.replace("import com.handmade.ecommerce.contentpage.model.*;", "import com.handmade.ecommerce.cms.model.*;")
                     updated = True

                # Replace Contentpage with ContentPage in class names and types
                # But NOT in package name (which is contentpage)
                if "Contentpage" in content:
                    content = content.replace("Contentpage", "ContentPage")
                    updated = True
                    
                if updated:
                    with open(file_path, 'w') as f:
                        f.write(content)
                    print(f"Updated content in {file_path}")

    print(f"Generated {SERVICE_MODULE_NAME} and updated configuration.")

if __name__ == "__main__":
    generate_service()
