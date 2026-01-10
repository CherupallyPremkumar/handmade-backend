#!/usr/bin/env python3
"""
JPA Repository Generator
Scans entity files and generates Spring Data JPA repositories with intelligent query methods
"""

import os
import re
from pathlib import Path
from typing import Dict, List, Tuple, Optional, Set
import csv

# Configuration
PROJECT_ROOT = "/Users/premkumar/Desktop/handmade"

# Fields to skip (inherited from base classes)
BASE_JPA_FIELDS = {'id', 'createdTime', 'lastModifiedTime', 'createdBy', 'lastModifiedBy', 'tenant', 'version', 'testEntity'}
STM_FIELDS = {'state', 'stateEntryTime', 'slaYellowDate', 'slaRedDate', 'slaTendingLate', 'slaLate'}

# Fields to skip for query generation (too large or not useful)
SKIP_QUERY_FIELDS = {'description', 'notes', 'content', 'metadata', 'details', 'payload', 'data'}


class EntityField:
    def __init__(self, name: str, java_type: str, column_name: str, unique: bool = False, 
                 nullable: bool = True, is_foreign_key: bool = False):
        self.name = name
        self.java_type = java_type
        self.column_name = column_name
        self.unique = unique
        self.nullable = nullable
        self.is_foreign_key = is_foreign_key


class EntityInfo:
    def __init__(self, name: str, package: str, file_path: str, is_stm: bool = False):
        self.name = name
        self.package = package
        self.file_path = file_path
        self.is_stm = is_stm
        self.fields: List[EntityField] = []


def scan_entity_files() -> List[str]:
    """Scan all entity files in domain modules."""
    entity_files = []
    
    for root, dirs, files in os.walk(PROJECT_ROOT):
        # Look for files in entity directories within domain modules
        if '/entity' in root and '-domain/src/main/java' in root:
            for file in files:
                if file.endswith('.java') and not file.startswith('Base') and not file.startswith('Abstract'):
                    entity_files.append(os.path.join(root, file))
   
    return sorted(entity_files)


def parse_entity_file(file_path: str) -> Optional[EntityInfo]:
    """Parse entity file and extract information."""
    try:
        with open(file_path, 'r') as f:
            content = f.read()
        
        # Extract package
        package_match = re.search(r'package\s+([\w.]+)\.entity;', content)
        if not package_match:
            return None
        package = package_match.group(1)
        
        # Extract class name
        class_match = re.search(r'public\s+class\s+(\w+)\s+extends\s+(BaseJpaEntity|AbstractJpaStateEntity)', content)
        if not class_match:
            return None
        
        entity_name = class_match.group(1)
        base_class = class_match.group(2)
        is_stm = (base_class == 'AbstractJpaStateEntity')
        
        entity_info = EntityInfo(entity_name, package, file_path, is_stm)
        
        # Extract fields
        # Pattern: @Column(...) private Type fieldName;
        field_pattern = re.compile(
            r'@Column\((.*?)\)\s*\n\s*private\s+(\w+(?:<\w+>)?)\s+(\w+);',
            re.MULTILINE | re.DOTALL
        )
        
        for match in field_pattern.finditer(content):
            column_attrs = match.group(1)
            java_type = match.group(2)
            field_name = match.group(3)
            
            # Skip inherited fields
            if field_name in BASE_JPA_FIELDS or field_name in STM_FIELDS:
                continue
            
            # Extract column name
            col_name_match = re.search(r'name\s*=\s*"(\w+)"', column_attrs)
            column_name = col_name_match.group(1) if col_name_match else field_name
            
            # Check if unique
            unique = 'unique = true' in column_attrs or 'unique=true' in column_attrs
            
            # Check if nullable
            nullable = 'nullable = false' not in column_attrs and 'nullable=false' not in column_attrs
            
            # Check if foreign key
            is_fk = column_name.endswith('_id') and column_name != 'id'
            
            field = EntityField(field_name, java_type, column_name, unique, nullable, is_fk)
            entity_info.fields.append(field)
        
        return entity_info
        
    except Exception as e:
        print(f"⚠️  Error parsing {file_path}: {e}")
        return None


def should_generate_query_method(field: EntityField) -> bool:
    """Determine if a query method should be generated for this field."""
    # Skip large text fields
    if field.name.lower() in SKIP_QUERY_FIELDS:
        return False
    
    # Always generate for unique fields
    if field.unique:
        return True
    
    # Always generate for foreign keys
    if field.is_foreign_key:
        return True
    
    # Generate for common business fields
    common_fields = ['status', 'type', 'category', 'code', 'name', 'email', 'phone', 
                     'country_code', 'state', 'active', 'enabled', 'priority']
    
    field_lower = field.name.lower()
    for common in common_fields:
        if common in field_lower:
            return True
    
    # Fields ending with common suffixes
    if field.name.endswith(('Code', 'Type', 'Status', 'Category', 'Number', 'Key', 'Email')):
        return True
    
    return False


def generate_query_methods(entity_info: EntityInfo) -> List[str]:
    """Generate query method signatures for entity."""
    methods = []
    
    # Generate findBy methods for queryable fields
    for field in entity_info.fields:
        if should_generate_query_method(field):
            method_name = f"findBy{field.name[0].upper()}{field.name[1:]}"
            
            if field.unique:
                # Unique fields return Optional
                methods.append(f"    Optional<{entity_info.name}> {method_name}({field.java_type} {field.name});")
            else:
                # Non-unique fields return List
                methods.append(f"    List<{entity_info.name}> {method_name}({field.java_type} {field.name});")
    
    # For STM entities, add state queries
    if entity_info.is_stm:
        methods.append("")
        methods.append("    // STM State queries")
        methods.append(f'    @Query("SELECT e FROM {entity_info.name} e WHERE e.state.stateId = :stateId")')
        methods.append(f"    List<{entity_info.name}> findByStateId(@Param(\"stateId\") String stateId);")
        methods.append("")
        methods.append(f'    @Query("SELECT e FROM {entity_info.name} e WHERE e.state.flowId = :flowId")')
        methods.append(f"    List<{entity_info.name}> findByFlowId(@Param(\"flowId\") String flowId);")
        methods.append("")
        methods.append(f'    @Query("SELECT e FROM {entity_info.name} e WHERE e.state.flowId = :flowId AND e.state.stateId = :stateId")')
        methods.append(f"    List<{entity_info.name}> findByFlowIdAndStateId(@Param(\"flowId\") String flowId, @Param(\"stateId\") String stateId);")
    
    # Add existsBy for unique fields
    unique_fields = [f for f in entity_info.fields if f.unique and should_generate_query_method(f)]
    if unique_fields:
        methods.append("")
        methods.append("    // Existence checks")
        for field in unique_fields:
            method_name = f"existsBy{field.name[0].upper()}{field.name[1:]}"
            methods.append(f"    boolean {method_name}({field.java_type} {field.name});")
    
    return methods


def generate_repository_code(entity_info: EntityInfo) -> str:
    """Generate repository interface code."""
    # Derive repository package from entity package
    repo_package = entity_info.package.replace('.entity', '.repository')
    
    # Generate imports
    imports = [
        f"import {entity_info.package}.entity.{entity_info.name};",
        "import org.springframework.data.jpa.repository.JpaRepository;",
        "import org.springframework.stereotype.Repository;",
    ]
    
    # Check if we need Query/Param imports
    query_methods = generate_query_methods(entity_info)
    needs_query = any('@Query' in m for m in query_methods)
    needs_param = any('@Param' in m for m in query_methods)
    
    if needs_query:
        imports.append("import org.springframework.data.jpa.repository.Query;")
    if needs_param:
        imports.append("import org.springframework.data.repository.query.Param;")
    
    # Check if we need Optional/List
    if any('Optional' in m for m in query_methods):
        imports.append("import java.util.Optional;")
    if any('List' in m for m in query_methods):
        imports.append("import java.util.List;")
    
    # Build Javadoc comment separately to avoid f-string issues
    javadoc = f"""/**
 * Repository for {entity_info.name}
 * Generated from entity file
 */"""
    
    # Generate code
    code = f"""package {repo_package};

{chr(10).join(sorted(set(imports)))}

{javadoc}
@Repository
public interface {entity_info.name}Repository extends JpaRepository<{entity_info.name}, String> {{
    
{chr(10).join(query_methods) if query_methods else '    // No auto-generated query methods'}
}}
"""
    
    return code


def map_entity_to_infrastructure_path(entity_file_path: str) -> str:
    """Map entity file path to infrastructure repository path."""
    # Example: .../tax/tax-domain/src/main/java/.../entity/TaxJurisdiction.java
    # To: .../tax/tax-infrastructure/src/main/java/.../repository/
    
    path = entity_file_path.replace('/entity/', '/repository/')
    path = path.replace('-domain/', '-infrastructure/')
    
    return os.path.dirname(path)


def generate_repositories():
    """Main function to generate all repositories."""
    print("=" * 80)
    print("Spring Data JPA Repository Generator")
    print("=" * 80)
    print()
    
    # Scan entity files
    print("Scanning entity files...")
    entity_files = scan_entity_files()
    print(f"Found {len(entity_files)} entity files")
    print()
    
    generated_count = 0
    skipped_count = 0
    errors = []
    summary_data = []
    
    for entity_file in entity_files:
        # Parse entity
        entity_info = parse_entity_file(entity_file)
        
        if not entity_info:
            print(f"⏭️  Skipping {os.path.basename(entity_file)} (parse error)")
            skipped_count += 1
            continue
        
        # Generate repository code
        try:
            repo_code = generate_repository_code(entity_info)
            
            # Determine output path
            repo_dir = map_entity_to_infrastructure_path(entity_file)
            os.makedirs(repo_dir, exist_ok=True)
            
            # Write repository file
            repo_file = os.path.join(repo_dir, f"{entity_info.name}Repository.java")
            
            # Check if file already exists
            if os.path.exists(repo_file):
                print(f"⚠️  {entity_info.name}Repository already exists, skipping")
                skipped_count += 1
                continue
            
            with open(repo_file, 'w') as f:
                f.write(repo_code)
            
            # Extract module info from path
            module_match = re.search(r'handmade-[\w-]+', entity_file)
            module = module_match.group(0) if module_match else "unknown"
            
            sub_module_match = re.search(r'/([\w-]+)/\1-domain/', entity_file)
            sub_module = sub_module_match.group(1) if sub_module_match else "unknown"
            
            query_count = len([m for m in generate_query_methods(entity_info) if 'find' in m.lower() or 'exists' in m.lower()])
            
            print(f"✅ Generated {entity_info.name}Repository → {module}/{sub_module} ({query_count} methods)")
            generated_count += 1
            
            # Collect summary data
            summary_data.append({
                'entity': entity_info.name,
                'module': module,
                'sub_module': sub_module,
                'is_stm': entity_info.is_stm,
                'query_methods': query_count,
                'fields': len(entity_info.fields)
            })
            
        except Exception as e:
            error_msg = f"Failed to generate {entity_info.name}Repository: {e}"
            print(f"❌ {error_msg}")
            errors.append(error_msg)
    
    # Generate summary report
    summary_file = os.path.join(PROJECT_ROOT, "repository-generation-summary.csv")
    with open(summary_file, 'w', newline='') as f:
        if summary_data:
            writer = csv.DictWriter(f, fieldnames=summary_data[0].keys())
            writer.writeheader()
            writer.writerows(summary_data)
    
    print()
    print("=" * 80)
    print("✅ Repository Generation Complete!")
    print("=" * 80)
    print(f"   Generated: {generated_count} repositories")
    print(f"   Skipped: {skipped_count} files")
    print(f"   Errors: {len(errors)}")
    print(f"   Summary: {summary_file}")
    print("=" * 80)
    print()
    
    if errors:
        print("Errors encountered:")
        for error in errors:
            print(f"  - {error}")
        print()
    
    print("Next Steps:")
    print("  1. Review generated repositories")
    print("  2. Add custom query methods if needed")
    print("  3. Run: mvn clean install -DskipTests")
    print("  4. Review summary CSV for statistics")
    print()


if __name__ == '__main__':
    generate_repositories()
