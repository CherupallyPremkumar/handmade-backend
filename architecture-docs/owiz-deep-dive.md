# Owiz: The Chenile Orchestration Engine (Official)

This document describes **Owiz**, the fundamental orchestration engine that powers every request flow, interceptor chain, and service bridge in the Chenile framework.

---

## 🏗️ 1. The Core Philosophy: Command & Control

Owiz is based on the **Command Pattern**, where every action is encapsulated as an atomic unit of work.

### Core Interface: `Command<InputType>`
```java
public interface Command<InputType> {
    void execute(InputType context) throws Exception;
}
```
In Chenile, the `InputType` is almost always the `ChenileExchange`.

---

## ⛓️ 2. Execution Patterns

Owiz provides three primary ways to organize and execute commands.

### A. The `Chain` (Sequential)
Executes a list of commands one after another. 
- **Usage**: Simple sequential pipelines or post-processing tasks.

### B. The `Router` (Conditional)
Calculates a "routing string" from the context and executes exactly one matching command.
- **Usage**: Transport routing (HTTP vs. MQTT), Version routing, or Multi-tenancy branching.

### C. The `FilterChain` (Surround Processing)
Enables AOP-style interception. Commands in a `FilterChain` (Interceptors) have access to a `ChainContext` and must explicitly call `doContinue()` to pass control to the next command.
- **Usage**: Security checks, Logging, Auditing, and Transaction management.

---

## 🧩 3. Dynamic Extensibility: Interpolation

Owiz supports **`InterpolationCommand`**, a powerful feature where a "placeholder" command can dynamically emit a list of other commands at runtime based on the context.

- **How it works**: The parent `Chain` detects the interpolation and replaces the placeholder with the actual commands returned by the interpolator.
- **Usage**: Loading service-specific interceptors or dynamic transformation branches without modifying the core flow.

---

## ⚙️ 4. Metadata-Driven Configuration

Owiz flows are not hardcoded. They are loaded from **XML** or **JSON** configurations, allowing for extreme flexibility.

### XML DSL (`owiz.xsd`)
```xml
<flow id="chenileCoreFlow">
    <chain id="mainChain">
        <command id="securityInterceptor"/>
        <command id="auditInterceptor"/>
        <command id="serviceDelegator" method="process"/>
    </chain>
</flow>
```
The **`XmlOrchConfigurator`** parses these into the runtime graph, resolving references to Spring beans via a `BeanFactoryAdapter`.

---

## 🎯 5. Handmade Implementation Rule
> [!IMPORTANT]
> **Orchestration Over Coding**: If a logic involves a sequence of steps or a set of cross-cutting concerns (Interceptors), define it as an Owiz flow. Do not hardcode the sequence in Java.
> **Stay Bypasable**: Ensure your commands are idempotent and can be bypassed if configured so in the flow.
> **Leverage FilterChain**: Always use `FilterChain` for any logic that needs to "wrap" the service call (e.g., performance timing or exception handling).

**This document defines the orchestration architecture for the Handmade platform.**
