// customerorder/CustomerOrderRepository.java
package com.ahmed.publisher.erp.customerorder;
import com.ahmed.publisher.erp.customerorder.dto.CreateCustomerOrderRequest;
import com.ahmed.publisher.erp.customerorder.dto.CustomerOrderDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;


// simple mapper (or use MapStruct)
public class CustomerOrderMapper {
    public static CustomerOrder toEntity(CreateCustomerOrderRequest r) {
        CustomerOrder o = new CustomerOrder();
        o.setCustomerId(r.getCustomerId());
        o.setCurrency(r.getCurrency());
        o.setCreatedAt(LocalDateTime.now());
        o.setStatus(OrderStatus.CREATED);
        for (var it : r.getItems()) {
            CustomerOrderItem i = new CustomerOrderItem();
            i.setProductId(it.getProductId());
            i.setSku(it.getSku());
            i.setQuantity(it.getQuantity());
            // unitPrice & lineTotal will be enriched from product service
            o.getItems().add(i);
        }
        return o;
    }

    public static CustomerOrderDto toDto(CustomerOrder o) {
        CustomerOrderDto dto = new CustomerOrderDto();
        dto.setId(o.getId());
        dto.setCustomerId(o.getCustomerId());
        dto.setStatus(o.getStatus().name());
        dto.setTotalAmount(o.getTotalAmount());
        return dto;
    }

}
