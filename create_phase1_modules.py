import os

domains = [
    "product", "order", "payment", "inventory", "seller",
    "cart", "catalog", "offer", "pricing", "promotion"
]

root_dir = "/Users/premkumar/homebase-backend.git"
pom_template = """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.handmade.ecommerce</groupId>
        <artifactId>handmade-parent</artifactId>
        <version>${{revision}}</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

    <artifactId>handmade-{domain}-management</artifactId>
    <packaging>pom</packaging>
    <name>handmade-{domain}-management</name>

    <modules>
    </modules>
</project>
"""

for domain in domains:
    module_name = f"handmade-{domain}-management"
    module_dir = os.path.join(root_dir, module_name)
    
    if not os.path.exists(module_dir):
        os.makedirs(module_dir)
        print(f"Created directory: {module_dir}")
    
    pom_path = os.path.join(module_dir, "pom.xml")
    if not os.path.exists(pom_path):
        with open(pom_path, "w") as f:
            f.write(pom_template.format(domain=domain))
        print(f"Created pom.xml: {pom_path}")
    else:
        print(f"Skipped (already exists): {pom_path}")
