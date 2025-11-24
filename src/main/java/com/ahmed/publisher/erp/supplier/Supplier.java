package com.ahmed.publisher.erp.supplier;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
public class Supplier {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;

    private String contactEmail;

    private String phone;

    private String address;

    private String tradeLicenseNumber;
}
