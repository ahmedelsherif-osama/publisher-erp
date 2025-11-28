package com.ahmed.publisher.erp.customerorder.dto;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerOrderRequest {
    private UUID customerId;
    private String currency;
    private List<CreateOrderItem> items;

}
