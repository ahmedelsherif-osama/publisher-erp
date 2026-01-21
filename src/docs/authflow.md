# Auth Flows:

## Business View:

1. Login Flow:
    ```mermaid
   graph TD;
    subgraph "Login"
        A[Client] --> B[InternalAuthLoginFlow]
        B --> C[AuthenticatedContext]
        C --> D[Client]
    end
    ```

2. Registration Flow:
     ```mermaid
   graph TD;
    subgraph "Registration"
        A[Client] --> B[InternalAuthRegisterFlow]
        B --> C[AuthenticatedContext]
        C --> D[Client]
    end
    ```

3. Refresh Flow:
```mermaid
   graph TD;
    subgraph "Refresh"
        A[Client] --> B[InternalAuthRefreshFlow]
        B --> C[AuthenticatedContext]
        C --> D[Client]
    end
```

## Internal View:

### 1. Login Flow

```mermaid
graph RL;
    subgraph "Login"
        A[Client sends login request] --> B[Security Filter Chain]
        B --> C[Login Request Validation]
        C -->|invalid / expired| D[401 Authentication Error]
        C -->|valid| E[Auth Service: Authenticate User]
        E --> F[Create Authenticated Context]
        F --> G[Issue JWT]
        G --> H[Return AuthResponse to Client]
    end
```

### 2. Registration Flow

```mermaid
flowchart LR;
    subgraph "Registration"
        I[Client sends registration request] --> J[Security Filter Chain]
        J --> K[Registration Request Validation]
        K -->|invalid data| L[400 Validation Error]
        K -->|email exists| M[409 Conflict Error]
        K -->|valid| N[Auth Service: Create User]
        N --> O[Create Authenticated Context]
        O --> P[Issue JWT]
        P --> Q[Return AuthResponse to Client]
    end
```

### 3. Refresh Flow:

```mermaid
graph RL;
    subgraph "Refresh"
        R[Client sends refresh token request] --> S[Security Filter Chain]
        S --> T[Refresh Token Validation]
        T -->|invalid / expired| U[401 Authentication Error]
        T -->|valid| V[Auth Service: Extract User & Rebuild Authenticated Context]
        V --> W[Issue New JWT]
        W --> X[Return AuthResponse to Client]
    end
```

## Legend

- [Validation] steps marked here represent required behavior.
- Some validation steps are not yet implemented (WIP).

