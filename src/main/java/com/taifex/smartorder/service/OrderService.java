package com.taifex.smartorder.service;

import com.taifex.smartorder.dto.OrderDTO;
import com.taifex.smartorder.entity.Order;
import com.taifex.smartorder.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    private OrderDTO mapToDTO(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setProductName(order.getProductName());
        dto.setAmount(order.getAmount());
        dto.setPrice(order.getPrice());
        dto.setTotal(order.getPrice()*order.getAmount());
        return dto;
    }

    private Order mapTOEntity(OrderDTO orderDTO){
        return Order.builder()
                .id(orderDTO.getId())
                .productName(orderDTO.getProductName())
                .amount(orderDTO.getAmount())
                .price(orderDTO.getPrice())
                .build();
    }

    // 新增訂單
    public OrderDTO saveOrder(OrderDTO orderDTO){
        Order order = mapTOEntity(orderDTO);
        Order saved = orderRepository.save(order);
        return mapToDTO(saved);
    }

    // 查全部
    public List<OrderDTO> getAllOrders(){
        return orderRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 查單筆
    public Optional<OrderDTO> getOrderById(Long id){
        return orderRepository.findById(id).map(this::mapToDTO);
    }

    // 更新訂單
    public Optional<OrderDTO> updateOrder(Long id, OrderDTO orderDTO){
        return orderRepository.findById(id).map(existing ->{
            existing.setProductName(orderDTO.getProductName());
            existing.setAmount(orderDTO.getAmount());
            existing.setPrice(orderDTO.getPrice());
            return mapToDTO(orderRepository.save(existing));
        });
    }

    // 刪除訂單
    public boolean deleteOrder(Long id){
        if(orderRepository.existsById(id)){
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
