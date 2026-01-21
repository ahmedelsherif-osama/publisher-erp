# Inventory Flows:
## Business View:

1. Adjust Inventory:
    ```mermaid
   graph TD;
   subgraph "Adjust Inventory"
        A[Client / Order System] --> B[InternalInventoryAdjustmentFlow]
        B --> C[Inventory Updated / Adjustment Recorded]
        C --> D[Client / Order System]
    end
    ```

2. Get Inventory:
     ```mermaid
   graph TD;
     subgraph "Get Inventory"
        A[Client / Order System] --> B[InternalInventoryQueryFlow]
        B --> C[Inventory Data Returned]
        C --> D[Client / Order System]
    end
    ```
3. Get Inventory Adjustments:
     ```mermaid
   graph TD;
    subgraph "Get Inventory Adjustments"
        A[Client / Order System] --> B[InternalInventoryAdjustmentQueryFlow]
        B --> C[List of Adjustments Returned]
        C --> D[Client / Order System]
    end
    ```

## Internal View:
### 1. Adjust Inventory — Technical Flow

```mermaid
graph RL;
    subgraph "Adjust Inventory"
        A["InventoryController.adjustStock (publicationId, request)"] --> B[Validate Stock Adjustment Request]
        B -->|delta == 0| C[Throw 400 BadRequestException]
        B -->|delta != 0| D[Check Publication Exists]
        D -->|not found| E[Throw 404 ResourceNotFoundException]
        D -->|found| F[Load or Create Inventory for Publication]
        F --> G[Calculate New Quantity]
        G -->|newQuantity < 0| H[Throw BusinessRuleViolationException]
        G -->|newQuantity >= 0| I[Update Inventory Entity and Save]
        I --> J[Create InventoryAdjustment Record and Save]
        J --> K[Return InventoryResponse to Client]
    end
```

### 2. Get Inventory — Technical Flow

```mermaid
graph RL;
     subgraph "Get Inventory"
        A["InventoryController.getAll() or getByPublication()"] --> B[InventoryService.fetchInventoryData]
        B --> C[Map Inventory Entities to InventoryResponse DTOs]
        C --> D["Return InventoryResponse(s) to Client"]
    end
```

### 3. Get Inventory Adjustments — Technical Flow

```mermaid
graph RL;
   subgraph "Get Inventory Adjustments"
        A["InventoryController.getAdjustmentsForPublication() or getAllAdjustments()"] --> B[InventoryService.fetchAdjustmentData]
        B --> C[Map InventoryAdjustment Entities to InventoryAdjustmentResponse DTOs]
        C --> D["Return List<InventoryAdjustmentResponse> to Client"]
    end
```

## Legend

- [Validation] steps marked here represent required behavior.
- Internal View diagrams show technical workflow, including validation, business rules, persistence, and exception handling.
- Adjustments triggered by orders (create/cancel) or manual operations all flow through InternalInventoryAdjustmentFlow.
- All exceptions are handled via GlobalExceptionHandler.
- Inventory and adjustments are consistent, atomic, and auditable.