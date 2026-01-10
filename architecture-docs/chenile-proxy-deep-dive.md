# Chenile Proxy: Location Transparency (Official)

This document describes the **Chenile Proxy** module, which enables transparent service invocation by bridging Java interfaces to either local or remote Chenile endpoints.

---

## 🏗️ 1. The Proxy Factory: `ProxyBuilder`

The `ProxyBuilder` is the core component that creates a Java Dynamic Proxy for any Chenile-governed interface.

### Dynamic Interaction:
- When a method is called on the proxy, `ProxyBuilder` intercepts it.
- It constructs a `ChenileExchange` using the `ChenileExchangeBuilder`.
- It populates the exchange with:
    - **Arguments**: Passed as a list in `apiInvocation`.
    - **Metadata**: Service ID and Operation ID derived from the interface and method name.
    - **Headers**: Copied from the current context via `HeaderCopier`.
- It executes the exchange through the **Chenile Proxy Orchestration Chain** (`chenileProxyOrchExecutor`).

---

## 🚦 2. Routing Logic: `ProxyTypeRouter`

Location transparency is achieved through the `ProxyTypeRouter`, which resides in the proxy orchestration chain.

### Routing Modes:
- **`LOCAL`**: Directly invokes the `ChenileEntryPoint` in the same JVM.
- **`REMOTE`**: Invokes the service over HTTP using `HttpInvoker`.
- **`COMPUTE_DYNAMICALLY`**: The default production mode. It compares the `moduleName` of the target service with the current module. If they differ, it routes to `REMOTE`.

---

## 📡 3. Remote Invocation: `HttpInvoker`

The `HttpInvoker` handles the complexities of remote REST calls.

### Responsibilities:
- **URL Construction**: Dynamically builds the target URL, including substituting path variables (e.g., `/{id}`).
- **Exchange Marshalling**: Translates the `ChenileExchange` body and headers into a standard `HttpEntity`.
- **Response Handling**:
    - Uses `RestTemplate` to perform the call.
    - Employs `ChenileResponseHandler` to unwrap the `GenericResponse<T>` envelope.
    - Automatically translates remote HTTP errors (4xx, 5xx) back into local `ErrorNumException`s, preserving the original sub-error codes.

---

## 🛠️ 4. Local Invocation: `LocalProxyInvoker`

If the service is determined to be local, `LocalProxyInvoker` ensures it still passes through the full core governance.
- It sets `localInvocation = true` on the exchange.
- It delegates directly to the `ChenileEntryPoint`.
- This ensures that **Security, Audit, and STM logic** are applied identically regardless of whether the service was called directly or via a proxy.

---

## 🎯 5. Handmade Implementation Rule
> [!IMPORTANT]
> **Use Proxies for Cross-Module Calls**: Never call another module's service implementation directly. Always use a proxy created via `ProxyBuilder`. 
> **Transparency**: This allows us to start as a **Modular Monolith** (all calls are `LOCAL`) and split into **Microservices** (calls become `REMOTE`) later, purely by changing a configuration property, without touching a single line of business code.

**This document defines the location transparency mechanism for the Handmade platform.**
