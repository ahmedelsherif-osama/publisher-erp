package com.ahmed.publisher.erp.user.mapper;

import com.ahmed.publisher.erp.user.dto.AddressDto;
import com.ahmed.publisher.erp.user.entity.Address;

import java.util.List;
import java.util.stream.Collectors;

public class AddressMapper {

    public static AddressDto toDto(Address address) {
        if (address == null) return null;
        return new AddressDto(
                address.getId(),
                address.getBuildingNumber(),
                address.getStreet(),
                address.getArea(),
                address.getCity(),
                address.getCountryCode(),
                address.getPostCode(),
                address.getExtraDetails(),
                address.isMainAddress()
        );
    }

    public static Address toEntity(AddressDto dto) {
        if (dto == null) return null;
        Address address = new Address();
        address.setId(dto.id());
        address.setBuildingNumber(dto.buildingNumber());
        address.setStreet(dto.street());
        address.setArea(dto.area());
        address.setCity(dto.city());
        address.setCountryCode(dto.countryCode());
        address.setPostCode(dto.postCode());
        address.setExtraDetails(dto.extraDetails());
        address.setMainAddress(dto.isMainAddress());
        return address;
    }

    public static List<AddressDto> toDtoList(List<Address> addresses) {
        return addresses.stream()
                .map(AddressMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Address> toEntityList(List<AddressDto> dtos) {
        return dtos.stream()
                .map(AddressMapper::toEntity)
                .collect(Collectors.toList());
    }
}
