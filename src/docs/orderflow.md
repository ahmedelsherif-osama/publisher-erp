# Order Flows:
## Business View:

1. Create Order Flow:
    ```mermaid
   graph TD;
    subgraph "Create Order"
        A[Client] --> B[InternalOrderCreateFlow]
        B --> C[Order Created]
        C --> D[Client]
    end
    ```

2. Cancel Order Flow:
     ```mermaid
   graph TD;
    subgraph "Cancel Order"
        A[Client] --> B[InternalOrderCancelFlow]
        B --> C[Order Cancelled]
        C --> D[Client]
    end
    ```
3. Complete Order Flow:
     ```mermaid
   graph TD;
      subgraph "Complete Order"
        A3[Client] --> B3[InternalOrderCompleteFlow]
        B3 --> C3[Order Completed]
        C3 --> D3[Client]
    end
    ```

4. Get All Orders Flow:
```mermaid
   graph TD;
    subgraph "Get All Orders"
        A[Client] --> B[InternalOrderQueryFlow]
        B --> C[List of Orders Returned]
        C --> D[Client]
    end
```

## Internal View:
### 1. Create Order Flow

```mermaid
graph RL;
    subgraph "Create Order"
        A[Client sends create order request] --> B[Security Filter Chain]
        B --> C[Order Request Validation]
        C -->|invalid| D[400 Bad Request / Validation Error]
        C -->|valid| E[Order Service]
        E --> F[Check User Exists]
        F -->|not found| G[404 Resource Not Found: User]
        F -->|found| H[Check Publications Exist & Adjust Inventory]
        H -->|any publication missing| I[404 Resource Not Found: Publication]
        H -->|all exist| J[Create Order + Save to DB]
        J --> K[Return OrderResponse to Client]
    end
```

### 2. Cancel Order Flow

```mermaid
graph RL;
    subgraph "Cancel Order"
        L[Client sends cancel order request] --> M[Security Filter Chain]
        M --> N[Check Order Exists]
        N -->|not found| O[404 Resource Not Found: Order]
        N -->|found| P[Check Order Status == CREATED]
        P -->|false| Q[409 Business Rule Violation]
        P -->|true| R[Rollback Inventory]
        R --> S[Set Order Status = CANCELLED + Save]
        S --> T[Return Success Response]
    end
```

### 3. Complete Order Flow

```mermaid
graph RL;
    subgraph "Complete Order"
        U[Client sends complete order request] --> V[Security Filter Chain]
        V --> W[Check Order Exists]
        W -->|not found| X[404 Resource Not Found: Order]
        W -->|found| Y[Check Order Status == CREATED]
        Y -->|false| Z[409 Business Rule Violation]
        Y -->|true| AA[Set Order Status = COMPLETED + Save]
        AA --> AB[Return Success Response]
    end
```

### 4. Get All Orders Flow

```mermaid
graph RL;
    subgraph "Order Flow - Get All Orders"
        AC[Client sends get all orders request] --> AD[Security Filter Chain]
        AD --> AE[Order Service: Fetch All Orders]
        AE --> AF[Return List<OrderResponse> to Client]
    end
```

## Legend

- [Validation] steps marked here represent required behavior.
- All exceptions are handled via GlobalExceptionHandler (ResourceNotFound, BusinessRuleViolation, ValidationError, etc.).
- Inventory adjustments are atomic and consistent.
- Orders cannot be modified after completion or cancellation.