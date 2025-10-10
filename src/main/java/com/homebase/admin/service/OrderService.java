package com.homebase.admin.service;

import com.homebase.admin.dto.OrderDTO;
import com.homebase.admin.dto.OrderItemDTO;
import com.homebase.admin.entity.Order;
import com.homebase.admin.entity.OrderItem;
import com.homebase.admin.entity.TenantContext;
import com.homebase.admin.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDTO> getAllOrders() {
        String tenantId = TenantContext.getCurrentTenant();
        return orderRepository.findByTenantId(tenantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getRecentOrders(int limit) {
        String tenantId = TenantContext.getCurrentTenant();
        return orderRepository.findByTenantId(tenantId).stream()
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .limit(limit)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        String tenantId = TenantContext.getCurrentTenant();
        Order order = orderRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long id, String status) {
        String tenantId = TenantContext.getCurrentTenant();
        Order order = orderRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
        Order updated = orderRepository.save(order);
        return convertToDTO(updated);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(String.valueOf(order.getId()));
        dto.setCustomerName(order.getCustomerName());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setItems(order.getItems().stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList()));
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus().name().toLowerCase());
        dto.setCreatedAt(order.getCreatedAt() != null ? order.getCreatedAt().toString() : null);
        dto.setUpdatedAt(order.getUpdatedAt() != null ? order.getUpdatedAt().toString() : null);
        return dto;
    }

    private OrderItemDTO convertItemToDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(String.valueOf(item.getId()));
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}
