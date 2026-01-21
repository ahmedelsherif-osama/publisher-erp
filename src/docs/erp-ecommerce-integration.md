# ERP → Ecommerce Integration Flows

## Business View

### 1. Push Publications to Bridge
```mermaid
graph TD;
    subgraph "ERP → Ecommerce Bridge"
        A[ERP System / Admin] --> B[InternalBridgeSyncFlow]
        B --> C[Publications Synced Successfully / Errors Reported]
        C --> D[ERP System / Admin]
    end
```

### 2. Push Multiple Publications (Batch)
```mermaid
graph TD;
    subgraph "ERP → Bridge Batch Sync"
        A[ERP System / Admin] --> B[InternalBridgeBatchSyncFlow]
        B --> C[Batch Synced / Errors Reported]
        C --> D[ERP System / Admin]
    end
```

## Internal View:
### 1. Push Single Publication — Technical Flow
```mermaid
graph LR;
    subgraph "ERP → Ecommerce Bridge"
        A[PublicationService / Admin] --> B[BridgeCatalogClient.pushPublications]
        B --> C[Prepare HTTP Request: JSON + Headers]
        C --> D[Call Bridge API POST /catalog/publications via RestTemplate]
        D -->|2xx HTTP Response| E[Log Success, Return to Service]
        D -->|Non-2xx Response| F[Throw BridgeApiException]
        D -->|Exception / Network Failure| F
        F --> G[Log Error, Propagate Exception to Caller]
    end
```

### 2. Push Multiple Publications — Batch Flow
```mermaid
graph LR;
     subgraph "ERP → Bridge Batch Sync"
        A[PublicationService / Admin] --> B[Iterate over List<BridgePublicationRequest>]
        B --> C[For each publication: call BridgeCatalogClient.pushPublications]
        C --> D[Aggregate Successes and Failures]
        D --> E[Return BatchSyncResult to Caller]
        D -->|Any failure| F[Throw BridgeApiException for failed items]
    end
```

## Legend

- Business View: abstract flows for stakeholders
- Internal View: step-by-step technical workflow
- Batch flow included for completeness