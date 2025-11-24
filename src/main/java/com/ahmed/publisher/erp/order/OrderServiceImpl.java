package com.ahmed.publisher.erp.order;

import com.ahmed.publisher.erp.common.exceptions.ResourceNotFoundException;
import com.ahmed.publisher.erp.order.dto.CreateOrderRequest;
import com.ahmed.publisher.erp.order.dto.CreateOrderItemRequest;
import com.ahmed.publisher.erp.order.dto.OrderDto;
import com.ahmed.publisher.erp.product.Product;
import com.ahmed.publisher.erp.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public OrderServiceImpl(OrderRepository orderRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @Override
    public OrderDto create(CreateOrderRequest request) {

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();

        for (CreateOrderItemRequest itemReq : request.items()) {
            Product product = productRepo.findById(itemReq.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.quantity());
            items.add(item);
        }

        order.setItems(items);

        Order saved = orderRepo.save(order);

        return OrderMapper.toDto(saved);
    }

    @Override
    public OrderDto findById(UUID id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return OrderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> findAll() {
        return orderRepo.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        orderRepo.deleteById(id);
    }
}
