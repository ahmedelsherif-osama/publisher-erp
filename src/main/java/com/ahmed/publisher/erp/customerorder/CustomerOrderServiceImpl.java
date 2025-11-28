package com.ahmed.publisher.erp.customerorder;

import com.ahmed.publisher.erp.common.exceptions.ResourceNotFoundException;
import com.ahmed.publisher.erp.customerorder.dto.CreateCustomerOrderRequest;
import com.ahmed.publisher.erp.customerorder.dto.CreateCustomerOrderItemRequest;
import com.ahmed.publisher.erp.customerorder.dto.CreateOrderItem;
import com.ahmed.publisher.erp.customerorder.dto.CustomerOrderDto;
import com.ahmed.publisher.erp.product.Product;
import com.ahmed.publisher.erp.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setCustomerId(request.getCustomerId());
        order.setCurrency(request.getCurrency());

        List<CustomerOrderItem> items = new ArrayList<>();

        for (CreateOrderItem itemReq : request.getItems()) {
            Product product = productRepo.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            CustomerOrderItem item = new CustomerOrderItem();
            item.setSku(product.getSku());

            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(product.getPrice());
            item.setLineTotal(product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())) );
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

//    @Override
//    public void delete(UUID id) {
//        orderRepo.deleteById(id);
//    }
}
