import os
import glob

root_dir = '/Users/premkumar/homebase-backend.git/handmade-liquibase-management/liquibase-changelogs/src/main/resources/db/changelog'
files = glob.glob(root_dir + '/**/*.yaml', recursive=True)

processed_count = 0

for file_path in files:
    if file_path.endswith('001-create-cart.yaml'):
        # Skip cart as I already fixed it manually to be sure
        continue

    with open(file_path, 'r') as f:
        content = f.read()
    
    if 'name: flowId' in content:
        print(f"Processing {file_path}")
        
        # Replace STM columns
        new_content = content.replace('name: flowId', 'name: flow_id')
        new_content = new_content.replace('name: stateId', 'name: state_id')
        new_content = new_content.replace('name: stateEntryTime', 'name: state_entry_time')
        new_content = new_content.replace('name: slaYellowDate', 'name: sla_yellow_date')
        new_content = new_content.replace('name: slaRedDate', 'name: sla_red_date')
        new_content = new_content.replace('name: slaTendingLate', 'name: sla_tending_late')
        new_content = new_content.replace('name: slaLate', 'name: sla_late')
        
        # Replace status in index if it exists in this file
        # Check if 'name: status' exists
        if 'name: status' in new_content:
             print(f"  Replacing 'name: status' with 'name: state_id'")
             new_content = new_content.replace('name: status', 'name: state_id')

        with open(file_path, 'w') as f:
            f.write(new_content)
        processed_count += 1

print(f"Processed {processed_count} files.")
