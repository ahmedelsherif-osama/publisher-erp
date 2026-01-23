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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

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
        log.info("Creating order for userId={}, number of items={}", userId, items.size());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id={}", userId);
                    return new ResourceNotFoundException("User not found with id " + userId);
                });

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest req : items) {
            Publication publication = publicationRepository.findById(req.publicationId())
                    .orElseThrow(() -> {
                        log.error("Publication not found with id={}", req.publicationId());
                        return new ResourceNotFoundException(
                                "Publication not found with id " + req.publicationId()
                        );
                    });

            inventoryService.adjustStock(
                    req.publicationId(),
                    req.quantity(),
                    "ORDER_CREATED",
                    null
            );
            log.debug("Stock adjusted for publicationId={}, quantity={}", req.publicationId(), req.quantity());

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setPublication(publication);
            item.setQuantity(req.quantity());
            orderItems.add(item);
        }

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with id={}, total items={}", savedOrder.getId(), savedOrder.getItems().size());
        return OrderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        log.info("Fetching all orders");
        List<OrderResponse> responses = orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
        log.info("Fetched {} orders", responses.size());
        return responses;
    }

    @Override
    public void cancel(UUID orderId) {
        log.info("Cancelling order with id={}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with id={}", orderId);
                    return new ResourceNotFoundException("Order not found with id " + orderId);
                });

        if (order.getStatus() != OrderStatus.CREATED) {
            log.warn("Cannot cancel order with id={}, status={}", orderId, order.getStatus());
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
            log.debug("Rolled back stock for publicationId={}, quantity={}", item.getPublication().getId(), item.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        log.info("Order cancelled successfully with id={}", orderId);
    }

    @Override
    public void complete(UUID orderId) {
        log.info("Completing order with id={}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with id={}", orderId);
                    return new ResourceNotFoundException("Order not found with id " + orderId);
                });

        if (order.getStatus() != OrderStatus.CREATED) {
            log.warn("Cannot complete order with id={}, status={}", orderId, order.getStatus());
            throw new BusinessRuleViolationException(
                    "Only orders with status CREATED can be completed"
            );
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        log.info("Order completed successfully with id={}", orderId);
    }
}
