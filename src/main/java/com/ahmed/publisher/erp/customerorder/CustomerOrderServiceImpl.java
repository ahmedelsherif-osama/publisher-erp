package com.ahmed.publisher.erp.customerorder;

import com.ahmed.publisher.erp.common.exceptions.ResourceNotFoundException;
import com.ahmed.publisher.erp.customerorder.dto.CreateCustomerOrderRequest;
import com.ahmed.publisher.erp.customerorder.dto.CreateCustomerOrderItemRequest;
import com.ahmed.publisher.erp.customerorder.dto.CustomerOrderDto;
import com.ahmed.publisher.erp.product.Product;
import com.ahmed.publisher.erp.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository orderRepo;
    private final ProductRepository productRepo;

    public CustomerOrderServiceImpl(CustomerOrderRepository orderRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @Override
    public CustomerOrderDto create(CreateCustomerOrderRequest request) {

        CustomerOrder order = new CustomerOrder();
        order.setCreatedAt(LocalDateTime.now());

        List<CustomerOrderItem> items = new ArrayList<>();

        for (CreateCustomerOrderItemRequest itemReq : request.items()) {
            Product product = productRepo.findById(itemReq.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            CustomerOrderItem item = new CustomerOrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.quantity());
            items.add(item);
        }

        order.setItems(items);

        CustomerOrder saved = orderRepo.save(order);

        return CustomerOrderMapper.toDto(saved);
    }

    @Override
    public CustomerOrderDto findById(UUID id) {
        CustomerOrder order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return CustomerOrderMapper.toDto(order);
    }

    @Override
    public List<CustomerOrderDto> findAll() {
        return orderRepo.findAll()
                .stream()
                .map(CustomerOrderMapper::toDto)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        orderRepo.deleteById(id);
    }
}
