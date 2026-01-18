# AuthfLows:

## Business View:

1. Login Flow:
   [Client] -> [InternalAuthLoginFlow] -> [AuthenticatedContext] -> [Client]

2. Registration Flow:
   [Client] -> [InternalAuthRegisterFlow] -> [AuthenticatedContext] -> [Client]

3. Refresh Flow:
   [Client] -> [InternalAuthRefreshFlow] -> [AuthenticatedContext] -> [Client]


## Internal View:

### 1. Login Flow

[Client Request]
        |
[Security Filter Chain]
        |
[Login Request Validation]
        |
        ├─ expired / invalid → [Authentication Error]
        |
        └─ valid
             |
        [Auth Service]
             |
        [Authenticated Context Created]
             |
        [JWT Issued]
             |
        [Response Returned]

### 2. Registration Flow

[Client Request]
        |
[Security Filter Chain]
        |
[Registration Validation]
        |
        ├─ invalid data → [Validation Error]
        |
        ├─ user exists → [Conflict Error]
        |
        └─ valid
             |
        [Auth Service]
             |
        [User Creation]
             |
        [Authenticated Context Created]
             |
        [JWT Issued]
             |
        [Response Returned]

### 3. Refresh Flow:

[Client Request]
        |
[Security Filter Chain]
        |
[Refresh Token Validation]
        |   
        ├─ expired / invalid → [Authentication Error]
        |
        └─ valid
             |
        [Auth Service]
             |
        [Authentication Context Rebuilt]
             |
        [New JWT Issued]
             |
        [Response Returned]

## Legend

- [Validation] steps marked here represent required behavior.
- Some validation steps are not yet implemented (WIP).

