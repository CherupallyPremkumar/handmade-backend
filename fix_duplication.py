import os

FILES = [
    "handmade-customer-management/handmade-subscription-service/src/main/java/com/handmade/ecommerce/subscription/service/cmds/RenewSubscriptionAction.java",
    "handmade-customer-management/handmade-subscription-service/src/main/java/com/handmade/ecommerce/subscription/service/cmds/ExpireSubscriptionAction.java",
    "handmade-customer-management/handmade-subscription-service/src/main/java/com/handmade/ecommerce/subscription/service/cmds/ActivatePaidSubscriptionAction.java",
    "handmade-customer-management/handmade-subscription-service/src/main/java/com/handmade/ecommerce/subscription/service/cmds/CancelSubscriptionAction.java",
    "handmade-customer-management/handmade-subscription-service/src/main/java/com/handmade/ecommerce/subscription/configuration/SubscriptionConfiguration.java"
]

REPO_ROOT = "/Users/premkumar/homebase-backend.git"

def fix_file(rel_path):
    path = os.path.join(REPO_ROOT, rel_path)
    with open(path, 'r') as f:
        content = f.read()
    
    # Find second occurrence of "package "
    first_pkg = content.find("package ")
    if first_pkg == -1:
        return
        
    second_pkg = content.find("package ", first_pkg + 1)
    if second_pkg != -1:
        print(f"Fixing {rel_path}")
        new_content = content[:second_pkg]
        with open(path, 'w') as f:
            f.write(new_content)
    else:
        print(f"No duplication found in {rel_path}")

if __name__ == "__main__":
    for f in FILES:
        fix_file(f)
