import os
import glob

root_dir = '/Users/premkumar/homebase-backend.git/handmade-liquibase-management/liquibase-changelogs/src/main/resources/db/changelog'
files = glob.glob(root_dir + '/**/*.yaml', recursive=True)

processed = 0

for file_path in files:
    with open(file_path, 'r') as f:
        lines = f.readlines()
    
    col_blocks = []
    
    i = 0
    while i < len(lines):
        line = lines[i]
        stripped_line = line.strip()
        
        # Look for start of column block
        if stripped_line.startswith('- column:'):
            block_start = i
            start_indent = len(line) - len(line.lstrip())
            
            # scan ahead to find name and type
            j = i + 1
            has_state_id = False
            has_type = False
            block_end = i + 1 # default
            
            while j < len(lines):
                subline = lines[j]
                if not subline.strip(): # Skip empty lines but include them in block if inside
                    j += 1
                    continue
                    
                curr_indent = len(subline) - len(subline.lstrip())
                
                # If we hit a line with same or less indentation (and not empty), it's the end of this column block
                if curr_indent <= start_indent:
                    block_end = j
                    break
                
                # Check properties
                if 'name: state_id' in subline:
                    has_state_id = True
                if 'type:' in subline: # Simplistic check, looking for 'type:' key
                    has_type = True
                
                j += 1
                block_end = j
            
            # If we reached EOF, block_end is correct (len(lines))
            
            if has_state_id and has_type:
                col_blocks.append((block_start, block_end))
            
            i = j # Move to next block
        else:
            i += 1
            
    if len(col_blocks) > 1:
        print(f"File {file_path} has {len(col_blocks)} state_id columns. Removing the first one.")
        # Remove the first block
        # We must be careful with indices if we remove lines, but we only remove one block per file here (the first one)
        # So it's safe to just slice.
        start, end = col_blocks[0]
        
        # Debug print
        # print("Removing lines:")
        # print("".join(lines[start:end]))
        
        new_lines = lines[:start] + lines[end:]
        
        with open(file_path, 'w') as f:
            f.writelines(new_lines)
        processed += 1

print(f"Deduplicated {processed} files.")
