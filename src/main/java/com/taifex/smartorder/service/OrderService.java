package com.taifex.smartorder.service;

import com.taifex.smartorder.dto.OrderDTO;
import com.taifex.smartorder.entity.Order;
import com.taifex.smartorder.entity.User;
import com.taifex.smartorder.exception.ResourceNotFoundException;
import com.taifex.smartorder.repository.OrderRepository;
import com.taifex.smartorder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    private OrderDTO mapToDTO(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setProductName(order.getProductName());
        dto.setAmount(order.getAmount());
        dto.setPrice(order.getPrice());
        dto.setTotal(order.getPrice()*order.getAmount());
        dto.setUserId(order.getUser().getId());
        return dto;
    }

    private Order mapTOEntity(OrderDTO orderDTO, User user){
        return Order.builder()
                .id(orderDTO.getId())
                .productName(orderDTO.getProductName())
                .amount(orderDTO.getAmount())
                .price(orderDTO.getPrice())
                .user(user)
                .build();
    }

    // 新增訂單
    public OrderDTO saveOrder(OrderDTO orderDTO){
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + orderDTO.getUserId()));
        Order order = mapTOEntity(orderDTO, user);
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

    // 查詢使用者的所有訂單
    public List<OrderDTO> getOrdersByUserId(Long id){
        List<Order> orders = orderRepository.findByUserId(id);
        return orders.stream().map(this::mapToDTO).toList();
    }
}
