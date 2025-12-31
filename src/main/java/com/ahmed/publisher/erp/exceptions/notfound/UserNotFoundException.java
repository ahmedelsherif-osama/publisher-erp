package com.ahmed.publisher.erp.exceptions.notfound;



import com.ahmed.publisher.erp.exceptions.ResourceNotFoundException;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(UUID id) {
        super("User with id " + id + " not found");
    }
}

