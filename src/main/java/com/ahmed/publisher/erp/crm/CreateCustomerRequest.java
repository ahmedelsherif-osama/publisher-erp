package com.ahmed.publisher.erp.crm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public record CreateCustomerRequest(
        String name,
        String email
) {}
