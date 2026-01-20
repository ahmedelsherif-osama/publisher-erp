package com.ahmed.publisher.erp.order.service;

import com.ahmed.publisher.erp.exceptions.business.BusinessRuleViolationException;
import com.ahmed.publisher.erp.exceptions.http.ResourceNotFoundException;
import com.ahmed.publisher.erp.inventory.service.InventoryService;
import com.ahmed.publisher.erp.order.dto.OrderItemRequest;
import com.ahmed.publisher.erp.order.dto.OrderResponse;
import com.ahmed.publisher.erp.order.entity.Order;
import com.ahmed.publisher.erp.order.entity.OrderItem;
import com.ahmed.publisher.erp.order.entity.OrderStatus;
import com.ahmed.publisher.erp.order.mapper.OrderMapper;
import com.ahmed.publisher.erp.order.repository.OrderRepository;
import com.ahmed.publisher.erp.publication.entity.Publication;
import com.ahmed.publisher.erp.publication.repository.PublicationRepository;
import com.ahmed.publisher.erp.user.service.CurrentUserService;
import com.ahmed.publisher.erp.user.entity.User;
import com.ahmed.publisher.erp.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;
    private final InventoryService inventoryService;
    private final CurrentUserService currentUserService;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            CurrentUserService currentUserService,
            UserRepository userRepository,
            PublicationRepository publicationRepository,
            InventoryService inventoryService
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
        this.inventoryService = inventoryService;
        this.currentUserService = currentUserService;
    }

    @Override
    public OrderResponse create(List<OrderItemRequest> items) {
        UUID userId = currentUserService.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + userId)
                );

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest req : items) {
            Publication publication = publicationRepository.findById(req.publicationId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Publication not found with id " + req.publicationId()
                            )
                    );

            inventoryService.adjustStock(
                    req.publicationId(),
                    req.quantity(),
                    "ORDER_CREATED",
                    null
            );

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setPublication(publication);
            item.setQuantity(req.quantity());

            orderItems.add(item);
        }

        order.setItems(orderItems);
        return OrderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    @Override
    public void cancel(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id " + orderId)
                );

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BusinessRuleViolationException(
                    "Only orders with status CREATED can be cancelled"
            );
        }

        // rollback inventory
        for (OrderItem item : order.getItems()) {
            inventoryService.adjustStock(
                    item.getPublication().getId(),
                    item.getQuantity(),
                    "ORDER_CANCELLED",
                    order.getId()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public void complete(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id " + orderId)
                );

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BusinessRuleViolationException(
                    "Only orders with status CREATED can be completed"
            );
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }
}
