package com.ahmed.publisher.erp.order;

import com.ahmed.publisher.erp.inventory.InventoryService;
import com.ahmed.publisher.erp.order.dto.OrderItemRequest;
import com.ahmed.publisher.erp.order.dto.OrderResponse;
import com.ahmed.publisher.erp.publication.Publication;
import com.ahmed.publisher.erp.publication.PublicationRepository;
import com.ahmed.publisher.erp.user.CurrentUserService;
import com.ahmed.publisher.erp.user.User;
import com.ahmed.publisher.erp.user.UserRepository;
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

    public OrderServiceImpl(OrderRepository orderRepository,
                            CurrentUserService currentUserService,
                            UserRepository userRepository,
                            PublicationRepository publicationRepository,
                            InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
        this.inventoryService = inventoryService;
        this.currentUserService = currentUserService;
    }

    @Transactional
    @Override
    public OrderResponse create(List<OrderItemRequest> items) {
        UUID userId = currentUserService.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest req : items) {
            Publication publication = publicationRepository.findById(req.publicationId())
                    .orElseThrow(() -> new IllegalArgumentException("Publication not found"));

            inventoryService.adjustStock(req.publicationId(), req.quantity(), "ORDER_CREATED", null);

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
    @Transactional
    public void cancel(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Only CREATED orders can be cancelled");
        }

        // Roll back inventory
        for (OrderItem item : order.getItems()) {
            UUID publicationId = item.getPublication().getId();
            int quantity = item.getQuantity();

            inventoryService.adjustStock(publicationId, quantity, "ORDER_CANCELLED", order.getId());
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void complete(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException(
                    "Only CREATED orders can be completed"
            );
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }


}
