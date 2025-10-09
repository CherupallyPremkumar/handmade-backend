package com.homebase.admin.service;

import com.homebase.admin.dto.OrderDTO;
import com.homebase.admin.dto.OrderItemDTO;
import com.homebase.admin.entity.Order;
import com.homebase.admin.entity.OrderItem;
import com.homebase.admin.entity.TenantContext;
import com.homebase.admin.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

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
    public OrderDTO createOrder(OrderDTO orderDTO) {
        String tenantId = TenantContext.getCurrentTenant();
        
        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setCustomerEmail(orderDTO.getCustomerEmail());
        order.setCustomerPhone(orderDTO.getCustomerPhone());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setSubtotal(orderDTO.getSubtotal());
        order.setTax(orderDTO.getTax());
        order.setShippingCost(orderDTO.getShippingCost());
        order.setTotal(orderDTO.getTotal());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setPaymentStatus("PENDING");
        order.setTenantId(tenantId);
        
        // Convert items
        List<OrderItem> items = orderDTO.getItems().stream()
                .map(itemDTO -> {
                    OrderItem item = new OrderItem();
                    item.setProductId(itemDTO.getProductId());
                    item.setProductName(itemDTO.getProductName());
                    item.setQuantity(itemDTO.getQuantity());
                    item.setPrice(itemDTO.getPrice());
                    return item;
                })
                .collect(Collectors.toList());
        
        order.setItems(items);
        
        Order saved = orderRepository.save(order);
        return convertToDTO(saved);
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
    
    @Transactional
    public OrderDTO updatePaymentStatus(Long id, String paymentStatus, String transactionId) {
        String tenantId = TenantContext.getCurrentTenant();
        Order order = orderRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentStatus(paymentStatus);
        order.setTransactionId(transactionId);
        
        if ("PAID".equals(paymentStatus)) {
            order.setStatus(Order.OrderStatus.PROCESSING);
        }
        
        Order updated = orderRepository.save(order);
        return convertToDTO(updated);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(String.valueOf(order.getId()));
        dto.setCustomerName(order.getCustomerName());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setCustomerPhone(order.getCustomerPhone());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setItems(order.getItems().stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList()));
        dto.setSubtotal(order.getSubtotal());
        dto.setTax(order.getTax());
        dto.setShippingCost(order.getShippingCost());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus().name().toLowerCase());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setTransactionId(order.getTransactionId());
        dto.setCreatedAt(order.getCreatedAt() != null ? order.getCreatedAt().toString() : null);
        dto.setUpdatedAt(order.getUpdatedAt() != null ? order.getUpdatedAt().toString() : null);
        return dto;
    }

    private OrderItemDTO convertItemToDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setSubtotal(item.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())));
        return dto;
    }
}
