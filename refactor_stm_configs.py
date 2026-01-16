import os
import re

directory = "/Users/premkumar/homebase-backend.git/handmade-stm-configuration/src/main/java/com/handmade/ecommerce/stm"

def refactor_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    # 1. Remove PostConstruct import
    content = content.replace("import jakarta.annotation.PostConstruct;\n", "")
    
    # 2. Remove @Autowired field for STMActionsInfoProvider
    content = re.sub(r'\s+@Autowired\s+@Qualifier\("[^"]+"\)\s+private STMActionsInfoProvider stmActionsInfoProvider;', '', content)
    
    # 3. Remove registerWorkflow method
    content = re.sub(r'\s+@PostConstruct\s+public void registerWorkflow\(\) \{\s+WorkflowRegistry\.addSTMActionsInfoProvider\(WORKFLOW_NAME, stmActionsInfoProvider\);\s+\}', '', content)
    
    # 4. Refactor Actions Info @Bean method
    # Find the method name and the return statement
    pattern = r'(@Bean\s+public STMActionsInfoProvider (\w+)\(\s+@Qualifier\("(\w+)"\)\s+STMFlowStoreImpl stmFlowStore\) \{\s+)return new STMActionsInfoProvider\(stmFlowStore\);(\s+\})'
    replacement = r'\1STMActionsInfoProvider provider = new STMActionsInfoProvider(stmFlowStore);\n        WorkflowRegistry.addSTMActionsInfoProvider(WORKFLOW_NAME, provider);\n        return provider;\4'
    
    new_content = re.sub(pattern, replacement, content)
    
    if new_content != content:
        with open(filepath, 'w') as f:
            f.write(new_content)
        print(f"Refactored {filepath}")
    else:
        print(f"Skipped {filepath} (no match)")

for filename in os.listdir(directory):
    if filename.endswith("StmConfiguration.java"):
        refactor_file(os.path.join(directory, filename))
