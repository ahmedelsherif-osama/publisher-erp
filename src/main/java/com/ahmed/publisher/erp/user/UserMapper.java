package com.ahmed.publisher.erp.user;


import com.ahmed.publisher.erp.user.dto.PatchUserRequest;
import com.ahmed.publisher.erp.user.dto.UpdateUserRequest;
import com.ahmed.publisher.erp.user.dto.UserDto;
import com.ahmed.publisher.erp.user.dto.UserRegistrationRequest;

public class UserMapper {
    public static User toEntity (UserRegistrationRequest request){
        User customer = new User();
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPassword(request.password());
        return  customer;
    }

    public static UserDto toDto (User customer){
        return new UserDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName()
        );
    }

    public static void applyUpdate (User existing, UpdateUserRequest updateRequest){
        existing.setFirstName(updateRequest.firstName());
        existing.setLastName(updateRequest.lastName());
    }
    public static void applyPatch (User existing, PatchUserRequest patchRequest){
        if(patchRequest.firstName()!=null) existing.setFirstName(patchRequest.firstName());
        if(patchRequest.lastName()!=null) existing.setLastName(patchRequest.lastName());
    }
}
