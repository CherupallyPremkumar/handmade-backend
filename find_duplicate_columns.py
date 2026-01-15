import os
import glob

root_dir = '/Users/premkumar/homebase-backend.git/handmade-liquibase-management/liquibase-changelogs/src/main/resources/db/changelog'
files = glob.glob(root_dir + '/**/*.yaml', recursive=True)

for file_path in files:
    with open(file_path, 'r') as f:
        content = f.read()
    
    # Simple check: count occurrences of "name: state_id"
    # We want to match exactly "name: state_id" to avoid matching comments or substrings if possible, 
    # but "name: state_id" is the key.
    count = content.count('name: state_id')
    if count > 1:
        print(f"File {file_path} has {count} occurrences of 'name: state_id'")
