package com.ahmed.publisher.erp.purchaseorder;

import com.ahmed.publisher.erp.customerorder.CustomerOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository){
        this.purchaseOrderRepository=purchaseOrderRepository;
    }

    public List<CustomerOrder> findAll(){
        return purchaseOrderRepository.findAll();
    }

    public CustomerOrder findById(UUID id){
        return purchaseOrderRepository.findById(id).orElse(null);
    }

    public CustomerOrder save(CustomerOrder order){
        return purchaseOrderRepository.save(order);
    }
}
