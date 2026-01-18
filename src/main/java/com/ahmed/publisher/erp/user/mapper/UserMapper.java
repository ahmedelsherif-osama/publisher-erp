package com.ahmed.publisher.erp.user.mapper;


import com.ahmed.publisher.erp.user.dto.PatchUserRequest;
import com.ahmed.publisher.erp.user.dto.UpdateUserRequest;
import com.ahmed.publisher.erp.user.dto.UserDto;
import com.ahmed.publisher.erp.user.dto.UserRegistrationRequest;
import com.ahmed.publisher.erp.user.entity.User;

public class UserMapper {
    public static User toEntity (UserRegistrationRequest request){
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        return  user;
    }

    public static UserDto toDto (User user){
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()

        );
    }

    public static void applyUpdate (User existing, UpdateUserRequest updateRequest){
        existing.setFirstName(updateRequest.firstName());
        existing.setLastName(updateRequest.lastName());
        existing.setEmail(updateRequest.email());
        existing.setRole(updateRequest.role());
        existing.setAddresses(AddressMapper.toEntityList(updateRequest.addresses()));
    }
    public static void applyPatch (User existing, PatchUserRequest patchRequest){
        if(patchRequest.firstName()!=null) existing.setFirstName(patchRequest.firstName());
        if(patchRequest.lastName()!=null) existing.setLastName(patchRequest.lastName());
        if(patchRequest.email()!=null) existing.setEmail(patchRequest.email());
        if(patchRequest.role()!=null) existing.setRole(patchRequest.role());
        if(patchRequest.addresses()!=null) existing.setAddresses(AddressMapper.toEntityList(patchRequest.addresses()));
    }
}
