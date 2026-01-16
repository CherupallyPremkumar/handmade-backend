import os
import re

# Paths
REPO_ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "../.."))
XML_DIR = os.path.join(REPO_ROOT, "handmade-stm-management/src/main/resources/stm/stm")
ENTITY_DIR = os.path.join(REPO_ROOT, "handmade-entity-managemet/src/main/java/com/handmade/ecommerce/domain")

# Manual Overrides (XML prefix -> Entity name)
MANUAL_MAPPING = {
    "onboarding": "SellerOnboardingCase",
    "notification": "NotificationLog",
    "pricing-rule": "PriceRule",
    "payment": "PaymentTransaction",
    "cms-entry": "CMSEntry",
    "gdpr-request": "GDPRRequest",
    "risk-signal": "RiskSignal",
    "orderline": "OrderLine",
    "paymentorder": "PaymentOrder",
    "seller-kyc": "SellerKyc",
    "notification-template": "NotificationTemplate",
    "etl-job": "ETLJob",
    "metric-definition": "MetricDefinition",
    "role": "Role",
    "permission": "Permission",
    "user-role": "UserRole"
}

def to_pascal(s):
    return "".join(word.capitalize() for word in s.split("-"))

def get_entities():
    entities = {}
    for root, dirs, files in os.walk(ENTITY_DIR):
        for file in files:
            if file.endswith(".java"):
                path = os.path.join(root, file)
                with open(path, "r") as f:
                    content = f.read()
                    pkg_match = re.search(r"package ([\w\.]+);", content)
                    if pkg_match:
                        pkg = pkg_match.group(1)
                        class_name = file[:-5]
                        # Use regex to find if it extends the state entities, allowing for any whitespace
                        extends_state = re.search(r"extends\s+(AbstractJpaStateEntity|AbstractExtendedStateEntity)", content) is not None
                        entities[class_name] = {
                            "full_path": path,
                            "package": pkg,
                            "extends_state": extends_state
                        }
    return entities

def main():
    entities = get_entities()
    xml_files = sorted([f for f in os.listdir(XML_DIR) if f.endswith("-states.xml")])
    
    report = []
    report.append("| STM XML File | Entity Class | Package | Extends State Entity? | Action |")
    report.append("| :--- | :--- | :--- | :--- | :--- |")
    
    for xml in xml_files:
        base_name = xml.replace("-states.xml", "")
        pascal_name = MANUAL_MAPPING.get(base_name, to_pascal(base_name))
        
        if pascal_name in entities:
            ent = entities[pascal_name]
            extends = "✅ Yes" if ent["extends_state"] else "❌ No"
            action = "Generate Config" if ent["extends_state"] else "Update Entity + Generate Config"
            report.append(f"| {xml} | {pascal_name} | {ent['package']} | {extends} | {action} |")
        else:
            report.append(f"| {xml} | {pascal_name} | Unknown | ❓ N/A | Skip (Entity not found) |")
            
    print("\n".join(report))

if __name__ == "__main__":
    main()
