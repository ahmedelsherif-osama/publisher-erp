package com.ahmed.publisher.erp.crm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name="customers")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String email;
}
