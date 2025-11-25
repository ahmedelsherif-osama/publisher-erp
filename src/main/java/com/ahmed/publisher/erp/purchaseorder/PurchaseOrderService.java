package com.ahmed.publisher.erp.purchaseorder;

import com.ahmed.publisher.erp.order.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository){
        this.purchaseOrderRepository=purchaseOrderRepository;
    }

    public List<Order> findAll(){
        return purchaseOrderRepository.findAll();
    }

    public Order findById(UUID id){
        return purchaseOrderRepository.findById(id).orElse(null);
    }

    public Order save(Order order){
        return purchaseOrderRepository.save(order);
    }
}
