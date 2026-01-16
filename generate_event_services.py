import os
import shutil
import subprocess
import re

# Configuration
REPO_ROOT = "/Users/premkumar/homebase-backend.git"
GEN_SCRIPT = "/Users/premkumar/chenile-gen/app-gen/scripts/gen-workflow-service-custom.sh"
CRUD_GEN_SCRIPT = "/Users/premkumar/chenile-gen/app-gen/scripts/gen-service.sh"
PARENT_MODULE = "handmade-event-management"
API_MODULE = "handmade-event-api"

SERVICES = [
    {
        "job_name": "eventqueue",
        "stm_xml": "handmade-stm-management/src/main/resources/stm/stm/event-queue-states.xml",
        "service_module": "handmade-event-queue-service",
        "entity_name": "EventQueue",
        "gen_entity_name": "Eventqueue"
    },
    {
        "job_name": "workflowtask",
        "stm_xml": "handmade-stm-management/src/main/resources/stm/stm/workflow-task-states.xml",
        "service_module": "handmade-workflow-task-service",
        "entity_name": "WorkflowTask",
        "gen_entity_name": "Workflowtask"
    }
]

def run_command(command, cwd=None):
    print(f"Running: {command}")
    result = subprocess.run(command, shell=True, cwd=cwd, capture_output=True, text=True)
    if result.returncode != 0:
        print(f"Error: {result.stderr}")
        # raise Exception(f"Command failed: {command}")
    return result.stdout

def generate_stm_service(config):
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

    # Copy generated Payloads to handmade-event-api
    gen_api_dir = os.path.join(gen_base, f"{job_name}-api")
    gen_payloads_dir = os.path.join(gen_api_dir, f"src/main/java/com/handmade/ecommerce/{job_name}/model")
    
    event_api_dir = os.path.join(REPO_ROOT, "handmade-event-management/handmade-event-api")
    target_payloads_dir = os.path.join(event_api_dir, "src/main/java/com/handmade/ecommerce/event/model")
    
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
                
                content = content.replace(f"package com.handmade.ecommerce.{job_name}.model;", "package com.handmade.ecommerce.event.model;")
                
                if gen_entity_name in content and gen_entity_name != entity_name:
                    content = content.replace(gen_entity_name, entity_name)
                    
                with open(dest_file, 'w') as f:
                    f.write(content)
                print(f"Copied and updated payload {new_filename} to Event API")

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
                
                # Replace import com.handmade.ecommerce.{job_name}.model.{gen_entity_name} with com.handmade.ecommerce.event.model.{entity_name}
                old_import = f"com.handmade.ecommerce.{job_name}.model.{gen_entity_name}"
                new_import = f"com.handmade.ecommerce.event.model.{entity_name}"
                
                if old_import in content:
                    content = content.replace(old_import, new_import)
                    updated = True
                elif f"import com.handmade.ecommerce.{job_name}.model.*;" in content:
                     content = content.replace(f"import com.handmade.ecommerce.{job_name}.model.*;", "import com.handmade.ecommerce.event.model.*;")
                     updated = True

                # Replace generated entity name with correct entity name globally
                if gen_entity_name in content and gen_entity_name != entity_name:
                    content = content.replace(gen_entity_name, entity_name)
                    updated = True
                
                # Fix payload imports
                if f"com.handmade.ecommerce.{job_name}.model" in content:
                    content = content.replace(f"com.handmade.ecommerce.{job_name}.model", "com.handmade.ecommerce.event.model")
                    updated = True
                    
                if updated:
                    with open(file_path, 'w') as f:
                        f.write(content)
                    print(f"Updated content in {file_path}")

def generate_scheduler_service():
    service_module = "handmade-scheduler-service"
    job_name = "scheduler"
    print(f"Generating CRUD service for {service_module}")
    
    temp_dir = f"/tmp/chenile_gen_{job_name}"
    if os.path.exists(temp_dir):
        shutil.rmtree(temp_dir)
    os.makedirs(temp_dir)
    
    cmd = f"{CRUD_GEN_SCRIPT} -d {temp_dir} -j {job_name}"
    run_command(cmd, cwd=REPO_ROOT)
    
    gen_svc_dir = os.path.join(temp_dir, job_name)
    target_base = os.path.join(REPO_ROOT, PARENT_MODULE)
    target_svc_dir = os.path.join(target_base, service_module)
    
    if os.path.exists(target_svc_dir):
        shutil.rmtree(target_svc_dir)
    
    shutil.copytree(gen_svc_dir, target_svc_dir)
    
    # Fix POM
    svc_pom = os.path.join(target_svc_dir, "pom.xml")
    with open(svc_pom, 'r') as f:
        content = f.read()
        
    new_parent_block = f"""<parent>
        <groupId>com.handmade.ecommerce</groupId>
        <artifactId>{PARENT_MODULE}</artifactId>
        <version>${{revision}}</version>
        <relativePath>../pom.xml</relativePath>
    </parent>"""
    content = re.sub(r'<parent>.*?</parent>', new_parent_block, content, flags=re.DOTALL)
    content = content.replace(f"<artifactId>{job_name}</artifactId>", f"<artifactId>{service_module}</artifactId>")
    content = content.replace(f"<name>{job_name}</name>", f"<name>Scheduler Service Implementations</name>")
    
    # Add API dependency
    dep = f"""
        <dependency>
            <groupId>com.handmade.ecommerce</groupId>
            <artifactId>{API_MODULE}</artifactId>
            <version>${{revision}}</version>
        </dependency>
    """
    content = content.replace("</dependencies>", f"{dep}\n    </dependencies>")
    
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

    # Create CRUD classes for JobScheduler
    base_package_dir = os.path.join(target_svc_dir, "src/main/java/com/handmade/ecommerce/scheduler")
    service_pkg = os.path.join(base_package_dir, "service")
    controller_pkg = os.path.join(base_package_dir, "controller")
    os.makedirs(service_pkg, exist_ok=True)
    os.makedirs(controller_pkg, exist_ok=True)
    
    # Repository
    repo_content = """package com.handmade.ecommerce.scheduler.service;

import com.handmade.ecommerce.event.model.JobScheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSchedulerRepository extends JpaRepository<JobScheduler, String> {
}
"""
    with open(os.path.join(service_pkg, "JobSchedulerRepository.java"), "w") as f:
        f.write(repo_content)

    # Service
    service_content = """package com.handmade.ecommerce.scheduler.service;

import com.handmade.ecommerce.event.model.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JobSchedulerService {

    @Autowired
    private JobSchedulerRepository jobSchedulerRepository;

    @Transactional(readOnly = true)
    public List<JobScheduler> findAll() {
        return jobSchedulerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<JobScheduler> findById(String id) {
        return jobSchedulerRepository.findById(id);
    }

    @Transactional
    public JobScheduler save(JobScheduler jobScheduler) {
        return jobSchedulerRepository.save(jobScheduler);
    }

    @Transactional
    public void deleteById(String id) {
        jobSchedulerRepository.deleteById(id);
    }
}
"""
    with open(os.path.join(service_pkg, "JobSchedulerService.java"), "w") as f:
        f.write(service_content)

    # Controller
    controller_content = """package com.handmade.ecommerce.scheduler.controller;

import com.handmade.ecommerce.event.model.JobScheduler;
import com.handmade.ecommerce.scheduler.service.JobSchedulerService;
import org.chenile.core.model.ChenileController;
import org.chenile.core.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduler/job")
@ChenileController(value = "jobSchedulerService", serviceName = "jobSchedulerService")
public class JobSchedulerController {

    @Autowired
    private JobSchedulerService jobSchedulerService;

    @GetMapping
    @Operation(summary = "Get all jobs")
    public List<JobScheduler> getAllJobs() {
        return jobSchedulerService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get job by ID")
    public ResponseEntity<JobScheduler> getJobById(@PathVariable String id) {
        return jobSchedulerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create or update job")
    public JobScheduler createJob(@RequestBody JobScheduler job) {
        return jobSchedulerService.save(job);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete job")
    public ResponseEntity<Void> deleteJob(@PathVariable String id) {
        jobSchedulerService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
"""
    with open(os.path.join(controller_pkg, "JobSchedulerController.java"), "w") as f:
        f.write(controller_content)

if __name__ == "__main__":
    for config in SERVICES:
        generate_stm_service(config)
    generate_scheduler_service()
    print("Done!")
