# Platform Management - PlantUML Diagrams

This directory contains PlantUML diagrams for the Platform Management module.

## Diagrams

### 1. Class Diagram
**File**: `platform-class-diagram.puml`

Shows the complete domain model including:
- Aggregate Root (PlatformOwner)
- Value Objects (BrandIdentity, CorporateIdentity, etc.)
- Entities (CommissionPolicy, FeatureConfiguration, PlatformAuditLog)
- Domain Services (PlatformManager, CommissionService)
- Repositories

### 2. State Diagram
**File**: `platform-state-diagram.puml`

Shows the platform lifecycle state machine:
- States: DRAFT → ACTIVE → SUSPENDED → DELETED
- Transitions with guard conditions
- Allowed actions per state
- Events published on state entry

## Viewing Diagrams

### Option 1: VS Code Extension
1. Install "PlantUML" extension
2. Open `.puml` file
3. Press `Alt+D` to preview

### Option 2: Online Viewer
Visit: http://www.plantuml.com/plantuml/uml/

Paste the diagram code to view online.

### Option 3: Generate PNG/SVG
```bash
# Install PlantUML
brew install plantuml  # macOS
apt-get install plantuml  # Linux

# Generate PNG
plantuml platform-class-diagram.puml
plantuml platform-state-diagram.puml

# Generate SVG
plantuml -tsvg platform-class-diagram.puml
plantuml -tsvg platform-state-diagram.puml
```

## Diagram Updates

When updating the domain model or state machine:
1. Update the corresponding `.puml` file
2. Regenerate PNG/SVG if needed
3. Update `ARCHITECTURE.md` if patterns change
4. Commit changes with descriptive message

## Color Coding

### State Diagram
- **Yellow**: DRAFT state (initial)
- **Green**: ACTIVE state (operational)
- **Orange**: SUSPENDED state (paused)
- **Gray**: DELETED state (terminal)

### Class Diagram
- **<<Aggregate Root>>**: Pink header
- **<<Entity>>**: Light blue header
- **<<Value Object>>**: Light yellow header
- **<<Domain Service>>**: Light green header

---

**Last Updated**: 2025-12-25
