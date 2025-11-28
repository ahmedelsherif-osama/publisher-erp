package com.ahmed.publisher.erp.customerorder.dto;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
@Getter @Setter
public class CustomerOrderDto {
    private UUID id;
    private UUID customerId;
    private String status;
    private BigDecimal totalAmount;
}
