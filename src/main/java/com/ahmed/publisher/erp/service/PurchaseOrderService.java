package com.ahmed.publisher.erp.service;

import com.ahmed.publisher.erp.entity.Order;
import com.ahmed.publisher.erp.repository.PurchaseOrderRepository;
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
