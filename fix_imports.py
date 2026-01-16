import os

REPO_ROOT = "/Users/premkumar/homebase-backend.git"
TARGET_DIR = os.path.join(REPO_ROOT, "handmade-customer-management/handmade-subscription-service/src/main/java")

def fix_imports():
    for root, dirs, files in os.walk(TARGET_DIR):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                with open(file_path, 'r') as f:
                    content = f.read()
                
                updated = False
                if "package com.handmade.ecommerce.subscription.model" in content:
                    # This is a payload file, it should be in customer package? 
                    # No, payloads were copied to customer API.
                    # But if there are leftover payloads in the service, we should probably ignore them or delete them?
                    # The build error was about "package does not exist" in Action classes.
                    pass
                
                if "import com.handmade.ecommerce.subscription.model" in content:
                    content = content.replace("import com.handmade.ecommerce.subscription.model", "import com.handmade.ecommerce.customer.model")
                    updated = True
                
                if updated:
                    with open(file_path, 'w') as f:
                        f.write(content)
                    print(f"Fixed imports in {file}")

if __name__ == "__main__":
    fix_imports()
