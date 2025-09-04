package com.taifex.smartorder.specification;

import com.taifex.smartorder.entity.Order;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderSpecification {

    public static Specification<Order> hasUserId(Long userId) {
        return ((root, query, cb) -> cb.equal(root.get("user").get("id"), userId));
    }

    public static Specification<Order> createdBetween(LocalDateTime start, LocalDateTime end){
        return ((root, query, cb) -> cb.between(root.get("createdAt"), start, end));
    }

    public static Specification<Order> productNameContains(String keyword) {
        return ((root, query, cb) -> cb.like(root.get("productName"), "%" + keyword + "%"));
    }
}
