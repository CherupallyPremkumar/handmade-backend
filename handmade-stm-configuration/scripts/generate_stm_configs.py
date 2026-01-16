import os
import re

# Paths
REPO_ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "../.."))
XML_DIR = os.path.join(REPO_ROOT, "handmade-stm-management/src/main/resources/stm/stm")
ENTITY_DIR = os.path.join(REPO_ROOT, "handmade-entity-managemet/src/main/java/com/handmade/ecommerce/domain")
OUTPUT_BASE = os.path.join(REPO_ROOT, "handmade-stm-configuration/src/main/java/com/handmade/ecommerce/stm")

# Package and Base Class
BASE_PACKAGE = "com.handmade.ecommerce.stm"
BASE_XML_PATH = "stm/stm"

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

def to_camel(s):
    pascal = to_pascal(s)
    return pascal[0].lower() + pascal[1:]

def find_entities():
    entities = {}
    for root, dirs, files in os.walk(ENTITY_DIR):
        for file in files:
            if file.endswith(".java"):
                path = os.path.join(root, file)
                with open(path, "r") as f:
                    content = f.read()
                    if "extends AbstractJpaStateEntity" in content or "extends AbstractExtendedStateEntity" in content:
                        # Extract package
                        pkg_match = re.search(r"package ([\w\.]+);", content)
                        if pkg_match:
                            pkg = pkg_match.group(1)
                            class_name = file[:-5]
                            entities[class_name] = f"{pkg}.{class_name}"
    return entities

def generate_config(xml_file, entity_class, full_entity_path):
    base_name = xml_file.replace("-states.xml", "")
    pascal_name = to_pascal(base_name)
    camel_name = to_camel(base_name)
    class_name = f"{pascal_name}StmConfiguration"
    
    output_path = os.path.join(OUTPUT_BASE, f"{class_name}.java")
    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    
    content = f"""package {BASE_PACKAGE};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.stm.impl.STMFlowStoreImpl;
import org.chenile.stm.impl.STMImpl;
import org.chenile.stm.impl.XmlFlowReader;
import org.chenile.stm.impl.BeanFactoryAdapter;
import org.chenile.stm.spring.SpringBeanFactoryAdapter;
import org.chenile.workflow.api.WorkflowRegistry;

import {full_entity_path};

@Configuration
public class {class_name} {{

    private static final String FLOW_DEFINITION_FILE = "{BASE_XML_PATH}/{xml_file}";
    private static final String WORKFLOW_NAME = "{base_name}";

    @Autowired
    @Qualifier("{camel_name}ActionsInfoProvider")
    private STMActionsInfoProvider stmActionsInfoProvider;

    @PostConstruct
    public void registerWorkflow() {{
        WorkflowRegistry.addSTMActionsInfoProvider(WORKFLOW_NAME, stmActionsInfoProvider);
    }}

    // -------- BeanFactoryAdapter (PER STM) --------
    @Bean
    public BeanFactoryAdapter {camel_name}BeanFactoryAdapter() {{
        return new SpringBeanFactoryAdapter();
    }}

    // -------- Flow Store --------
    @Bean
    public STMFlowStoreImpl {camel_name}FlowStore(
            @Qualifier("{camel_name}BeanFactoryAdapter")
            BeanFactoryAdapter beanFactoryAdapter) {{

        STMFlowStoreImpl stmFlowStore = new STMFlowStoreImpl();
        stmFlowStore.setBeanFactory(beanFactoryAdapter);
        return stmFlowStore;
    }}

    // -------- STM --------
    @Bean
    public STM<{entity_class}> {camel_name}EntityStm(
            @Qualifier("{camel_name}FlowStore")
            STMFlowStoreImpl stmFlowStore) {{

        STMImpl<{entity_class}> stm = new STMImpl<>();
        stm.setStmFlowStore(stmFlowStore);
        return stm;
    }}

    // -------- Actions Info --------
    @Bean
    public STMActionsInfoProvider {camel_name}ActionsInfoProvider(
            @Qualifier("{camel_name}FlowStore")
            STMFlowStoreImpl stmFlowStore) {{

        return new STMActionsInfoProvider(stmFlowStore);
    }}

    // -------- XML Flow Reader --------
    @Bean
    public XmlFlowReader {camel_name}FlowReader(
            @Qualifier("{camel_name}FlowStore")
            STMFlowStoreImpl flowStore) throws Exception {{

        XmlFlowReader flowReader = new XmlFlowReader(flowStore);
        flowReader.setFilename(FLOW_DEFINITION_FILE);
        return flowReader;
    }}
}}
"""
    with open(output_path, "w") as f:
        f.write(content)
    print(f"✅ Generated {class_name}")

def main():
    entities = find_entities()
    xml_files = [f for f in os.listdir(XML_DIR) if f.endswith("-states.xml")]
    
    matched_count = 0
    for xml in xml_files:
        base_name = xml.replace("-states.xml", "")
        pascal_name = MANUAL_MAPPING.get(base_name, to_pascal(base_name))
        
        # Try exact match
        if pascal_name in entities:
            generate_config(xml, pascal_name, entities[pascal_name])
            matched_count += 1
        else:
            # Try some common variations if needed, but for now let's stick to exact
            print(f"⚠️ No entity found for {xml} (expected {pascal_name})")
            
    print(f"\nSummary: Matched {matched_count}/{len(xml_files)} state machines.")

if __name__ == "__main__":
    main()
