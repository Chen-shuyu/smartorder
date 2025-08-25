package com.taifex.smartorder.repository;

import com.taifex.smartorder.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA 會自動實作這些方法！

    // 根據名字查找
    List<User> findByName(String name);

    // 根據 email 查找
    Optional<User> findByEmail(String email);

    // 根據年齡範圍查找
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);

    // 根據名字包含特定字串查找
    List<User> findByNameContaining(String keyword);

    // 根據年齡大於特定值查找，並按名字排序
    List<User> findByAgeGreaterThanOrderByName(Integer age);


    // 原本的
    @Query("SELECT u FROM User u JOIN FETCH u.orders")
    List<User> findAllWithOrders();


    // 新增一個 EntityGraph 版本
    @EntityGraph(attributePaths = "orders")
    List<User> findAllBy();
}
